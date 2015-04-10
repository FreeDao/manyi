package com.manyi.fyb.callcenter.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manyi.fyb.callcenter.base.model.Response;

@SuppressWarnings({ "deprecation", "unused" })
public class HttpClientHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);
	
	// static HttpClient httpclient = new DefaultHttpClient();
	static BasicCookieStore cookieStore = new BasicCookieStore();
	static BasicHttpContext httpContext = new BasicHttpContext();
	static int connectTimeout = 25000;
	static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

	static {
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
		cm.setMaxTotal(10000);
		cm.closeExpiredConnections();
	}

	public static HttpClient getHttpClient() {
		CloseableHttpClient httpclient = HttpClients.custom().setConnectionManager(cm).build();
				
		return httpclient;
	}
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * 
	 * @param shortUrl
	 * @return
	 */
	@Deprecated
	public static JSONObject sendRestInterShort(String shortUrl) {
		return sendRestInter(Constants.LINK_FRONT_PART + shortUrl, "");
	}

	@Deprecated
	public static JSONObject sendRestInter(String url) {
		return sendRestInter(url, "");
	}
	
	@Deprecated
	public static JSONObject sendRestInterShort(String shortUrl, Map<String, Object> pars) {

		System.out.println(Constants.LINK_FRONT_PART + shortUrl);
		return sendRestInter(Constants.LINK_FRONT_PART + shortUrl, pars);
	}
	
	@Deprecated
	public static JSONObject sendRestInterShortObject(String shortUrl, Object obj) {

		return sendRestInter(Constants.LINK_FRONT_PART + shortUrl, obj2Json(obj));
	}

	@Deprecated
	public static JSONObject sendRestInter(String url, Map<String, Object> pars) {
		JSONObject jobj = new JSONObject();
		if (pars != null) {
			Iterator<String> iter = pars.keySet().iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				Object value = pars.get(key);
				jobj.put(key, value);
			}
		}

		return sendRestInter(url, jobj.toString());
	}
	
	@Deprecated
	public static JSONObject sendRestInter(String url , String str){
		String returnStr = sendRestJsonStr2Str(url,str);
		return returnStr == null ? null :JSONObject.fromObject(returnStr);
	}
	
	
	/**
	 * 传递短链接 传递字符串json返回 JSONObject
	 */
	public static JSONObject sendRestJsonShortObj2JsonObj(String url ,Object obj){
		String returnStr = sendRestJsonStr2Str(url,obj2Json(obj));
		return returnStr == null ? null :JSONObject.fromObject(returnStr);
	}
	
	/**
	 * 传递短链接 传递字符串json返回字符串json
	 */
	public static String sendRestJsonShortStr2Str(String shortUrl, String str){
		return sendRestJsonStr2Str(Constants.LINK_FRONT_PART + shortUrl,str);
	}
	
	/**
	 * 传递短链接 传递对象返回字符串json
	 */
	public static String sendRestJsonShortObj2Str(String shortUrl, Object obj){
		return sendRestJsonShortStr2Str(shortUrl ,obj2Json(obj));
	}
	
	/**
	 * 传递短链接 传递对象返回对象
	 */
	public static <T> T sendRestJsonShortObj2Obj(String shortUrl, Object obj, Class<T> clazz) {
		return json2Obj(sendRestJsonShortStr2Str(shortUrl, obj2Json(obj)), clazz);
	}

	/**
	 * 传递短链接 传递字符串json返回对象
	 */
	public static <T> T sendRestJsonShortStr2Obj(String shortUrl, String str, Class<T> clazz) {
		return json2Obj(sendRestJsonShortStr2Str(shortUrl, str), clazz);
	}
	
	/**
	 * 传递完整链接 传递字符串json返回字符串json
	 */
	public static String sendRestJsonStr2Str(String url, String str) {
//		String smsUrl=url; 
//        HttpPost httppost = new HttpPost(smsUrl); 
//        
//        httppost.setConfig(requestConfig);
//        
//        
//        String strResult = "";  
//        ByteInputStream bis = null;
//        try {
//            
//            BasicHttpEntity en = new BasicHttpEntity();
//            en.setContentType("application/json");
//            if(str == null){
//            	str="";
//            }
//            byte[] bytes = str.getBytes("UTF-8");
//            
//            bis = new ByteInputStream(bytes,bytes.length);
//            en.setContent(bis);
//            en.setContentLength(bis.getCount());
//            httppost.setEntity(en);
//            
//            JSONObject sobj = null;
//            HttpResponse response = getHttpClient().execute(httppost,httpContext);  
//            if (response.getStatusLine().getStatusCode() == 200) {
//                /*读返回数据*/
//                strResult = EntityUtils.toString(response.getEntity());
//            } else {
//            	
//                String err = response.getStatusLine().getStatusCode() + "";
//                strResult += obj2Json(new Response(-999,"发送失败！" + err));
//            }
//            String msg = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+" url:"+url+"\n pars: "+str+"\n result:"+strResult+"\n";
//            System.out.println(msg);
//            return strResult;
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }  catch (Exception e) {
//            e.printStackTrace();
//        }  finally {
//        	httppost.releaseConnection();
//        	if (bis != null) {
//        		try {
//					bis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//        	
//        }
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		if(str==null){
			str="";
		}
		
		try {
			String strResult = "";
			URL urlSrc = new URL(url);
			connection = (HttpURLConnection)urlSrc.openConnection();
			connection.setRequestMethod("POST");
			connection.setReadTimeout(60000);
			connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			connection.setRequestProperty("Content-Length", str.getBytes("UTF-8").length+"");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			outputStream = connection.getOutputStream();
			//connection.connect();
			outputStream.write(str.getBytes("UTF-8"));
			outputStream.flush();
			
			inputStream = connection.getInputStream();
			if(connection.getResponseCode()==200){
				BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
				String line = "";
				StringBuffer response = new StringBuffer();
				while ((line = rd.readLine()) != null) {
					response.append(line);
				}
				strResult = response.toString();
			}else{
				String err = connection.getResponseCode() + "";
				strResult += obj2Json(new Response(-999,"发送失败！" + err));
			}
			return strResult;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(inputStream!=null){
					inputStream.close();
				}
				if(outputStream!=null){
					outputStream.close();
				}
				if(connection!=null){
					connection.disconnect();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
        return obj2Json(new Response(-998, "出现连接异常！"));
	}
	
	public static String obj2Json(Object obj) {
		String args = null;
		try {
			args = objectMapper.writeValueAsString(obj);
			return args;
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj2Json(new Response(-997, "转换格式时异常！"));
	}
	
	public static <T> T json2Obj(String json, Class<T> clz) {
		if (StringUtils.isEmpty(json)) {
			return null;
		}
		try {
			return objectMapper.readValue(json, clz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String HttpClientGetShort(String url) {
		return HttpClientGet(Constants.UC_FRONT_PART + url );
	}
	public static String HttpClientGet(String url) {
		logger.info(url);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = getHttpClient().execute(httpGet,httpContext);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error clientprotocol";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error io";
		}

		if (response != null && response.getStatusLine().getStatusCode() == 200) {
			/*读返回数据*/
			String result = null;
			try {
				result = EntityUtils.toString(response.getEntity());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error parse";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "error io";
			}
				System.out.println(result);
				return result;
		} else {
			return "error";
		}

	}
	
	public static void main(String[] args){
//		Class<HttpClientHelper> c = HttpClientHelper.class;
//		Method ms[] = c.getDeclaredMethods();
//		for(Method m : ms){
//			if(m.getName().startsWith("test")){
//				try {
//					m.invoke((Object)null);
//				} catch (IllegalAccessException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IllegalArgumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		sendRestJsonStr2Str("http://127.0.0.1","{name:'gg',age:12}");
		
		
	}
	
	/*private static void testGrid(){
		//String url = "http://localhost:8080/callcenter/testGrid";
		String url="http://localhost/rest/uc/regist.rest";
		Map<String, Object> pars=new HashMap<String, Object>();
		pars.put("realName", 5); 
		pars.put("code", "2222"); 
		JSONObject jsonObject = HttpClientHelper.sendRestInter(url,pars);
		System.out.println(jsonObject);
	}*/
	
	private static void testLogin() throws ClientProtocolException, IOException{
		//String url = "http://localhost:8080/callcenter/testGrid";
		String url="http://192.168.0.254/asterccinterfaces?EVENT=QUEUE&type=1&usertype=account&user=769&orgidentity=8&pwdtype=textplain&password=769";
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = getHttpClient().execute(httpGet,httpContext);  
         if (response.getStatusLine().getStatusCode() == 200) {
             /*读返回数据*/
             String result = EntityUtils.toString(response.getEntity());
             System.out.println(result);
         } else {
        	 
         }
		
	}
}

