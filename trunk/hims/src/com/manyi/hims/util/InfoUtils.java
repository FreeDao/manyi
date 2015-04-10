package com.manyi.hims.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leo.common.util.MD5Digest;
import com.manyi.hims.Global;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class InfoUtils {
	static Logger log = LoggerFactory.getLogger(InfoUtils.class);
	/**
	 * 向手机发送短信
	 */
	public static int sendSMS(String mobile,String content)throws Exception{
		StringBuffer sb = new StringBuffer(Global.url);
		sb.append("username="+Global.username);
		sb.append("&password="+Global.password);
		sb.append("&productid="+Global.productid);
		sb.append("&mobile="+mobile);
		sb.append("&content="+URLEncoder.encode(content,"utf-8"));
		URL url = new URL(sb.toString());
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("POST");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));//获取返回信息 
		String inputLine = in.readLine();
		System.out.println(inputLine);
		log.info("发送短信返回状态:"+inputLine);
		int status = -2;//表示网络异常，请求失败
		if(StringUtils.isNotBlank(inputLine)){
			if(inputLine.indexOf(",")>0){
				String[] input = inputLine.split(",");
//				if(input[0].equals("1")){
				status = Integer.parseInt(input[0]);
					System.out.println(status);
//					return true;
//				}
			}
		}
	
		
		 
//		String[] input = inputLine.split("\\s+"); 
//		if(input[0].equals("1") || input[0].equals("4")){
//			return true;
//		}else{
//			return false;
//		}
	   return status;
	}
	
	
	
	/**
	 * 向手机发送短信   北京
	 */
