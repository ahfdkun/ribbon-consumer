package com.ahfdkun.ribbon_consumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Test {

	public static void main(String[] args) {
		List<String> upServerList = new ArrayList<String>();
		List<String> x = Collections.unmodifiableList(upServerList);
		System.out.println(x.get(new Random().nextInt(2)));
	}
}
