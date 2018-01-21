package com.example.ukasz.erecepta.model;

import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-14.
 */

public class RealmSex extends RealmObject {

    private  String enumDescription;

    public RealmSex() {
    }

    public void saveEnum(Sex val) {
        this.enumDescription = val.toString();
    }
    public Sex getEnum() {
        return Sex.valueOf(enumDescription);
    }
}
