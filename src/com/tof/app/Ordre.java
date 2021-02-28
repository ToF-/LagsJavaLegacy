package com.tof.app;

public class Ordre implements Comparable<Ordre> {
    private String id;
    private int debut;
    private int duree;
    private double prix;

    public Ordre(String id, int debut, int duree, double prix)
    {
        this.id = id;
        this.debut = debut;  // au format AAAAJJJ par exemple 25 février 2015 = 2015056
        this.duree = duree;
        this.prix = prix;
    }
    //id de l'ordre 
    public String getId() {
       return this.id;
    }
    // debut
    public int getDebut() {
        return this.debut;
    }
    // duree
    public int getDuree() {
        return this.duree;
    }
    // valeur
    public double prix() {
        return this.prix;
    }
    public int compareTo(Ordre other) {
        return this.debut - other.getDebut();
    }

}
