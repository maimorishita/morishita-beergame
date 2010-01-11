package jp.co.isken.beerGame.presentation;

public class FormUtil {

	private FormUtil() {}
	
	public static String trim(String value) {
		if (value == null) {
			return value;
		}
		return value.replaceAll("^[\\sÅ@]*", "").replaceAll("[\\sÅ@]*$", "");
	}

	public static boolean isNullOrEmpty(String value) {
		if (value == null) {
			return true;
		}
		return (value.equals(""));
	}
}
