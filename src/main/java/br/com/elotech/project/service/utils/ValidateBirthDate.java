package br.com.elotech.project.service.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ValidateBirthDate {

	private static final String DATE_ERROR = "Data de Nascimento deve ser igual ou menor que a data atual";

	public static boolean isBirthDate(Date birthdate) throws BusinessException {

		LocalDate now = LocalDate.now();
		LocalDateTime date = LocalDateTime.ofInstant(birthdate.toInstant(), ZoneId.systemDefault());
		LocalDate birthdateDate = LocalDate.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

		if (birthdateDate.isAfter(now))
			throw new BusinessException(DATE_ERROR);

		return true;
	}
}
