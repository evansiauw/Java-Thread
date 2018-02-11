import java.util.Random;
import java.util.Vector;

public class Customer extends main implements Runnable{

	public Customer(int id) { // customer constructor
		setName("Customer_" + id); }
	
	public void msg(String m) {  // to print message
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+ getName()+": "+m); }
	
	public void msgcfc(String m) {  // to print message
		System.out.print("["+(System.currentTimeMillis()-time)+"] "+ getName()+": "+m+ FCname + "\n"); }
	
	public void LastManStanding(){
		
		LastCustCounter++;
		if(LastCustCounter != Cust) {
			msg("Browsing around the Store and waiting for closing time"); }
		else {
			msg("Letting The Store workers knows that I am the last Customer"); 
			msg("Leaving the store together with other Customers");
			for(int i=0; i<TotalWorkers; i++){
				WaitTillClose.release(); }
			}
	} 
	
	public void Increment_Customer(){
		
		msg("Found the items that I've been looking for");
		sleep();
		Cname = currentThread().getName();
		WaitTillCustCome.release();
		msg("Looking for an available Floor_clerk");
		try { CustQueue.acquire(); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		msgcfc("Being served by ");
		sleep();
		msg("Received a slip to pay for the items"); }
	
	public void RandomizingItem(){
		
		Random rand = new Random();
		boolean Random = rand.nextBoolean();
		// check condition, if # of light items exceeds the 70%, make it heavy
		if(Random == true && (LightItem >= Math.ceil(Cust*0.30))) { Random = false;} 
		// check condition, if # of heavy items exceeds 30%, make it light
		if(Random == false && HeavyItem >= Math.floor((Cust*0.70))) { Random = true; }
		// keep counting how many custs with light items
		if(Random == true) { 
			msg("My Shopping is Light, I could just carry it myself");
			sleep();
			msg("Stopping by at the Cafetaria");
			sleep();
			msg("Eating like there's no tomorrow");
			sleep();
			msg("Stopping by at the Restroom to take care some business");
			sleep();
			LightItem++;
			Mutex1.release();
			} 
		// keep counting how many custs with heavy items
		if(Random == false) { 
			msg("My Shopping are heavy and I am skinny.");
			sleep();
			msg("I gotta use some help from the Storage clerks to carry those for me");
			sleep();
			msg("Walking slowly likes a turtle to the storage room");
			HeavyItem++;
			Mutex1.release();
			sleep();
			try { CustWithHeavy.acquire(); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			sleep();
			msg("Being helped by 3 Storage clerk");
			}
		}
	
	public void run(){
		
		sleep();
		msg("Entering BALA store and looking for something to buy");
		sleep();
		msg("Still wandering around the Store");
		sleep();
		try { Mutex1.acquire(); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		Increment_Customer();
		Mutex1.release();
		sleep();
		msg("Running to the Cashier before the line gets crowded");
		sleep();
		msg("Arriving at the Cashier");
		sleep();
		msg("Deciding which credit card to use");
		sleep();
		msg("Paying for the items");
		sleep();
		try { Mutex1.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
		RandomizingItem();
		sleep();
		try { Mutex4.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
		LastManStanding();
		Mutex4.release(); }
	}
