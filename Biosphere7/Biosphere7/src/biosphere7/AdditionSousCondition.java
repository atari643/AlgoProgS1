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
    
    AdditionSousCondition(int VitaliteR, int vitaliteB, int uneValeurAjouter){
        VitaliteRouge=VitaliteR;
        VitaliteBleu=vitaliteB;
        valeurAjouter=uneValeurAjouter;
    }
    void Condition(Case[][] plateau, Coordonnees coord){
        if (plateau[coord.ligne][coord.colonne].couleur == 'R'){
            VitaliteRouge+=valeurAjouter;
            
        }
        else if (plateau[coord.ligne][coord.colonne].couleur == 'B'){
            VitaliteBleu+=valeurAjouter;
            
        }
    }
}
