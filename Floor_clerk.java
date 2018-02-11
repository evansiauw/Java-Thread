public class Floor_clerk extends main implements Runnable{
	
	public Floor_clerk(int id) { setName("Floor clerk_" + id); }	 // floor clerk constructor
	
	public static void msgFc(String m) { // printing message
		System.out.print("["+(System.currentTimeMillis()-time)+"] "+currentThread().getName()+": "+ m + Cname + "\n");  }
	
	public static void msg(String m) { // printing message
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+currentThread().getName()+": "+ m );  }
	
	public void run(){
		
		while(Cust_served < Cust && Lock){
		yield();
		try { Mutex2.acquire(); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		Cust_served++;
		if(Cust_served < Cust) { 
		try { WaitTillCustCome.acquire(); } 
		catch (InterruptedException e1) { e1.printStackTrace(); } }
		else { Lock = false; }
		msgFc("serving ");
		sleep();
		FCname = currentThread().getName();
		CustQueue.release(); 
		Mutex2.release();
		}
		
		msg("Finish Serving ");
		try { WaitTillClose.acquire(); } 
		catch (InterruptedException e) { e.printStackTrace(); }
		msg("What a Long day! Time to go home");
		sleep();
		msg("Finally I could get out from this place");
		}
}
