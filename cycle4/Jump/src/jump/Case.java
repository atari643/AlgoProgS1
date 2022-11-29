package jump;

/**
 * Une case de la carte.
 */
public class Case {
    
    /**
     * Contenu de la case, sous la forme d'un caractère.
     */
    char contenu;

    /**
     * Contenu d'une case vide.
     */
    final static char CASE_VIDE = ' ';
    
    /**
     * Contenu d'une case plateforme.
     */
    final static char CASE_PLATEFORME = '=';
    
    /**
     * Constructeur pour une case vide.
     */
    Case() {
        contenu = CASE_VIDE;
    }
    
    /**
     * Constructeur à partir d'un contenu.
     * 
     * @param unContenu contenu de la case.
     */
    Case(char unContenu) {
        contenu = unContenu;
    }

    /**
     * Indique si la case est vide.
     * 
     * @return vrai ssi la case est vide
     */
    boolean estVide() {
        return contenu == CASE_VIDE;
    }

    /**
     * Indique si la case fait partie d'une plateforme.
     * 
     * @return vrai ssi la case fait partie d'une plateforme
     */
    boolean estPlateforme() {
        return contenu == CASE_PLATEFORME;
    }
    
    /**
     * Représentation texte d'une case.
     * 
     * @return représentation texte d'une case
     */
    @Override
    public String toString() {
        return "" + contenu;
    }
}
