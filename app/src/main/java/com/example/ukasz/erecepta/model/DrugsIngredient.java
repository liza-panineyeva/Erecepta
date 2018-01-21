package com.example.ukasz.erecepta.model;

import io.realm.RealmObject;

/**
 * Created by Agnieszka on 2018-01-19.
 */

public class DrugsIngredient extends RealmObject {

    public Drug drug;
    public ChemicalCompound chemicalCompound;
    public double dose;
    public int idDrug;

    public int getIdDrug() {
        return idDrug;
    }

    public void setIdDrug(int idDrug) {
        this.idDrug = idDrug;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public ChemicalCompound getChemicalCompound() {
        return chemicalCompound;
    }

    public void setChemicalCompound(ChemicalCompound chemicalCompound) {
        this.chemicalCompound = chemicalCompound;
    }

    public double getDose() {
        return dose;
    }

    public void setDose(double dose) {
        this.dose = dose;
    }
}
