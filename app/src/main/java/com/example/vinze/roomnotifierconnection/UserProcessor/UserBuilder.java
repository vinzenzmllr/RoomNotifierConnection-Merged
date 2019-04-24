package com.example.vinze.roomnotifierconnection.UserProcessor;

public class UserBuilder {

    public static User buildUser = new User(null,null,null,0.0,0.0,null,null);


    public static void printUser(){
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Username: " + buildUser.getUsername());
        System.out.println("Passwort: " + buildUser.getPassword());
        System.out.println("Geburtstdatum: " + buildUser.getBirthdate());
        System.out.println("Größe: " + buildUser.getGroesse());
        System.out.println("Gewicht: " + buildUser.getGewicht());
        System.out.println("Geschlecht: " + buildUser.getGeschlecht());
        System.out.println("----------------------------------------------------------------------------------");
    }

}
