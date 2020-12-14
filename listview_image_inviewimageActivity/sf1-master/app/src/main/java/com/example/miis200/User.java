package com.example.miis200;


public class User {
    private String PatientName;
    private String PatientID;
    private String PatientPnum;
    private String PatientGender;
    private String PatientBir;

    public User(String pName,String pId, String pPnum, String pGender, String pBir){
        PatientName = pName;
        PatientID = pId;
        PatientPnum = pPnum;
        PatientGender = pGender;
        PatientBir = pBir;

    }

    public String getPatientName() {
        return PatientName;
    }

    public String getPatientID() {
        return PatientID;
    }

    public String getPatientPnum() {
        return PatientPnum;
    }

    public String getPatientGender() {
        return PatientGender;
    }

    public String getPatientBir() {
        return PatientBir;
    }


}
