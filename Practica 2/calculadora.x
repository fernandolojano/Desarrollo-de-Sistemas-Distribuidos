/* archivo .x: Calculadora.x*/

struct datos {
 double operador1;
 double operador2;
 double operador3;
 double operador4;
	
};

program CALCULADORA {
	version CALCULADORAV0{
		double sumar(datos) = 1;
		double restar(datos) = 2;
		double multiplicar(datos) = 3;
		double dividir(datos) = 4;
		double potencia(datos) = 5;
		datos sumarVector(datos) = 7;
		datos restarVector(datos) = 8;
		double multiplicarVector(datos) = 9;


	} = 1;
} = 0x20000001;
