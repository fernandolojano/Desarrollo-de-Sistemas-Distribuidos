import glob
import sys

import Calculadora
from ttypes import InvalidOperation, Operator, Solution

from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from thrift.server import TServer

import logging

logging.basicConfig(level=logging.DEBUG)


class CalculadoraHandler:
    def __init__(self):
        self.log = {}

    def ping(self):
        print("me han hecho ping()")

    def calcOperation(self, operators):
        sol = Solution()

        if operators.operator3 == 0 and operators.operator4 == 0:
            print("Operacion solicitada: " + str(operators.operator1) +" " + str(operators.op) + " " + str(operators.operator2))
        else:
            print("Operacion solicitada: " + str(operators.operator1) + "x " + str(operators.operator2) + "y " + str(operators.op) + " " + str(operators.operator3) + "x " + str(operators.operator4) + "y " )


        if operators.op == Operator.ADD:
            sol.firstValue = operators.operator1 + operators.operator2
        elif operators.op == Operator.SUBSTRACT:
            sol.firstValue = operators.operator1 - operators.operator2
        elif operators.op == Operator.MULTIPLY:
            sol.firstValue = operators.operator1 * operators.operator2
        elif operators.op == Operator.DIVIDE:
            if operators.operator2==0:
                raise InvalidOperation(operators.op, "No se puede dividir entre 0")
            sol.firstValue = operators.operator1 / operators.operator2
        elif operators.op == Operator.POW:
            sol.firstValue = operators.operator1 ** operators.operator2
        elif operators.op == Operator.ADDVECTOR:
            sol.firstValue = operators.operator1 + operators.operator3
            sol.secondValue = operators.operator2 + operators.operator4
        elif operators.op == Operator.SUBSTRACTVECTOR:
            sol.firstValue = operators.operator1 - operators.operator3
            sol.secondValue = operators.operator2 - operators.operator4
        elif operators.op == Operator.MULTIPLYVECTOR:
            valx = operators.operator1 * operators.operator3
            valy = operators.operator2 * operators.operator4
            sol.firstValue = valx+valy
        else:
            raise InvalidOperation(operators.op, "Operacion invalida")

        return sol



if __name__ == "__main__":
    handler = CalculadoraHandler()
    processor = Calculadora.Processor(handler)
    transport = TSocket.TServerSocket(host="127.0.0.1", port=9090)
    tfactory = TTransport.TBufferedTransportFactory()
    pfactory = TBinaryProtocol.TBinaryProtocolFactory()

    server = TServer.TSimpleServer(processor, transport, tfactory, pfactory)

    print("iniciando servidor...")
    server.serve()
    print("fin")
