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


public class Order {
	String tableName;
	int numberOfBurger;
	int numberOfFry;
	int numberOfCoke;
	
	String cookName;
	long timeToProcess;
	long timeForBurgur;
	long timeForFry;
	long timeForCoke;
	long finishTimeMills;
	long finishTime;
	
	Order(int nob, int nof, int noc) {
		this.tableName = "";
		this.numberOfBurger = nob;
		this.numberOfFry = nof;
		this.numberOfCoke = noc;
	}
	
}
