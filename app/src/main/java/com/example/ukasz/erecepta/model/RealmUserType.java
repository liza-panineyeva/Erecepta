package com.example.ukasz.erecepta.model;

import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-07.
 */

public class RealmUserType extends RealmObject {
    private  String enumDescription;

    public RealmUserType() {
   }
    public void saveEnum(UserType val) {
        this.enumDescription = val.toString();
    }
    public UserType getEnum() {
        return UserType.valueOf(enumDescription);
    }
}
