package jump;

/**
 * Journalisation : type d'entrée dans le journal (log).
 */
enum TypeLog {
    /**
     * Connexion d'un joueur.
     */
    AJOUT,
    /**
     * Déconnexion d'un joueur.
     */
    SUPPRESSION,
    /**
     * Message d'information.
     */
    INFO,
    /**
     * Message d'erreur.
     */
    ERREUR;
    
    /**
     * Affichage côté serveur.
     * 
     * @return chaîne préfixant une entrée de journal de ce type dans le serveur
     */
    String formatServeur() {
        String chaine = "";
        switch (this) {
            case AJOUT:
                chaine = "[+]";
                break;
            case SUPPRESSION:
                chaine = "[-]";
                break;
            case INFO:
                chaine = "[i]";
                break;
            case ERREUR:
                chaine = "[e]";
                break;
            default:
                System.out.println("Cas non géré : " + this);
                break;
        }
        return chaine;
    }
}
