package com.company;

public class Main {

    public static void main(String[] args) {

    	SwingGUI gui = new SwingGUI();

    	gui.startGUI();

	final int PRIME_LIMIT = 10000;
	final int[] threadPoolSizes = new int[]{1, 2, getCoreCount()+1, 10, 50, 100, 150, 200};

		for (int poolsize : threadPoolSizes)
		{
			PrimeCounter pc = new PrimeCounter(poolsize);

			long start_time = System.currentTimeMillis();
			pc.start2(PRIME_LIMIT);
			long end_time = System.currentTimeMillis();
			long duration_ms = (end_time-start_time);

			System.out.println("Poolsize: " + poolsize);
			System.out.println("Prime Limit: " + PRIME_LIMIT);
			System.out.println("Result: " + pc.getCount());
			System.out.println("Runtime(ms): " + duration_ms);
			System.out.println();
		}
    }

    public static int getCoreCount()
	{
		return Runtime.getRuntime().availableProcessors();
	}
}
