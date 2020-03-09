package com.example.sih.Models;

public class Symptoms_model {
    String first;
    String second;

    public Symptoms_model()
    {
        //Empty Constructor
    }

    public Symptoms_model(String first, String second) {
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
