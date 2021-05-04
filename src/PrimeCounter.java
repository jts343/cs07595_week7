import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PrimeCounter
{
    public static ExecutorService exec;
    public static boolean interruptCalled;
    private static int count;
    private final int nThreads;
    private final boolean isAvailable;

    PrimeCounter()
    {
        isAvailable = false;
        interruptCalled = false;
        nThreads = getCoreCount() + 1;
    }

    public static int getCoreCount()
    {
        return Runtime.getRuntime().availableProcessors();
    }

    @Deprecated
    public void start(int start_n,
                      int stop_n)
    {
        count = 0;

        for(int i = start_n; i < stop_n; i++)
        {
            int thread_n = i;
            Thread thread = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    if(isPrime(thread_n))
                    {
                        synchronized("count")
                        {
                            count++;
                        }
                    }
                }
            });
            thread.start();
        }
    }

    public void start2(int start_n,
                       int stop_n,
                       JTextArea resultBox)
    {
        count = 0;
        exec = Executors.newFixedThreadPool(nThreads);

        try
        {
            for(int i = stop_n; i > start_n; i--)
            {
                int thread_n = i;

                Runnable task = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(isPrime(thread_n) && !interruptCalled)
                        {
                            synchronized("count")
                            {
                                count++;
                                resultBox.setText(String.valueOf(count));
                            }
                        }
                    }
                };

                exec.execute(task);
            }

            exec.shutdown();
            exec.awaitTermination(2, TimeUnit.MINUTES);
            interruptCalled = false;
            System.out.println("Final count: " + count);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void start3(int start_n,
                       int stop_n,
                       JTextArea resultBox)
    {
        count = 0; //reset count

        //generate List, [start_n:stop_n]
        List<Integer> range = IntStream
                .rangeClosed(start_n, stop_n)
                .boxed()
                .collect(Collectors.toList());

        range
                .parallelStream() // multithreaded stream
                .filter(i -> isPrime(i)) // filter on Primes
                .forEach(i -> { // for each prime number
                    resultBox.setText(Integer.toString(++count)); // set result display to new count
                    resultBox.update(resultBox.getGraphics()); // refresh result display incrementally
                });

        System.out.println("Final count: " + resultBox.getText());
    }

    public boolean isPrime(int n)
    {
        // Corner case
        if(n <= 1)
        {
            return false;
        }

        // Check from 2 to n-1
        for(int i = 2; i < n; i++)
        {
            if(interruptCalled)
            {
                return false;
            }

            if(n % i == 0)
            {
                return false;
            }
        }

        return true;
    }

    public int getCount()
    {
        return count;
    }

    public void interrupt()
    {
        if(exec != null)
        {
            exec.shutdownNow();
            interruptCalled = true;
        }
    }

    public boolean isAvailable()
    {
        if(exec != null)
        {
            return exec.isTerminated();
        }
        else
        {
            return true;
        }
    }
}
