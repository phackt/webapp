package com.gab.onewebapp.utils;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gab.onewebapp.utils.DateUtils;


/**
 * @author gabriel
 * This allows performing requests and generating responses without the need for running in a Servlet container.
 * 
 */

public class DateUtilsTest {

	private static final Logger logger = LoggerFactory.getLogger(DateUtilsTest.class);
	
	@Test
	public void should_format_date_thanks_to_locale() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse("21/12/2016");
			
			assertEquals("12/21/2016",DateUtils.formatDate(date, new Locale("EN","en")));
			assertEquals("21/12/2016",DateUtils.formatDate(date, new Locale("FR","fr")));
		} catch (ParseException e) {
			logger.error("Exception raised in test method TransferFileTest.should_format_date_thanks_to_locale()");
			logger.error(e.toString());
			logger.error("stack trace: " + e.getStackTrace());
		}
		
		
	}

}
