package com.example.ukasz.erecepta.model;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Agnieszka on 2018-01-06.
 */

public class Prescription extends RealmObject {

    private Patient patient;
    private String prescriptionNumber;
    private String nfzNumber;
    private Date prescriptionDate;

    //optional
    private Nurse nursePrescribing;
    private Physician physicianPrescribing;
    private Pharmacist pharmacistPrescribing;
    private Pharmacist pharmacistRealizing;
    private Date realizationDate;
    private Date realizationDateFrom;
    private Drug drug;

    public Prescription() {}
    public Prescription(Patient patient, String prescriptionNumber, String NFZNumber, Date prescriptionDate) {
        this.patient = patient;
        this.prescriptionNumber = prescriptionNumber;
        this.nfzNumber = NFZNumber;
        this.prescriptionDate = prescriptionDate;
    }

    public static int realisedPrecsriptionsCount(Realm realm) {
        RealmResults<Prescription> prescriptions = realm.where(Prescription.class).isNotNull("pharmacistRealizing").findAll();
        return prescriptions.size();
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getPrescriptionNumber() {
        return prescriptionNumber;
    }

    public void setPrescriptionNumber(String prescriptionNumber) {
        this.prescriptionNumber = prescriptionNumber;
    }

    public String getNfzNumber() {
        return nfzNumber;
    }

    public void setNfzNumber(String nfzNumber) {
        this.nfzNumber = nfzNumber;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public Nurse getNursePrescribing() {
        return nursePrescribing;
    }

    public void setNursePrescribing(Nurse nursePrescribing) {
        this.nursePrescribing = nursePrescribing;
    }

    public Physician getPhysicianPrescribing() {
        return physicianPrescribing;
    }

    public void setPhysicianPrescribing(Physician physicianPrescribing) {
        this.physicianPrescribing = physicianPrescribing;
    }

    public Pharmacist getPharmacistPrescribing() {
        return pharmacistPrescribing;
    }

    public void setPharmacistPrescribing(Pharmacist pharmacistPrescribing) {
        this.pharmacistPrescribing = pharmacistPrescribing;
    }

    public Pharmacist getPharmacistRealizing() {
        return pharmacistRealizing;
    }

    public void setPharmacistRealizing(Pharmacist pharmacistRealizing) {
        this.pharmacistRealizing = pharmacistRealizing;
    }

    public Date getRealizationDate() {
        return realizationDate;
    }


    public void setRealizationDate(Date realizationDate) throws Exception {
        if (realizationDate.getTime() - new Date().getTime() > 0 || (realizationDate.getTime() - this.prescriptionDate.getTime() < 0)) {
            this.realizationDate = realizationDate;
        } else {
            throw new Exception("Data realizacji recepty nie może być późniejsza niż dzisiejszy dzień");
        }

    }

    public Date getRealizationDateFrom() {
        return realizationDateFrom;
    }

    public void setRealizationDateFrom(Date realizationDateFrom) {
        this.realizationDateFrom = realizationDateFrom;
    }

    public void addDrug(Drug drug) {
        this.setDrug(drug);
        drug.getPrescriptionsWithDrug().add(this);
    }
}

