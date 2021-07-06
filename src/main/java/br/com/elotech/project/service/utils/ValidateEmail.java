package br.com.elotech.project.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidateEmail {

	private static final String EMAIL_ERROR = "E-mail não é válido";

	public static boolean isValidEmailAddressRegex(String email) throws BusinessException {
		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches())
			throw new BusinessException(EMAIL_ERROR);
		return true;
	}
}
