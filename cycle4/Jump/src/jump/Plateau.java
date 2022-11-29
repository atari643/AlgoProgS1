package jump;

import java.util.Scanner;

/**
 * Un plateau.
 */
class Plateau {

    /**
     * Largeur;
     */
    int largeur;

    /**
     * Hauteur.
     */
    int hauteur;

    /**
     * Contenu des cases.
     */
    Case cases[][];

    /**
     * Probabilité de continuer une plateforme.
     */
    static final double PROBA_CONTINUER = .7;

    /**
     * Probabilité de créer une nouvelle plateforme.
     */
    static final double PROBA_CREER = .1;

    /**
     * Séparateur de champs pour la sérialisation/désérialisation.
     */
    static final String SEPARATEUR = "!";

    /**
     * Constructeur.
     *
     * @param larg largeur
     * @param haut hauteur
     */
    Plateau(int larg, int haut) {
        largeur = larg;
        hauteur = haut;
        cases = new Case[haut][larg];
    }

    /**
     * Générer un plateau aléatoire.
     *
     * @param larg largeur
     * @param haut hauteur
     * @return la plateau généré
     */
    static Plateau aleatoire(int larg, int haut) {
        Plateau plateau = new Plateau(larg, haut);
        plateau.genererPremieresColonnes();
        for (int colonne = 4; colonne < plateau.largeur; colonne++) {
            plateau.genererColonne(colonne);
        }
        return plateau;
    }

    /**
     * Faire défiler le plateau : le décaler vers la gauche et créer une
     * nouvelle dernière colonne.
     */
    void defiler() {
        decalerGauche();
        genererColonne(largeur - 1);
    }

    /**
     * Décaler d'une case vers la gauche, toutes les colonnes.
     */
    void decalerGauche() {
        for (int colonne = 0; colonne < largeur - 1; colonne++) {
            for (int ligne = 0; ligne < hauteur; ligne++) {
                cases[ligne][colonne] = cases[ligne][colonne + 1];
            }
        }
    }

    /**
     * Générer les 4 premières colonnes.
     */
    void genererPremieresColonnes() {
        // plateforme du milieu, du haut et du bas
        int ligneM = ligneMilieu();
        int ligneH = ligneHaut();
        int ligneB = ligneBas();
        // remplissage des 4 premières colonnes
        for (int colonne = 0; colonne < 4; colonne++) {
            for (int ligne = 0; ligne < hauteur; ligne++) {
                if (ligne == ligneM || ligne == ligneB || ligne == ligneH) {
                    cases[ligne][colonne] = new Case(Case.CASE_PLATEFORME);
                } else {
                    cases[ligne][colonne] = new Case(Case.CASE_VIDE);
                }
            }
        }
    }

    /**
     * Ligne du milieu (horizontalement), utilisée pour la plateforme de
     * démarrage.
     *
     * @return la ligne du milieu
     */
    int ligneMilieu() {
        return hauteur / 2;
    }

    /**
     * Ligne du haut (horizontalement) dans les premières colonnes, à la
     * création du plateau.
     *
     * @return la ligne du haut
     */
    int ligneHaut() {
        int ligneH;
        do {
            ligneH = (int) (Math.random() * (hauteur / 2));
        } while (ligneH <= 0 || ligneH >= ligneMilieu() - 1);
        return ligneH;
    }

    /**
     * Ligne du bas (horizontalement) dans les premières colonnes, à la création
     * du plateau.
     *
     * @return la ligne du bas
     */
    int ligneBas() {
        int ligneB;
        do {
            ligneB = (int) (Math.random() * (hauteur / 2)) + (hauteur / 2);
        } while ((ligneB <= ligneMilieu() + 1) || (ligneB >= hauteur - 1));
        return ligneB;
    }

    /**
     * Générer une colonne.
     *
     * @param colonne colonne à générer
     */
    void genererColonne(int colonne) {
        for (int ligne = 0; ligne < hauteur; ligne++) {
            if ((plateformeAGauche(ligne, colonne)
                    && continuerPlateforme()
                    && !Plateau.this.plateformeAuDessus(ligne, colonne))
                    || creerPlateforme(ligne, colonne)) {
                cases[ligne][colonne] = new Case(Case.CASE_PLATEFORME);
            } else {
                cases[ligne][colonne] = new Case(Case.CASE_VIDE);
            }
        }
    }

    /**
     * Indique si la case à gauche fait partie d'une plateforme.
     *
     * @param ligne ligne de la case considérée
     * @param colonne colonne de la case considérée
     * @return vrai ss'il y a une plateforme sur la case de gauche
     */
    boolean plateformeAGauche(int ligne, int colonne) {
        return colonne > 0 && cases[ligne][colonne - 1].estPlateforme();
    }

    /**
     * Indique s'il faut continuer la plateforme de gauche.
     *
     * @return vrai ss'il faut continuer la plateforme de gauche
     */
    boolean continuerPlateforme() {
        return Math.random() < PROBA_CONTINUER;
    }

