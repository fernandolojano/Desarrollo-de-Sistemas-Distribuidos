import Calculadora

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol
from ttypes import InvalidOperation, Operator, action

transport = TSocket.TSocket("localhost", 9090)
transport = TTransport.TBufferedTransport(transport)
protocol = TBinaryProtocol.TBinaryProtocol(transport)

client = Calculadora.Client(protocol)

transport.open()

print("hacemos ping al server")
client.ping()

resultado = client.suma(1, 1)
print("1 + 1 = " + str(resultado))
resultado = client.resta(1, 1)
print("1 - 1 = " + str(resultado))

CalcOperaciones = action()
CalcOperaciones.op = Operator.SUBSTRACT
CalcOperaciones.operator1 = 15
CalcOperaciones.operator2 = 10

defResult = client.calcOperation(CalcOperaciones)
print('15-10=' + str(defResult))



transport.close()
