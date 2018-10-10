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

public class Orders {
	
	ArrayList<Order> element;
	Orders() {
		element = new ArrayList<Order>();
	}
	synchronized Order get() {
		while (element.size() == 0)
		try {
			wait();	
		} catch (InterruptedException e) {
			
		}
		Order outputElement = element.get(0);
		element.remove(0);
		notifyAll();
		return outputElement;
	}
	
	synchronized void put(Order inputElement) {
		element.add(inputElement);
		notifyAll();
	}
}

