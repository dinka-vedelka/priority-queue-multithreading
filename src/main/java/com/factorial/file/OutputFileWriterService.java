package com.factorial.file;

import com.factorial.file.consumer.FactorialConsumer;
import com.factorial.lang.AlreadyRunningException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Service component for writing to an output file
 * */
public class OutputFileWriterService implements FileService {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final FactorialConsumer factorialConsumer;
    private final String fileLocation;
    private boolean isRunning = false;

    public OutputFileWriterService(FactorialConsumer factorialConsumer, String fileLocation) {
        this.factorialConsumer = factorialConsumer;
        this.fileLocation = fileLocation;
    }

    @Override
    public void start() throws AlreadyRunningException {
        //  Prevents double execution
        if (isRunning) {
            throw new AlreadyRunningException("OutputFileWriterService already running");
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
        System.out.println("Output file writer started");
        writeFile();
        System.out.println("Output file writer done");
    }

    private void writeFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileLocation, true))) {
            while (isRunning) {
                final String line = factorialConsumer.acquire();

                //  No more data available to write. Stopping
                if (line == null) {
                    break;
                }

                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (Throwable t) {
            //  Catch everything
            t.printStackTrace();
        } finally {
            stop();
        }
    }
}
