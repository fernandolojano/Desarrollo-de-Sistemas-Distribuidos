import java.io.Serializable;


public class Usuario implements Serializable{
    
    private String nombre;
    private String password;
    private int numDonaciones;
    private int totalDonado;
    private String entidad;
    
    public Usuario(String nombre, String entidad, String password){
        this.nombre = nombre;
        this.password = password;
        this.numDonaciones = 0;
        this.totalDonado = 0;
        this.entidad = entidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public int getNumDonaciones() {
        return numDonaciones;
    }

    public int getTotalDonado() {
        return totalDonado;
    }

    public String getEntidad(){
        return entidad;
    }

    public void setEntidad(String tipo){
        this.entidad = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNumDonaciones(int numDonaciones) {
        this.numDonaciones = numDonaciones;
    }

    public void setTotalDonado(int totalDonado) {
        this.totalDonado = totalDonado;
    }
    
    public void hacerDonacion(float cantidad) {
        numDonaciones++;
        totalDonado += cantidad;
    }
    
    
    
}
