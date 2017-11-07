package org.sally.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.StringUtils;


@Converter(autoApply = true)
public class DateConverter implements AttributeConverter<String, Date>
{

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public Date convertToDatabaseColumn(String attribute)
	{
		// TODO Auto-generated method stub
		Date date_db = null;
		
		try
		{
			date_db = StringUtils.isBlank(attribute) ? new Date() : sdf.parse(attribute);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date_db;
	}

	public String convertToEntityAttribute(Date dbData)
	{
		// TODO Auto-generated method stub
		return sdf.format(dbData == null? new Date() : dbData);
	}

}
