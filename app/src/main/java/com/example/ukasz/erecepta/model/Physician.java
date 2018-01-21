package com.example.ukasz.erecepta.model;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-06.
 */

public class Physician extends RealmObject {

    private User user;
    private String pwzNumber;
    private String pwzStatus;
    private String okregowaIzbaLekarska;
    private String physicianType;

    //optional
    private RealmList<PhysiciansSpecialities> physiciansSpecialities;
    private RealmList<Prescription> prescriptions;
    private String PhoneNumber;

    public Physician() {}
    public Physician(User user, String PWZNumber, String PWZStatus, String okregowaIzbaLekarska, String physicianType) {
        this.user = user;
        this.pwzNumber = PWZNumber;
        this.pwzStatus = PWZStatus;
        this.okregowaIzbaLekarska = okregowaIzbaLekarska;
        this.physicianType = physicianType;
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

    public String getOkregowaIzbaLekarska() {
        return okregowaIzbaLekarska;
    }

    public void setOkregowaIzbaLekarska(String okregowaIzbaLekarska) {
        this.okregowaIzbaLekarska = okregowaIzbaLekarska;
    }

    public String getPhysicianType() {
        return physicianType;
    }

    public void setPhysicianType(String physicianType) {
        this.physicianType = physicianType;
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

    public RealmList<PhysiciansSpecialities> getPhysiciansSpecialities() {
        return physiciansSpecialities;
    }

    public void setPhysiciansSpecialities(RealmList<PhysiciansSpecialities> physiciansSpecialities) {
        this.physiciansSpecialities = physiciansSpecialities;
    }

    public void addSpecialty(Realm realm, Specialty specialty){
        PhysiciansSpecialities physiciansSpecialities = realm.createObject(PhysiciansSpecialities.class);
        physiciansSpecialities.setPhysician(this);
        physiciansSpecialities.setSpecialty(specialty);
        getPhysiciansSpecialities().add(physiciansSpecialities);
        specialty.getPhysiciansSpecialities().add(physiciansSpecialities);
    }
}
