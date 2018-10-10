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
import java.util.Scanner;
import java.io.*;

public class Restaurant {
	private static final int ms = 20;
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		File file;
		if (args.length > 0) {
			file = new File(args[0]);
		} else {
			file = new File("project-sample-input-2.txt");
		}

		InputStreamReader reader = null;

		try {
			reader = new InputStreamReader(  
			        new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BufferedReader br = new BufferedReader(reader);

		int numberOfDiner = 0;
		int numberOfTable = 0;
		int numberOfCook = 0;

		try {		
			String ndiner = br.readLine();	
			numberOfDiner = Integer.parseInt(ndiner);
		    //System.out.println(numberOfDiner +"************************\n");	
			String ntable = br.readLine();	
			numberOfTable = Integer.parseInt(ntable);
			//System.out.println(numberOfTable +"************************\n");
			String ncook = br.readLine();
			numberOfCook = Integer.parseInt(ncook);
			//System.out.println(numberOfCook +"************************\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long startTime = System.currentTimeMillis();

		Tables table = new Tables();	//Queue of available Tables
		Orders orders = new Orders(); 	//Queue of orders ordered by diners
		ServeOrders serveOrders = new ServeOrders();	//Queue of finished orders by cooks
		
		for (int i = 1; i <= numberOfTable; i++) {
			table.put("Table #" + i);	//Tables are numbered from 1 to numberOfTable
		}
		
		for (int i = 1; i <= numberOfCook; i++) {
			Cook cook = new Cook("Cook #" + i, orders, serveOrders, startTime);
									//Cooks are named from 1 to numberOfCook
			Thread cookThread = new Thread(cook);
			cookThread.setDaemon(true);
			cookThread.start();
			
		}
		
		ArrayList<Thread> dinerThreads = new ArrayList<Thread>();
		try {
			for (int i = 0; i < numberOfDiner; i++) {
				String line = br.readLine();
				//System.out.println(line +"************************\n");

				String[] ss = line.split(",");
				int arriveTime = Integer.parseInt(ss[0]);
				int numberOfBurger = Integer.parseInt(ss[1]);
				int numberOfFry = Integer.parseInt(ss[2]);
				int numberOfCoke = Integer.parseInt(ss[3]);

				Order order = new Order(numberOfBurger,numberOfFry,numberOfCoke);
				Diner diner = new Diner("Diner #" + (i + 1), arriveTime, order, table, orders, serveOrders, startTime);
				Thread dinerThread = new Thread(diner);
				dinerThreads.add(dinerThread);
				dinerThread.start();
			}
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		try {
			for (int i = 0; i < numberOfDiner; i++) {
				dinerThreads.get(i).join();
			}
		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		
		System.out.println("************************\nThe last diner left at " + (System.currentTimeMillis()-startTime) / ms + " min\n");

		System.out.println("!!!Please note that: the final left time might have 1 minute error because of System.currentTimeMillis() error. Just run the program again to get the right result.\n");
		System.out.println("I reorganize the output format to make the process more clear for every diner. Just check the left time for each diner.");

	}

}
