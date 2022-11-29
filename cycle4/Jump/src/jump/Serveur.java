package jump;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;

/**
 * Serveur de jeu.
 */
public class Serveur extends UnicastRemoteObject implements IServeur, Runnable {

    /**
     * La partie en cours gérée par le serveur.
     */
    Partie partie;

    /**
     * Port utilisé par le serveur.
     */
    final static int PORT = 52369;

    /**
     * Nom du fichier de logs (journalisation).
     */
    final static String LOG_FILENAME = "jump.log";

    /**
     * Fichier de logs (journalisation).
     */
    static PrintWriter logfile;

    /**
     * Constructeur.
     *
     * @throws RemoteException erreur de communication
     */
    public Serveur() throws RemoteException {
        super(0);
        partie = new Partie();
    }

    /**
     * Connecter un joueur au serveur.
     *
     * @param nomJoueur nom du joueur
     * @param client client (callback) du joueur
     * @return le résultat de l'ajout
     * @throws RemoteException si problème de connexion
     */
    @Override
    public ResultatAjout connecterJoueur(String nomJoueur, ICallback client) throws RemoteException {
        ResultatAjout ajout = partie.ajouterJoueur(new Joueur(nomJoueur, client));
        ajout.afficherServeur(nomJoueur);
        envoyerMessageTous(UI.vueGlobale(partie));
        return ajout;
    }

    /**
     * Déconnecter un joueur du serveur.
     *
     * @param nomJoueur nom du joueur
     * @throws RemoteException si problème de connexion
     */
    public void deconnecterJoueur(String nomJoueur) throws RemoteException {
        partie.retirerJoueur(nomJoueur);
        log(TypeLog.SUPPRESSION, "déconnexion : " + nomJoueur);
        envoyerMessageTous(UI.vueGlobale(partie));
        envoyerMessageTous("# " + nomJoueur + " a quitté la partie.");
    }

    /**
     * Envoyer un message à tous les joueurs.
     *
     * @param message message à transmettre aux joueurs
     * @throws RemoteException si problème de connexion.
     */
    void envoyerMessageTous(String message) throws RemoteException {
        log(TypeLog.INFO, "envoi d'un message à tous les joueurs");
        for (int j = 0; j < partie.nbJoueurs; j++) {
            ICallback cl = partie.joueurs[j].client;
            if (cl != null) {
                cl.messageDuServeur(message);
            }
        }
    }

    /**
     * Un joueur envoie une action au serveur.
     *
     * @param nomJoueur nom du joueur
     * @param action l'action du joueur
     * @throws RemoteException si problème de connexion
     */
    @Override
    public void envoyerActionAuServeur(String nomJoueur, Action action)
            throws RemoteException {
        log(TypeLog.INFO, "action reçue : " + action);
        Joueur joueur = partie.joueurDeNom(nomJoueur);
        switch (action) {
            case DEMARRER:
                envoyerMessageTous(partie.demarrer());
                break;
            case QUITTER:
                deconnecterJoueur(nomJoueur);
                break;
            case HAUT:
            case BAS:
            case RESTER:
                if (partie.etat == EtatPartie.EN_COURS) {
                    envoyerMessageTous(partie.bouger(joueur, action));
                }
                break;
            case SAUVEGARDER:
                partie.sauvegarder();
                envoyerMessageTous("# Partie sauvegardée.");
                break;
            case RESTAURER:
                boolean ok = partie.restaurer();
                if (ok) {
                    envoyerMessageTous(UI.vueGlobale(partie));
                    envoyerMessageTous("# Partie restaurée.");
                } else {
                    envoyerMessageTous("# Échec de la restauration de la partie"
                            + " (le nombre de joueurs a probablement changé).");
                }
                break;
            default:
                log(TypeLog.ERREUR, "action non gérée : " + action);
                break;
        }
        if (partie.finDeTour()) {
            partie.tourSuivant();
            envoyerMessageTous(UI.vueGlobale(partie));
            if (partie.finDePartie()) {
                envoyerMessageTous("# Partie terminée !");
                log(TypeLog.INFO, "partie terminée");
                envoyerMessageTous("# Vainqueur(s) : "
                        + partie.listeVainqueurs());
                partie.sauverScores();
                partie.reinitialiser();
                envoyerMessageTous("# Nouvelle partie :");
                envoyerMessageTous(UI.vueGlobale(partie));
            }
        }
    }

    /**
     * Renvoie l'URL RMI du serveur, construit à partir du nom d'hôte.
     *
     * @param hote nom de l'hôte
     * @return l'URL du serveur
     */
    static String urlDepuisHote(String hote) {
        return "rmi://" + hote + ":" + PORT + "/Jump";
    }

    /**
     * Enregistrement d'une entrée dans les logs.
     *
     * @param type type d'entrée
     * @param message message à journaliser
     */
    static void log(TypeLog type, String message) {
        Calendar c = Calendar.getInstance();
        System.out.format("%s %td/%tm/%ty %tT %s%s", type.formatServeur(),
                c, c, c, c, message, System.lineSeparator());
    }

    /**
     * Action principale.
     *
     * @param args arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        log(TypeLog.INFO, "Démarrage du serveur.");
        // récupération de l'argument passé en ligne de commande
        if (args.length != 1) {
            log(TypeLog.ERREUR, "Arguments attendus : hote. Abandon.");
            System.exit(1);
        }
        String hote = args[0];
        // lancement du registre RMI, s'il n'existe pas déjà.
        try {
            LocateRegistry.createRegistry(PORT);
        } catch (RemoteException e) {
            // le registre existe déjà : on ignore
        }
        // instantiation du serveur
        Serveur serveur = null;
        try {
            serveur = new Serveur();
        } catch (RemoteException ex) {
            log(TypeLog.ERREUR, ex.getMessage());
            log(TypeLog.ERREUR, "Échec du lancement du serveur. Abandon.");
            System.exit(1);
        }
        String url = Serveur.urlDepuisHote(hote);
        try {
            // Enregistrer le serveur dans le registre RMI.
            Naming.rebind(url, serveur);
        } catch (RemoteException e) {
            log(TypeLog.ERREUR, "Échec de la connexion du serveur au registre RMI à l'adresse : " + url + ". Abandon.");
            log(TypeLog.ERREUR, e.getMessage());
            System.exit(1);
        } catch (MalformedURLException e) {
            log(TypeLog.ERREUR, "URL incorrecte : " + url + ". Abandon.");
            log(TypeLog.ERREUR, e.getMessage());
            System.exit(1);
        }
        // création du thread
        Thread thread = new Thread(serveur);
        thread.start();
        log(TypeLog.INFO, "Serveur prêt !");
    }

    /**
     * Action principale du thread du serveur.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                log(TypeLog.ERREUR, "Exception thread.");
            }
        }
    }
}
