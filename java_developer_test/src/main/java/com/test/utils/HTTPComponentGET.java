package com.test.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.test.config.ConfigProperties;

public class HTTPComponentGET {
	
//	@Autowired
//	UtilsHelper util;
	
	private Logger log = LogManager.getLogger(HTTPComponentGET.class);
	
	private String api_url;
	private int rto;
	private int cto;
	
	public HTTPComponentGET(){
	}
	
	public List<Map<String, Object>> sendToHttpGetList(Map<String,Object> request) throws Exception {		
		
		UtilsHelper util = new UtilsHelper();
		
		List<Map<String,Object>> responsebuild = new ArrayList<Map<String, Object>>();

		HttpURLConnection con = null;
		try {
			String url = this.api_url;
			
//			for (String key : request.keySet()) {
////				url = url+key+"="+URLEncoder.encode(request.get(key).toString())+"&";
//				url = url+key+"="+request.get(key).toString()+"&";
//			}
//			url = url.substring(0,url.length()-1);

			log.info("Request 'GET' -> "+url);

			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			con.setReadTimeout(rto);
			con.setConnectTimeout(cto);
			
			InputStream in = new BufferedInputStream(con.getInputStream());
			String response = IOUtils.toString(in, "UTF-8");
			
           	in.close();
            con.disconnect();
			
            log.info("Response 'GET' -> "+response.toString());
			
        	responsebuild = util.stringJsonToArray(response.toString());
			return responsebuild;
			
		
		} catch (ConnectException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			
		} catch (SocketTimeoutException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			
		} catch (SocketException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			
		} catch (IOException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Map<String, Object> sendToHttpGetMap(Map<String,Object> request) throws Exception {		
		
		UtilsHelper util = new UtilsHelper();
		
		Map<String,Object> responsebuild = new HashMap<String,Object>();

		HttpURLConnection con = null;
		try {
			String url = this.api_url;
			
//			for (String key : request.keySet()) {
////				url = url+key+"="+URLEncoder.encode(request.get(key).toString())+"&";
//				url = url+key+"="+request.get(key).toString()+"&";
//			}
//			url = url.substring(0,url.length()-1);

			log.info("Request 'GET' -> "+url);

			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			con.setReadTimeout(rto);
			con.setConnectTimeout(cto);
			
			InputStream in = new BufferedInputStream(con.getInputStream());
			String response = IOUtils.toString(in, "UTF-8");
			
           	in.close();
            con.disconnect();
			
            log.info("Response 'GET' -> "+response.toString());
			
        	responsebuild = util.stringJsonToMap(response.toString());
			return responsebuild;
			
		
		} catch (ConnectException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			
		} catch (SocketTimeoutException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			
		} catch (SocketException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			
		} catch (IOException ex) {
			log.error(ex.getMessage());
			ex.printStackTrace();
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}

	public String getURL() {
		return api_url;
	}
	
	public void setURL(String api_url) {
		this.api_url = api_url;
	}
	
	public int getRto() {
		return rto;
	}

	public void setRto(int rto) {
		this.rto = rto;
	}

	public int getCto() {
		return cto;
	}

	public void setCto(int cto) {
		this.cto = cto;
	}
}
