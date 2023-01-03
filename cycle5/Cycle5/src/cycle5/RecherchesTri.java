/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cycle5;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author qartigala
 */
public class RecherchesTri {

    final static int MAX_TAILLE = 10000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

    }

    /**
     * Recherche d'un élément d'en un tableau trié avec la bouble pour
     *
     * @param tab tableau d'entier
     * @param n nombre à rechercher
     * @return le nombre de comparaison
     */
    static int recherchePour(int[] tab, int n) {
        int result = 0;
        int compteur = 0;
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] == n) {
                result = n;
            }
            compteur++;
        }
        return compteur;
    }

    static int[] triTableauAléatoire(int[] tab) {
        Arrays.sort(tab, 0, tab.length);
        return tab;
    }

    /**
     * recherche d'un élément d'en un tableau trié avec la boucle tant que
     *
     * @param tab tableau d'entier
     * @param n entier à rechercher
     * @return le nombre de comparaison
     */
    static int rechercheTantQue(int[] tab, int n) {
        int compteur = 1;
        int index = 0;
        while (tab[index] != n) {
            index++;
            compteur++;
        }
        return compteur;
    }

    /**
     * Fonction qui créer un tableau remplie de nombre aléatoire
     *
     * @param n taille du tableau
     * @return le tableau remplie
     */
    static int[] tableauAleatoire(int n) {
        Random rd = new Random();
        int[] tab = new int[n];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = rd.nextInt(n);
        }
        return tab;
    }

    /**
     * recherche d'un élément d'en un tableau trié avec la dichotomie
     *
     * @param tab tableau d'entier
     * @param n entier à rechercher
     * @return le nombre de comparaison
     */
    static int rechercheDichotomique(int[] tab, int n) {
        int g = 0;
        int d = tab.length;
        int compteur = 1;
        while (tab[0] != n && (d - g) != 0) {
            if (n > tab[d / 2]) {
                g = (d / 2) + 1;
            } else if (n < tab[d / 2]) {
                d = (d / 2) - 1;
            }
            compteur++;
        }
        return compteur;
    }

    static int typeDeTri(int[] tab, TypeRecherche type) {
        int comparaison = 0;
        switch (type) {
            case POUR:
                comparaison = recherchePour(tab, MAX_TAILLE);
                break;
            case TANTQUE:
                comparaison = rechercheTantQue(tab, MAX_TAILLE);
                break;
            case DICHO:
                comparaison = rechercheDichotomique(tab, MAX_TAILLE);
                break;
            default:
                comparaison = 0;
                break;
        }
        return comparaison;

    }

    static int rechercheTri(TypeRecherche type) {
        int[] tab;
        int moy = 0;
        tab = tableauAleatoire(MAX_TAILLE);
        triTableauAléatoire(tab);
        for (int i = 0; i < 100000; i++) {
            moy += typeDeTri(tab, type)/MAX_TAILLE;
        }
        return moy;
    }

}
