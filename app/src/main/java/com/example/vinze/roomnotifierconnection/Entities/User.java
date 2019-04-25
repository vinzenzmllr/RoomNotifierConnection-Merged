package com.example.vinze.roomnotifierconnection.Entities;


public class User {

    public User(String u_id, String u_geschlecht, String u_gewicht, String u_groesse, String u_nachname, String u_passwort, String u_username, String u_vorname/*, Set<String> risikofaktoren*/) {
        this.id = u_id;
        this.geschlecht = u_geschlecht;
        this.gewicht = u_gewicht;
        this.groesse = u_groesse;
        this.nachname = u_nachname;
        this.passwort = u_passwort;
        this.username = u_username;
        this.vorname = u_vorname;
        //this.risikofaktoren = risikofaktoren;
    }

    public User() {
        super();
    }

    private String id;

    private String geschlecht;

    private String gewicht;

    private String groesse;

    private String nachname;

    private String passwort;

    private String username;

    private String vorname;

    /*
    @JsonProperty("u_id")
    Set<String> risikofaktoren;
*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(String geschlecht) {
        this.geschlecht = geschlecht;
    }

    public String getGewicht() {
        return gewicht;
    }

    public void setGewicht(String gewicht) {
        this.gewicht = gewicht;
    }

    public String getGroesse() {
        return groesse;
    }

    public void setGroesse(String groesse) {
        this.groesse = groesse;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    /*public Set<String> getRisikofaktoren() {
        return risikofaktoren;
    }

    public void setRisikofaktoren(Set<String> risikofaktoren) {
        this.risikofaktoren = risikofaktoren;
    }*/

    @Override
    public String toString() {
        return "User:" + '\n' +
                "id: " + id + '\n' +
                "geschlecht: " + geschlecht + '\n' +
                "gewicht: " + gewicht + '\n' +
                "groesse: " + groesse + '\n' +
                "nachname: " + nachname + '\n' +
                "passwort: " + passwort + '\n' +
                "username: " + username + '\n' +
                "vorname: " + vorname + '\n';
    }
}
