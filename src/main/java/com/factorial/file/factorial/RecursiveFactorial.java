package com.factorial.file.factorial;

import java.math.BigInteger;

public class RecursiveFactorial implements Factorial {

    @Override
    public BigInteger compute(int factorial) {
        return factorial <= 1
                ? BigInteger.valueOf(1)
                : BigInteger.valueOf(factorial)
                    .multiply(compute(factorial - 1));
    }
}
