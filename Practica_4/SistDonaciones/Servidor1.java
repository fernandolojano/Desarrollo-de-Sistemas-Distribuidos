import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Servidor1{
	public static void main(String[] args){
		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}

		try{
			String nombreServidor1 = "Servidor1";
			String nombreServidor2 = "Servidor2";

			Registry registry = LocateRegistry.getRegistry();
			Donacion Servidor1 = new Donacion(nombreServidor1, nombreServidor2, "localhost");
			System.out.println(nombreServidor1 + " esperando...");

		} catch(Exception e){
			System.err.println("Servidor1 exception:");
			e.printStackTrace();

		}
	}
}