import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class RequestorThread extends Thread {
	private String url;
	private CyclicBarrier barrier;
	private int responseCode;
	
	
	public RequestorThread(String url, CyclicBarrier barrier) {
		this.url = url;
		this.barrier = barrier;
	}
	
	@Override
	public void run() {
		System.out.println(this.getName() + " waiting for other threads...");
		
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println(this.getName() + " sending request!");
		sendRequest();
	}
	
	private void sendRequest() {
		try {
			
			URL urlObject = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
			connection.setRequestMethod("GET");
			
			responseCode  = -1;
			responseCode = connection.getResponseCode();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getResponseCode() {
		return responseCode;
	}

}
