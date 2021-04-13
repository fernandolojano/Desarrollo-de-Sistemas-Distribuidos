import glob
import sys

import Calculadora
from ttypes import InvalidOperation, Operator

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

    def suma(self, n1, n2):
        print("sumando " + str(n1) + " con " + str(n2))
        return n1 + n2

    def resta(self, n1, n2):
        print("restando " + str(n1) + " con " + str(n2))
        return n1 - n2

    def calcOperation(self, operators):
        if operators.op == Operator.ADD:
            val = operators.operator1 + operators.operator2
        elif operators.op == Operator.SUBSTRACT:
            val = operators.operator1 - operators.operator2
        elif operators.op == Operator.MULTIPLY:
            val = operators.operator1 * operators.operator2
        elif operators.op == Operator.DIVIDE:
            if operators.operator2==0:
                raise InvalidOperation(operators.op, "No se puede dividir entre 0")
            val = operators.operator1 / operators.operator2
        else:
            raise InvalidOperation(operators.op, "Operacion invalida")

        return val




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
