package com.example.ukasz.erecepta.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-06.
 */

public class Specialty extends RealmObject {

    private String name;
    private String specialtyKey;

    public RealmList<PhysiciansSpecialities> getPhysiciansSpecialities() {
        return physiciansSpecialities;
    }

    public void setPhysiciansSpecialities(RealmList<PhysiciansSpecialities> physiciansSpecialities) {
        this.physiciansSpecialities = physiciansSpecialities;
    }

    //optional
    private RealmList<PhysiciansSpecialities> physiciansSpecialities;

    public Specialty() {
    }

    public Specialty(String name, String specialtyKey) {
        this.name = name;
        this.specialtyKey = specialtyKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}