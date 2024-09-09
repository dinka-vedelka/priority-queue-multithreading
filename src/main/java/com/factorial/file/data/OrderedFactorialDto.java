package com.factorial.file.data;

public class OrderedFactorialDto implements Comparable<OrderedFactorialDto> {
    private final long number;
    private final String original;
    private final String factorial;

    public OrderedFactorialDto(long number, String original, String factorial) {
        this.number = number;
        this.original = original;
        this.factorial = factorial;
    }

    public long getNumber() {
        return number;
    }

    public String getOriginal() {
        return original;
    }

    public String getFactorial() {
        return factorial;
    }

    public String format() {
        return original + " = " + factorial;
    }

    public String numberedFormat() {
        return number + " " + original + " = " + factorial;
    }

    @Override
    public int compareTo(OrderedFactorialDto orderedFactorialDto) {
        return Long.compare(number, orderedFactorialDto.getNumber());
    }

    @Override
    public String toString() {
        return "OrderedFactorialDto{" +
                "number=" + number +
                ", original='" + original + '\'' +
                ", factorial='" + factorial + '\'' +
                '}';
    }
}
