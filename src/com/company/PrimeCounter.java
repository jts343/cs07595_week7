package com.company;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PrimeCounter {

    private static int count;
    private static final int N_THREADS = 100;
    private static ExecutorService exec;

    PrimeCounter(int _n_threads)
    {
        exec = Executors.newFixedThreadPool(_n_threads);
    }

    @Deprecated
    public void start(int _n) {
        count = 0;

        for (int i = 0; i < _n; i++) {
            int thread_n = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (isPrime(thread_n)) {
                        synchronized ("count") {
                            count++;
                        }
                    }
                }
            });
            thread.start();
        }
    }

    public void start2(int _n) {
        count = 0;

        try {
            for (int i = _n; i > 0; i--) {
                int thread_n = i;

                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        if (isPrime(thread_n)) {
                            synchronized ("count") {
                                count++;
                            }
                        }
                    }
                };

                exec.execute(task);
            }

            exec.shutdown();
            exec.awaitTermination(2, TimeUnit.MINUTES);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isPrime(int n) {
        // Corner case
        if (n <= 1)
            return false;

        // Check from 2 to n-1
        for (int i = 2; i < n; i++)
            if (n % i == 0)
                return false;

        return true;
    }

    public int getCount() {
        return count;
    }
}
