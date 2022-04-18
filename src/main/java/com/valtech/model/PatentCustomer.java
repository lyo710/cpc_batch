package com.valtech.model;

import java.io.File;
import java.util.List;

public class PatentCustomer {

    String customerName;
    String customerSerial;
    String customerAddress;
    String customerZipCode;
    String contactName;
    String contactMobile;

    List<PatentCase> patentCaseList;

    List<File> picFiles;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSerial() {
        return customerSerial;
    }

    public void setCustomerSerial(String customerSerial) {
        this.customerSerial = customerSerial;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerZipCode() {
        return customerZipCode;
    }

    public void setCustomerZipCode(String customerZipCode) {
        this.customerZipCode = customerZipCode;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public List<PatentCase> getPatentCaseList() {
        return patentCaseList;
    }

    public void setPatentCaseList(List<PatentCase> patentCaseList) {
        this.patentCaseList = patentCaseList;
    }

    public List<File> getPicFiles() {
        return picFiles;
    }

    public void setPicFiles(List<File> picFiles) {
        this.picFiles = picFiles;
    }
}
