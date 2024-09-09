package com.factorial;

import com.factorial.file.FileService;
import com.factorial.file.InputFileReaderService;
import com.factorial.file.OutputFileWriterService;
import com.factorial.file.consumer.FactorialConsumer;
import com.factorial.file.consumer.OrderedFactorialConsumer;
import com.factorial.file.factorial.factory.FactorialFactory;
import com.factorial.file.factorial.factory.IterativeFactorialFactory;
import com.factorial.file.processor.FactorialProcessor;
import com.factorial.file.processor.OrderedFactorialProcessor;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Application runner
 * */
public class Application {

    public static void main(String[] args) throws Exception {
        final int threads = userPromptForThreads();

        final ExecutorService executorService = Executors.newFixedThreadPool(threads);
        final FactorialFactory factorialFactory = new IterativeFactorialFactory();
        final FactorialConsumer factorialConsumer = new OrderedFactorialConsumer();
        final FactorialProcessor factorialProcessor = new OrderedFactorialProcessor(executorService,
                factorialFactory, factorialConsumer);

        final FileService readerFileService = new InputFileReaderService(factorialProcessor, "./input.txt");
        final FileService writerFileService = new OutputFileWriterService(factorialConsumer, "./output.txt");

        readerFileService.start();
        writerFileService.start();

        System.out.println("Factorials computing started");
    }

    private static int userPromptForThreads() {
        System.out.println("Welcome to factorials computing!");
        final Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Please enter desired number ot threads for tasks execution (max allowed = 1000):");
            System.out.print("number>");
            String input = scanner.nextLine();

            if (input == null || !input.trim().matches("^\\b([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|1000)\\b")) {
                System.out.println("Invalid input. Try again.");
            } else {
                System.out.println("Success");
                return Integer.parseInt(input);
            }
        } while (true);
    }
}
