

public class User {
	private String username;
	private String password;
	private int cantidadDonada = 0;
	private int totalDonaciones = 0;


	User(String username, String password){
		this.username = username;
		this.password = password;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public void setCantidadDonada(int cantidadDonada){
		this.cantidadDonada = cantidadDonada;
	}

	public void setTotalDonaciones(int totalDonaciones){
		this.totalDonaciones = totalDonaciones;
	}

	public String getUsername(){
		return this.username;
	}

	public String getPassword(){
		return this.password;
	}

	public int getCantidadDonada(){
		return this.cantidadDonada;
	}

	public int getTotalDonaciones(){
		return this.totalDonaciones;
	}

	public void donar( int cantidad){
		cantidadDonada = cantidadDonada + cantidad;
		totalDonaciones++;
	}
}