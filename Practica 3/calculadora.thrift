enum Operator {
    ADD = 1,
    SUBSTRACT = 2,
    MULTIPLY = 3,
    DIVIDE = 4,
    ADDVECTOR = 5,
    SUBSTRACTVECTOR = 6,
    MULTIPLYVECTOR = 7,
    POW = 8,
}


struct Terms {
    1: double operator1 = 0,
    2: double operator2 = 0,
    3: double operator3 = 0,
    4: double operator4 = 0,
    5: Operator op,

}

struct Solution{
    1: double firstValue = 0,
    2: double secondValue = 0,
}

exception InvalidOperation {
1: i32 Op,
2: string why
}

service Calculadora{
   void ping(),
   Solution calcOperation(1: Terms opt) throws (1:InvalidOperation error ),
}
