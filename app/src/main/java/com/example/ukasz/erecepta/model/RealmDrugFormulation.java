package com.example.ukasz.erecepta.model;

import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-14.
 */

public class RealmDrugFormulation extends RealmObject {
    private  String enumDescription;

    public RealmDrugFormulation() {
    }
    public void saveEnum(DrugFormulation val) {
        this.enumDescription = val.toString();
    }
    public DrugFormulation getEnum() {
        return DrugFormulation.valueOf(enumDescription);
    }

}
