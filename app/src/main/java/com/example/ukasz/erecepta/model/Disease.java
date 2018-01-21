package com.example.ukasz.erecepta.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Łukasz on 20.01.2018.
 */

public class Disease extends RealmObject {

    private String name;
    private String icd10Code;
    private RealmList<Drug> aviableDrugs = new RealmList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcd10Code() {
        return icd10Code;
    }

    public void setIcd10Code(String icd10Code) {
        this.icd10Code = icd10Code;
    }

    public void addDrug(Drug drug) {
        this.aviableDrugs.add(drug);
    }
}
