import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class Donaciones extends UnicastRemoteObject implements Donaciones_I{
    private ArrayList<Usuario> usuarios;
    private String nombre;
    private String replica;
    private float subtotalPub;
    private float subtotalPriv;
    private float subtotal;
    private int numDonaciones;

    private String host;
    
    public Donaciones(String nombre, String replica, String host) throws RemoteException {
        usuarios = new ArrayList<>();
        this.nombre = nombre;
        this.replica = replica;
        this.host = host;
        this.numDonaciones =0;
    }
    
    @Override
    public boolean identificarUsuario(String nombre, String password)throws RemoteException{
       boolean identificado = false;
       Usuario usuario = getUsuarioTotal(nombre);
       
       if(usuario != null){
           if(usuario.getPassword().equals(password))
               identificado = true;
       }
       return identificado;
    }
    
    @Override
    public boolean intentoRegistrar(String usuario, String tipo,  String password) throws RemoteException {
        boolean exito = false;
        String entidad;
        
        boolean existe = this.existeUsuario(nombre);
        if(tipo == "0"){
            entidad = "publica";
        }

        else entidad = "privada";
        
        if(!existe){
            Donaciones_I replica = this.getReplica(this.replica);
            
            int numUsuarios = this.getNumUsuarios();
            int numUsuariosReplica = replica.getNumUsuarios();
            
            if(numUsuariosReplica < numUsuarios)
                replica.registrar(usuario, tipo, password);
            else this.registrar(usuario, tipo, password);
            
            exito = true;
        }    
        return exito;    
    }
    
    @Override
    public String getServidorUsuario(String usuario) throws RemoteException {
        String servidor = this.nombre;
        
        boolean existe = this.existeUsuario(usuario);
        
        if(!existe)
            servidor = this.replica;
        
        return servidor;
    }
    
    @Override 
    public int getNumUsuarios() throws RemoteException {
        return usuarios.size();
    }
    
    @Override 
    public String getNombre() throws RemoteException {
        return this.nombre;
    }
    
    @Override
    public void registrar(String nombre, String entidad, String password) throws RemoteException {
        usuarios.add(new Usuario(nombre, entidad, password));
        System.out.println("Nuevo cliente registrado: " + nombre);
    }
    
    @Override
    public boolean existeUsuario(String nombre) throws RemoteException {
        boolean existe = true;
        
        Usuario usuario = getUsuario(nombre);
        
        if(usuario == null) existe = false;
        
        return existe;
    }
    
    @Override
    public boolean existeUsuarioTotal(String nombe) throws RemoteException {
        Usuario usuario = getUsuarioTotal(nombre);
         return usuario != null;
    }
    
    
    @Override
    public void donar(String nombreUsuario, float cantidad) throws RemoteException {
        Usuario usuario = getUsuario(nombreUsuario);
        if(usuario.getEntidad()=="publica"){
            usuario.hacerDonacion(cantidad);
            this.subtotalPub += cantidad;
            numDonaciones++;
        }

        else{
             usuario.hacerDonacion(cantidad);
            this.subtotalPriv += cantidad;
            numDonaciones++;
        }



        this.subtotal+=cantidad;

       
    }
    
    
    @Override 
    public Usuario getUsuario(String nombreUsuario) throws RemoteException {
        Usuario usuario = null;
        
        for(Usuario user : usuarios) {
            if(user.getNombre().equals(nombreUsuario)){
                usuario = user;
                break;
            }
        } 
        return usuario; 
    }
    
    @Override 
    public Usuario getUsuarioTotal(String nombreUsuario) throws RemoteException {
        Usuario usuario = this.getUsuario(nombreUsuario);
        
        if(usuario == null){
            Donaciones_I replica = this.getReplica(this.replica);
            usuario = replica.getUsuario(nombreUsuario);
        }
        
        return usuario;
    }
    
    @Override
    public float getTotalUsuario(String nombreUsuario) throws RemoteException {
        return getUsuario(nombreUsuario).getTotalDonado();
    }
    
     @Override
    public int getNumDonaciones(String usuario) throws RemoteException {
        return getUsuario(usuario).getNumDonaciones();
    }

    @Override
    public float getSubtotal() throws RemoteException {
        return this.subtotal;
    }

    @Override
    public float getTotalDonado() throws RemoteException {
        return this.getSubtotal() + this.getReplica(this.replica).getSubtotal();
    }
    
    @Override
    public Donaciones_I getReplica(String nombre) throws RemoteException {
        Donaciones_I replica = null;
        
        try{
            Registry mireg = LocateRegistry.getRegistry(host, 1099);
            replica = (Donaciones_I)mireg.lookup(nombre);
        }catch(NotBoundException | RemoteException e){
            System.err.println("Exception del sistema: " + e);
        }
        return replica;
    }

    @Override
    public float getSubtotalPriv() throws RemoteException {
        return this.subtotalPriv;
    }

     @Override
    public float getSubtotalPub() throws RemoteException {
        return this.subtotalPub;
    }


    @Override
    public float getTotalPriv() throws RemoteException {
        return this.getSubtotalPriv() + this.getReplica(this.replica).getSubtotalPriv();
    }

    @Override
    public float getTotalPub() throws RemoteException {
        return this.getSubtotalPub() + this.getReplica(this.replica).getSubtotalPub();
    }

    @Override
    public float getDiferenciaEntidades() throws RemoteException{
        return this.getSubtotalPriv() - this.getSubtotalPub();
    }

    @Override
    public float getMedia(String nombreUsuario) throws RemoteException{
       Usuario usuario = this.getUsuario(nombreUsuario);
       return usuario.getTotalDonado()/usuario.getNumDonaciones();
    }
}
