package jp.co.axa.apidemo.utils;

import java.util.Arrays;
import java.util.List;

public class CommonUtils {

	private static final List<String> URL_SUFFIX = Arrays.asList("/.ico", ".css", ".js", ".gif", ".png", ".jpg",
			".jpeg");

	public static boolean isNotPublicUrl(String url) {
		final String uri = url.toLowerCase();
//		if (0 < uri.lastIndexOf(".") && URL_SUFFIX.contains(uri.substring(uri.lastIndexOf(".")))) {
//			return true;
//		}
		
		return uri.length() > 16 && "/api/v1/employees".equals(uri.substring(0,17));
	}

}
