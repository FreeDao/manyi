/**
 * 
 */
package com.leo.jackson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//import org.codehaus.jackson.JsonParser;
//import org.codehaus.jackson.JsonProcessingException;
//import org.codehaus.jackson.map.DeserializationContext;
//import org.codehaus.jackson.map.JsonDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author leo.li
 *
 */
public class FileDeserializer extends JsonDeserializer<File> {
	@Override
	public File deserialize(JsonParser parser, DeserializationContext context) throws  IOException, JsonProcessingException {
		
		File result = File.createTempFile("Manyi_", ".tmp");
		
		System.out.println(result.getPath());
		
		FileOutputStream fos = new FileOutputStream(result);
		fos.write(parser.getBinaryValue());
		fos.close();
		return result;
	}

}
