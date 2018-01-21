package com.example.ukasz.erecepta;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ukasz.erecepta.model.AdditionalIssueKey;
import com.example.ukasz.erecepta.model.ChemicalCompound;
import com.example.ukasz.erecepta.model.Disease;
import com.example.ukasz.erecepta.model.Drug;
import com.example.ukasz.erecepta.model.DrugsIngredient;
import com.example.ukasz.erecepta.model.Drugstore;
import com.example.ukasz.erecepta.model.Patient;
import com.example.ukasz.erecepta.model.PaymentLevel;
import com.example.ukasz.erecepta.model.Pharmacist;
import com.example.ukasz.erecepta.model.PharmacistsEmployment;
import com.example.ukasz.erecepta.model.Physician;
import com.example.ukasz.erecepta.model.Prescription;
import com.example.ukasz.erecepta.model.RealmAdditionalIssueKey;
import com.example.ukasz.erecepta.model.RealmPaymentLevel;
import com.example.ukasz.erecepta.model.RealmSex;
import com.example.ukasz.erecepta.model.RealmUserType;
import com.example.ukasz.erecepta.model.Sex;
import com.example.ukasz.erecepta.model.Specialty;
import com.example.ukasz.erecepta.model.User;
import com.example.ukasz.erecepta.model.UserType;

import java.sql.Date;
import java.util.ArrayList;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.exceptions.RealmException;


