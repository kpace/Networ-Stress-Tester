import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class StartPoint {

	public static final int NUMBER_OF_THREADS = 5000;
	
	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(NUMBER_OF_THREADS);

		Thread t = null;
		ArrayList<Thread> list = new ArrayList<Thread>();
		
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			t = new RequestorThread("https://www.facebook.com/", barrier);
			list.add(t);
			try {
				t.start();				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (Thread thread : list) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int successfullThreads = 0;
		int notValidResponseCode = 0;
		System.out.println("Calculating number of successfull threads...");
		 
		for (Thread currentThread : list) {
			if ( ((RequestorThread) currentThread).getResponseCode() == 200 ) {
				successfullThreads++;
			}
			else if (((RequestorThread) currentThread).getResponseCode() == -1) {
				notValidResponseCode++;
			}
		}
		
		System.out.println("Number of successfull threads: " + successfullThreads);
		System.out.println("Not valid response code: " + notValidResponseCode);
		
	}

}
