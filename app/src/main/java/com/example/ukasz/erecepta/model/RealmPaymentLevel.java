package com.example.ukasz.erecepta.model;

import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-14.
 */

public class RealmPaymentLevel extends RealmObject {

    private  String enumDescription;

    public RealmPaymentLevel() {
    }
    public void saveEnum(PaymentLevel val) {
        switch (val) {
            case PIECDZIESIATPROCENT:
                this.enumDescription = "50%";
                break;
            case STOPROCENT:
                this.enumDescription = "100%";
                break;
            case BEZPLATNE:
                this.enumDescription = "B";
                break;
            case RYCZALT:
                this.enumDescription = "R";
                break;
            case TRZYDZIESCIPROCENT:
                this.enumDescription = "30%";
                break;
            default:
                this.enumDescription = val.toString();
                break;
        }
    }
    public PaymentLevel getEnum() {
        return PaymentLevel.valueOf(enumDescription);
    }
}
