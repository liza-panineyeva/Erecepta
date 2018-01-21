package com.example.ukasz.erecepta.model;

import io.realm.RealmObject;

/**
 * Created by Łukasz on 06.01.2018.
 */

public class Institute extends RealmObject {

    private String name;
    private Boolean isNFZ;
    private String street;
    private String buildingNumber;
    private String city;
    private String voivodeship;
    private String gmina;

    //optional
    private String LocalNumber;
    //TODO add association classes and references to them


    public Institute() {
    }

    public Institute(Boolean isNFZ, String street, String buildingNumber, String city, String voivodeship, String gmina) {
        this.isNFZ = isNFZ;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.city = city;
        this.voivodeship = voivodeship;
        this.gmina = gmina;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNFZ() {
        return isNFZ;
    }

    public void setNFZ(Boolean NFZ) {
        isNFZ = NFZ;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public String getGmina() {
        return gmina;
    }

    public void setGmina(String gmina) {
        this.gmina = gmina;
    }

    public String getLocalNumber() {
        return LocalNumber;
    }

    public void setLocalNumber(String localNumber) {
        LocalNumber = localNumber;
    }
}
