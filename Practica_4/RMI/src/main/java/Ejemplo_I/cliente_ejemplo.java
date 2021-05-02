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

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class cliente_ejemplo {
    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        try {
            String nombre_objeto_remoto = "EjemploI";
            System.out.println("Buscando el objeto remoto");
            Registry registry = LocateRegistry.getRegistry(args[0]);
            EjemploI instancia_local = (EjemploI) registry.lookup(nombre_objeto_remoto);
            System.out.println("Invocando el objeto remoto");
            instancia_local.escrbir_mensaje(Integer.parseInt(args[1]));
        } catch (Exception e) {
            System.err.println("EjemploI exception:");
            e.printStackTrace();
        }
           
        }
}
