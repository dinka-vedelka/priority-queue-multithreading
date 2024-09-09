package com.factorial.file.processor;

import com.factorial.file.consumer.FactorialConsumer;
import com.factorial.file.data.OrderedFactorialDto;
import com.factorial.file.factorial.factory.FactorialFactory;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Processor of input file lines; used for computing factorials
 * */
public class OrderedFactorialProcessor implements FactorialProcessor {
    private static final int THREADS_THROTTLING_THRESHOLD = 100;
    private final ExecutorService executorService;
    private final FactorialFactory factorialFactory;
    private final FactorialConsumer factorialConsumer;
    private static final AtomicInteger runningThreads = new AtomicInteger();
    private static final AtomicInteger lineNumber = new AtomicInteger(1);

    public OrderedFactorialProcessor(ExecutorService executorService, FactorialFactory factorialFactory,
                                     FactorialConsumer factorialConsumer) {
        this.executorService = executorService;
        this.factorialFactory = factorialFactory;
        this.factorialConsumer = factorialConsumer;
    }

    @Override
    public void process(final String factorialData) {
        executorService.execute(() -> computeFactorial(factorialData));
    }

    private void computeFactorial(final String factorialData) {
        runningThreads.incrementAndGet();

        //  Throttling
        if (runningThreads.get() >= THREADS_THROTTLING_THRESHOLD) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //  For empty or non-numeric entries
        if (factorialData.isEmpty() || !isNumeric(factorialData)) {
            final OrderedFactorialDto NOP = new OrderedFactorialDto(lineNumber.getAndIncrement(),
                    factorialData, factorialData);
            factorialConsumer.consume(NOP);
        } else {
            final int factorial = Integer.parseInt(factorialData.trim());
            final BigInteger computedFactorial = factorialFactory.getInstance().compute(factorial);
            final OrderedFactorialDto orderedFactorialDto = new OrderedFactorialDto(lineNumber.getAndIncrement(),
                    factorialData, computedFactorial.toString());
            factorialConsumer.consume(orderedFactorialDto);
        }

        runningThreads.decrementAndGet();
    }

    private static boolean isNumeric(final String factorialData) {
        return factorialData.trim().matches("^[1-9]*$");
    }
}
