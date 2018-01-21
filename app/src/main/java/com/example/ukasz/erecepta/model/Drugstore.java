package com.example.ukasz.erecepta.model;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-08.
 */

public class Drugstore extends RealmObject {

    private String name;
    private String concessionNumber;
    private Date concessionDate;
    private Date concessionCancelDate;
    private RealmList<PharmacistsEmployment> pharmacistEmployments;

    public Drugstore() {
        pharmacistEmployments = new RealmList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getConcessionNumber() {
        return concessionNumber;
    }

    public void setConcessionNumber(String concessionNumber) {
        this.concessionNumber = concessionNumber;
    }

    public RealmList<PharmacistsEmployment> getPharmacistEmployments() {
        return pharmacistEmployments;
    }

    public void setPharmacistEmployments(RealmList<PharmacistsEmployment> pharmacistEmployments) {
        this.pharmacistEmployments = pharmacistEmployments;
    }

    public Date getConcessionDate() {
        return concessionDate;
    }

    public void setConcessionDate(Date concessionDate) {
        this.concessionDate = concessionDate;
    }

    public Date getConcessionCancelDate() {
        return concessionCancelDate;
    }

    public void setConcessionCancelDate(Date concessionCancelDate) {
        this.concessionCancelDate = concessionCancelDate;
    }

    public void employ(Realm realm, Pharmacist pharmacist, Date employmentDate) {

        PharmacistsEmployment employment = realm.createObject(PharmacistsEmployment.class);
        employment.setEmploymentDate(employmentDate);
        employment.setDrugstore(this);
        employment.setPharmacist(pharmacist);
        pharmacist.getEmployments().add(employment);
        getPharmacistEmployments().add(employment);

    }
}
