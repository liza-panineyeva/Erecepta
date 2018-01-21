package com.example.ukasz.erecepta.model;

import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-14.
 */

public class RealmAdditionalIssueKey extends RealmObject {
    private String enumDescription;

    public RealmAdditionalIssueKey() {
    }
    public void saveEnum(AdditionalIssueKey val) {
        this.enumDescription = val.toString();
    }
    public AdditionalIssueKey getEnum() {
        return AdditionalIssueKey.valueOf(enumDescription);
    }
}
