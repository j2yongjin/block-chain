package com.daou.books.core.service;

import com.daou.books.core.config.SupplierConfig;
import com.daou.books.core.domain.Company;
import com.daou.books.core.domain.User;
import com.daou.books.core.domain.model.BlockchainUserModel;
import com.daou.books.core.domain.model.PageModel;
import com.daou.books.core.domain.model.UserModel;
import com.daou.books.core.repository.CompanyRepository;
import com.daou.books.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserSerivceImpl implements UserSerivce {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    @Transactional(readOnly = true)
    public PageModel<UserModel> getAllUsers(Pageable pageable, Long companyId) {
        Company company = companyRepository.findOne(companyId);
        Page<User> users = userRepository.findByCompany(company, pageable);

        List<UserModel> models = Lists.newArrayList();
        for(User user: users) {
            models.add(new UserModel(user));
        }

        return new PageModel(models, users);
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<UserModel> getUsers(Pageable pageable, Long companyId) {
        Company company = companyRepository.findOne(companyId);
        Page<User> users = userRepository.findByCompanyAndRole(company, User.UserRole.USER, pageable);

        List<UserModel> models = Lists.newArrayList();
        for(User user: users) {
            models.add(new UserModel(user));
        }

        return new PageModel(models, users);
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<UserModel> getAdmins(Pageable pageable, Long companyId) {
        Company company = companyRepository.findOne(companyId);
        Page<User> users = userRepository.findByCompanyAndRole(company, User.UserRole.ADMIN, pageable);

        List<UserModel> models = Lists.newArrayList();
        for(User user: users) {
            models.add(new UserModel(user));
        }

        return new PageModel(models, users);
    }

    @Override
    @Transactional(readOnly = true)
    public PageModel<UserModel> getAllAdmins(Pageable pageable) {

        Page<User> users = userRepository.findByRole(User.UserRole.ADMIN, pageable);

        List<UserModel> models = Lists.newArrayList();
        for(User user: users) {
            models.add(new UserModel(user));
        }

        return new PageModel(models, users);
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel getUser(Long userId) {
        return new UserModel(userRepository.findOne(userId));
    }

    @Override
    @Transactional
    public UserModel addUser(User user, User.UserRole role) {

        // 1. DB insert
        user.setRole(role);
        UserModel model =  new UserModel(userRepository.save(user));

        // 2. chaincode에 user 등록
        try {
            BlockChainUserService blockChainUserService = new BlockChainUserServiceImpl(SupplierConfig.CA_ORG1_URL);
            if(User.UserRole.USER.equals(role)) {
                // 일반 user로 등록
                // 1. admin 조회
                String adminName = "admin";
                String adminPw = "adminpw";
                BlockchainUserModel bCLoginAdmin = new BlockchainUserModel();
                bCLoginAdmin.setAffiliation(SupplierConfig.ORG1);
                bCLoginAdmin.setMspId(SupplierConfig.ORG1_MSP);
                bCLoginAdmin.setName(adminName);

                BlockchainUserModel bCAdmin;
                bCAdmin = blockChainUserService.enrollAdminUser(bCLoginAdmin, adminName, adminPw);

                // 2. user 등록
                BlockchainUserModel blockChainUser = new BlockchainUserModel();
                blockChainUser.setName(user.getLoginId());
                blockChainUser.setAffiliation(SupplierConfig.ORG1);
                blockChainUser.setMspId(SupplierConfig.ORG1_MSP);

                blockChainUserService.enrollUser(blockChainUser, bCAdmin);
            } else {
                // admin으로 등록
                BlockchainUserModel newBCAdmin = new BlockchainUserModel();
                newBCAdmin.setAffiliation(SupplierConfig.ORG1);
                newBCAdmin.setMspId(SupplierConfig.ORG1_MSP);
                newBCAdmin.setName(user.getLoginId());

                BlockchainUserModel bCAdmin;
                bCAdmin = blockChainUserService.enrollAdminUser(newBCAdmin, user.getLoginId(), user.getPassword());
            }
        } catch (Exception e) {

        }

        return model;
    }

//    @Override
//    @Transactional
//    public UserModel addUser(User user, User.UserRole role) {
//
//        // 1. DB insert
//        user.setRole(role);
//        UserModel model =  new UserModel(userRepository.save(user));
//
//        // 2. chaincode에 user 등록
//        try {
//            BlockChainUserService blockChainUserService = new BlockChainUserServiceImpl(SupplierConfig.CA_ORG1_URL);
//            if(User.UserRole.USER.equals(role)) {
//                // 일반 user로 등록
//                // 1. admin 조회
//                String adminName = "admin";
//                String adminPw = "adminpw";
//                BlockchainUserModel bCLoginAdmin = new BlockchainUserModel();
//                bCLoginAdmin.setAffiliation(SupplierConfig.ORG1);
//                bCLoginAdmin.setMspId(SupplierConfig.ORG1_MSP);
//                bCLoginAdmin.setName(adminName);
//
//                BlockchainUserModel bCAdmin;
//                bCAdmin = blockChainUserService.enrollAdminUser(bCLoginAdmin, adminName, adminPw);
//
//                // 2. user 등록
//                BlockchainUserModel blockChainUser = new BlockchainUserModel();
//                blockChainUser.setName(user.getLoginId());
//                blockChainUser.setAffiliation(SupplierConfig.ORG1);
//                blockChainUser.setMspId(SupplierConfig.ORG1_MSP);
//
//                blockChainUserService.enrollUser(blockChainUser, bCAdmin);
//            } else {
//                // admin으로 등록
//                BlockchainUserModel newBCAdmin = new BlockchainUserModel();
//                newBCAdmin.setAffiliation(SupplierConfig.ORG1);
//                newBCAdmin.setMspId(SupplierConfig.ORG1_MSP);
//                newBCAdmin.setName(user.getLoginId());
//
//                BlockchainUserModel bCAdmin;
//                bCAdmin = blockChainUserService.enrollAdminUser(newBCAdmin, user.getLoginId(), user.getPassword());
//            }
//        } catch (Exception e) {
//
//        }
//
//        return model;
//    }

}
