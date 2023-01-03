/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cycle5;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author qartigala
 */
public class RecherchesTri {

    final static int MAX_TAILLE = 100000;

    final static String FICHIERPOUR = "benchmarkRechercheBouclePour.txt";

    final static String FICHIERTANTQUE = "benchmarkRechercheBoucleTantQue.txt";

    final static String FICHIERDICHOTOMIQUE = "benchmarkRechercheDichotomique.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ecriture(FICHIERPOUR, TypeRecherche.POUR);
        ecriture(FICHIERTANTQUE, TypeRecherche.TANTQUE);
        ecriture(FICHIERDICHOTOMIQUE, TypeRecherche.DICHO);
    }

    static void ecriture(String fichier, TypeRecherche type) {
        try ( PrintWriter write = new PrintWriter(fichier)) {
            for (int taille = 10000; taille < MAX_TAILLE; taille += 10000) {
                write.println(taille  + " " + rechercheTri(10, taille, type));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecherchesTri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Recherche d'un élément d'en un tableau trié avec la bouble pour
     *
     * @param tab tableau d'entier
     * @param n nombre à rechercher
     * @return le nombre de comparaison
     */
    static int recherchePour(int[] tab,int taille, int n) {
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
        int index = 1;
        while (tab[index - 1] != n) {
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
        int[] tab = new int[MAX_TAILLE];
        for (int i = 0; i < tab.length; i++) {
            tab[i] = rd.nextInt();
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
        boolean trouve = false;
        while (g <= d && !trouve) {
            int m = (d + g) / 2;
            if (n > tab[m]) {
                g = m + 1;
            } else if (n < tab[m]) {
                d = m - 1;
            } else {
                trouve = true;
            }
            compteur++;
        }
        return compteur;
    }

    static int typeDeTri(int[] tab, TypeRecherche type, int taille) {
        int comparaison = 0;
        Random rd = new Random();
        switch (type) {
            case POUR:
                comparaison = recherchePour(tab, tab[rd.nextInt(taille)]);
                break;
            case TANTQUE:
                comparaison = rechercheTantQue(tab, tab[rd.nextInt(taille)]);
                break;
            case DICHO:
                comparaison = rechercheDichotomique(tab, tab[rd.nextInt(taille)]);
                break;
            default:
                comparaison = 0;
                break;
        }
        return comparaison;

    }

    static double rechercheTri(int repetition, int taille, TypeRecherche type) {
        int[] tab;
        double moy = 0;
        for (int i = 0; i < repetition; i++) {
            tab = tableauAleatoire(taille);
            triTableauAléatoire(tab);
            moy += typeDeTri(tab, type, taille);
        }
        return moy / taille;
    }

}
