package by.bsuir.store.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Coder {
    	public static String hashMD5(String text) {
    		return DigestUtils.md5Hex(text);
    	}
}
