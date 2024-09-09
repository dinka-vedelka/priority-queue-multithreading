package com.factorial.file.factorial.factory;

import com.factorial.file.factorial.Factorial;
import com.factorial.file.factorial.RecursiveFactorial;

public class RecursiveFactorialFactory implements FactorialFactory {
    private static final RecursiveFactorial cachedFactorial = new RecursiveFactorial();

    @Override
    public Factorial getInstance() {
        //  Using as singleton{
        return cachedFactorial;
    }
}
