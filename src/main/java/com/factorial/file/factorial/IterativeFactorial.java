package com.factorial.file.factorial;

import java.math.BigInteger;

public class IterativeFactorial implements Factorial {

    @Override
    public BigInteger compute(final int factorial) {
        BigInteger computed = BigInteger.ONE;

        for (int i = 1; i <= factorial; i++) {
            computed = computed.multiply(BigInteger.valueOf(i));
        }

        return computed;
    }
}