    /**
     * Indique s'il faut créer une plateforme sur cette case. On suppose avoir
     * testé qu'il n'y a pas de plateforme à sa gauche.
     *
     * @param ligne ligne de la case
     * @param colonne colonne de la case
     * @return vrai ss'il faut créer une plateforme sur cette case
     */
    boolean creerPlateforme(int ligne, int colonne) {
        return (!Plateau.this.plateformeAuDessus(ligne, colonne))
                && Math.random() < PROBA_CREER;
    }

    /**
     * Indique s'il y a une plateforme au-dessus d'une case donnée. Le bord
     * supérieur est considéré comme une plateforme.
     *
     * @param ligne ligne de la case
     * @param colonne colonne de la case
     * @return vrai ss'il y a une plateforme au-dessus
     */
    boolean plateformeAuDessus(int ligne, int colonne) {
        return ligne == 0 || cases[ligne - 1][colonne].estPlateforme();
    }

    /**
     * Appliquer une action de déplacement sur le plateau.
     *
     * @param joueur joueur à l'origine de l'action
     * @param action l'action à appliquer
     * @return vrai ssi l'action est valide
     */
    boolean appliquer(Joueur joueur, Action action) {
        boolean actionValide = false;
        switch (action) {
            case HAUT:
                int iPlateformeAuDessus = plateformeAuDessus(joueur);
                actionValide = (iPlateformeAuDessus >= 0);
                if (actionValide) {
                    deplacerJoueur(joueur, iPlateformeAuDessus - 1);
                }
                break;
            case BAS:
                int iPlateformeEnDessous = plateformeEnDessous(joueur);
                actionValide = (iPlateformeEnDessous >= 0);
                if (actionValide) {
                    deplacerJoueur(joueur, iPlateformeEnDessous - 1);
                }
                break;
            case RESTER:
                // rien à faire, toujours valide à ce stade
                actionValide = true;
                break;
            default:
                System.out.println("Pas une action de déplacement : ignorée.");
                break;
        }
        return actionValide;
    }

    /**
     * Déplacer un joueur sur une plateforme donnée. On suppose ici que le
     * déplacement est valide.
     *
     * @param joueur joueur à déplacer
     * @param iPlateformeDest ligne au-dessus de la plateforme destination
     */
    void deplacerJoueur(Joueur joueur, int iPlateformeDest) {
        int ligneSrc = ligneJoueur(joueur);
        cases[ligneSrc][joueur.colonne].contenu = Case.CASE_VIDE;
        cases[iPlateformeDest][joueur.colonne].contenu
                = joueur.caracterePlateau();
    }

    /**
     * Ligne de la plateforme se situant au-dessus du joueur (ou -1 si elle
     * n'existe pas).
     *
     * @param joueur le joueur à considérer
     * @return la ligne de la plateforme au-dessus du joueur
     */
    int plateformeAuDessus(Joueur joueur) {
        int ligneSrc = ligneJoueur(joueur);
        boolean trouve = false;
        int i = ligneSrc - 1;
        while (i >= 1 && !trouve) {
            trouve = cases[i][joueur.colonne].estPlateforme();
            if (!trouve) {
                i--;
            }
        }
        if (!trouve) {
            i = -1;
        }
        return i;
    }

    /**
     * Ligne de la plateforme se situant en-dessous du joueur (ou -1 si elle
     * n'existe pas).
     *
     * @param joueur le joueur à considérer
     * @return la ligne de la plateforme en-dessous du joueur
     */
    int plateformeEnDessous(Joueur joueur) {
        return plateformeEnDessousCase(
                ligneJoueur(joueur) + 1, joueur.colonne);
    }

    /**
     * Ligne de la plateforme se situant en-dessous d'une case (ou -1 si elle
     * n'existe pas).
     *
     * @param ligne la ligne de la case considérée
     * @param colonne la colonne de la case considérée
     * @return la ligne de la plateforme en-dessous de cette case
     */
    int plateformeEnDessousCase(int ligne, int colonne) {
        boolean trouve = false;
        int i = ligne + 1;
        while (i < hauteur && !trouve) {
            trouve = cases[i][colonne].estPlateforme();
            if (!trouve) {
                i++;
            }
        }
        if (!trouve) {
            i = -1;
        }
        return i;
    }

    /**
     * Indique si la plateforme sous le joueur se poursuit sur la droite.
     *
     * @param joueur le joueur
     * @return vrai ssi la plateforme se poursuit
     */
    boolean plateformeACote(Joueur joueur) {
        return cases[ligneJoueur(joueur) + 1][joueur.colonne + 1].estPlateforme();
    }

