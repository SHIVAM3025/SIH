package com.example.sih.Models;

public class Name_model {

    String Dosage;
    String Frequency;

    public Name_model()
    {
        //Empty Constructor
    }

    public Name_model(String dosage, String frequency) {
        Dosage = dosage;
        Frequency = frequency;
    }

    public String getDosage() {
        return Dosage;
    }

    public String getFrequency() {
        return Frequency;
    }

    public void setDosage(String dosage) {
        Dosage = dosage;
    }

    public void setFrequency(String frequency) {
        Frequency = frequency;
    }
}