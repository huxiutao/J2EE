package com.delta.my.learn;

public class TestCode {
	static {
		System.out.println("1111");
	}
	public TestCode() {
		System.out.println(333);
	}
	public static void main(String[] args) {
		System.out.println(2222);
		new TestCode();
	}
}
