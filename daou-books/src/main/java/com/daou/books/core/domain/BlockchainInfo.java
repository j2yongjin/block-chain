package com.daou.books.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "block_chain_Infos")
@Getter
@Setter
public class BlockchainInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    protected String name;

    @Column(name = "blockRoles")
    @ElementCollection
    @CollectionTable(name = "block_chain_Info_role")
    protected Set<String> roles;

    @Column
    protected String account;

    @Column
    protected String affiliation;

    @Column
    protected String enrollment;

    @Column
    protected String mspId;

    @Column
    private Date createdAt;

    @Column
    private Date updatedAt;

}
