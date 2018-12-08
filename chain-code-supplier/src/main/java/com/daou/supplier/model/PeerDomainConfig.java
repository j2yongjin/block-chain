package com.daou.supplier.model;

/**
 * Created by yjlee on 2018-12-04.
 */
public class PeerDomainConfig {

    String org;
    String msp;
    String peerOrg;
    String peerOrgUrl;
    String ca;

    public PeerDomainConfig(String org, String msp, String peerOrg, String peerOrgUrl, String ca) {
        this.org = org;
        this.msp = msp;
        this.peerOrg = peerOrg;
        this.peerOrgUrl = peerOrgUrl;
        this.ca = ca;
    }

    public String getOrg() {
        return org;
    }

    public String getMsp() {
        return msp;
    }

    public String getPeerOrg() {
        return peerOrg;
    }

    public String getPeerOrgUrl() {
        return peerOrgUrl;
    }

    public String getCa() {
        return ca;
    }
}
