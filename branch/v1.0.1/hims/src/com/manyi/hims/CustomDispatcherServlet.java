package com.manyi.hims;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.web.servlet.DispatcherServlet;

public class CustomDispatcherServlet extends DispatcherServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String contentType = request.getHeader("Content-Type");

		if (!StringUtils.isBlank(contentType) && contentType.toLowerCase().startsWith("application/json"))
			request = new FilterRequestWrapper(request);

		super.doService(request, response);
	}

	public static class FilterRequestWrapper extends HttpServletRequestWrapper {

		private final String payload;
		private String encoding = null;

		public FilterRequestWrapper(HttpServletRequest request) {
			super(request);
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader bufferedReader = null;
			String encoding = request.getCharacterEncoding();
			try {

				// read the payload into the StringBuilder
				// 按照正确的encoding，将inputStream中的内容写入到String中
				InputStream inputStream = request.getInputStream();
				if (inputStream != null) {

					if (StringUtils.isEmpty(encoding)) {
						bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					} else {
						this.encoding = encoding;
						bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoding));
					}
					int bytesRead = -1;
					int total = request.getContentLength();
					int remaining = total;
					char[] charBuffer = new char[total];
					while ((bytesRead = bufferedReader.read(charBuffer, total - remaining, remaining)) != -1 
						     && remaining > 0) { 
						    remaining -= bytesRead;
					}
					stringBuilder.append(charBuffer);
//					while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
//						System.out.println("bytesRead-->"+bytesRead);
//						stringBuilder.append(charBuffer, 0, bytesRead);
//					}

				} else {
					// make an empty string since there is no payload
					stringBuilder.append("");
				}
			} catch (IOException ex) {
				throw new RuntimeException("Error reading the request payload", ex);
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException iox) {
						// ignore
					}
				}
			}
			payload = stringBuilder.toString();
		}

		public String getPayload() {
			return this.payload;
		}

		// 重载getInputStream方法，获取ServletInputStream流
		@Override
		public ServletInputStream getInputStream() throws IOException {

			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(StringUtils.isEmpty(encoding) ? payload.getBytes() : payload.getBytes(encoding));
			
			DelegatingServletInputStream inputStream = new DelegatingServletInputStream(byteArrayInputStream);

//			ServletInputStream inputStream = new ServletInputStream() {
//				public int read() throws IOException {
//					return byteArrayInputStream.read();
//				}
//			};
			return inputStream;
		}
	}

}
