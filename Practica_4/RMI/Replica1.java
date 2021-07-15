import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Replica1 {
    public static void main(String[] args){
        if(System.getSecurityManager()==null){
            System.setSecurityManager(new SecurityManager());
        }
        
        try{
            String nombreServidor = "Replica1", nombreReplica ="Replica2";
            Registry reg = LocateRegistry.createRegistry(1099);
            Donaciones Replica1 = new Donaciones(nombreServidor, nombreReplica, "localhost");
            Naming.rebind(nombreServidor, Replica1);
            System.out.println("Servidor "+ nombreServidor + " a la espera...");
        } catch(RemoteException | MalformedURLException e){
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
}
