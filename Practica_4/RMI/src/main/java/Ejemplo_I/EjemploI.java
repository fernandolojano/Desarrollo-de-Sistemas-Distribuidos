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


import java.rmi.Remote;
import java.rmi.RemoteException;


public interface EjemploI extends Remote {
    public void escrbir_mensaje(int id_proceso) throws RemoteException;
}

