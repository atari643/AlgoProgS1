package jump;

import java.util.Scanner;

/**
 * Un joueur.
 */
class Joueur {

    /**
     * Nom du joueur.
     */
    String nom;

    /**
     * Colonne où se trouve le joueur.
     */
    int colonne;

    /**
     * Le client utilisé par ce joueur.
     */
    ICallback client;

    /**
     * Score.
     */
    int score;

    /**
     * Statut du joueur en cours de partie.
     */
    StatutJoueur statut;

    /**
     * Séparateur de champs pour la sérialisation/désérialisation.
     */
    final static String SEPARATEUR = "!";

    /**
     * Constructeur, sans client associé.
     *
     * @param unNom nom du joueur
     */
    Joueur(String unNom) {
        this(unNom, null);
    }

    /**
     * Constructeur.
     *
     * @param unNom nom du joueur
     * @param unClient client du joueur
     */
    Joueur(String unNom, ICallback unClient) {
        nom = unNom;
        client = unClient;
        score = 0;
        statut = StatutJoueur.A_JOUE;
    }

    /**
     * Retourne le nom tronqué sur 10 caractères (ou le nom s'il est composé de
     * 10 caractères ou moins).
     *
     * @param unNom le nom
     * @return le nom tronqué
     */
    static String tronquerNom(String unNom) {
        String nomJoueur = unNom;
        if (!nomJoueur.isEmpty() && nomJoueur.length() > 10) {
            nomJoueur = unNom.substring(0, 10);
        }
        return nomJoueur; // TODO
    }

    /**
     * Caractère représentant ce joueur sur le plateau.
     *
     * @return caractère représentant ce joueur sur le plateau
     */
    char caracterePlateau() {
        return nom.charAt(0);
    }

    /**
     * Indique si le nom du joueur est correct, c'est-à-dire s'il commence par
     * une lettre majuscule non accentuée.
     *
     * @return vrai ssi le nom du joueur est correct
     */
    boolean nomCorrect() {
        boolean caract = false;
        if (!nom.isEmpty()) {
            char initial = caracterePlateau();
            if (initial >= 'A' && initial <= 'Z'
                    && !nom.contains(Plateau.SEPARATEUR)
                    && !nom.contains(Joueur.SEPARATEUR)
                    && !nom.contains(Partie.SEPARATEUR)) {
                caract = true;
            }

        }
        return caract;
    }

    /**
     * Sérialiser un joueur.
     *
     * @return une chaîne représentant ce joueur
     */
    String serialiser() {
        StringBuilder info = new StringBuilder();
        info.append(nom)
                .append(SEPARATEUR)
                .append(colonne)
                .append(SEPARATEUR)
                .append(score)
                .append(SEPARATEUR)
                .append(statut.serialiser())
                .append(SEPARATEUR);
        return info.toString();
    }

    /**
     * Désérialiser un joueur.
     *
     * @param serial la sérialisation du joueur
     * @return le joueur obtenu à partir d'une sérialisation donnée.
     */
    static Joueur deserialiser(String serial) {
        Joueur newJoueur;
        try ( Scanner scanner = new Scanner(serial)) {
            scanner.useDelimiter(SEPARATEUR);
            newJoueur = new Joueur(scanner.next());
            newJoueur.colonne = scanner.nextInt();
            newJoueur.score = scanner.nextInt();
            newJoueur.statut = StatutJoueur.deserialiser(scanner.next());
        }
        return newJoueur;
    }

    /**
     * Ligne correspondant à ce joueur dans le fichier de scores.
     *
     * @return la ligne correspondant à ce joueur dans le fichier de scores
     */
    String ligneFichierScore() {
        StringBuilder ligneJoueur = new StringBuilder();
        ligneJoueur.append(nom).append(SEPARATEUR).append(score);
        return ligneJoueur.toString();
    }
}
