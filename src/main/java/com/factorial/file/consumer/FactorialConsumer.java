package com.factorial.file.consumer;

import com.factorial.file.data.OrderedFactorialDto;

public interface FactorialConsumer {
    void consume(OrderedFactorialDto orderedFactorialDto);

    String acquire();

    boolean isEmpty();

    int size();
}
