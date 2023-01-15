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

    /**
     * La taille maximum d'un tableau
     */
    final static int MAX_TAILLE = 100000;

    /**
     * le fichier pour stocker les comparaison de boucle Pour
     */
    final static String FICHIERPOUR = "benchmarkRechercheBouclePour.txt";
    /**
     * le fichier pour stocker les comparaison de boucle Tant Que
     */
    final static String FICHIERTANTQUE = "benchmarkRechercheBoucleTantQue.txt";
    /**
     * le fichier pour stocker les comparaison de Dichotomique
     */
    final static String FICHIERDICHOTOMIQUE = "benchmarkRechercheDichotomique.txt";
    /**
     * le fichier pour stocker les comparaison de boucle Pour triée par
     * selection
     */
    final static String FICHIERTRIPOUR = "benchmarkTriSelectionBouclePour.txt";
    /**
     * le fichier pour stocker les comparaison de boucle Tant que triée par
     * selection
     */
    final static String FICHIERTRITANTQUE = "benchmarkTriSelectionBoucleTantQue.txt";
    /**
     * le fichier pour stocker les comparaison de Dichotomie trié par selection
     */
    final static String FICHIERTRIDICHOTOMIQUE = "benchmarkTriSelectionRechercheDicho.txt";
    /**
     * le fichier pour stocker les comparaison de recherche non triée
     */
    final static String FICHIERSANSTRI = "benchmarkRechercheSansTri.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int graine = (int) System.currentTimeMillis();
        ecriture(FICHIERSANSTRI, TypeRecherche.POURNONTRI, graine);
        ecriture(FICHIERTRIPOUR, TypeRecherche.POUR, graine);
        ecriture(FICHIERTRITANTQUE, TypeRecherche.TANTQUE, graine);
        ecriture(FICHIERTRIDICHOTOMIQUE, TypeRecherche.DICHO, graine);

    }

    /**
     * *
     * Fonction qui écrit dans un fichier les résultat de la recherche
     *
     * @param fichier ke nom du fichier ou stoker les infos
     * @param type le type de trie
     * @param seed le nombre aléatoire
     */
    static void ecriture(String fichier, TypeRecherche type, int seed) {
        try (PrintWriter write = new PrintWriter(fichier)) {
            write.println("nombre_de_recherches nombre_de_comparaisons");
            for (int taille = 1; taille <= 200000; taille += 20000) {
                IndiceComparaison resul = rechercheTri(taille, taille, type, seed);
                write.println(resul.indice + " " + resul.comparaison);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecherchesTri.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Fonction qui effectue le tri par sélection
     *
     * @param tab le tableau à trié
     * @param taille la taille du tableau
     * @return le nombre de comparaison
     */
    static long triParSelection(int[] tab, int taille) {
        int indice = 0;
        long compteur = 0;
        for (int i = 0; i < taille; i++) {
            int min = tab[i];
            for (int n = i; n < taille; n++) {
                if (tab[n] <= min) {
                    min = tab[n];
                    indice = n;
                    compteur++;
                }
                compteur++;
            }
            echanger(tab, i, indice);
            compteur++;
        }
        return compteur;
    }

    /**
     * fonction qui permet d'échanger de valeur dans un tableau
     *
     * @param tab le tableau
     * @param indiceMin indice minimum précédent
     * @param indiceRemplacer nouvelle indice
     */
    static void echanger(int[] tab, int indiceMin, int indiceRemplacer) {
        int temp = tab[indiceMin];
        tab[indiceMin] = tab[indiceRemplacer];
        tab[indiceRemplacer] = temp;
    }

    /**
     * Recherche d'un élément d'en un tableau trié avec la bouble pour
     *
     * @param tab tableau d'entier
     * @param n nombre à rechercher
     * @return le nombre de comparaison
     */
    static IndiceComparaison recherchePour(int[] tab, int taille, int n) {
        int result = 0;
        long compteur = 0;
        for (int i = 0; i < taille; i++) {
            if (tab[i] == n) {
                result = i;
            }
            compteur++;
        }
        return new IndiceComparaison(result, compteur);
    }

    /**
     * *
     * Fonction qui effectue le tri par sélection
     *
     * @param tab le tableau à trié
     * @param taille la taille du tableau
     * @return le nombre de comparaison faite
     */
    static long triTableau(int[] tab, int taille) {
        long comparaison = triParSelection(tab, taille);
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
        long compteur = 0;
        int index = 0;
        while (tab[index] != n) {
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
    static int[] tableauAleatoire(int taille, int seed) {
        Random rd = new Random(seed);
        int[] tab = new int[MAX_TAILLE];
        for (int i = 0; i < taille; i++) {
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
        long compteur = 1;
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

    /**
     * Fonction qui selection la recherche à effectuer
     *
     * @param tab le tableau à rechercher
     * @param type le type de recherche
     * @param taille la taille du tableau
     * @param seed le nombre aléatoire défini
     * @return le nombre de comparaison et son indice
     */
    static IndiceComparaison rechercher(int[] tab, TypeRecherche type, int taille, int seed) {
        IndiceComparaison comparaison = new IndiceComparaison(0, 0);
        Random rd = new Random();
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
            case POURNONTRI:
                comparaison = recherchePour(tab, taille, tab[rd.nextInt(taille)]);
                break;
            default:
                System.out.println("stop");
                break;
        }
        return comparaison;

    }

    /**
     * Fonction de recherche pour chaque fonction de recherche
     *
     * @param repetition le nombre de répétion de l'expérience
     * @param taille la taille du tableau courant
     * @param type le type de recherche
     * @param seed le modèle de nombre aléatoire
     * @return un tableau avec la moyenne et l'indice où il a était trouver
     */
    static IndiceComparaison rechercheTri(int repetition, int taille, TypeRecherche type, int seed) {
        int[] tab;
        long moy = 0;
        tab = tableauAleatoire(MAX_TAILLE, seed);
        if (type != TypeRecherche.POURNONTRI) {
            moy += triTableau(tab, MAX_TAILLE);
        }
        long te = 0;
        for (int i = 0; i < repetition; i++) {
            IndiceComparaison test = rechercher(tab, type, MAX_TAILLE, i);
            if (type != TypeRecherche.POURNONTRI) {
                moy += test.comparaison;
            } else {
                moy += test.indice;
            }
        }
        return new IndiceComparaison(repetition, moy);

    }

}
