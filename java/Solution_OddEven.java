import java.util.Arrays;

public class Solution {
	public static class Counter {

		volatile boolean evenTurn;

		public synchronized void printOddVal(long value) {
			while(evenTurn) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("value "+value+ " from thread "+ Thread.currentThread().getName());
			evenTurn = true;
			notify();
		}
		
		public synchronized void printEvenVal(long value) {
			while(!evenTurn) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("value "+value+ " from thread "+ Thread.currentThread().getName());
			evenTurn = false;
			notify();
		}
	}

	public static class CounterThreadOdd extends Thread {

		protected Counter counter = null;

		public CounterThreadOdd(Counter counter) {
			this.counter = counter;
		}

		public void run() {
			for (int i = 1; i < 10; i=i+2) {
				counter.printOddVal(i);
			}
		}
	}
	
	public static class CounterThreadEven extends Thread {

		protected Counter counter = null;

		public CounterThreadEven(Counter counter) {
			this.counter = counter;
		}

		public void run() {
			for (int i = 2; i < 10; i=i+2) {
				counter.printEvenVal(i);
			}
		}
	}
	

	public static void main(String[] args) {
		Counter counter = new Counter();
		Thread threadA = new CounterThreadOdd(counter);
		Thread threadB = new CounterThreadEven(counter);

		threadA.start();
		threadB.start();
	}
}