    /**
     * Trouver la ligne du joueur dans le plateau.
     *
     * @param joueur le joueur considéré
     * @return la ligne corespondante, ou -1 s'il n'est pas présent
     */
    int ligneJoueur(Joueur joueur) {
        int ligne = hauteur - 1;
        boolean trouve = false;
        while (ligne >= 0 && !trouve) {
            trouve = cases[ligne][joueur.colonne].contenu == joueur.caracterePlateau();
            if (!trouve) {
                ligne--;
            }
        }
        return ligne;
    }

    /**
     * Trouver la première case libre sur la plateforme du milieu en début de
     * partie.
     *
     * @return indice de la colonne de la première case libre
     */
    int trouverColonneLibre() {
        int ligne = ligneMilieu() - 1;
        boolean trouve = false;
        int colonne = 0;
        while (!trouve && colonne < Partie.MAX_JOUEURS) {
            trouve = cases[ligne][colonne].estVide();
            if (!trouve) {
                colonne++;
            }
        }
        return colonne;
    }

    /**
     * Retirer un joueur du plateau.
     *
     * @param joueur
     */
    void retirerJoueur(Joueur joueur) {
        int ligne = ligneJoueur(joueur);
        if (ligne >= 0) {
            cases[ligne][joueur.colonne].contenu = Case.CASE_VIDE;
        }
    }

    /**
     * Sérialiser ce plateau, c'est-à-dire construire une représentation texte à
     * partir de son contenu.
     *
     * @return une chaîne représentant ce plateau
     */
    String serialiser() {
        StringBuilder texte = new StringBuilder();
        // premier champ : largeur
        texte.append(largeur).append(SEPARATEUR);
        // deuxième champ : hauteur
        texte.append(hauteur).append(SEPARATEUR);
        // lignes suivantes : plateau, ligne par ligne
        for (int ligne = 0; ligne < hauteur; ligne++) {
            texte.append(serialiserLigne(ligne, true)).append(SEPARATEUR);
        }
        return texte.toString();
    }

    /**
     * Sérialiser une ligne du plateau.
     *
     * @param ligne numéro de la ligne à sérialiser
     * @param avecJoueurs indique si on souhaite garder les joueurs dans la 
     * ligne retournée
     * @return une chaîne représentant la ligne de ce plateau
     */
    StringBuilder serialiserLigne(int ligne, boolean avecJoueurs) {
        StringBuilder texte = new StringBuilder();
        for (int colonne = 0; colonne < largeur; colonne++) {
            char car = cases[ligne][colonne].contenu;
            boolean caseJoueur = (car != Case.CASE_VIDE && car != Case.CASE_PLATEFORME);
            if (caseJoueur && !avecJoueurs) {
                car = Case.CASE_VIDE;
            }
            texte.append(car);
        }
        return texte;
    }

    /**
     * Désérialiser un plateau, c'est-à-dire construire la matrice à partir
     * d'une représentation texte.
     *
     * @param textePlateau texte représentant la plateau
     * @return le plateau correspondant
     */
    static Plateau deserialiser(String textePlateau) {
        Scanner scanner = new Scanner(textePlateau);
        scanner.useDelimiter(SEPARATEUR);
        // premier champ : largeur
        int largeur = scanner.nextInt();
        // deuxième champ : hauteur
        int hauteur = scanner.nextInt();
        Plateau plateau = new Plateau(largeur, hauteur);
        // lignes suivantes : plateau, ligne par ligne
        int ligne = 0;
        while (scanner.hasNext()) {
            String ligneComplete = scanner.next();
            for (int colonne = 0; colonne < largeur; colonne++) {
                plateau.cases[ligne][colonne]
                        = new Case(ligneComplete.charAt(colonne));
            }
            ligne++;
        }
        return plateau;
    }

    /**
     * Ligne horizontale pour l'affichage.
     *
     * @return ligne horizontale
     */
    StringBuilder contourHoriz() {
        StringBuilder texte = new StringBuilder();
        texte.append("+");
        for (int colonne = 0; colonne < largeur; colonne++) {
            texte.append("-");
        }
        texte.append("+");
        return texte;
    }

    /**
     * Calcule le nombre de plateformes sur une ligne donnée (vide).
     *
     * @param ligne le numéro de la ligne
     * @return le nombre de plateformes
     */
    int nbPlateformesLigne(int ligne) {
        String texteLigne = serialiserLigne(ligne, false).toString();
        int nbPlateformes = 0;
        // ...
        return nbPlateformes;
    }

    /**
     * Représentation graphique du plateau.
     * 
     * @return représentation graphique du plateau
     */
    public String toString() {
        String texte = contourHoriz() + System.lineSeparator();
        for (int ligne = 0; ligne < hauteur; ligne++) {
            texte += "|";
            for (int colonne = 0; colonne < largeur; colonne++) {
                texte += cases[ligne][colonne].toString();
            }
            texte += "|" + System.lineSeparator();
        }
        texte += contourHoriz() + System.lineSeparator();
        return texte;
    }
}
