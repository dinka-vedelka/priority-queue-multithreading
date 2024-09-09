package com.factorial.file.consumer;

import com.factorial.file.data.OrderedFactorialDto;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Component queues factorials in numbered order and converts to strings when requested
 * making it uniform for writing to text files
 * */
public class OrderedFactorialConsumer implements FactorialConsumer {
    private final BlockingQueue<OrderedFactorialDto> BLOCKING_QUEUE = new PriorityBlockingQueue<>();

    @Override
    public void consume(final OrderedFactorialDto orderedFactorialDto) {
        BLOCKING_QUEUE.add(orderedFactorialDto);
    }

    @Override
    public String acquire() {
        try {
            final OrderedFactorialDto orderedFactorialDto = BLOCKING_QUEUE.poll(5, TimeUnit.SECONDS);
            return orderedFactorialDto == null ? null : orderedFactorialDto.format();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean isEmpty() {
        return BLOCKING_QUEUE.isEmpty();
    }

    @Override
    public int size() {
        return BLOCKING_QUEUE.size();
    }
}
