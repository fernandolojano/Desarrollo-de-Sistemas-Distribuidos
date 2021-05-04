import java.util.ArrayList;
import java.rmi.*;

public class Donacion implements Donacion_I{
	private ArrayList<User> listaUsuarios;
	private String nombreDonante;
	private String nombreReplica;
	private float cantidadSubtotal;
	private String host;

	public Donacion(String nombreUsuario, String nombreReplica, String host) throws RemoteException{
		listaUsuarios = new ArrayList<>();
		this.nombreDonante = nombreUsuario;
		this.nombreReplica = nombreReplica;
		this.host = host;
	}

	@Overrride
	public boolean identificar(String usuario, String password) throws RemoteException{
		for(usuario user : listaUsuarios){
			if(user.getUsername().equals(usuario)){
				if(user.getPassword().equals(password))
					return true;
			}
		}

		return false;
	}

	@Override
	public boolean registrar(String usuario, String password) throws RemoteException{


	}


	@Override 
	public Donacion_I getReplica(String nombreReplica) throws RemoteException{
		Donacion_I replica = null;

		try {
			Registry Registry = LocateRegistry.getRegistry();
			replica = (Donacion_I) Registry.lookup(nombreReplica);
		} catch (Exception e) {
			System.err.println("Excepcion Donacion:");
			e.printStackTrace();

		}

		return replica;

	}
}