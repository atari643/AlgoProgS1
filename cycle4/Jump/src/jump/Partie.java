package jump;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Une partie.
 */
class Partie {

    /**
     * Nombre maximal de joueurs.
     */
    static final int MAX_JOUEURS = 4;

    /**
     * Joueurs connectés. Ce tableau ne contient pas de trou : les cases
     * remplies sont celles les plus à gauche.
     */
    Joueur[] joueurs;

    /**
     * Nombre de joueurs connectés.
     */
    int nbJoueurs;

    /**
     * Le plateau de cette partie.
     */
    Plateau plateau;

    /**
     * État de la partie.
     */
    EtatPartie etat;

    /**
     * Numéro du tour courant.
     */
    int numTour;

    /**
     * Largeur du plateau de jeu.
     */
    final static int LARGEUR_PLATEAU = 50;

    /**
     * Hauteur du plateau de jeu.
     */
    final static int HAUTEUR_PLATEAU = 20;

    /**
     * Nom du fichier de sauvegarde d'une partie.
     */
    final static String FICHIER_SAUVEGARDE = "jump-partie.txt";

    /**
     * Nom du fichier des scores.
     */
    final static String FICHIER_SCORES = "jump-scores.txt";

    /**
     * Séparateur de champs pour la sérialisation/désérialisation.
     */
    final static String SEPARATEUR = ":";

    /**
     * Constructeur d'une partie.
     */
    Partie() {
        joueurs = new Joueur[MAX_JOUEURS];
        nbJoueurs = 0;
        plateau = Plateau.aleatoire(LARGEUR_PLATEAU, HAUTEUR_PLATEAU);
        etat = EtatPartie.ATTENTE_CONNEXIONS;
        numTour = 0;
    }

    /**
     * Démarrer la partie.
     *
     * @return l'état du jeu à afficher sur les clients
     */
    String demarrer() {
        StringBuilder message = new StringBuilder();
        etat = EtatPartie.EN_COURS;
        numTour = 1;
        for (int i = 0; i < nbJoueurs; i++) {
            joueurs[i].statut = StatutJoueur.DOIT_JOUER;
        }
        message.append(UI.vueGlobale(this))
                .append(System.lineSeparator())
                .append("### Début du jeu !");
        return message.toString();
    }

    /**
     * Traiter une action de mouvement (haut / bas / rester).
     *
     * @param joueur le joueur
     * @param action l'action à traiter
     * @return l'affichage du jeu
     */
    String bouger(Joueur joueur, Action action) {
        StringBuilder message = new StringBuilder();
        if (joueur.statut == StatutJoueur.DOIT_JOUER) {
            boolean actionValide = plateau.appliquer(joueur, action);
            if (actionValide) {
                joueur.statut = StatutJoueur.A_JOUE;
                message.append(UI.vueGlobale(this))
                        .append("# ")
                        .append(joueur.nom)
                        .append(" a joué : ")
                        .append(action.toString());
            } else {
                perdu(joueur);
                message.append(UI.vueGlobale(this))
                        .append("# ")
                        .append(joueur.nom)
                        .append(" est éliminé : ")
                        .append(action.toString())
                        .append(" impossible.");
            }
        }
        return message.toString();
    }

    /**
     * Ajouter un joueur.
     *
     * @param joueur le joueur à ajouter
     * @return le code de résultat de l'action d'ajout
     */
    ResultatAjout ajouterJoueur(Joueur joueur) {
        ResultatAjout ajout;
        if (nbJoueurs >= MAX_JOUEURS) {
            ajout = ResultatAjout.REFUS_SERVEUR_PLEIN;
        } else if (!joueur.nomCorrect()) {
            ajout = ResultatAjout.REFUS_FORMAT_NOM_INCORRECT;
        } else if (dejaConnecte(joueur.nom)) {
            ajout = ResultatAjout.REFUS_NOM_UTILISE;
        } else if (etat != EtatPartie.ATTENTE_CONNEXIONS) {
            ajout = ResultatAjout.REFUS_PARTIE_COMMENCEE;
        } else {
            joueurs[nbJoueurs] = joueur;
            nbJoueurs++;
            placerJoueurAGauche(joueur);
            ajout = ResultatAjout.ACCEPTE;
        }
        return ajout;
    }

