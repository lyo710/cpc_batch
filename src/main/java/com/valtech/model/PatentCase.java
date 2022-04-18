package com.valtech.model;

public class PatentCase {
    String sqSerial;
    String sqName;

    public PatentCase() {
    }

    public PatentCase(String sqSerial, String sqName) {
        this.sqSerial = sqSerial;
        this.sqName = sqName;
    }

    public String getSqSerial() {
        return sqSerial;
    }

    public void setSqSerial(String sqSerial) {
        this.sqSerial = sqSerial;
    }

    public String getSqName() {
        return sqName;
    }

    public void setSqName(String sqName) {
        this.sqName = sqName;
    }
}
