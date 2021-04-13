enum Operator {
    ADD = 1,
    SUBSTRACT = 2,
    MULTIPLY = 3,
    DIVIDE = 4,
    ADDVECTOR = 5,
    SUBSTRACTVECTOR = 6,
    MULTIPLYVECTOR = 7,
}


struct action {
    1: double operator1 = 0,
    2: double operator2 = 0,
    3: double operator3 = 0,
    4: double operator4 = 0,
    5: Operator op,

}

exception InvalidOperation {
1: i32 Op,
2: string why
}

service Calculadora{
   void ping(),
   i32 suma(1:i32 num1, 2:i32 num2),
   i32 resta(1:i32 num1, 2:i32 num2),
   double calcOperation(1: action opt) throws (1:InvalidOperation error ),
}