//	public static int sendSMS(String mobile,String content)throws Exception{
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String timestamp = sdf.format(new Date());
//		//lowercase(md5(lowercase(md5(密码)) + timestamp)) 
//		String authenticationString =  MD5Digest.getMD5Digest(MD5Digest.getMD5Digest("fybsw").toLowerCase()+timestamp).toLowerCase();
//		StringBuffer sb = new StringBuffer("http://i.uxins.com/interface/mtrecv_batch.jsp?");
//		sb.append("partner_code="+"sms_fybsw");
//		sb.append("&product_code="+"sms_fybsw_api");
//		sb.append("&timestamp="+timestamp);
//		sb.append("&authenticationString="+authenticationString);
//		sb.append("&mobile="+mobile);
//		sb.append("&message="+URLEncoder.encode(content,"utf-8"));
//		URL url = new URL(sb.toString());
//		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//		connection.setRequestMethod("POST");
//		
////		int responseCode = connection.getResponseCode();
//		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));//获取返回信息 
//		
//		int status = -2;//表示网络异常，请求失败.
//		String readLine = null;
//		while (in.readLine()!=null) {
//			readLine = in.readLine();
//			System.out.println(readLine);
//			if(readLine!=null){
//				status = Integer.parseInt(readLine);
//				/**
//				 * 此处转换是为了方法能够公用，不用改后面的短息模块
//				 */
//				if(status==0){
//					status = 1;
//				}
//			}
//		}
//		log.info("发送短信返回状态:"+status);
//	   return status;
//	}
	
	/**
	 * 向手机发送短信  优酷土豆
	 */
	public static int sendSMS1(String mobile,String content)throws Exception{
		String ret = "";
		List<String> mobileList = new ArrayList<String>();
		mobileList.add(mobile);
		StringBuffer sb = new StringBuffer("http://221.130.190.21/wl/sms_send.jsp?");
		sb.append("mobile="+mobileList.toString());
		sb.append("&content="+URLEncoder.encode(content,"utf-8"));
		URL url = new URL(sb.toString());
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		
		int responseCode = connection.getResponseCode();

		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		connection.connect(); 
		
		if (responseCode == 200) {
			String line = br.readLine(); 
	
			while(line != null){ 
	
				log.info(line);
				ret = JSONObject.fromObject(line).getJSONObject("e").getString("code");
				line = br.readLine(); 
	
			} 
			
			if ("0".equals(ret)) {
				log.info("手机{},短信发送成功：{}", mobile, ret);
				return 1;
			} else {
				log.info("手机{},短信发送失败：{}", mobile, ret);
				return -1;
			}
		} else {
			log.info("手机{},短信发送失败,服务器错误，错误：{}", mobile, responseCode);
			return -1;
		}
	}
	
	
	static HttpClient httpclient = new DefaultHttpClient();
	static BasicCookieStore cookieStore = new BasicCookieStore();
	static BasicHttpContext httpContext = new BasicHttpContext();
	static{
	    httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}
	
	public static JSONObject sendRestInter(String url, JSONObject jobj) {
		return sendRest(url, jobj);
	}
	
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
		return sendRest(url, jobj);
	}
	
	private static JSONObject sendRest(String url, JSONObject jobj) {
		String smsUrl=url; 
        HttpPost httppost = new HttpPost(smsUrl);
        //pars.put("App-Key", "fybao.superjia.com");
		//pars.put("App-Secret", "MT0VT5EN1FAP7SGA840OBW2DUFJUAB");
        
        httppost.addHeader("App-Key", "fybao.superjia.com");
        httppost.addHeader("App-Secret","MT0VT5EN1FAP7SGA840OBW2DUFJUAB");
        
        String strResult = "";
          
        try {  
	        //nameValuePairs.add(new BasicNameValuePair("msg", getStringFromJson(jobj)));  
	        //httppost.addHeader("Content-type", "application/x-www-form-urlencoded");  
	        //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));  
	        BasicHttpEntity en = new BasicHttpEntity();
	        en.setContentType("application/json");
	        
	        String str = jobj.toString();
	        if(str == null){
	        	str="";
	        }
	        byte[] bytes = str.getBytes();
	        
	        ByteInputStream bis = new ByteInputStream(bytes,bytes.length);
	        en.setContent(bis);
	        en.setContentLength(bis.getCount());
	        httppost.setEntity(en);
	        
	          
	        JSONObject sobj = null;
	        HttpResponse response = httpclient.execute(httppost,httpContext);  
	        if (response.getStatusLine().getStatusCode() == 200) {
	        	sobj = new JSONObject();
	            /*读返回数据*/  
	            String conResult = EntityUtils.toString(response.getEntity());  
	            sobj = sobj.fromObject(conResult);  
	            strResult=sobj.toString();
	        } else {  
	            String err = response.getStatusLine().getStatusCode()+"";  
	            strResult += "发送失败:"+err;  
	        }
	        String msg = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+" url:"+url+"\n 参数:"+jobj.toString()+"\n result:"+strResult+"\n";
	        System.out.println(msg);
	        File file =new File("d:/1.txt");
	        FileOutputStream os =new FileOutputStream(file,true);
	        os.write(msg.getBytes());
	        os.close();
	        return sobj;
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;
	}
	 
	 @SuppressWarnings("unused")
	private static String getStringFromJson(JSONObject adata) {
	        StringBuffer sb = new StringBuffer();  
	        sb.append("{");  
	        for(Object key:adata.keySet()){  
	            sb.append("\""+key+"\":\""+adata.get(key)+"\",");  
	        }  
	        String rtn = sb.toString().substring(0, sb.toString().length()-1)+"}";  
	        return rtn;  
	    }  
	 
	 
	 
	 
	public static void main(String[] args) {
		try {
//			sendSMS("13651685162",Global.registBeforeV+"111111"+Global.registAfterV);
//			sendSMS("13611996368",Global.registBeforeV+"111111"+Global.registAfterV);
			//sendSMS("13651685162", "房源宝五月活动开始咯，当月上传信息审核成功累计200条再奖二百，500条奖一千，1000条奖二千五，赶快行动吧！劳动最光荣祝大家节日快乐！【房源宝】");
//			sendSMS("13651685162","111111,【房源宝】");
//			InfoUtils.sendSMS("18516285363","111111,【房源宝】");
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
