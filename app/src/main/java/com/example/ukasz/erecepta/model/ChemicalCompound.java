package com.example.ukasz.erecepta.model;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by Agnieszka on 2018-01-19.
 */

public class ChemicalCompound extends RealmObject {

    @Index
    public String internationalName;

    public String getInternationalName() {
        return internationalName;
    }

    public void setInternationalName(String internationalName) {
        this.internationalName = internationalName;
    }
}
