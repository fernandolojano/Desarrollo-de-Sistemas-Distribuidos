import java.rmi.*;


public class Donacion_I extends Remote {
	void donar(String usuario, float cantidad) throws RemoteException;
	boolean registrar(String usuario, String password) throws RemoteException;
	boolean identificar(String usuario, String password) throws RemoteException;
	Donacion_I getReplica(String nombreReplica) throws RemoteException;
	
}