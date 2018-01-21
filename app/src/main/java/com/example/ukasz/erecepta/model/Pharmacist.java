/*
 * Copyright 2014 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ukasz.erecepta.model;

import android.util.Log;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Required;

public class Pharmacist extends RealmObject {

    private User user;
    @Required
    private String pwzNumber;
    @Required
    private String pwzStatus;
    private String izbaAptekarska;

    //optional
    private RealmList<Prescription> prescriptions;
    private RealmList<PharmacistsEmployment> employments = new RealmList<>();
    private String PhoneNumber;

    public Pharmacist() {}
    public Pharmacist(User user, String PWZNumber, String IzbaAptekarska) {
        this.user = user;
        this.pwzNumber = PWZNumber;
        this.izbaAptekarska = IzbaAptekarska;
    }


    public RealmList<PharmacistsEmployment> getEmployments() {
        return employments;
    }

    public void setEmployments(RealmList<PharmacistsEmployment> employments) {
        this.employments = employments;
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

    public String getIzbaAptekarska() {
        return izbaAptekarska;
    }

    public void setIzbaAptekarska(String izbaAptekarska) {
        this.izbaAptekarska = izbaAptekarska;
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

    private void setPharm(Prescription p) throws Exception {
        p.setPharmacistRealizing(this);
        p.setRealizationDate(new Date());
    }

    public boolean realisePrescription(String prescriptionNumber, final Realm realm) {
        final RealmResults<Prescription> r = realm.where(Prescription.class).equalTo("prescriptionNumber", prescriptionNumber).findAll();
        if (r.size() != 0) {
            if (r.first().getPharmacistRealizing() == null) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        try {
                            setPharm(r.first());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                return true;
            }
            else {
                Log.v("wynik", "recepta jest już zrealizowana");
                return false;
            }
        }
        else {
            Log.v("wynik", "nie odnaleziono recepty");
            return  false;
        }
    }


}
