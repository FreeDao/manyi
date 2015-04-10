package com.manyi.hims.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.Header;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
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

import com.manyi.hims.Global;
import com.manyi.hims.uc.service.UserServiceImpl;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

public class InfoUtils {
	static Logger log = LoggerFactory.getLogger(InfoUtils.class);
	/**
	 * 向手机发送短信
	 */
	public static boolean sendSMS(String mobile,String content)throws Exception{
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
		if(StringUtils.isNotBlank(inputLine)){
			if(inputLine.indexOf(",")>0){
				String[] input = inputLine.split(",");
				if(input[0].equals("1")){
					return true;
				}
			}
		}
	
		
		 
//		String[] input = inputLine.split("\\s+"); 
//		if(input[0].equals("1") || input[0].equals("4")){
//			return true;
//		}else{
//			return false;
//		}
	   return false;
	}
	
	static HttpClient httpclient = new DefaultHttpClient();
	static BasicCookieStore cookieStore = new BasicCookieStore();
	static BasicHttpContext httpContext = new BasicHttpContext();
	static{
	    httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	}
	
	@SuppressWarnings("static-access")
	public static JSONObject sendRestInter(String url , Map<String, Object> pars){  
	 
	       

	        String smsUrl=url; 
	        HttpPost httppost = new HttpPost(smsUrl);
	        //pars.put("App-Key", "fybao.superjia.com");
			//pars.put("App-Secret", "MT0VT5EN1FAP7SGA840OBW2DUFJUAB");
	        
	        httppost.addHeader("App-Key", "fybao.superjia.com");
	        httppost.addHeader("App-Secret","MT0VT5EN1FAP7SGA840OBW2DUFJUAB");
	        
	        String strResult = "";
	        
	          
	        try {  
	
	 	        
	              
	              // List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();  
	                JSONObject jobj = new JSONObject();  
	                if(pars != null){
	                	Iterator<String> iter= pars.keySet().iterator();
	                	while (iter.hasNext()) {
							String key =iter.next();
							Object value = pars.get(key);
							jobj.put(key, value);
						}
	                }
	                  
	                  
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
	                String msg = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+" url:"+url+"\n 参数:"+JSONObject.fromObject(pars).toString()+"\n result:"+strResult+"\n";
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
			//sendSMS("13651685162",Global.registBeforeV+"111111"+Global.registAfterV);
			//sendSMS("13651685162", "房源宝五月活动开始咯，当月上传信息审核成功累计200条再奖二百，500条奖一千，1000条奖二千五，赶快行动吧！劳动最光荣祝大家节日快乐！【房源宝】");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
