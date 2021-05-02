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
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.thrift.TException;

// Generated code

import java.util.HashMap;

public class CalculatorHandler implements Calculator.Iface {




  public void ping() {
    System.out.println("ping()");
  }



   public Solution calcOperation(Terms opt) throws InvalidOperation {
    Solution sol = null;

    
    switch (opt.op) {
    case ADD:
        sol.firstValue = opt.operator1 + opt.operator2;
      break;
      
    case SUBSTRACT:
      sol.firstValue = opt.operator1 - opt.operator2;
      break;
      
    case MULTIPLY:
      sol.firstValue = opt.operator1 * opt.operator2;
      break;
      
    case DIVIDE:
     if (opt.operator2==0) {
        InvalidOperation io = new InvalidOperation();
        io.Op = opt.op.getValue();
        io.why = "Cannot divide by 0";
        throw io;
      }
      sol.firstValue = opt.operator1 / opt.operator2;
      break;
      
      case ADDVECTOR:
        sol.firstValue = opt.operator1 + opt.operator3;
        sol.secondValue = opt.operator2 + opt.operator4;
      break;
      
    case SUBSTRACTVECTOR:
        sol.firstValue = opt.operator1 - opt.operator3;
        sol.secondValue = opt.operator2 - opt.operator4;
      break;
      
    case MULTIPLYVECTOR:
      double valorx = opt.operator1 * opt.operator3;
      double valory = opt.operator2 * opt.operator4;
      
      sol.firstValue = valorx+valory;
      break;
    
    case POW:
        sol.firstValue = Math.pow(opt.operator1,opt.operator2);
      
      
      
    default:
      InvalidOperation io = new InvalidOperation();
      io.Op = opt.op.getValue();
      io.why = "Unknown operation";
      throw io;
    }

    return sol;
   }

}