    /**
     * Placer un nouveau joueur sur la plateforme du milieu.
     * 
     * @param joueur le joueur à ajouter
     */
    void placerJoueurAGauche(Joueur joueur) {
        int ligne = plateau.ligneMilieu() - 1;
        int colonne = plateau.trouverColonneLibre();
        plateau.cases[ligne][colonne].contenu = joueur.caracterePlateau();
        joueur.colonne = colonne;
    }

    /**
     * Indique si un joueur de même initiale est déjà connecté avec ce nom.
     *
     * @param nomJoueur le nom du noueau joueur
     * @return vrai ssi un joueur de même initiale est déjà connecté
     */
    boolean dejaConnecte(String nomJoueur) {
        return trouverJoueur(nomJoueur) != -1;
    }

    /**
     * Retirer un joueur de la partie.
     *
     * @param nomJoueur joueur quittant la partie
     */
    void retirerJoueur(String nomJoueur) {
        int i = trouverJoueur(nomJoueur);
        if (i >= 0) {
            // le retirer du plateau
            plateau.retirerJoueur(joueurs[i]);
            // le retirer de la liste des joueurs
            decalageGauche(i);
            nbJoueurs--;
            joueurs[nbJoueurs] = null;
        }
    }

    /**
     * Renvoie l'indice d'un joueur dans le tableau des joueurs. Il ne peut
     * apparaître qu'une fois.
     *
     * @param nomJoueur nom du joueur à chercher
     * @return l'indice du joueur, ou -1 s'il n'est pas présent.
     */
    int trouverJoueur(String nomJoueur) {
        boolean trouve = false;
        int i = 0;
        int res = -1;
        while (!trouve && i < nbJoueurs) {
            if (joueurs[i].nom.equals(nomJoueur)) {
                trouve = true;
                res = i;
            }
            i++;
        }
        return res;
    }

    /**
     * Renvoie un joueur depuis son nom, ou null s'il n'existe pas.
     *
     * @param nomJoueur le nom du joueur
     * @return le joueur
     */
    Joueur joueurDeNom(String nomJoueur) {
        int iJoueur = trouverJoueur(nomJoueur);
        Joueur joueur = null;
        if (iJoueur >= 0) {
            joueur = joueurs[iJoueur];
        }
        return joueur;
    }

    /**
     * Décaler les noms des joueurs d'une case vers la gauche.
     *
     * @param i indice à partir duquel on décale (première case écrasée).
     */
    void decalageGauche(int i) {
        int j = i;
        while (j + 1 < nbJoueurs) {
            joueurs[j] = joueurs[j + 1];
            j++;
        }
    }

    /**
     * Indique si la fin du tour est atteinte, c'est-à-dire si aucun joueur ne
     * doit encore jouer durant ce tour.
     *
     * @return vrai ssi la fin de tour est atteinte
     */
    boolean finDeTour() {
        boolean finDeTour = true;
        int i = 0;
        while (i < nbJoueurs && finDeTour) {
            if (joueurs[i].statut == StatutJoueur.DOIT_JOUER) {
                finDeTour = false;
            }
            i++;
        }
        return finDeTour;
    }

    /**
     * Indique si la partie est finie, c'est-à-dire si tous les joueurs ont
     * quitté.
     *
     * @return vrai ssi la fin de partie est atteinte
     */
    boolean finDePartie() {
        boolean finDePartie = true;
        int i = 0;
        while (i < nbJoueurs && finDePartie) {
            if (joueurs[i].statut != StatutJoueur.A_QUITTE) {
                finDePartie = false;
            }
            i++;
        }
        return finDePartie;
    }

    /**
     * Fin d'un tour : le plateau avance d'une case.
     */
    void tourSuivant() {
        deplacerChaqueJoueur();
        plateau.decalerGauche();
        plateau.genererColonne(plateau.largeur - 1);
        numTour++;
        majStatutsEtScoresJoueurs();
    }

