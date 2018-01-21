package com.example.ukasz.erecepta.model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-08.
 */

public class PharmacistsEmployment extends RealmObject {

    private Pharmacist pharmacist;
    private Drugstore drugstore;
    private Date employmentDate;
    private Date dismissalDate;
    private long employmentTime;

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    public Drugstore getDrugstore() {
        return drugstore;
    }

    public void setDrugstore(Drugstore drugstore) {
        this.drugstore = drugstore;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public Date getDismissalDate() {
        return dismissalDate;
    }

    public void setDismissalDate(Date dismissalDate) {
        this.dismissalDate = dismissalDate;
    }

    public void calculateEmploymentTime() {
        if (dismissalDate == null) {
            employmentTime = Math.abs(new Date().getTime() - getEmploymentDate().getTime());
        } else {
            employmentTime = Math.abs(getDismissalDate().getTime() - getEmploymentDate().getTime());
        }
    }
}
