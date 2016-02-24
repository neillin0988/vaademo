package com.prototype.vaadin.core.util;

public class ExceptionUtils {

	public static String parseException(Throwable e) {
		StringBuilder result = new StringBuilder();

		result.append(String.format("<font size='4' color='red'>%s : </font>",e.getClass().getName()));
		result.append(String.format("<font size='4' color='red'>%s</font>",e.getMessage()) + "<br>");

		int cunt = -1;
		for (StackTraceElement ele : e.getStackTrace()) {
			String eleStr = ele.toString();
			String newString = "";

			if (eleStr.contains("com.prototype.vaadin")) {
				cunt = 0;

				newString = String.format(
						"<font size='3' color='red' style='padding-left:3em'>at %s</font>", eleStr);
			} else {
				newString = String.format(
						"<font size='3' style='padding-left:3em'>at %s</font>", eleStr);
			}

			if (cunt == 0 && !eleStr.contains("com.prototype.vaadin")) {
				// break;
			}

			result.append(newString + "<br>");
		}

		return result.toString();
	}
}
