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

    int VitaliteRouge;

    int VitaliteBleu;

    int valeurAjouter;

    AdditionSousCondition() {
    }

    AdditionSousCondition(int vitaliteR, int vitaliteB, int uneValeurAjouter) {
        VitaliteRouge = vitaliteR;
        VitaliteBleu = vitaliteB;
        valeurAjouter = uneValeurAjouter;
    }

    void Condition(Case[][] plateau, Coordonnees coord) {
        if (plateau[coord.ligne][coord.colonne].couleur == 'R') {
            VitaliteRouge += valeurAjouter;

        } else if (plateau[coord.ligne][coord.colonne].couleur == 'B') {
            VitaliteBleu += valeurAjouter;

        }
    }

    void Condition(char couleur) {
        if (couleur == 'R') {
            VitaliteRouge += valeurAjouter;
        } else {
            VitaliteBleu += valeurAjouter;
        }
    }

    void Condition(boolean condition) {
        if (condition) {
            VitaliteRouge += valeurAjouter;
        } else {
            VitaliteBleu += valeurAjouter;
        }
    }

}
