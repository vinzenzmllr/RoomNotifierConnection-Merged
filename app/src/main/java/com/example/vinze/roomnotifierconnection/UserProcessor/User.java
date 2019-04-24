package com.example.vinze.roomnotifierconnection.UserProcessor;

import java.util.ArrayList;
import java.util.Date;

public class User {

    private String username;
    private String password;
    private Date birthdate;
    private double groesse;
    private double gewicht;
    private String geschlecht;
    private ArrayList<String> risks;

    public User(String username, String password, Date birthdate, double groesse, double gewicht, String geschlecht, ArrayList<String> risks) {
        this.username = username;
        this.password = password;
        this.birthdate = birthdate;
        this.groesse = groesse;
        this.gewicht = gewicht;
        this.geschlecht = geschlecht;
        this.risks = risks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public double getGroesse() {
        return groesse;
    }

    public void setGroesse(double groesse) {
        this.groesse = groesse;
    }

    public double getGewicht() {
        return gewicht;
    }

    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public ArrayList<String> getRisks() {
        return risks;
    }

    public void setRisks(ArrayList<String> risks) {
        this.risks = risks;
    }
}
