package com.gab.onewebapp.utils;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

/**
 * @author gabriel
 * 
 */
public class DateUtilsTest {

	@Test
	public void shouldFormatDateWithLocale() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;

		date = sdf.parse("21/12/2016");
		
		assertEquals("12/21/2016",DateUtils.formatDate(date, new Locale("EN","en")));
		assertEquals("21/12/2016",DateUtils.formatDate(date, new Locale("FR","fr")));		
		
	}

}
