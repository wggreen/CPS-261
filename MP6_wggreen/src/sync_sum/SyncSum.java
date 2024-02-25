package sync_sum;

class MySum implements Runnable{
	public int sum;
	
	public MySum() {
		sum = 0;
	}
	
	public void increaseSum() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized(this) {
			++sum;
			System.out.println(Thread.currentThread().getName() + " sum is: " + sum);
		}

	}
	
	public void run() {
		increaseSum();
	}
	
}

public class SyncSum {

	public static void main(String[] args) {
		
		MySum ms = new MySum();
		for(int i = 0; i < 100; i++) {
			Thread thread = new Thread(ms);
			thread.start();
		}
	}

}
