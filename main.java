// Iwan Siauw
// Cs340 Fall 2017
// Project1 

import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.Scanner;
import java.math.*;

// orlando
// iwan
public class main extends Thread{
	
	static Semaphore CustQueue = new Semaphore(0,false); // number of customer in the queue
	static Semaphore WaitTillClose = new Semaphore(0,false); // waiting until store close
	static Semaphore WaitTillCustCome = new Semaphore(0,false); // waiting until cust come
	static Semaphore Mutex1 = new Semaphore(1); // mutual exclusion
	static Semaphore Mutex2 = new Semaphore(1); // mutual exclusion
	static Semaphore Mutex3 = new Semaphore(1); // mutual exclusion
	static Semaphore Mutex4 = new Semaphore(1); // mutual exclusion
	static Semaphore Mutex5 = new Semaphore(1); // mutual exclusion
	static Semaphore CustWithHeavy = new Semaphore(0,false); // adding cust with heavy items into sem queue
	static Semaphore ScWaitToHelp = new Semaphore(3,false); // 3 Storage clerk served 1 customer
	static Semaphore ScReadyServe = new Semaphore(0,false); // waiting to serve customers
	static int TotalWorkers = 0; // number of store workers to be notified by last customers
	static String Cname; // Customer name
	static String FCname; // Floor clerk name
	static int Sc_Count = 0; // storage clerk count
	static int Group_Size = 3; // group size
	static long time = System.currentTimeMillis(); // initialize the time
	static boolean ServedByClerk = true; // if its false, all cust has been served by f clerk
	static int Cust_served = 0; // a counter to keep track how many cust has been served
	static boolean waitforCust = true; // f clerk waits till cust arrives
	static int LightItem = 0; // a counter to keep track on how many Customer with Heavy Items
	static int HeavyItem = 0; // a counter to keep track on how many Customer with Heavy Items
	static int NumofCust = 10; // default value of total Customers
	static int NumofFloor = 2; // default value of total Floor Clerk
	static int NumofStorage = 6; // default value of total Storage Clerk
	static int Cust = NumofCust; // assigning number of Customer if default chosen
	static int Floor = NumofFloor; // assigning number of Floor Clerk if default chosen
	static int Storage = NumofStorage; // assigning number of Storage Clerk if default chosen
	static int AvailableSClerk; // a counter to keep track on how many Storage Clerk available
	static boolean Lock = true; // set it to false when all of the cust has been served by storage clerk
	static boolean Lock2 = true; // set it to false when alll of the cust has been served by floor clerk
	static int CustServedBySc = 0; // to keep track on how many cust has been already served by storage clerk
	static int LastCustCounter = 0; // if the # equal to the # of cust, notify the floor clerk and storage clerk
	
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("Do you wanna use the default value of Customers, Floor Clerk, and Storage Clerk ? Yes or No"); 
		Scanner read = new Scanner (System.in); // scanner
		Scanner reader = new Scanner(System.in); // scanner
		
		char answer = read.next().charAt(0);
		
		if(answer == 'N' || answer == 'n'){
			
		System.out.println("Please enter the Number of Customers: ");
		int NumofCust = reader.nextInt(); 
		Cust = NumofCust;
		
		while(NumofCust <= 0){ 
			System.out.println("Please enter the Number of Customers again: ");
			NumofCust = reader.nextInt();
			Cust = NumofCust;
		}
		
		System.out.println("Please enter the Number of Floor Clerk: ");
		int NumofFloor = reader.nextInt();
		Floor = NumofFloor;

		
		while(NumofCust <= 0){ 
			System.out.println("Please enter the Number of Floor Clerk again: ");
			NumofFloor = reader.nextInt();
			Floor = NumofFloor;

		}
		
		System.out.println("Please enter the Number of Storage Clerk: ");
		int NumofStorage = reader.nextInt();
		Storage = NumofStorage;
		
		
		while(NumofCust <= 0){ 
			System.out.println("Please enter the Number of Storage Clerk again: ");
			NumofStorage = reader.nextInt();
			Storage = NumofStorage;
		}
		
		}
		
		int NumCust = Cust + 1;
		
		Customer[] cust = new Customer[NumCust];

		for (int i=1; i<NumCust; i++){
			cust[i] = new Customer(i); // creating a new object of cust
			cust[i].start(); // starting a new cust thread
			}
		
		Floor_clerk[] Fclerk = new Floor_clerk[++Floor];

		for (int i=1; i<Floor; i++){ 
			Fclerk[i] = new Floor_clerk(i); // creating a new object of floor clerk
			Fclerk[i].start();
			TotalWorkers++; } // starting a new floor clerk thread
		
		Storage_clerk[] Sclerk = new Storage_clerk[++Storage];

		for (int i=1; i<Storage; i++){
			Sclerk[i] = new Storage_clerk(i); // creating a new object of storage clerk
			Sclerk[i].start();
			TotalWorkers++; } // starting a new floor clerk thread 
		
	}
		
	public static void sleep(){ // creating a sleep random time
		Random rand = new Random();
		int num = rand.nextInt(2000);
		try { sleep(num); } 
		catch (InterruptedException e) { e.printStackTrace(); } }
	
	public static void Longsleep(){ // creating a long sleep random time
		try { sleep(70000); } 
		catch (InterruptedException e) { e.printStackTrace(); } }
	
	
}
