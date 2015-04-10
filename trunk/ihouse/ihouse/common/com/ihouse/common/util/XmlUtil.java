/**
 * by leili
 */
package com.ihouse.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

/**
 * @author leili
 *
 */
public class XmlUtil {
	private static final Log LOG = LogFactory.getLog(XmlUtil.class);
	
	private static final Map<Class<? extends Object>,JAXBContext> MARSHALLERS = new HashMap<Class<? extends Object>,JAXBContext>();
	
	public static void toXML(Writer writer,Object body){
		try {
			JAXBContext context = MARSHALLERS.get(body.getClass());
			if(context==null){
				context = registerEntity(body.getClass());
			}
			Marshaller m = context.createMarshaller();
			m.marshal(body, writer);
			
		} catch (Exception e) {
			LOG.info(e,e);
			throw new RuntimeException(e);
			//LOG.error(e);
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				LOG.info(e,e);
			}
		}

	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T toEntity(Class<T> clazz,InputStream is){
		try {
			JAXBContext context = MARSHALLERS.get(clazz);
			if(context==null){
				context = registerEntity(clazz);
			}
			Unmarshaller um = context.createUnmarshaller();
			return (T) um.unmarshal(is);
		} catch (Exception e) {
			LOG.info(e,e);
			throw new RuntimeException(e);
			//LOG.error(e);
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				LOG.info(e,e);
			}
		}

	}
	
	public static <T> T toEntity(Class<T> clazz,File file){
		try {
			FileInputStream fis = new FileInputStream(file);
			return toEntity(clazz,fis);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}

	}
	
	
	public static synchronized JAXBContext registerEntity(Class<? extends Object> clazz) throws JAXBException {
		JAXBContext context = MARSHALLERS.get(clazz);
		if(context==null){
			context = JAXBContext.newInstance(clazz);
			MARSHALLERS.put(clazz,context);
		}
		return context;
	}


	/**
	 * @author Dengkai
	 * @param stream
	 * @return
	 * @throws Exception
	 */
	public static Document parseXml(InputStream stream)throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);
		factory.setIgnoringComments(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(stream); 
		return doc;
	}
	
	
}
