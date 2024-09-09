package com.factorial.file;

import com.factorial.lang.AlreadyRunningException;

public interface FileService {
    void start() throws AlreadyRunningException;

    void stop();
}
