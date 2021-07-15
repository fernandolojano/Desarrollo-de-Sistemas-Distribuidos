import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Cliente {
    public static void main(String[] args){
        if(System.getSecurityManager()== null){
            System.setSecurityManager(new SecurityManager());
        }
        
        String host = "localhost";
        String servidor = "Replica1";
        String opcion,nombreUsuario="", password;
        float importe, totalDonado, totalUsuario;
        int numDonaciones;
        String tipoEntidad;
        
        Scanner in = new Scanner(System.in);
        
        try{
            Registry mireg = LocateRegistry.getRegistry(host, 1099);
            Donaciones_I gestorDonaciones = (Donaciones_I)mireg.lookup(servidor);
            
            boolean identificado = false, salir = false;
            
            while(!salir){
                if(!identificado){
                    System.out.println("Seleccione una de las siguientes opciones:\n"+
                            "\tI: Identificarse\n"+
                            "\tR: Registrarse\n"+
                            "\tS: Salir\n");
                    opcion = in.nextLine().toUpperCase();
                    
                    switch (opcion) {
                        case "R":
                            System.out.println("\n------------Registro------------\n");
                            System.out.println("Intorduzca el nombre de usuario: ");
                            nombreUsuario = in.nextLine();
                            System.out.println("Entidad publica (0) o privada(1)?: ");
                            tipoEntidad = in.nextLine();
                            System.out.println("Introduzca la contraseña de su cuenta: ");
                            password = in.nextLine();
                            
                            if(gestorDonaciones.intentoRegistrar(nombreUsuario, tipoEntidad, password))
                                System.out.println("\nUsted se ha registrado correctamtente.\n");
                            else
                                System.out.println("\nError en el registro: Username already exists.\n");
                            
                            Thread.sleep(2000);
                        break;
                        
                        case "I":
                            System.out.println("\n------------Login------------\n");
                            System.out.println("Intorduzca el nombre de usuario: ");
                            nombreUsuario = in.nextLine();
                            System.out.println("Introduzca la contraseña de su cuenta: ");
                            password = in.nextLine();  
                            
                            if(gestorDonaciones.identificarUsuario(nombreUsuario, password)){
                                identificado = true;
                                servidor = gestorDonaciones.getServidorUsuario(nombreUsuario);
                                gestorDonaciones = (Donaciones_I)mireg.lookup(servidor);
                                System.out.println("\nSe ha iniciado sesión como " + nombreUsuario + 
                                        " en " + servidor + "\n");                        
                            }
                            else
                                System.out.println("\nUsuario o contraseña incorrecta\n");
                            
                            Thread.sleep(2000);
                        break;
                        
                        case "S":
                            salir = true;
                            System.out.println("\nGracias por utilizar nuestro servicio!");
                            Thread.sleep(2000);
                        break;
                        
                        default:
                            System.out.println("\nLa opción "+opcion+" no existe\n");
                            Thread.sleep(2000);
                        break;     
                    }
                }
                else{
                    System.out.println("Hola "+nombreUsuario + 
                            ", selecciona una de las siguientes opciones:\n"  
                            + "\tD: Realizar una donación\n"
                            + "\tC: Consultar total donado\n"
                            + "\tP: Consultar total donado de mi tipo de entidad\n"
                            + "\tM: Consultar media de donaciones\n"
                            + "\tN: Consultar diferencia de donaciones entre entidades\n"
                            + "\tS: Cerrar sesión\n");
                    opcion = in.nextLine().toUpperCase();
                    
                    
                    switch (opcion) {
                        case "D":
                            System.out.println("\n------------Donacion------------\n");
                            do{
                                System.out.println("Intorduzca el importe a donar: ");
                                importe = Float.parseFloat(in.nextLine());  
                            }while(importe <=0);
                            
                            gestorDonaciones.donar(nombreUsuario, importe);
                            
                            System.out.println("\nGracias "+nombreUsuario+" por donar"+ importe+" eur\n");
                            Thread.sleep(2000);
                        break;
                        
                        case "C":
                            numDonaciones = gestorDonaciones.getNumDonaciones(nombreUsuario);
                            
                            if(numDonaciones >0){
                                totalDonado = gestorDonaciones.getTotalDonado();
                                
                                System.out.println("La cantidad total donada hasta ahora es "+totalDonado+" eur\n");                                
                            }
                            else
                                System.out.println("Debe realizar una donaciona para poder ver el total donado\n");
                            
                            Thread.sleep(2000);
                        break;
                        
                          case "P":
                            Usuario  tmp = gestorDonaciones.getUsuario(nombreUsuario);
                            float totalEntidad;

                            if(tmp.getEntidad() == "publica"){
                                totalEntidad = gestorDonaciones.getTotalPub();
                            }

                            else totalEntidad = gestorDonaciones.getTotalPriv();

                            if(totalEntidad > 0)
                                System.out.println("\nSu tipo de entidad ha donado " + totalEntidad + "€\n");
                            else
                                System.out.println("\nAún no se ha realizado ninguna donación. Se agradece cualquier participación\n");

                            Thread.sleep(2000);
                            break;

                        case "N":
                            float diferenciaDonaciones = gestorDonaciones.getDiferenciaEntidades();

                            if(diferenciaDonaciones > 0)
                                System.out.println("\nUsted ha realizado un total de " + diferenciaDonaciones + " donaciones.\n");
                            else
                                System.out.println("\nAún no ha realizado ninguna donación. Se agradece cualquier participación\n");

                            Thread.sleep(2000);
                            break;

                         case "M":
                            float media = gestorDonaciones.getMedia(nombreUsuario);

                            if(media > 0)
                                System.out.println("\nLa media de las donaciones es " + media + " donaciones.\n");
                            else
                                System.out.println("\nAún no ha realizado ninguna donación. Se agradece cualquier participación\n");

                            Thread.sleep(2000);
                            break;

                        case "S":
                            identificado = false;
                            System.out.println("\nHasta pronto!\n");

                            Thread.sleep(2000);
                            break;
                            
                          default:
                            System.out.println("\nLa opción " + opcion + " no existe\n");

                            Thread.sleep(2000);
                            break;
                    }
                    
                }
            }
        }catch (NotBoundException | RemoteException | InterruptedException e){
            System.err.println("Exception del sistema: "+e);
        }
    }
    
}
