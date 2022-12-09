/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biosphere7;

/**
 *
 * @author atari
 */
public class AdditionSousCondition {

    /**
     * La valeur de vitalité du joueur rouge
     */
    int VitaliteRouge;
    /**
     * La valeur de vitalité du joueur Bleu
     */
    int VitaliteBleu;
    /**
     * La valeur a ajouter
     */
    int valeurAjouter;
    /**
     * Constructeur d'un objet vide
     */
    AdditionSousCondition() {
    }

    /**
     * Initialisation des variables
     *
     * @param vitaliteR la valeur de la vitaliteR du Joueur rouge
     * @param vitaliteB la valeur de la vitaliteB du Joueur bleu
     * @param uneValeurAjouter la valeur à ajouter
     */
    AdditionSousCondition(int vitaliteR, int vitaliteB, int uneValeurAjouter) {
        VitaliteRouge = vitaliteR;
        VitaliteBleu = vitaliteB;
        valeurAjouter = uneValeurAjouter;
    }

    /**
     * Fonction qui affecte la valeur Ajouter selon si la cood est rouge dans le
     * plateau ou bleu
     *
     * @param plateau le plateau de jeux
     * @param coord les coordonnees de la case
     */
    void Condition(Case[][] plateau, Coordonnees coord) {
        if (plateau[coord.ligne][coord.colonne].couleur == 'R') {
            VitaliteRouge += valeurAjouter;

        } else if (plateau[coord.ligne][coord.colonne].couleur == 'B') {
            VitaliteBleu += valeurAjouter;

        }
    }

    /**
     * Vérifie la couleur du joueur
     *
     * @param couleur La couleur du joueur
     */
    void Condition(char couleur) {
        if (couleur == 'R') {
            VitaliteRouge += valeurAjouter;
        } else {
            VitaliteBleu += valeurAjouter;
        }
    }

    /**
     * Vérifie une autre condition qui implique le joueur Rouge ou le joueur
     * Bleu
     *
     * @param condition une condition a respecter avec la vitalite Rouge
     */
    void Condition(boolean condition) {
        if (condition) {
            VitaliteRouge += valeurAjouter;
        } else {
            VitaliteBleu += valeurAjouter;
        }
    }
    /**
     * Admet les condition du joueur Rouge
     * @param couleur du joeur
     */
    void ConditionRouge(char couleur) {
        if (couleur == 'R') {
            VitaliteRouge += valeurAjouter;
        }
    }
    /**
     * Admet les condition du joueur Bleur
     * @param couleur couleur du joueur
     * **/
    void ConditionBleu(char couleur){
        if (couleur == 'B'){
            VitaliteBleu+=valeurAjouter;
        }
    }
    
}
