package com.company;

public class Main {

    public static void main(String[] args) {
	System.out.println("HELLO");

	PrimeCounter pc = new PrimeCounter();
	pc.start2(10000);

	System.out.println(pc.getCount());
    }
}
