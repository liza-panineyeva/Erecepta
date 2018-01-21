package com.example.ukasz.erecepta.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-06.
 */

public class Nurse extends RealmObject {

    private User user;
    private String pwzNumber;
    private String pwzStatus;

    //optional
    private String PhoneNumber;
    private RealmList<Prescription> prescriptions;

    public Nurse() {}
    public Nurse(User user, String pwzNumber) {
        this.user = user;
        this.pwzNumber = pwzNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPwzNumber() {
        return pwzNumber;
    }

    public void setPwzNumber(String pwzNumber) {
        this.pwzNumber = pwzNumber;
    }

    public String getPwzStatus() {
        return pwzStatus;
    }

    public void setPwzStatus(String pwzStatus) {
        this.pwzStatus = pwzStatus;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public RealmList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(RealmList<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}
