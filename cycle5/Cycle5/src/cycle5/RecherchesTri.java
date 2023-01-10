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
    
    final static String FICHIERTRIPOUR = "benchmarkTriSelectionBouclePour.txt";

    final static String FICHIERTRITANTQUE = "benchmarkTriSelectionBoucleTantQue.txt";

    final static String FICHIERTRIDICHOTOMIQUE = "benchmarkTriSelectionRechercheDicho.txt";

    final static String FICHIERSANSTRI = "benchmarkRechercheSansTri.txt";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int graine = (int) System.currentTimeMillis();
        ecriture(FICHIERTRIPOUR, TypeRecherche.POUR, graine);
        ecriture(FICHIERTRITANTQUE, TypeRecherche.TANTQUE, graine);
        ecriture(FICHIERTRIDICHOTOMIQUE, TypeRecherche.DICHO, graine);
        ecriture(FICHIERSANSTRI, TypeRecherche.POUR, graine);
    }

    static void ecriture(String fichier, TypeRecherche type, int seed) {
        try ( PrintWriter write = new PrintWriter(fichier)) {
            write.println("taille nbcomparaison");
            for (int taille = 10000; taille <= MAX_TAILLE; taille += 10000) {
                write.println(taille  + " " + rechercheTri(3, taille, type, seed));
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecherchesTri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static int triParSelection(int[] tab, int taille){
        int indice = 0;
        int compteur = 0;
        for(int i = 0; i<taille; i++){
            int min = tab[i];
            for(int n=i; n<taille; n++){
                compteur++;
                if(tab[n]<=min){
                    min=tab[n];
                    indice = n;
                }
            }
            echanger(tab, i, indice);
        }
        return compteur;
    }
    static void echanger(int[] tab, int indiceMin, int indiceRemplacer){
        int temp = tab[indiceMin];
        tab[indiceMin]=tab[indiceRemplacer];
        tab[indiceRemplacer]=temp;
    }
    
    
    /**
     * Recherche d'un élément d'en un tableau trié avec la bouble pour
     *
     * @param tab tableau d'entier
     * @param n nombre à rechercher
     * @return le nombre de comparaison
     */
    static IndiceComparaison recherchePour(int[] tab,int taille, int n) {
        int result = 0;
        int compteur = 0;
        for (int i = 0; i < taille; i++) {
            if (tab[i] == n) {
                result = i;
            }
            compteur++;
        }
        return new IndiceComparaison(result, compteur);
    }

    static int triTableau(int[] tab, int taille) {
        int comparaison = triParSelection(tab, taille);
        return comparaison;
    }

    /**
     * recherche d'un élément d'en un tableau trié avec la boucle tant que
     *
     * @param tab tableau d'entier
     * @param n entier à rechercher
     * @return le nombre de comparaison
     */
    static IndiceComparaison rechercheTantQue(int[] tab, int taille, int n) {
        int compteur = 1;
        int index = 1;
        while (tab[index - 1] != n) {
            index++;
            compteur++;
        }
        return new IndiceComparaison(index, compteur);
    }

    /**
     * Fonction qui créer un tableau remplie de nombre aléatoire
     *
     * @param n taille du tableau
     * @return le tableau remplie
     */
    static int[] tableauAleatoire(int n, int seed) {
        Random rd = new Random(seed);
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
    static IndiceComparaison rechercheDichotomique(int[] tab, int taille, int n) {
        int g = 0;
        int d = taille;
        int compteur = 1;
        int m = 0;
        boolean trouve = false;
        while (g <= d && !trouve) {
            m = (d + g) / 2;
            if (n > tab[m]) {
                g = m + 1;
            } else if (n < tab[m]) {
                d = m - 1;
            } else {
                trouve = true;
            }
            compteur++;
        }
        return new IndiceComparaison(m, compteur);
    }

    static IndiceComparaison rechercher(int[] tab, TypeRecherche type, int taille, int seed) {
        IndiceComparaison comparaison = new IndiceComparaison(0, 0);
        Random rd = new Random(seed);
        switch (type) {
            case POUR:
                comparaison = recherchePour(tab, taille, tab[rd.nextInt(taille)]);
                break;
            case TANTQUE:
                comparaison = rechercheTantQue(tab, taille, tab[rd.nextInt(taille)]);
                break;
            case DICHO:
                comparaison = rechercheDichotomique(tab, taille, tab[rd.nextInt(taille)]);
                break;
            default:
                System.out.println("stop");
                break;
        }
        return comparaison;

    }

    static double rechercheTri(int repetition, int taille, TypeRecherche type, int seed) {
        int[] tab;
        long moy = 0;
        for (int i = 0; i < repetition; i++) {
            tab = tableauAleatoire(taille, i);
            moy += triTableau(tab, taille);
            moy += rechercher(tab, type, taille, seed).comparaison;
        }
        return moy / repetition;
    }

}
