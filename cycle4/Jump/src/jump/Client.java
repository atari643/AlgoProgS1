package jump;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Client pour le jeu.
 */
class Client {

    /**
     * Le serveur sur lequel le client se connecte.
     */
    IServeur serveur;

    /**
     * Objet Scanner pour la saisie clavier.
     */
    Scanner scanner;

    /**
     * Callback pour récupérer des informations transmises par le serveur.
     */
    Callback callback;

    /**
     * Référence vers la seule instance de cette classe (singleton).
     */
    private static Client SINGLETON_CLIENT = null;

    /**
     * Récupérer la seule instance de cette classe (pattern singleton).
     *
     * @return la seule instance de cette classe
     */
    @SuppressWarnings("DoubleCheckedLocking")
    public static final Client getInstance() {
        if (SINGLETON_CLIENT == null) {
            synchronized (Client.class) {
                if (SINGLETON_CLIENT == null) {
                    SINGLETON_CLIENT = new Client();
                }
            }
        }
        return SINGLETON_CLIENT;
    }

    /**
     * Constructeur (privé, pour le pattern singleton).
     */
    private Client() {
        scanner = new Scanner(System.in);
        try {
            callback = new Callback();
        } catch (RemoteException e) {
            System.out.println("Problème de connection du callback. Abandon.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Connexion au serveur.
     *
     * @param adresse URL du serveur
     */
    void connexionServeur(String adresse) {
        try {
            serveur = (IServeur) Naming.lookup(adresse);
        } catch (NotBoundException ex) {
            System.out.println("Le serveur ne semble pas connecté à l'adresse : " + adresse + " : abandon.");
            System.exit(1);
        } catch (MalformedURLException ex) {
            System.out.println("URL incorrecte : abandon.");
            System.exit(1);
        } catch (RemoteException ex) {
            System.out.println("Problème de communication : abandon. Vérifiez que le serveur est lancé.");
            System.exit(1);
        }
    }

    /**
     * Lancement du client.
     * 
     * @param hote Nom du serveur hôte
     * @throws java.rmi.RemoteException en cas de problème de communication
     */
    void lancer(String hote) throws RemoteException {
        // connexion au serveur
        String url = Serveur.urlDepuisHote(hote);
        System.out.print(". connexion au serveur " + url + "... ");
        connexionServeur(url);
        System.out.println("ok");
        // saisie de l'identifiant, et connexion
        String nomJoueur;
        ResultatAjout inscrit;
        do {
            System.out.print(". identifiant : ");
            nomJoueur = scanner.next();
            inscrit = serveur.connecterJoueur(nomJoueur, callback);
            inscrit.afficherClient();
        } while (!ResultatAjout.ACCEPTE.equals(inscrit));
        // boucle principale
        Action action;
        do {
            char saisie = scanner.next().toLowerCase().charAt(0);
            action = Action.depuisCaractere(saisie);
            if (action != null) {
                serveur.envoyerActionAuServeur(nomJoueur, action);
            } else {
                System.out.println("Action non reconnue : " + saisie);
            }
        } while (action != Action.QUITTER);
        System.exit(0);
    }

    /**
     * Vérifie qu'il n'y a qu'un seul argument.
     *
     * @param args les arguments passés en ligne de commande
     */
    static void verifierArgs(String[] args) {
        if (args.length != 1) {
            System.out.println("Un et un seul argument doit être passé en paramètre, et non pas " + args.length);
            System.out.println("arguments attendus : hote.");
            System.exit(1);
        }
    }

    /**
     * Action principale.
     *
     * @param args arguments de la ligne de commande
     * @throws java.rmi.RemoteException en cas de problème de communication
     */
    public static void main(String[] args) throws RemoteException {
        verifierArgs(args);
        getInstance().lancer(args[0]);
    }
}
