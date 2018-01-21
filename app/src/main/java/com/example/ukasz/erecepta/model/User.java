package com.example.ukasz.erecepta.model;

/**
 * Created by Agnieszka on 2018-01-06.
 */
import android.util.Log;

import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Pattern;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;


public class User extends RealmObject {

    static RealmList<RealmUserType> userTypes = new RealmList<>();

    @Required
    private String name;
    @Required
    private String surname;
    private RealmSex sex;
    private String login;
    private String password;
    @Required
    private Date birthDate;
    @Required
    private String pesel;


    private RealmUserType userType;


    //optional
//    private Nurse userNurse;
//    private Patient userPatient;
//    private Pharmacist userPharmacist;
//    private Physician userPhysican;

    public User() {}
    public User(String name, String surname, RealmSex sex, String login, String password, Date birthDate, String PESEL) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.login = login;
        this.password = password;
        this.birthDate = birthDate;
        this.pesel = PESEL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(Pattern.matches("\\w{2,50}", name))
            this.name = name;
        else throw new Exception("Podane imie jest niepoprawne");
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws Exception {

        if(Pattern.matches("\\w{2,50}", surname))
            this.surname = surname;
        else throw new Exception("Podane nazwisko jest niepoprawne");
    }

    public RealmSex getSex() {
        return sex;
    }

    public void setSex(RealmSex sex) {
        this.sex = sex;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) throws Exception{
        if(birthDate.after(java.sql.Date.valueOf("1900-01-01")) && birthDate.before(new Date()))
        this.birthDate = birthDate;
        else throw new Exception("Data urodzenia niepoprawna");
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) throws Exception {
        if(Pattern.matches("\\d{11}", pesel))
            this.pesel = pesel;
        else throw new Exception("Numer pesel musi składać się z 11 cyfr");

    }

    public RealmUserType getUserType() {
        return userType;
    }

    public void setUserType(RealmUserType userType) {
            this.userType = userType;
    }
}
