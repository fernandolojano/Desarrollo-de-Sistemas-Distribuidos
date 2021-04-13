/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r98.calculadorathrift;

/**
 *
 * @author reko
 */



import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

public class JavaClient {
  public static void main(String [] args) {

    double ope1=0;
    double ope2=0;
    double ope3=0;
    double ope4=0;
    String op="";
    
        
    System.out.println(args.length);
    if(args.length < 3){
        
        System.out.println("Formato de entrada Incorrecto");
        System.exit(0);
    }
    
    
    
    if(args.length == 3){
  
    ope1 = Double.parseDouble(args[0]);
    op = args[1];
    ope2 = Double.parseDouble(args[2]);
    ope3=0;
    ope4=0;
     
    }
    
    if(args.length==5){
    ope1 = Double.parseDouble(args[0]);
    op = args[1];
    ope2 = Double.parseDouble(args[2]);
    ope3= Double.parseDouble(args[3]);
    ope4= Double.parseDouble(args[4]);
    }
    
    
    try {
      TTransport transport;
     
        transport = new TSocket("localhost", 9090);
        transport.open();
     

      TProtocol protocol = new  TBinaryProtocol(transport);
      Calculator.Client client = new Calculator.Client(protocol);

      perform(client, op, ope1, ope2, ope3, ope4);

      transport.close();
    } catch (TException x) {
      x.printStackTrace();
    } 
  }

  private static void perform(Calculator.Client client, String operacion, double valor1, double valor2, double valor3, double valor4) throws TException
  {
    client.ping();
    System.out.println("ping()");

    Terms work = new Terms();
    
    switch(operacion){
        case "+":
            if(valor3==0 && valor4==0)
                work.op = Operator.ADD;
            
            else work.op = Operator.ADDVECTOR;
            
        break;
        
        case "-":
            if(valor3==0 && valor4==0)
                work.op = Operator.SUBSTRACT;
            
            else work.op = Operator.SUBSTRACTVECTOR;
            
        break;
        
        case "*":
            if(valor3==0 && valor4==0)
                work.op = Operator.MULTIPLY;
            
            else work.op = Operator.MULTIPLYVECTOR;
        break;
        
        case "/":
            if(valor3==0 && valor4==0)
                work.op = Operator.DIVIDE;
  
            else {
                System.out.println("Operacion no permitida");
                System.exit(0);
            }
        break;
        
        case "^":
            if(valor3==0 && valor4==0)
                work.op = Operator.POW;
            else {
                System.out.println("Operacion no permitida");
                System.exit(0);
            }
        break;
            
        default:
            System.out.println("Operacion no permitida");
            System.exit(0);
        break;
    }
    
    work.operator1=valor1;
    work.operator2=valor2;
    work.operator3=valor3;
    work.operator4=valor4;
    

    try {
      Solution diff = client.calcOperation(work);
      System.out.println(valor1 + operacion + valor2 +"=" + diff);
    } catch (InvalidOperation io) {
      System.out.println("Invalid operation: " + io.why);
    }

   
  }
}