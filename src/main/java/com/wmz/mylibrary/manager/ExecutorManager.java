package com.wmz.mylibrary.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wmz on 26/5/16.
 */
public class ExecutorManager {
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);
//    private static ExecutorService executorService1 = Executors.newScheduledThreadPool(3);
//    private static ExecutorService executorService2 = Executors.newCachedThreadPool();
//    private static ExecutorService executorService3 = Executors.newSingleThreadExecutor();

    public static ExecutorService getExecutorInstance() {
        return executorService;
    }

    public static void execute(Runnable command) {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.execute(command);
        }
    }

    public static void shutdown() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
