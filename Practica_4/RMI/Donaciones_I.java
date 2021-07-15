import java.rmi.Remote;
import java.rmi.RemoteException;


public interface Donaciones_I extends Remote {
    boolean intentoRegistrar(String usuario, String tipo, String password) throws RemoteException;
    void donar(String nombreUsuario, float cantidad) throws RemoteException;
    float getTotalUsuario(String usuario) throws RemoteException;
    boolean existeUsuario(String nombre) throws RemoteException;
    int getNumUsuarios() throws RemoteException;
    String getNombre() throws RemoteException;
    void registrar(String nombre, String entidad,String password) throws RemoteException;
    boolean identificarUsuario(String nombre, String password) throws RemoteException;
    String getServidorUsuario(String usuario) throws RemoteException;
    Donaciones_I getReplica(String nombre) throws RemoteException;
    Usuario getUsuario(String nombreUsuario) throws RemoteException;
    int getNumDonaciones(String usuario) throws RemoteException;
    float getSubtotal() throws RemoteException;
    float getTotalDonado() throws RemoteException;
    boolean existeUsuarioTotal(String nombre) throws RemoteException;
    Usuario getUsuarioTotal(String nombreUsuario) throws RemoteException;
    float getTotalPub()throws RemoteException;
    float getTotalPriv()throws RemoteException;
    float getDiferenciaEntidades() throws RemoteException;
    float getMedia(String nombreUsuario) throws RemoteException;
    float getSubtotalPriv() throws RemoteException;
    float getSubtotalPub() throws RemoteException;
}
