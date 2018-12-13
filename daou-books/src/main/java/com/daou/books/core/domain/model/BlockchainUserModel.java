package com.daou.books.core.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.io.Serializable;
import java.util.Set;

@Data
@Getter
@Setter
public class BlockchainUserModel implements User, Serializable {

    protected String name;
    protected Set<String> roles;
    protected String account;
    protected String affiliation;
    protected Enrollment enrollment;
    protected String mspId;

}
