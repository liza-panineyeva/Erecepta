package com.example.ukasz.erecepta.model;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-08.
 */

public class Drug extends RealmObject {

    private int idDrug;
    private String name;
    private int volume;
    private Date marketingAuthorisationDate;
    private Date withdrawalDate;
    private int authorisationNumber;
    private double governmentPrice;
    private boolean isRefundable;
    private RealmList<RealmDrugFormulation> drugFormulations;
    private RealmList<RealmPaymentLevel> availablePaymentLevels;
    private RealmList<DrugsIngredient> drugsIngredients = new RealmList<>();
    private RealmList<Prescription> prescriptionsWithDrug;
    private RealmList<Disease> diseases = new RealmList<>();

    public Drug() {
        availablePaymentLevels = new RealmList<RealmPaymentLevel>();
    }

    public int getIdDrug() {
        return idDrug;
    }

    public void setIdDrug(int idDrug) {
        idDrug = idDrug;
    }

    public void addPrescription(Prescription p) {
        prescriptionsWithDrug.add(p);
    }

    public RealmList<Prescription> getPrescriptionsWithDrug() {
        return prescriptionsWithDrug;
    }

    public void setPrescriptionsWithDrug(RealmList<Prescription> prescriptionsWithDrug) {
        this.prescriptionsWithDrug = prescriptionsWithDrug;
    }

    public RealmList<DrugsIngredient> getDrugsIngredients() {
        return drugsIngredients;
    }

    public void addIngredient(DrugsIngredient drugsIngredient) {
        drugsIngredients.add(drugsIngredient);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPaymentLevel (RealmPaymentLevel level) {
        availablePaymentLevels.add(level);
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Date getMarketingAuthorisationDate() {
        return marketingAuthorisationDate;
    }

    public void setMarketingAuthorisationDate(Date marketingAuthorisationDate) {
        this.marketingAuthorisationDate = marketingAuthorisationDate;
    }

    public Date getWithdrawalDate() {
        return withdrawalDate;
    }

    public void setWithdrawalDate(Date withdrawalDate) {
        this.withdrawalDate = withdrawalDate;
    }

    public int getAuthorisationNumber() {
        return authorisationNumber;
    }

    public void setAuthorisationNumber(int authorisationNumber) {
        this.authorisationNumber = authorisationNumber;
    }

    public double getGovernmentPrice() {
        return governmentPrice;
    }

    public void setGovernmentPrice(double governmentPrice) {
        this.governmentPrice = governmentPrice;
    }

    public boolean isRefundable() {
        return isRefundable;
    }

    public void setRefundable(boolean refundable) {
        isRefundable = refundable;
    }

    public RealmList<RealmPaymentLevel> getAvailablePaymentLevels() {
        return availablePaymentLevels;
    }

    public void setAvailablePaymentLevels(RealmList<RealmPaymentLevel> availablePaymentLevels) {
        this.availablePaymentLevels = availablePaymentLevels;
    }

    public RealmList<RealmDrugFormulation> getDrugFormulations() {
        return drugFormulations;
    }

    public void setDrugFormulations(RealmList<RealmDrugFormulation> drugFormulations) {
        this.drugFormulations = drugFormulations;
    }

    public void addDisease(Disease disease) {
        this.diseases.add(disease);
    }
}