public class MainActivity extends Activity {
    Button addButton, checkIfHavePrescription;
    EditText name, surname, pesel, prescriptionNumber;
    TextView result, tvPeselError;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_example);

        addButton = findViewById(R.id.btn_Add);
        name = findViewById(R.id.et_name);
        surname = findViewById(R.id.etSurname);
        pesel = findViewById(R.id.et_pesel);
        result = findViewById(R.id.tvresult);
        tvPeselError = findViewById(R.id.tvPeselError);
        checkIfHavePrescription = findViewById(R.id.btnCheckIfHavePrescription);
        prescriptionNumber = findViewById(R.id.etPrescriptionNumber);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String nameUser = name.getText().toString();
                if(nameUser.length() > 0) {
                    validateName(nameUser);
                }
                //Toast.makeText(getApplicationContext(), "CHANGE!", Toast.LENGTH_LONG).show();
                /*if(nameUser.length() > 0) {
                    if(validateName(nameUser)) {
                        nameUser = nameUser.substring(0, 1).toUpperCase() + nameUser.substring(1);
                        name.setText(nameUser);
                    }
                }*/
            }
        });

        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String nameUser = surname.getText().toString();
                if(nameUser.length() > 0) {
                    validateName(nameUser);
                }
                //Toast.makeText(getApplicationContext(), "CHANGE!", Toast.LENGTH_LONG).show();
                /*if(nameUser.length() > 0) {
                    if(validateName(nameUser)) {
                        nameUser = nameUser.substring(0, 1).toUpperCase() + nameUser.substring(1);
                        name.setText(nameUser);
                    }
                }*/
            }
        });

        pesel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String nameUser = pesel.getText().toString();
                if(nameUser.length() > 0) {
                    validatePesel(nameUser);
                }

            }
        });

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    String nameUser = name.getText().toString().trim();
                    if(nameUser.length() > 0) {
                        String changed = nameUser.substring(0, 1).toUpperCase() + nameUser.substring(1);
                        name.setText(changed);
                    }
                }
            }
        });

        surname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                    String nameUser = surname.getText().toString().trim();
                    if(nameUser.length() > 0) {
                        String changed = nameUser.substring(0, 1).toUpperCase() + nameUser.substring(1);
                        surname.setText(changed);
                    }
                }
            }
        });

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();

        // Clear the realm from last time
        Realm.deleteRealm(realmConfiguration);

        // Create a new empty instance of Realm
        realm = Realm.getInstance(realmConfiguration);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUser = name.getText().toString().trim();
                String surnameUser = surname.getText().toString().trim();
                String peselUser = pesel.getText().toString().trim();

                saveToDBAsync(nameUser,surnameUser,peselUser);
                refresh_view();
            }
        });

        checkIfHavePrescription.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                boolean havePrescription = false;
                String prescriptionNumberS = prescriptionNumber.getText().toString();

                RealmResults<Prescription> prescriptionsList = realm.where(Prescription.class)
                        .equalTo("prescriptionNumber", prescriptionNumberS)
                        .findAll();
                RealmResults<Patient> r = realm.where(Patient.class).findAll();

                for (Patient pat : r) {
                    if(prescriptionsList.size() > 0)
                    if(pat.checkIfHavePrescription(prescriptionsList.first()))
                        havePrescription = true;
                }

                if(havePrescription)
                    Toast.makeText(getApplicationContext(), "Istnieje pacjent posiadający receptę o podanym numerze", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Nie istnieje pacjent posiadający receptę o podanym numerze", Toast.LENGTH_LONG).show();
            }
        });

        saveExampleDataToBD();
        myQuery();

        saveAgnieszkaDataNew();
        get_DrugsIngredientsCount();
        getPharmacist_Sorted_By_Employment_Time();
        get_drugsIngredients_by_dose_prescribed();

        saveMyData();
        getRefundableDrugsToDisease("Odra");
        getPharmacistsWorkingInSpecificDrugstoreInSpecificYears("Apteka promyk", Date.valueOf("2001-01-01"), Date.valueOf("2004-01-01"));
        getPatientsWithMostExpensiveDrugs();
        getWorkersByPWZstatus("Apteka promyk");
        getNumberOfPrescriptionByDate("Żukowski");
        getPrescriptionByIssueKey();
    }


    private boolean validateName(String name) {
        return name.length() <= 50;
    }

    private void validatePesel(String pesel) {
        if(!Pattern.matches("\\d{1,11}", pesel))
            tvPeselError.setVisibility(View.VISIBLE);
        else
            tvPeselError.setVisibility((View.GONE));
    }

    private void refresh_view() {
        RealmResults<Patient> r = realm.where(Patient.class)
                .findAll();
        String output = "";
        for (Patient pat : r) {
            output+= pat.getUser().getName();
            output+=" ";
            output+= pat.getUser().getSurname();
            output+="\n";
        }
        result.setText(output);
    }

    private void saveToDBAsync(final String strName, final String strSurname, final String strPesel) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) throws RealmException{
                try {
                    Prescription prescription = bgRealm.createObject(Prescription.class);
                    prescription.setPrescriptionNumber("ABC");

                    Patient patient = bgRealm.createObject(Patient.class);
                    User user = bgRealm.createObject(User.class);
                    RealmUserType userType = bgRealm.createObject(RealmUserType.class);
                    userType.saveEnum(UserType.PATIENT);
                    user.setUserType(userType);
                    user.setName(strName);
                    user.setSurname(strSurname);
                    user.setPesel(strPesel);
                    user.setBirthDate(java.sql.Date.valueOf("2000-01-01"));
                    patient.setUser(user);
                    patient.setPhoneNumber("123 123 123");
                    patient.prescribePrescription(prescription);

                } catch (Exception e) {
                    throw new RealmException(e.getMessage());
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                refresh_view();
                name.setText("");
                surname.setText("");
                pesel.setText("");
                Log.v("Database", "added");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveAgnieszkaData() {
    realm.executeTransactionAsync(new Realm.Transaction() {
        @Override
        public void execute(Realm bgRealm) throws RealmException{
            try {
            Prescription prescription = bgRealm.createObject(Prescription.class);
            Drug drug = bgRealm.createObject(Drug.class);
            RealmPaymentLevel level = bgRealm.createObject(RealmPaymentLevel.class);
            level.saveEnum(PaymentLevel.BEZPLATNE);
            drug.addPaymentLevel(level);
            prescription.setPrescriptionNumber("numera");
            Patient patient = bgRealm.createObject(Patient.class);
            User user = bgRealm.createObject(User.class);
            RealmUserType userType = bgRealm.createObject(RealmUserType.class);
            userType.saveEnum(UserType.PATIENT);
            user.setUserType(userType);
            user.setName("Antoni");
            user.setSurname("Sieranski");
            user.setPesel("91021483921");
            patient.setUser(user);
            User u2 = bgRealm.createObject(User.class);
            u2.setName("ImieFarm");
            u2.setSurname("NazwiskoFarm");
            Pharmacist p = bgRealm.createObject(Pharmacist.class);
            p.setPwzNumber("343434");
            p.setUser(u2);

        } catch (Exception e) {
            throw new RealmException(e.getMessage());
        }
        }
    }, new Realm.Transaction.OnSuccess() {
        @Override
        public void onSuccess() {
            Log.v("Database", "added");
        }
    }, new Realm.Transaction.OnError() {
        @Override
        public void onError(Throwable error) {
            Log.e("database", error.getMessage());
        }
    });
}

    public void saveExampleDataToBD() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                Physician physician = realm.createObject(Physician.class);
                User user = realm.createObject(User.class);
                RealmUserType userType = realm.createObject(RealmUserType.class);
                userType.saveEnum(UserType.PHYSICIAN);
                user.setUserType(userType);
                user.setName("Kazimierz");
                user.setSurname("Odnowiciel");
                physician.setUser(user);

                Specialty specialty = realm.createObject(Specialty.class);
                specialty.setName("Onkolog");

                physician.addSpecialty(realm, specialty);

                } catch (Exception e) {

                    e.printStackTrace();
                    Log.v("Database", "error while addingExampleData");
                }
            }
        });
    }

    public void myQuery(){
        RealmResults<Physician> physicians = realm.where(Physician.class)
                .equalTo("physiciansSpecialities.specialty.name", "Onkolog")
                .findAll();


        for (Physician physician : physicians) {
            Log.v("Physician name", physician.getUser().getName());
        }
    }


    //ZAPYTANIA AGNIESZKI
    private void saveAgnieszkaDataNew() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {

                    RealmSex femalesx = realm.createObject(RealmSex.class);
                    femalesx.saveEnum(Sex.K);
                    RealmSex malesex = realm.createObject(RealmSex.class);
                    malesex.saveEnum(Sex.M);
                    RealmPaymentLevel freeLevel = realm.createObject(RealmPaymentLevel.class);
                    freeLevel.saveEnum(PaymentLevel.BEZPLATNE);
                    RealmPaymentLevel fullLevel = realm.createObject(RealmPaymentLevel.class);
                    fullLevel.saveEnum(PaymentLevel.STOPROCENT);
                    RealmPaymentLevel level30 = realm.createObject(RealmPaymentLevel.class);
                    level30.saveEnum(PaymentLevel.TRZYDZIESCIPROCENT);
                    RealmAdditionalIssueKey ib = realm.createObject(RealmAdditionalIssueKey.class);
                    ib.saveEnum(AdditionalIssueKey.IB);

                    //tworzenie pacjenta
                    User user = realm.createObject(User.class);
                    user.setName("Antoni");
                    user.setSurname("Sieranski");
                    user.setPesel("91021483921");
                    user.setSex(malesex);

                    RealmUserType patientUserType = realm.createObject(RealmUserType.class);
                    patientUserType.saveEnum(UserType.PATIENT);
                    user.setUserType(patientUserType);

                    Patient patient = realm.createObject(Patient.class);
                    patient.setUser(user);

                    //tworzenie pacjenta
                    User user1 = realm.createObject(User.class);
                    user1.setName("Marek");
                    user1.setSurname("Żukowski");
                    user1.setPesel("88101022391");
                    user1.setUserType(patientUserType);
                    user1.setSex(malesex);

                    Patient patient1 = realm.createObject(Patient.class);
                    patient1.setUser(user1);
                    patient1.setIssueKey(ib);

                    //tworzenie pacjenta
                    User user2 = realm.createObject(User.class);
                    user2.setName("Wojciech");
                    user2.setSurname("Karpicki");
                    user2.setPesel("22041342391");
                    user2.setUserType(patientUserType);
                    user2.setSex(malesex);

                    Patient patient2 = realm.createObject(Patient.class);
                    patient2.setUser(user2);
                    patient2.setIssueKey(ib);

                    //tworzenie pacjenta
                    User user3 = realm.createObject(User.class);
                    user3.setName("Adrianna");
                    user3.setSurname("Nowicka");
                    user3.setPesel("71040542391");
                    user3.setUserType(patientUserType);
                    user3.setSex(femalesx);

                    Patient patient3 = realm.createObject(Patient.class);
                    patient3.setUser(user3);

                    //tworzenie farmaceuty
                    User u1 = realm.createObject(User.class);
                    u1.setName("Paulina");
                    u1.setSurname("Kwiatkowska");
                    u1.setSex(femalesx);

                    RealmUserType pharmacistUser = realm.createObject(RealmUserType.class);
                    pharmacistUser.saveEnum(UserType.PHARMACIST);
                    u1.setUserType(pharmacistUser);

                    Pharmacist p1 = realm.createObject(Pharmacist.class);
                    p1.setPwzNumber("1882390");
                    p1.setUser(u1);
                    p1.setPwzStatus("wstrzymany");

                    //tworzenie farmaceuty
                    User u2 = realm.createObject(User.class);
                    u2.setName("Monika");
                    u2.setSurname("Wawrzyniak");
                    u2.setSex(femalesx);
                    u2.setUserType(pharmacistUser);

                    Pharmacist p2 = realm.createObject(Pharmacist.class);
                    p2.setPwzNumber("3239482");
                    p2.setUser(u2);
                    p2.setPwzStatus("wstrzymany");

                    //tworzenie farmaceuty
                    User u3 = realm.createObject(User.class);
                    u3.setName("Alicja");
                    u3.setSurname("Kowalska");
                    u3.setSex(femalesx);
                    u3.setUserType(pharmacistUser);

                    Pharmacist p3 = realm.createObject(Pharmacist.class);
                    p3.setPwzNumber("2233002");
                    p3.setUser(u3);
                    p3.setPwzStatus("wstrzymany");

                    //tworzenie farmaceuty
                    User u4 = realm.createObject(User.class);
                    u4.setName("Michał");
                    u4.setSurname("Ziębicki");
                    u4.setSex(femalesx);
                    u4.setUserType(pharmacistUser);

                    Pharmacist p4 = realm.createObject(Pharmacist.class);
                    p4.setPwzNumber("2391934");
                    p4.setUser(u4);
                    p4.setPwzStatus("wstrzymany");

                    //tworzenie apteki
                    Drugstore drugstore1 = realm.createObject(Drugstore.class);
                    Drugstore drugstore2 = realm.createObject(Drugstore.class);
                    Drugstore drugstore3 = realm.createObject(Drugstore.class);

                    //tworzenie zatrudnienia farmaceuty
                    PharmacistsEmployment phEmployment1 = realm.createObject(PharmacistsEmployment.class);
                    phEmployment1.setDrugstore(drugstore1);
                    phEmployment1.setPharmacist(p1);
                    phEmployment1.setEmploymentDate(new Date(2001, 12, 14));
                    phEmployment1.setDismissalDate(new Date(2007, 12, 15));

                    //tworzenie zatrudnienia farmaceuty
                    PharmacistsEmployment phEmployment2 = realm.createObject(PharmacistsEmployment.class);
                    phEmployment2.setDrugstore(drugstore2);
                    phEmployment2.setPharmacist(p2);
                    phEmployment2.setEmploymentDate(new Date(1991, 10, 14));
                    phEmployment2.setDismissalDate(new Date(1995, 1, 1));

                    //tworzenie zatrudnienia farmaceuty
                    PharmacistsEmployment phEmployment3 = realm.createObject(PharmacistsEmployment.class);
                    phEmployment3.setDrugstore(drugstore2);
                    phEmployment3.setPharmacist(p1);
                    phEmployment3.setEmploymentDate(new Date(2001, 10, 14));

                    //tworzenie lekarza

                    User up = realm.createObject(User.class);
                    up.setName("Zbigniew");
                    up.setSurname("Religa");
                    up.setSex(malesex);

                    RealmUserType physicianUser = realm.createObject(RealmUserType.class);
                    physicianUser.saveEnum(UserType.PHYSICIAN);
                    up.setUserType(physicianUser);

                    Physician ph = realm.createObject(Physician.class);
                    ph.setPwzNumber("1882390");
                    ph.setUser(up);

                    //
                    ChemicalCompound cc1 = realm.createObject(ChemicalCompound.class);
                    cc1.setInternationalName("Adrenalinum");

                    ChemicalCompound cc2 = realm.createObject(ChemicalCompound.class);
                    cc2.setInternationalName("Ibuprofenum");

                    ChemicalCompound cc3 = realm.createObject(ChemicalCompound.class);
                    cc3.setInternationalName("Paracetamolum");

                    ChemicalCompound cc4 = realm.createObject(ChemicalCompound.class);
                    cc4.setInternationalName("Tamsulosinum");

                    ChemicalCompound cc5 = realm.createObject(ChemicalCompound.class);
                    cc5.setInternationalName("Amoxicillinum");

                    ChemicalCompound cc6 = realm.createObject(ChemicalCompound.class);
                    cc6.setInternationalName("Acidum clavulanicum");
                    //

                    Drug metafen = realm.createObject(Drug.class);
                    metafen.setIdDrug(1);
                    metafen.setName("Metafen");
                    metafen.setVolume(30);
                    metafen.setRefundable(false);
                    metafen.setGovernmentPrice(30);

                    //
                    DrugsIngredient ingredientParacet500 = realm.createObject(DrugsIngredient.class);
                    ingredientParacet500.setChemicalCompound(cc3);
                    ingredientParacet500.setDose(500.0);
                    ingredientParacet500.setDrug(metafen);

                    DrugsIngredient ingredientIbuprof200 = realm.createObject(DrugsIngredient.class);
                    ingredientIbuprof200.setChemicalCompound(cc2);
                    ingredientIbuprof200.setDose(200.0);
                    ingredientIbuprof200.setDrug(metafen);
                    //

                    metafen.addIngredient(ingredientParacet500);
                    metafen.addIngredient(ingredientIbuprof200);
                    //

                    Drug amoksiklav = realm.createObject(Drug.class);
                    amoksiklav.setIdDrug(2);
                    amoksiklav.setName("Amoksiklav");
                    amoksiklav.setRefundable(true);
                    amoksiklav.setVolume(14);
                    amoksiklav.setGovernmentPrice(38);
                    //

                    DrugsIngredient di2 = realm.createObject(DrugsIngredient.class);
                    di2.setChemicalCompound(cc5);
                    di2.setDose(800);
                    di2.setDrug(amoksiklav);

                    DrugsIngredient di3 = realm.createObject(DrugsIngredient.class);
                    di3.setChemicalCompound(cc6);
                    di3.setDose(200);
                    di3.setDrug(amoksiklav);
                    //
                    amoksiklav.addIngredient(di2);
                    amoksiklav.addIngredient(di3);
                    //
                    Prescription pr1 = realm.createObject(Prescription.class);
                    pr1.setPatient(patient);
                    pr1.setPrescriptionNumber("99223300110");
                    pr1.setNfzNumber("01");
                    pr1.setPrescriptionDate(new Date(2017, 12, 12));
                    pr1.setPhysicianPrescribing(ph);
                    pr1.setRealizationDate(new Date(2018, 01, 01));
                    pr1.setPharmacistRealizing(p1);
                    pr1.setDrug(metafen);
                    metafen.addPrescription(pr1);

                    //
                    DrugsIngredient di4 = realm.createObject(DrugsIngredient.class);
                    di4.setChemicalCompound(cc4);
                    di4.setDose(400);
                    //

                    Prescription pr2 = realm.createObject(Prescription.class);
                    pr2.setPatient(patient1);
                    pr2.setPrescriptionNumber("3222994939");
                    pr2.setNfzNumber("01");
                    pr2.setPrescriptionDate(new Date(2017, 9, 9));
                    pr2.setRealizationDate(new Date(2017,10,10));
                    pr2.setPhysicianPrescribing(ph);
                    pr2.setDrug(amoksiklav);
                    amoksiklav.addPrescription(pr2);

                    Prescription pr3 = realm.createObject(Prescription.class);
                    pr3.setPatient(patient1);
                    pr3.setPrescriptionNumber("3222995559");
                    pr3.setNfzNumber("01");
                    pr3.setPrescriptionDate(new Date(2017, 9, 9));
                    pr3.setRealizationDate(new Date(2017,11,11));
                    pr3.setPhysicianPrescribing(ph);
                    pr3.setDrug(amoksiklav);
                    amoksiklav.addPrescription(pr3);


                    Drug metafen2 = realm.createObject(Drug.class);
                    metafen2.setIdDrug(1);
                    metafen2.setName("Metafen max");
                    metafen2.setVolume(30);
                    metafen2.setRefundable(false);
                    metafen2.setGovernmentPrice(12);

                    //
                    DrugsIngredient di11 = realm.createObject(DrugsIngredient.class);
                    di11.setChemicalCompound(cc3);
                    di11.setDose(1000.0);
                    di11.setDrug(metafen2);

                    DrugsIngredient di12 = realm.createObject(DrugsIngredient.class);
                    di12.setChemicalCompound(cc2);
                    di12.setDose(200.0);
                    di12.setDrug(metafen2);
                    //
                    metafen2.addIngredient(di11);
                    metafen2.addIngredient(di12);
                    //

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // metoda która ustawi w textbox = result stringa z wynikiem

    private void get_DrugsIngredientsCount() {

        //lista leków, które wystąpiły na jakiejkolwiek recepcie - poj. wystąpienia
        RealmResults<Drug> prescribedDrugs = realm.where(Drug.class).isNotEmpty("prescriptionsWithDrug").findAll();
        String s1 = "";
        for (Drug d : prescribedDrugs) {
            s1 = s1 + d.getName() + "\n";
        }

        //tablica z nazwami leków, które były przepisane
        String[] drugsNames = new String[prescribedDrugs.size()];
        for (int i = 0; i < prescribedDrugs.size(); i++) {
            drugsNames[i] = prescribedDrugs.get(i).getName();
        }

        //składniki leków które zostały przepisane na receptach - ale składniki po nazwach chemicznych, nie wszystkie jak lecą
        RealmResults<DrugsIngredient> di = realm.where(DrugsIngredient.class).in("drug.name", drugsNames).findAll();
        int dd = di.size();

        //
        ArrayList<Tuple<DrugsIngredient, Integer>> compoundCount = new ArrayList<>();

        //mam listę składników wraz z napisaniem w ilu lekach występuje ta pozycje
        for (DrugsIngredient drugsIngredient : di) {
            RealmResults<Drug> listaLekow = realm.where(Drug.class).contains("drugsIngredients.chemicalCompound.internationalName", drugsIngredient.getChemicalCompound().getInternationalName())
                    .isNotEmpty("prescriptionsWithDrug").findAll();

            int prescCount = 0;
            for (Drug drug : listaLekow) {
                prescCount = drug.getPrescriptionsWithDrug().size();
            }
            compoundCount.add(new Tuple<>(drugsIngredient, prescCount));
        }

        String s = "";
        for (Tuple<DrugsIngredient, Integer> tpl : compoundCount) {
            s = s + tpl.x.getChemicalCompound().getInternationalName();
            s = s + " ";
            s = s + tpl.y.toString() + " " + "\n";
        }

        //result.setText(s);
    }

    private void getPharmacist_Sorted_By_Employment_Time() {
        final RealmResults<PharmacistsEmployment> pharmacistsEmployments = realm.where(PharmacistsEmployment.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                for (PharmacistsEmployment pemp : pharmacistsEmployments) {
                    pemp.calculateEmploymentTime();
                }
            }
        });

        pharmacistsEmployments.sort("employmentTime");

        RealmList<Pharmacist> pharmacists = new RealmList<>();
        for (PharmacistsEmployment pe : pharmacistsEmployments) {
            pharmacists.add(pe.getPharmacist());
        }

        String output = "";
        for (Pharmacist pharmacist : pharmacists) {
            output += pharmacist.getUser().getName() + " " + pharmacist.getUser().getSurname() + "\n";
        }
        result.setText(output);
    }

    private void get_drugsIngredients_by_dose_prescribed() {
        //lista leków, które wystąpiły na jakiejkolwiek recepcie - poj. wystąpienia
        RealmResults<Drug> prescribedDrugs = realm.where(Drug.class).isNotEmpty("prescriptionsWithDrug").findAll();
        String s1 = "";
        for (Drug d : prescribedDrugs) {
            s1 = s1 + d.getName() + "\n";
        }

        //tablica z nazwami leków, które były przepisane
        String[] drugsNames = new String[prescribedDrugs.size()];
        for (int i = 0; i < prescribedDrugs.size(); i++) {
            drugsNames[i] = prescribedDrugs.get(i).getName();
        }

        //składniki leków które zostały przepisane na receptach - ale składniki po nazwach chemicznych, nie wszystkie jak lecą
        RealmResults<DrugsIngredient> di = realm.where(DrugsIngredient.class).in("drug.name", drugsNames).findAll();

        //
        ArrayList<Tuple<DrugsIngredient, Double>> compoundCount = new ArrayList<>();

        //mam listę składników wraz z informacją w ilu lekach występuje ta pozycje
        for (DrugsIngredient drugsIngredient : di) {
            RealmResults<Drug> listaLekow = realm.where(Drug.class).contains("drugsIngredients.chemicalCompound.internationalName", drugsIngredient.getChemicalCompound().getInternationalName())
                    .isNotEmpty("prescriptionsWithDrug").findAll();

            int prescCount = 0;
            for (Drug drug : listaLekow) {
                prescCount = drug.getPrescriptionsWithDrug().size();
            }
            double prescribed_amount = prescCount * drugsIngredient.getDose();
            compoundCount.add(new Tuple<>(drugsIngredient, prescribed_amount));
        }

        String s = "";
        for (Tuple<DrugsIngredient, Double> tpl : compoundCount) {
            s = s + tpl.x.getChemicalCompound().getInternationalName();
            s = s + " ";
            s = s + tpl.y.toString() + " " + "\n";
        }
        result.setText(s);
    }

    private void saveMyData() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    Drug drugEspumisan = realm.createObject(Drug.class);
                    drugEspumisan.setName("EspumisanL");
                    drugEspumisan.setVolume(100);
                    drugEspumisan.setRefundable(true);
                    drugEspumisan.setGovernmentPrice(10);

                    Drug drugRutinacea = realm.createObject(Drug.class);
                    drugRutinacea.setName("RutinaceaL");
                    drugRutinacea.setVolume(6000);
                    drugRutinacea.setRefundable(true);
                    drugRutinacea.setGovernmentPrice(10);

                    Drug drugRutinoscorbin = realm.createObject(Drug.class);
                    drugRutinoscorbin.setName("RutinoscorbinL");
                    drugRutinoscorbin.setVolume(10);
                    drugRutinoscorbin.setRefundable(false);
                    drugRutinoscorbin.setGovernmentPrice(10);

                    Disease diseaseOdra = realm.createObject(Disease.class);
                    diseaseOdra.setName("Odra");

                    Disease diseaseRozyczka = realm.createObject(Disease.class);
                    diseaseRozyczka.setName("Różyczka");

                    Disease diseaseTezec = realm.createObject(Disease.class);
                    diseaseTezec.setName("Tężec");

                    //connections to Odra

                    diseaseOdra.addDrug(drugEspumisan);
                    drugEspumisan.addDisease(diseaseOdra);

                    diseaseOdra.addDrug(drugRutinoscorbin);
                    drugRutinoscorbin.addDisease(diseaseOdra);

                    diseaseOdra.addDrug(drugRutinacea);
                    drugRutinacea.addDisease(diseaseOdra);


                    //adding pharmacists

                    Pharmacist PharmacistWlodzimierz = realm.createObject(Pharmacist.class);
                    User user1 = realm.createObject(User.class);
                    RealmUserType userType1 = realm.createObject(RealmUserType.class);
                    userType1.saveEnum(UserType.PHARMACIST);
                    user1.setUserType(userType1);
                    user1.setName("Włodzimierz");
                    user1.setSurname("Nowak");
                    PharmacistWlodzimierz.setUser(user1);
                    PharmacistWlodzimierz.setPwzStatus("wstrzymany");

                    Pharmacist PharmacistPawel = realm.createObject(Pharmacist.class);
                    User user2 = realm.createObject(User.class);
                    RealmUserType userType2 = realm.createObject(RealmUserType.class);
                    userType2.saveEnum(UserType.PHARMACIST);
                    user2.setUserType(userType2);
                    user2.setName("Pawel");
                    user2.setSurname("Pietrzyński");
                    PharmacistPawel.setUser(user2);
                    PharmacistPawel.setPwzStatus("wstrzymany");

                    Pharmacist PharmacistKarol = realm.createObject(Pharmacist.class);
                    User user3 = realm.createObject(User.class);
                    RealmUserType userType3 = realm.createObject(RealmUserType.class);
                    userType3.saveEnum(UserType.PHARMACIST);
                    user3.setUserType(userType3);
                    user3.setName("Karol");
                    user3.setSurname("Okrasa");
                    PharmacistKarol.setUser(user3);
                    PharmacistKarol.setPwzStatus("wsztrzymany");

                    Pharmacist PharmacistPrzemyslaw = realm.createObject(Pharmacist.class);
                    User user4 = realm.createObject(User.class);
                    RealmUserType userType4 = realm.createObject(RealmUserType.class);
                    userType4.saveEnum(UserType.PHARMACIST);
                    user4.setUserType(userType3);
                    user4.setName("Pezemyslaw");
                    user4.setSurname("Ralalala");
                    PharmacistPrzemyslaw.setUser(user4);
                    PharmacistPrzemyslaw.setPwzStatus("wsztrzymany");


                    //adding drugstores
                    Drugstore drugstoreAptekaPromyk = realm.createObject(Drugstore.class);
                    drugstoreAptekaPromyk.setName("Apteka promyk");

                    Drugstore drugstoreSzpital = realm.createObject(Drugstore.class);
                    drugstoreSzpital.setName("Szpital");

                    //adding connections employy pharmacists to drugstores
                    drugstoreAptekaPromyk.employ(realm, PharmacistKarol, Date.valueOf("2000-01-01"));
                    drugstoreAptekaPromyk.employ(realm, PharmacistPawel, Date.valueOf("2002-01-01"));
                    drugstoreAptekaPromyk.employ(realm, PharmacistWlodzimierz, Date.valueOf("2003-01-01"));
                    drugstoreAptekaPromyk.employ(realm, PharmacistPrzemyslaw, Date.valueOf("2005-01-01"));

                    drugstoreSzpital.employ(realm, PharmacistKarol, Date.valueOf("2001-01-01"));
                    drugstoreSzpital.employ(realm, PharmacistWlodzimierz, Date.valueOf("2001-02-01"));


                    Patient patientCezary = realm.createObject(Patient.class);
                    User userCezary = realm.createObject(User.class);
                    RealmUserType userTypeCezary = realm.createObject(RealmUserType.class);
                    userTypeCezary.saveEnum(UserType.PATIENT);
                    userCezary.setUserType(userTypeCezary);
                    userCezary.setName("Cezary");
                    userCezary.setSurname("Ziobro");
                    patientCezary.setUser(userCezary);

                    Prescription prescription1 = realm.createObject(Prescription.class);
                    prescription1.addDrug(drugEspumisan);
                    patientCezary.prescribePrescription(prescription1);

                    Prescription prescription2 = realm.createObject(Prescription.class);
                    prescription2.addDrug(drugEspumisan);
                    patientCezary.prescribePrescription(prescription2);

                    Prescription prescription3 = realm.createObject(Prescription.class);
                    prescription3.addDrug(drugRutinoscorbin);
                    patientCezary.prescribePrescription(prescription3);


                } catch (Exception e) {

                    e.printStackTrace();
                    Log.v("Database", "error while addingExampleData");
                }
            }
        });
    }


    //KONIEC ZAPYTAN AGNIESZKI

    private void getRefundableDrugsToDisease(String diseaseName) {
        RealmResults<Drug> drugs = realm.where(Drug.class)
                .equalTo("diseases.name", diseaseName)
                .equalTo("isRefundable", true)
                .findAll();

        Log.v("msg", "#########LISTA LEKÓW REFUNDOWANYCH WYPISYWANYCH NA" + diseaseName + "#############");

        for (Drug drug : drugs) {
            Log.v("Nazwa : dawka : Ref", drug.getName() + " : " + drug.getVolume() + " : " + drug.isRefundable());
        }
    }

    private void getPharmacistsWorkingInSpecificDrugstoreInSpecificYears(String instituteName, Date year1, Date year2) {
        RealmResults<Pharmacist> pharmacists = realm.where(Pharmacist.class)
                .equalTo("employments.drugstore.name", instituteName)
                .greaterThan("employments.employmentDate", year1)
                .lessThan("employments.employmentDate", year2)
                .findAll();

        Log.v("msg", "#########LISTA FARMACEUTÓW PRACUJĄCYCH W " + instituteName + " w przedziale od " + year1.toString() + " do " + year2.toString());

        for (Pharmacist pharmacist : pharmacists) {
            Log.v("pharm", pharmacist.getUser().getName() + " : " + pharmacist.getUser().getSurname());
        }
    }

    private void getPatientsWithMostExpensiveDrugs() {
        final RealmResults<Patient> patients = realm.where(Patient.class).findAll();


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {

                    for (Patient patient : patients) {
                        RealmResults<Prescription> prescriptions = realm.where(Prescription.class)
                                .equalTo("patient.id", patient.getId())
                                .findAll();

                        long cost = 0;
                        for (Prescription prescription : prescriptions) {
                            cost += prescription.getDrug().getGovernmentPrice();
                        }

                        patient.setCostOfDrugs(cost);
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    Log.v("Database", "error while addingExampleData");
                }
            }
        });

        RealmResults<Patient> patients2 = realm.where(Patient.class)
                .sort("costOfDrugs", Sort.DESCENDING)
                .findAll();


        Log.v("msg", "#########LISTA PACJENTÓW PRZYJMUJĄCYCH NAJDROŻSZE LEKI");
        for (Patient patient : patients2) {
            Log.v("cost", "msg:" + patient.getUser().getName() + " " + patient.getUser().getSurname() + " " + patient.getCostOfDrugs());
        }

    }


    
    //Wszystkie farmaceuci instytutu x owstrzymanym pwz
    private void getWorkersByPWZstatus(String InstName) {
        RealmResults<Pharmacist> pharm = realm.where(Pharmacist.class)
                .equalTo("employments.drugstore.name",InstName)
                .equalTo("pwzStatus", "wstrzymany")
                .findAll();
        String s = "#########LISTA FARMACETÓW APTEKI " + InstName + " o wstrzymanym PWZ#############\n";
        for (Pharmacist p : pharm) {
            s = s + p.getUser().getName() + " " + p.getUser().getSurname() + " : " + p.getPwzStatus() +"\n";
        }

        //result.setText(s);
    }

    //Liczba recept zrealizowana przez pacjenta X w okresie 2017-2018
    private void getNumberOfPrescriptionByDate(String surname) {
        RealmResults<Prescription> pr = realm.where(Prescription.class)
                .equalTo("patient.user.surname",surname)
                .greaterThan("realizationDate", new Date(2017,1,1))
                .lessThan("realizationDate", new Date(2018,1,1))
                .findAll();

        int n = 0;
        for (Prescription p : pr) {

            //s = s + p.getPrescriptionNumber() + " "  + p.getRealizationDate() +"\n";
            n+=1;
        }
        String s = "#########LICZBA RECEPT ZREALIZOWANYCH DLA PACJENTA  " + surname + " w latach 2017-2018 = " + n + " #############\n";
        //result.setText(s);
    }

    //Recepty wystawione dla inwalidów wojskowych
    private void getPrescriptionByIssueKey() {
        RealmResults<Prescription> pr = realm.where(Prescription.class)
                .equalTo("patient.issueKey.enumDescription","IB")
                .findAll();

        String s = "#########LISTA RECEPT WYSTAWIONYCH DLA INWALIDÓW WOJSKOWYCH############\n";
        for (Prescription p : pr) {

            s = s + p.getPrescriptionNumber() + " "  + p.getPatient().getIssueKey().getEnum()+"\n";

        }

        //result.setText(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
    }

    //inner class to return tuples
    public class Tuple<X, Y> {
        public X x;
        public Y y;

        public Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

}