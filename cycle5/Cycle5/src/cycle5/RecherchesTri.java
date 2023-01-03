/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cycle5;

/**
 *
 * @author qartigala
 */
public class RecherchesTri {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    }
    /**
     * Recherche d'un élément d'en un tableau trié avec la bouble pour
     * @param tab tableau d'entier
     * @param n nombre à rechercher
     * @return le nombre de comparaison 
     */
    static int recherchePour(int[] tab, int n){
        int result=0;
        int compteur = 1;
        for (int i = 0; i<tab.length; i++){
            if(tab[i]==n){
                result=n;
            }
            compteur++;
        }
        return compteur;
    }
    /**
     * recherche d'un élément d'en un tableau trié avec la boucle tant que
     * @param tab tableau d'entier
     * @param n entier à rechercher
     * @return le nombre de comparaison
     */
    static int rechercheTantQue(int[] tab, int n){
        int compteur=1;
        int index = 0;
        while(tab[index]!=n){
            index++;
            compteur++;
        }
        return compteur;
    }
    /**
     * recherche d'un élément d'en un tableau trié avec la dichotomie 
     * @param tab tableau d'entier
     * @param n entier à rechercher
     * @return le nombre de comparaison
     */
    static int rechercheDichotomique(int[] tab, int n){
        int g=0;
        int d=tab.length;
        int compteur=1;
        while(tab[0]!=n && (d-g)!=0){
            if(n>tab[d/2]){
                g=d/2;
            }else if(n<tab[d/2]){
                d=d/2;
            }
            compteur++;
        }
        return compteur;
    }
    
    
}
