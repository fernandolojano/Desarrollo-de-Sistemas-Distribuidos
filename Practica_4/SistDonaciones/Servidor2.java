import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class Servidor2{
	public static void main(String[] args){
		if(System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}

		try{
			String nombreServidor1 = "Servidor1";
			String nombreServidor2 = "Servidor2";

			//Registry registry = LocateRegistry.getRegistry();
			Donacion Servidor2 = new Donacion(nombreServidor2, nombreServidor1, "localhost");
			System.out.println(nombreServidor2 + " esperando...");

		} catch(Exception e){
			System.err.println("Servidor1 exception:");
			e.printStackTrace();

		}
	}
}