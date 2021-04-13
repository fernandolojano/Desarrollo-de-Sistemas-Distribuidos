/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r98.calculadorarpc;

/**
 *
 * @author reko
 */


import org.apache.thrift.TException;



import java.util.HashMap;

public class CalculatorHandler implements Calculator.Iface {


  public void ping() {
    System.out.println("ping()");
  }

  public int suma(int n1, int n2) {
    System.out.println("suma(" + n1 + "," + n2 + ")");
    return n1 + n2;
  }
  
  public int resta(int n1, int n2){
    System.out.println("resta(" + n1 + "," + n2 + ")");
    return n1 - n2;
  }

  public double calcOperation(action work) throws InvalidOperation {
   
    double val = 0;
    switch (work.op) {
    case ADD:
      val = work.operator1 + work.operator2;
      break;
    case SUBSTRACT:
      val = work.operator1- work.operator2;
      break;
    case MULTIPLY:
      val = work.operator1 * work.operator2;
      break;
    case DIVIDE:
      if (work.operator2 == 0) {
        InvalidOperation io = new InvalidOperation();
        io.Op = work.op.getValue();
        io.why = "Cannot divide by 0";
        throw io;
      }
      val = work.operator1 / work.operator2;
      break;
    default:
      InvalidOperation io = new InvalidOperation();
      io.Op = work.op.getValue();
      io.why = "Unknown operation";
      throw io;
    }
    return val;
  }
   
}



