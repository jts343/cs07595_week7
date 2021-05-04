import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

class PrimeCounterTest
{
    PrimeCounter pc;
    JTextArea jta;
    List<Integer> endList;

    @BeforeEach
    void setUp()
    {
        pc = new PrimeCounter();
        jta = new JTextArea();

        endList = Arrays.asList(1000, 5000, 10000, 50000, 100000, 500000);
    }

    @Test
    void start1()
    {
        System.out.println("start1 run");

        for(Integer i : endList)
        {
            System.out.println("N: " + i);
            long starttime = System.nanoTime();
            pc.start(0, i);
            long stoptime = System.nanoTime();

            long duration = (stoptime - starttime) / 1000; // in milliseconds
            System.out.println("duration: " + duration + "ms");
            System.out.println();
        }
    }

    @Test
    void start2()
    {
        System.out.println("start2 run");

        for(Integer i : endList)
        {
            System.out.println("N: " + i);

            long starttime = System.nanoTime();
            pc.start2(0, i, jta);
            long stoptime = System.nanoTime();

            long duration = (stoptime - starttime) / 1000; // in milliseconds
            System.out.println("duration: " + duration + "ms");
            System.out.println();
        }
    }

    @Test
    void start3()
    {
        System.out.println("start3 run");

        for(Integer i : endList)
        {
            System.out.println("N: " + i);

            long starttime = System.nanoTime();
            pc.start3(0, i, jta);
            long stoptime = System.nanoTime();

            long duration = (stoptime - starttime) / 1000; // in milliseconds
            System.out.println("duration: " + duration + "ms");
            System.out.println();
        }
    }
}