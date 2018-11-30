package com.delta.my.learn;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A --> B --> C
 * A打印二次
 * B打印四次
 * B打印六次
 * 循环十次
 * 
 * @author Xuekao.Hu
 *
 */
public class Order {
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ShareResource sr = new ShareResource();
		
		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				sr.printA(i);
			}
		}, "AA").start();
		
		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				sr.printB(i);
			}
		}, "BB").start();
		
		new Thread(() -> {
			for (int i = 1; i <= 10; i++) {
				sr.printC(i);
			}
		}, "CC").start();
	}
}

/**
 * 资源类
 * @author Xuekao.Hu
 */
class ShareResource{
	private String flag = "A";
	private Lock lock = new ReentrantLock();
	private Condition conditionA = lock.newCondition();
	private Condition conditionB = lock.newCondition();
	private Condition conditionC = lock.newCondition();
	
	public void printA(int totalNumber) {
		lock.lock();
		try {
			// 1.判断
			while ( !flag.equals("A")) {
				conditionA.await();
			}
			
			// 2.操作
			for (int i = 1; i <= 2; i++) {
				System.out.println(Thread.currentThread().getName() + "\t " + flag + "_" + String.valueOf(i) + "\t" + totalNumber);
			}
			flag = "B";
			// 3.通知
			conditionB.signal();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void printB(int totalNumber) {
		lock.lock();
		try {
			// 1.判断
			while (!flag.equals("B")) {
				conditionB.await();
			}
			
			// 2.操作
			for (int i = 1; i <= 4; i++) {
				System.out.println(Thread.currentThread().getName() + "\t " + flag + "_" + String.valueOf(i) + "\t" + totalNumber);
			}
			flag = "C";
			// 3.通知
			conditionC.signal();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public void printC(int totalNumber) {
		lock.lock();
		try {
			// 1.判断
			while (!flag.equals("C")) {
				conditionC.await();
			}
			
			// 2.操作
			for (int i = 1; i <= 6; i++) {
				System.out.println(Thread.currentThread().getName() + "\t " + flag + "_" + String.valueOf(i) + "\t" + totalNumber);
			}
			flag = "A";
			// 3.通知
			conditionA.signal();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
}
