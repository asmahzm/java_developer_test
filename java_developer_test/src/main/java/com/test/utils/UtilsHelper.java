package com.test.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UtilsHelper {

	public String getDateTimeByFormat(String format, int dayChange) throws Exception {
		
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, dayChange);
		String datetime = dateFormat.format(cal.getTime());
		return datetime;
	}
	
	public String getExpiredTime(String minutes){
		
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(new Date()); 
		cal.add(Calendar.MINUTE, Integer.parseInt(minutes));
		String expired_time = sdf.format(cal.getTime());
	    return expired_time;
	}
	
	public String getRandomStringInteger(int many) throws Exception {
		
        String RANDOMCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuilder random_text = new StringBuilder();
        Random rnd = new Random();
        while (random_text.length() < many) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOMCHARS.length());
            random_text.append(RANDOMCHARS.charAt(index));
        }
        String randStr = random_text.toString();
        return randStr;
    }
	
	public String getRandomInteger(int many) throws Exception {
		
        String RANDOMCHARS = "1234567890";
        StringBuilder random_text = new StringBuilder();
        Random rnd = new Random();
        while (random_text.length() < many) { // length of the random string.
            int index = (int) (rnd.nextFloat() * RANDOMCHARS.length());
            random_text.append(RANDOMCHARS.charAt(index));
        }
        String randStr = random_text.toString();
        return randStr;
    }

	public List<Map<String, Object>> stringJsonToArray(String json) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		List<Map<String, Object>> datalistmap = new ArrayList<Map<String,Object>>();
		datalistmap = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>() {});
		return datalistmap;
	}
	
	public HashMap<String, Object> stringJsonToMap(String json) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, Object> datahashmap = new HashMap<String, Object>();
		datahashmap = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {});
		return datahashmap;
	}
	
	public Map<String, Object> stringMapToMap(String map) throws Exception {

		HashMap<String, Object> datahashmap = new HashMap<String, Object>();
		String[] pairs = map.split(", ");
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split("=");
			datahashmap.put(keyValue[0], keyValue[1]);
		}
		return datahashmap;
	}
	
	//OBJECT TO JSON BYTE
	public byte[] serialize(Object data) throws IOException {
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	    byte[] responseByte = ow.writeValueAsBytes(data);
	    return responseByte;
	}
	
	//OBJECT TO JSON STRING
	public String toJson(Object data) throws IOException {
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String JSONmsg = ow.writeValueAsString(data);
//		JSONmsg = JSONmsg.replace("\r\n", "");
	    return JSONmsg;
	}
	
	//JSON OBJECT TO OBJECT
	public Map<String, Object> toMap(JSONObject data) throws IOException {
		
		Map<String, Object> DataMapping = 
				new ObjectMapper().readValue(data.toString(), new TypeReference<Map<String, Object>>() {});
		return DataMapping;
	}
	
	// SET SIGNATURE JWT
	public String hmacSha256(String data, String secret) {
	    try {
	        //MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = secret.getBytes(StandardCharsets.UTF_8);	//digest.digest(secret.getBytes(StandardCharsets.UTF_8));

	        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
	        SecretKeySpec secretKey = new SecretKeySpec(hash, "HmacSHA256");
	        sha256Hmac.init(secretKey);

	        byte[] signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));

	        return Base64.getUrlEncoder().withoutPadding().encodeToString(signedBytes);
	        
	    } catch (Exception e) {
	    	
			e.printStackTrace();
			return null;
	    }
	}
	
	public Map<String, Object> logDataMap(String param) throws Exception {
		HashMap<String, Object> logData = new HashMap<String, Object>();
		String[] pairs = param.toString().replace("{", "").replace("}", "").split(", ");
		for (int i = 0; i < pairs.length; i++) {
			String pair = pairs[i];
			String[] keyValue = pair.split("=");
			if (!keyValue[0].equals("file_value")) {
				logData.put(keyValue[0], keyValue[1]);
			} else {
				logData.put("file_value", "value encoded base64");
			}
//			log.info("LOG REQUEST -> "+logData);
		}
		return logData;
	}
}
