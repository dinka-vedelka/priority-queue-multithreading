package com.factorial.file.factorial.factory;

import com.factorial.file.factorial.Factorial;
import com.factorial.file.factorial.IterativeFactorial;

public class IterativeFactorialFactory implements FactorialFactory {
    private static final Factorial cachedFactorial = new IterativeFactorial();

    @Override
    public Factorial getInstance() {
        //  Using as singleton
        return cachedFactorial;
    }
}
