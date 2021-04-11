import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrimeCounter
{
    private static int count;
    public static ExecutorService exec;
    public static boolean interruptCalled;
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
            interruptCalled = false;
            System.out.println("Final count: " + count);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
        exec.shutdownNow();
        interruptCalled = true;
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
