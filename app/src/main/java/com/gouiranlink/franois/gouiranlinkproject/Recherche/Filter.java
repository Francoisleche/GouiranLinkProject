package com.gouiranlink.franois.gouiranlinkproject.Recherche;

import java.util.ArrayList;

/**
 * Created by François on 30/03/2017.
 */

public class Filter {

    private ArrayList<String> prestations;
    private boolean acceptation_Automatique_RDV;
    private boolean les_plusreserve_semaine;
    private String specialite;
    private String type;
    private String jour_ouverture;

    public Filter(){
    }

    public Filter(ArrayList<String> prestations,boolean acceptation_Automatique_RDV,boolean les_plusreserve_semaine,
            String specialite,String type, String jour_ouverture){
        this.setPrestations(prestations);
        this.setAcceptation_Automatique_RDV(acceptation_Automatique_RDV);
        this.setLes_plusreserve_semaine(les_plusreserve_semaine);
        this.setSpecialite(specialite);
        this.setType(type);
        this.setJour_ouverture(jour_ouverture);

    }


    public ArrayList<String> getPrestations() {
        return prestations;
    }

    public void setPrestations(ArrayList<String> prestations) {
        this.prestations = prestations;
    }

    public boolean isAcceptation_Automatique_RDV() {
        return acceptation_Automatique_RDV;
    }

    public void setAcceptation_Automatique_RDV(boolean acceptation_Automatique_RDV) {
        this.acceptation_Automatique_RDV = acceptation_Automatique_RDV;
    }

    public boolean isLes_plusreserve_semaine() {
        return les_plusreserve_semaine;
    }

    public void setLes_plusreserve_semaine(boolean les_plusreserve_semaine) {
        this.les_plusreserve_semaine = les_plusreserve_semaine;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJour_ouverture() {
        return jour_ouverture;
    }

    public void setJour_ouverture(String jour_ouverture) {
        this.jour_ouverture = jour_ouverture;
    }
}
