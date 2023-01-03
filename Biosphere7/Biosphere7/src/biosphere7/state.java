/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biosphere7;

/**
 *
 * @author atari
 */
public class state {

    int hashCase(Case c) {
        int hash = 0;
        int plantTypeInt = 0;
        int plantColorInt = 0;
        if (c.plantePresente()) {
            plantTypeInt = 1;
            switch (c.couleur) {
                case 'R':
                    plantColorInt = 1;
                    break;
                case 'B':
                    plantColorInt = 2;
                    break;
                default:
                    plantColorInt = 0;
                    break;
            }
        }
        hash = (plantTypeInt * 100) + (plantColorInt * 10) + (c.vitalite * 1);
        return hash;
    }

    int hashMatrice(Case[][] matrice) {
        int hash = 0;
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                if (hashCase(matrice[i][j]) != 0) {
                    hash += hashCase(matrice[i][j]) + i * 14 + j;
                }
            }
        }
        return hash;
    }
}
