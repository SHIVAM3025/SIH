package com.example.sih.Models;

public class Diagnosis_model {

    String first;
    String second;

    public Diagnosis_model()
    {
        //Empty Constructor
    }

    public Diagnosis_model(String first, String second) {
        this.first = first;
        this.second = second;
    }

    //Getter


    public String getFirst() {
        return first;
    }

    public String getSecond() {
        return second;
    }

    //Setter


    public void setFirst(String first) {
        this.first = first;
    }

    public void setSecond(String second) {
        this.second = second;
    }


}
