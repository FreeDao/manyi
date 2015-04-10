/**
 * 
 */
package com.leo.jackson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author hongci.li
 * 
 */
public class FileSerializer extends JsonSerializer<File> {
	@Override
	public void serialize(File value, JsonGenerator gen, SerializerProvider p) throws IOException, JsonProcessingException {
		FileInputStream fis = new FileInputStream(value);
		gen.writeBinary(fis,-1);

		
//		byte[] byt = new byte[fis.available()];
		System.out.println(fis.available());
//		fis.
//		
//		fis.
//		ByteInputStream bis = new ByteInputStream();
//		bis.
//		
		
//		ByteOutputStream bos = new ByteOutputStream();
//		bos.write(fis);
		
//		System.out.println(bos.getBytes().length);
		
//		gen.writeBinary(byt, 0, byt.length);
		
		
		
//		bos.flush();
		fis.close();
//		bos.close();
	}

}
