package jump;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface du serveur.
 */
public interface IServeur extends Remote {

    /**
     * Connecter un joueur au serveur.
     * 
     * @param nomJoueur nom du joueur
     * @param client client (callback) du joueur
     * @return le résultat de l'ajout
     * @throws RemoteException si problème de connexion
     */
    public ResultatAjout connecterJoueur(String nomJoueur, ICallback client) 
            throws RemoteException;

    /**
     * Un joueur envoie une action au serveur.
     * 
     * @param nomJoueur nom du joueur
     * @param action l'action du joueur
     * @throws RemoteException si problème de connexion
     */
    public void envoyerActionAuServeur(String nomJoueur, Action action)
            throws RemoteException;
}