    /**
     * Mettre à jour les statuts des joueurs en fin de tour, et incrémenter les
     * scores des joueurs encore en jeu.
     */
    void majStatutsEtScoresJoueurs() {
        for (int i = 0; i < nbJoueurs; i++) {
            Joueur joueur = joueurs[i];
            if (joueur.statut != StatutJoueur.A_QUITTE) {
                joueurs[i].score++;
                joueurs[i].statut = StatutJoueur.DOIT_JOUER;
            }
        }
    }

    /**
     * Déplacer chaque joueur d'une colonne vers la droite.
     */
    void deplacerChaqueJoueur() {
        // calcul de la future position de chaque joueur
        int[] nouvellesLignesJoueurs = new int[nbJoueurs];
        for (int i = 0; i < nbJoueurs; i++) {
            if (joueurs[i].statut != StatutJoueur.A_QUITTE) {
                nouvellesLignesJoueurs[i] = nouvelleLigneJoueur(joueurs[i]);
            }
        }
        // déplacement de chaque joueur sur le plateau
        for (int i = nbJoueurs - 1; i >= 0; i--) {
            int nouvelleLigne = nouvellesLignesJoueurs[i];
            if (nouvelleLigne == -1) {
                perdu(joueurs[i]);
            } else if (joueurs[i].statut != StatutJoueur.A_QUITTE) {
                deplacerUnJoueur(joueurs[i], nouvelleLigne);
            }
        }
    }

    /**
     * Déplacer un joueur d'une colonne vers la droite.
     *
     * @param joueur le joueur à déplacer
     * @param nouvelleLigne la nouvelle ligne du joueur
     */
    void deplacerUnJoueur(Joueur joueur, int nouvelleLigne) {
        int ancienneLigne = plateau.ligneJoueur(joueur);
        plateau.cases[ancienneLigne][joueur.colonne].contenu = Case.CASE_VIDE;
        plateau.cases[nouvelleLigne][joueur.colonne + 1].contenu
                = joueur.caracterePlateau();
    }

    /**
     * Ligne d'un joueur après défilement d'une colonne vers la gauche, ou -1 si
     * le joueur a perdu.
     *
     * @param joueur le joueur
     * @return la nouvelle ligne du joueur, ou -1 s'il a perdu
     */
    int nouvelleLigneJoueur(Joueur joueur) {
        int ancienneLigne = plateau.ligneJoueur(joueur);
        int nouvelleLigne = -1;
        if (plateau.plateformeACote(joueur)) {
            // la plateforme continue : le joueur reste dessus
            nouvelleLigne = ancienneLigne;
        } else {
            // plateforme en-dessous dans la colonne suivante ?
            int plateformeDessous = plateau.plateformeEnDessousCase(ancienneLigne, joueur.colonne + 1);
            if (plateformeDessous < 0) {
                perdu(joueur);
            } else {
                nouvelleLigne = plateformeDessous - 1;
            }
        }
        return nouvelleLigne;
    }

    /**
     * Mettre à jour le statut d'un joueur qui vient de perdre.
     *
     * @param joueur le perdant
     */
    void perdu(Joueur joueur) {
        joueur.statut = StatutJoueur.A_QUITTE;
    }

    /**
     * Score maximal parmi tous les joueurs.
     *
     * @return score maximal parmi tous les joueurs
     */
    int scoreMax() {
        int max = -1;
        for (int i = 0; i < nbJoueurs; i++) {
            if (joueurs[i].score > max) {
                max = joueurs[i].score;
            }
        }
        return max;
    }

    /**
     * La liste des vainqueurs, sous forme textuelle.
     *
     * @return la liste des vainqueurs
     */
    String listeVainqueurs() {
        StringBuilder liste = new StringBuilder();
        int scoreMax = scoreMax();
        for (int i = 0; i < nbJoueurs; i++) {
            if (joueurs[i].score == scoreMax) {
                liste.append(joueurs[i].nom).append(" ");
            }
        }
        return liste.toString();
    }

