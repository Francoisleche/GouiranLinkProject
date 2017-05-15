package com.gouiranlink.franois.gouiranlinkproject.Rendezvous;

/**
 * Created by François on 10/05/2017.
 */

public class Indisponibilite {

    private String annee;
    private String mois;
    private String jour;

    private String heure;
    private String min;


    public Indisponibilite(){

    }

    public Indisponibilite(String annee,String mois, String jour, String heure, String min){
        this.setAnnee(annee);
        this.setMois(mois);
        this.setJour(jour);
        this.setHeure(heure);
        this.setMin(min);
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }


    public String toString(){
        return getAnnee()+getMois()+getJour()+getHeure()+getMin();
    }


}
