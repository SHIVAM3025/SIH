package com.example.sih.Models;

public class Presciption_model {

    String medicine;
    String dosage;
    String frequency;

    public Presciption_model(String medicine, String dosage, String frequency) {
        this.medicine = medicine;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public Presciption_model()
    {

    }

    public String getMedicine() {
        return medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
