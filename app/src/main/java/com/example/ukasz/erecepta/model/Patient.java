package com.example.ukasz.erecepta.model;

import java.util.UUID;
import java.util.regex.Pattern;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-06.
 */

public class Patient extends RealmObject {

    private String id = UUID.randomUUID().toString();
    private User user;
    private String insuranceState;
    private String street;
    private String buildingNumber;
    private String city;
    private String voivodeship;
    private String gmina;
    private RealmAdditionalIssueKey issueKey;

    //for queries
    private long costOfDrugs = 0;

    //optional
    private RealmList<Prescription> prescriptions;
    private String PhoneNumber;
    private String LocalNumber;

    public Patient () {}


    public Patient(User user, String insuranceState, String street, String buildingNumber, String city, String voivodeship, String gmina) {
        this.user = user;
        this.insuranceState = insuranceState;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.city = city;
        this.voivodeship = voivodeship;
        this.gmina = gmina;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCostOfDrugs() {
        return costOfDrugs;
    }

    public void setCostOfDrugs(long costOfDrugs) {
        this.costOfDrugs = costOfDrugs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) throws Exception {
        if(user.getUserType().getEnum() == UserType.PATIENT)
            this.user = user;
        else throw new Exception("Podany typ uzytkownika jest nieprawidłowy:!");
    }

    public String getInsuranceState() {
        return insuranceState;
    }

    public void setInsuranceState(String insuranceState) throws Exception{
        if(insuranceState.length() <= 50)
        this.insuranceState = insuranceState;
        else throw new Exception("Nazwa uzbezpieczenia musi być mniejsza niż 50 znaków:!");
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) throws Exception {
        if(street.length() <= 50)
        this.street = street;
        else throw new Exception("Podana nazwa ulicy jest zbyt długa!");
    }

    public String getBuildingNumber(){

        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) throws Exception{
        if(buildingNumber.length() <= 5)
        this.buildingNumber = buildingNumber;
        else throw new Exception("Podany number budynku jest zbyt długi");
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) throws Exception {
        if(city.length() <= 50)
        this.city = city;
        else throw new Exception("Podana nazwa ulicy jest zbyt długa!");
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) throws Exception{
        if(voivodeship.length() <= 50)
        this.voivodeship = voivodeship;
        else throw new Exception("Podana nazwa województwa jest zbyt długa!");
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) throws Exception{
        if(gmina.length() <= 50)
        this.gmina = gmina;
        else throw new Exception("Podana nazwa gminy jest zbyt długa!");
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception{

        if(Pattern.matches("\\d{9}|\\d{3} \\d{3} \\d{3}", phoneNumber))
        PhoneNumber = phoneNumber;
        else throw new Exception("Podana numer telefonu jest niepoprawny!");
    }

    public String getLocalNumber() {
        return LocalNumber;
    }

    public void setLocalNumber(String localNumber) throws Exception{
        LocalNumber = localNumber;
    }

    public RealmAdditionalIssueKey getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(RealmAdditionalIssueKey issueKey) throws Exception{
        this.issueKey = issueKey;
    }

    public RealmList<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(RealmList<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public boolean checkIfHavePrescription(Prescription prescription){
        return this.prescriptions.contains(prescription);
    }

    public boolean prescribePrescription(Prescription prescription){
        getPrescriptions().add(prescription);
        prescription.setPatient(this);
        return true;
    }
}