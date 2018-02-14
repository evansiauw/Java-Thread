public class Storage_clerk extends main implements Runnable{
	
	public Storage_clerk(int id) { setName("Storage clerk_" + id); } // storage clerk constructor
	
	public static void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] " + currentThread().getName()+": "+m); }
	
	public void run(){
		
		Longsleep();
		
		int NumofHeavyCust = CustWithHeavy.getQueueLength();
		
		while(CustServedBySc < NumofHeavyCust && Lock2){
			yield();
			
			try { Mutex3.acquire(); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			
			if(CustServedBySc == NumofHeavyCust){
				Lock2 = false;
				Mutex3.release(); 
				} 
			
			if(CustServedBySc < NumofHeavyCust) {
				Mutex3.release();
				try { ScWaitToHelp.acquire(); } 
				catch (InterruptedException e) { e.printStackTrace(); }
			}
				
			try { Mutex5.acquire(); } 
			catch (InterruptedException e) { e.printStackTrace(); }
				
			Sc_Count++; 
			
			if(Sc_Count % Group_Size != 0) {	
				
				Mutex5.release();
				try { ScReadyServe.acquire(); } 
				catch (InterruptedException e) { e.printStackTrace(); }
				sleep();
				msg("Helping Customer");
			 }	
				
			if(Sc_Count % Group_Size == 0){
			
				CustServedBySc++;
				CustWithHeavy.release();
			
				sleep();
				msg("Helping Customer");
				ScReadyServe.release(2);
				Mutex5.release();
				sleep();
				ScWaitToHelp.release(3);
				}
		}
			
		try { WaitTillClose.acquire(); } 
		catch (InterruptedException e) { e.printStackTrace();}
		
		msg("Got paid minimum salary and expected to work like a horse");
		sleep();
		msg("I am going back to college studying CompSci");
		sleep();
		msg("Going home! I really hate this job");
		
	}
}

