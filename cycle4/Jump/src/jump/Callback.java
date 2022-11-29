package jump;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Callback pour que le client reçoive des informations du serveur.
 */
public class Callback extends UnicastRemoteObject implements ICallback, Serializable {
   
    /**
     * Constructeur.
     * 
     * @throws RemoteException exception en cas de problème de communication
     */
    public Callback() throws RemoteException {
    }
    
    /**
     * Le serveur envoie un message au client.
     * 
     * @param message message à envoyer
     * @throws RemoteException 
     */
    @Override
    public void messageDuServeur(String message) throws RemoteException {
        // affichage dans la console du client
        System.out.println(message);
    }
    
}
