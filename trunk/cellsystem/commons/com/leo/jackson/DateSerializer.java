/**
 * 
 */
package com.leo.jackson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 * @author hongci.li
 *
 */
public class DateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator gen,SerializerProvider p) throws IOException,JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");//yyyy-MM-dd HH:mm:ss
		String formattedDate = formatter.format(value);
		gen.writeString(formattedDate);
	}

}
