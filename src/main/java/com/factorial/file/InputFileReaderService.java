package com.factorial.file;

import com.factorial.file.processor.FactorialProcessor;
import com.factorial.lang.AlreadyRunningException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service component for reading and parsing an input file
 * */
public class InputFileReaderService implements FileService {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final FactorialProcessor factorialProcessor;
    private final String fileLocation;
    private boolean isRunning = false;

    public InputFileReaderService(FactorialProcessor factorialProcessor, String fileLocation) {
        this.factorialProcessor = factorialProcessor;
        this.fileLocation = fileLocation;
    }

    @Override
    public void start() throws AlreadyRunningException {
        //  Prevents double execution
        if (isRunning) {
            throw new AlreadyRunningException("InputFileReaderService already running");
        }

        executorService.execute(this::runInternal);
        isRunning = true;
    }

    @Override
    public void stop() {
        isRunning = false;
        executorService.shutdown();
    }

    private void runInternal() {
        System.out.println("Input file reader started");
        readFile();
        System.out.println("Input file reader done");
    }

    private void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
            String line = reader.readLine();

            while (line != null && isRunning) {
                factorialProcessor.process(line);
                line = reader.readLine();
            }
        } catch (Throwable t) {
            //  Catch everything
            t.printStackTrace();
        } finally {
            stop();
        }
    }
}
