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

public class Diner implements Runnable{
	private static final int ms = 20;
	/**
	 * @param args
	 */
	private String dinerName;
	private int arriveTime;
	private Order order;
	private Tables table;
	private Orders orders;
	private ServeOrders serveOrders;
	private long startTime;
	
	private long seatedTime;
	private long leaveTime;
	
	//private String TableName;
	
	
	Diner(String d, int a, Order o, Tables t, Orders os, ServeOrders so, long st) {
		dinerName = d;
		arriveTime = a;
		order = o;
		table = t;
		orders = os;
		serveOrders = so;
		startTime = st;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//
		
		long sleepTime = arriveTime * ms - System.currentTimeMillis() + startTime;
		if (sleepTime < 0) sleepTime = 0;
		
		//long t1 = System.currentTimeMillis();
		//System.out.println(this.dinerName + " " + t1 + " " + sleepTime);
		
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			System.out.println("WTF");
			e1.printStackTrace();			
		}
		//long t2 = System.currentTimeMillis();
		//System.out.println(this.dinerName + " " + t2 + " " + (t2 - t1));
		
		order.tableName = table.get();
		
		seatedTime = (System.currentTimeMillis() - startTime) / ms;
		
		//System.out.println(dinerName + " arrive " +TableName);
		
		//TableName;
		orders.put(order);
		
		serveOrders.find(order);
		
		sleepTime = 30 * ms - System.currentTimeMillis() + order.finishTimeMills;
		//if (sleepTime < 0) sleepTime = 0;
		
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		leaveTime = order.finishTime + 30;
		
		String outS = "************************\n"
			+ dinerName + " arrived at " + arriveTime + " min,\n"
			+ "  seated at " + order.tableName + " at " + seatedTime + " min,\n"
			+ "  ordered " ;
		if (order.numberOfBurger > 0) {
			outS = outS + order.numberOfBurger;
			if (order.numberOfBurger == 1) {
				outS = outS + " burger, ";
			} else {
				outS = outS + " burgers, ";
			}
		}
		
		if (order.numberOfFry > 0) {
			outS = outS + order.numberOfFry + " Fries, ";
			}
		
		if (order.numberOfCoke > 0) {
			outS = outS + order.numberOfCoke + " Coke, ";
			}
		
		outS = outS + "\n" + order.cookName + " began to process at " + order.timeToProcess + " min, \n";

		if (order.numberOfBurger > 0) {
			outS = outS + "  burger machine was used from " + order.timeForBurgur
				+ " min to " + (order.timeForBurgur + order.numberOfBurger * 5) + " min, \n";
		}
		
		if (order.numberOfFry > 0) {
			outS = outS + "  fries machine was used from " + order.timeForFry
			+ " min to " + (order.timeForFry + order.numberOfFry * 3) + " min, \n";
		}
		
		if (order.numberOfCoke > 0) {
			outS = outS + "  coke machine was used from " + order.timeForCoke
			+ " min to " + (order.timeForCoke + order.numberOfCoke) + " min, \n";
		}
		
		outS = outS + order.cookName + " finished and served the order at at " + order.finishTime + " min. \n";
		
		outS = outS	+ dinerName + " left at " + leaveTime + " min.\n";
		
		System.out.println(outS);
		
		
		table.put(order.tableName);
	}

}
