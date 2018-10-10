/*
********************************************************
CSE 6431 Project
Author: Cheng Zhang
Contact: zhang.7804@osu.edu
Basic idea: A diner waits for a table from Tables Queue,
puts his order in Orders Queue, waits for his order
being finished from ServeOrders Queue, eats and leaves.

A cook gets a order from Orders Queue, processes the 
order and puts the finished order back to ServeOrders Queue

********************************************************
*/


import java.util.ArrayList;

public class Tables {
	ArrayList<String> element;
	Tables() {
		element = new ArrayList<String>();
	}
	synchronized String get() {
		while (element.size() == 0)
		try {
			wait();	
		} catch (InterruptedException e) {
			
		}
		String outputElement = element.get(0);
		element.remove(0);
		notifyAll();
		return outputElement;
	}
	
	synchronized void put(String inputElement) {
		element.add(inputElement);
		notifyAll();
	}
}
