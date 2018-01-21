package com.example.ukasz.erecepta.model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-06.
 */

public class PhysiciansSpecialities extends RealmObject {

    private Physician physician;
    private Specialty specialty;
    private Date date;

    public PhysiciansSpecialities() {
    }

    public PhysiciansSpecialities(Physician physician, Specialty specialty, Date date) {
        this.physician = physician;
        this.specialty = specialty;
        this.date = date;
    }

    public Physician getPhysician() {
        return physician;
    }

    public void setPhysician(Physician physician) {
        this.physician = physician;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}