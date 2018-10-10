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


public class Cook implements Runnable{

	private static final String burgerMachine = "burger";
	private static final String fryMachine = "fry";
	private static final String cokeMachine = "coke";
	private static final String machineAvailable = "available";
	private static boolean bm = true;
	private static boolean fm = true;
	private static boolean cm = true;
	
	private static final int ms = 20;
	String cookName;
	Order order;
	Orders orders;
	ServeOrders serveOrders;
	long startTime;
	//long burgerTime;
	//long fryTime;
	
	Cook(String cn, Orders os, ServeOrders so, long st) {
		this.cookName = cn;
		this.orders = os;
		this.serveOrders = so;
		this.startTime = st;
		//this.burgerTime = st;
		//this.fryTime = st;
	}

	private boolean getBurger() {
		if (order.timeForBurgur < 0) {
			synchronized (burgerMachine) {
				if (bm) {
					bm = false;
				} else {
					return false;
				}
			}
			this.order.timeForBurgur = (System.currentTimeMillis() - startTime) / ms;
			try {
				Thread.sleep(order.numberOfBurger * 5 * ms);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Thread.currentThread().setPriority(Thread.currentThread().getPriority()+1);
			synchronized (burgerMachine) {
				bm = true;
			}
			synchronized (machineAvailable) {
				machineAvailable.notifyAll();
			}
			return true;
			//notifyAll();
			//this.burgerTime = (System.currentTimeMillis() - startTime) / ms;
		}
		return false;
	}
	
	private boolean getFry() {
		if (order.timeForFry < 0) {
			synchronized (fryMachine) {
				if (fm) {
					fm = false;
				} else {
					return false;
				}
			}
			this.order.timeForFry = (System.currentTimeMillis() - startTime) / ms;
			try {
				Thread.sleep(order.numberOfFry * 3 * ms);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Thread.currentThread().setPriority(Thread.currentThread().getPriority()+1);
			synchronized (fryMachine) {
				fm = true;
			}
			synchronized (machineAvailable) {
				machineAvailable.notifyAll();
			}
			return true;
			//notifyAll();
			//this.fryTime = (System.currentTimeMillis() - startTime) / ms;
		}
		return false;
	}
	
	private boolean getCoke() {
		if (order.timeForCoke < 0) {
			synchronized (cokeMachine) {
				if (cm) {
					cm = false;
				} else {
					return false;
				}
			}
			this.order.timeForCoke = (System.currentTimeMillis() - startTime) / ms;
			try {
				Thread.sleep(order.numberOfCoke * ms);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (cokeMachine) {
				cm = true;
			}
			synchronized (machineAvailable) {
				machineAvailable.notifyAll();
			}
			return true;
			//notifyAll();
		}
		return false;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			order = orders.get();
			order.cookName = this.cookName;
			order.timeToProcess = (System.currentTimeMillis() - startTime) / ms;
			
			/*if (this.burgerTime > this.fryTime) {
				getBurger();
				getFry();
			} else {
				getFry();
				getBurger();
			}*/

			if (order.numberOfBurger > 0) {
				order.timeForBurgur = -1;
			} else {
				order.timeForBurgur = 0;
			}
			if (order.numberOfFry > 0) {
				order.timeForFry = -1;
			} else {
				order.timeForFry = 0;
			}
			if (order.numberOfCoke > 0) {
				order.timeForCoke = -1;
			} else {
				order.timeForCoke = 0;
			}
			//Thread.currentThread().setPriority(5);
			while (true) {
				boolean cookSomething = false;
				cookSomething = cookSomething || getBurger();
				cookSomething = cookSomething || getFry();
				cookSomething = cookSomething || getCoke();
				if (order.timeForBurgur >= 0 && order.timeForFry >= 0 && order.timeForCoke >= 0) break;
				/*try {
					Thread.sleep(ms / 2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				if (cookSomething) continue;
				try {
					synchronized (machineAvailable) {
						machineAvailable.wait();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.order.finishTimeMills = System.currentTimeMillis();
			this.order.finishTime = (System.currentTimeMillis() - startTime) / ms;
			serveOrders.put(order);			
		}
	}
}
