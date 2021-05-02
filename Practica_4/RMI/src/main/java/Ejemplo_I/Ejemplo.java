/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejemplo_I;

/**
 *
 * @author reko
 */

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.lang.Thread;


public class Ejemplo implements EjemploI {
    
    public Ejemplo(){
        super();
    }
    

    @Override
    public void escrbir_mensaje(int id_proceso) throws RemoteException {
      
        System.out.println("Recibida peticion de proceso: "+id_proceso);
        if(id_proceso == 0){
            try{
                System.out.println("Empezamos a dormir");
                Thread.sleep(5000);
                System.out.println("Terminamos de dormir");
            }
            
            catch (Exception e){
                System.err.println("Ejemplo exception:");
                e.printStackTrace();
            }
        }
        System.out.println("\nHebra"+id_proceso);
    }
    
    public static void main(String[] args){
        if(System.getSecurityManager()== null){
            System.setSecurityManager(new SecurityManager());
        }
        
        try{
            String nombre_objeto_remoto = "EjemploI";
            EjemploI prueba = new Ejemplo();
            EjemploI stub =
                    (EjemploI) UnicastRemoteObject.exportObject(prueba, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(nombre_objeto_remoto, stub);
            System.out.println("Ejemplo bound");   
        } catch (Exception e){
            System.err.println("Ejemplo exception:");
            e.printStackTrace();
        }
        
    }
}
