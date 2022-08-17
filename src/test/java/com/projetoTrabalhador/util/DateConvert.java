package com.projetoTrabalhador.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvert {

	public static Date convertData(String text) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	    Date date = sdf.parse(text);
		return date;
	}
	
}