    /**
     * Réinitialiser la partie (une fois qu'elle est finie).
     */
    void reinitialiser() {
        plateau = Plateau.aleatoire(LARGEUR_PLATEAU, HAUTEUR_PLATEAU);
        etat = EtatPartie.ATTENTE_CONNEXIONS;
        numTour = 0;
        for (int i = 0; i < nbJoueurs; i++) {
            Joueur joueur = joueurs[i];
            joueur.statut = StatutJoueur.A_JOUE;
            joueur.score = 0;
            placerJoueurAGauche(joueur);
        }
    }

    /**
     * Sauvegarder la partie en cours.
     */
    void sauvegarder() {
    }

    /**
     * Restaurer la partie sauvegardée.
     *
     * @return renvoie vrai ssi la partie a pu être restaurée
     */
    boolean restaurer() {
        // lecture du fichier
        String contenu = null;
        try (Scanner scanner = new Scanner(new File(FICHIER_SAUVEGARDE))) {
            if (scanner.hasNextLine()) {
                contenu = scanner.nextLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Fichier non trouvé : " + ex);
        }
        // traitement du contenu du fichier
        Partie sauvegarde = deserialiser(contenu, this);
        boolean resultat = false;
        if (sauvegarde != null) {
            resultat = true;
            // on écrase cette partie par celle du fichier de sauvegarde
            nbJoueurs = sauvegarde.nbJoueurs;
            joueurs = sauvegarde.joueurs;
            etat = sauvegarde.etat;
            plateau = sauvegarde.plateau;
            numTour = sauvegarde.numTour;
        }
        return resultat;
    }

    /**
     * Sérialiser la partie, c'est-à-dire construire une chaîne avec toutes ses
     * informations.
     *
     * @return la sérialisation de la partie.
     */
    String serialiser() {
        StringBuilder serial = new StringBuilder();
        // numéro du tour
        serial.append(numTour).append(SEPARATEUR);
        // nombre de joueurs
        serial.append(nbJoueurs).append(SEPARATEUR);
        // état de la partie (en attente, en cours ou terminée)
        serial.append(etat.ordinal()).append(SEPARATEUR);
        // liste des joueurs : nom, colonne, score, statut
        for (int i = 0; i < nbJoueurs; i++) {
            serial.append(joueurs[i].serialiser()).append(SEPARATEUR);
        }
        // plateau
        serial.append(plateau.serialiser()).append(SEPARATEUR);
        return serial.toString();
    }

    /**
     * Construire une partie à partir de sa sérialisation.
     *
     * @param serial une chaîne représentant une partie
     * @param partieEnCours la partie courante, pour récupérer les clients
     * @return la partie correspondante
     */
    static Partie deserialiser(String serial, Partie partieEnCours) {
        Partie partie = new Partie();
        Scanner scanner = new Scanner(serial);
        scanner.useDelimiter(SEPARATEUR);
        // numéro du tour
        partie.numTour = scanner.nextInt();
        // nombre de joueurs
        partie.nbJoueurs = scanner.nextInt();
        if (partie.nbJoueurs != partieEnCours.nbJoueurs) {
            partie = null;
        } else {
            // état de la partie (en attente, en cours ou terminée)
            partie.etat = EtatPartie.values()[scanner.nextInt()];
            // liste des joueurs : nom, colonne, score, statut
            for (int i = 0; i < partie.nbJoueurs; i++) {
                partie.joueurs[i] = Joueur.deserialiser(scanner.next());
                partie.joueurs[i].client = partieEnCours.joueurs[i].client;
            }
            // plateau
            partie.plateau = Plateau.deserialiser(scanner.next());
        }
        return partie;
    }

    /**
     * Sauvegarde des scores dans le fichier de scores.
     */
    void sauverScores() {
    }
    
    /**
     * Nombre de parties jouées par un joueur donné dans le fichier de scores.
     * 
     * @param nomJoueur nom du joueur
     * @return nombre de parties jouées par ce joueur
     */
    static int nombrePartiesJouees(String nomJoueur) {
        return 0;
    }
    
    /**
     * Sauvegarde des 10 meilleurs scores dans le fichier de scores.
     */
    void sauverTopScores() {
    }    
}
