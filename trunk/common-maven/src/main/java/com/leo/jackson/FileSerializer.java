/**
 * 
 */
package com.leo.jackson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

/**
 * @author hongci.li
 * 
 */
public class FileSerializer extends JsonSerializer<File> {
	@Override
	public void serialize(File value, JsonGenerator gen, SerializerProvider p) throws IOException, JsonProcessingException {
		FileInputStream fis = new FileInputStream(value);
		ByteOutputStream bos = new ByteOutputStream();
		bos.write(fis);
		gen.writeBinary(bos.getBytes());
		fis.close();
		bos.close();
	}

}
