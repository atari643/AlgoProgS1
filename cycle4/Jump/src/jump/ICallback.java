package jump;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface de callback (serveur vers client).
 */
public interface ICallback extends Remote {
    
    /**
     * Le serveur envoie un message à un joueur.
     * 
     * @param message message à envoyer
     * @throws RemoteException 
     */
    public void messageDuServeur(String message) throws RemoteException;
}
