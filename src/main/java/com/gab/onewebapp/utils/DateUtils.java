package com.gab.onewebapp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author gabriel
 * 
 */
public class DateUtils {

	public static String formatDate(Date date, Locale... locale){
		
		Locale currentLocale = LocaleContextHolder.getLocale();
		
		if(locale != null){
			currentLocale = locale[0];
		}
		
		SimpleDateFormat sdf = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT, currentLocale);
		sdf.applyPattern(sdf.toPattern().replace("yy","yyyy"));
		
		return sdf.format(date);
		
	}
}
