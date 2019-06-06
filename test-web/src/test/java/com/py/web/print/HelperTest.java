package com.py.web.print;

import org.hashids.Hashids;

/**
 * @Description:
 * @Author: zhenghui.li
 * @Date: Created in 2019/06/04
 */
public class HelperTest {

	public static void main(String[] args) {
		Hashids hashids = new Hashids();
		String encodeStr = hashids.encode(9876542112345L);
		long[] decode = hashids.decode(encodeStr);
		System.out.println("encodeStr = " + encodeStr);
		System.out.println("decodeStr = " + decode[0]);
	}
}
