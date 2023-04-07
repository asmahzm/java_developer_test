package com.test.services;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.config.ConfigProperties;
import com.test.config.FlowVariables;
import com.test.repositories.AccountLoginJwtRepository;
import com.test.repositories.AccountLoginRepository;
import com.test.utils.UtilsHelper;

@Transactional
@Service
public class LoginApi {

	@Autowired
	ConfigProperties prop;
	
	@Autowired
	UtilsHelper util;
	
	@Autowired
	AccountLoginRepository db1;
	
	@Autowired
	AccountLoginJwtRepository db2;
	
	private Logger log = LogManager.getLogger(LoginApi.class);
	
	public Map<String, Object> hitLoginApi(FlowVariables flowVars) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		Map<String, Object> headers =  flowVars.getHeaders();
		Map<String, Object> payload = flowVars.getParameters();
		
		try {
			String username = (String) payload.get("username");
			String password = (String) payload.get("password");
			
			Map<String, Object> dataAccountLogin = db1.getAccountLogin(username, password);
			if (dataAccountLogin.isEmpty()) {
				
				response.put("response_code", "01");
				response.put("response_description", "Username Or Password Not Valid");
				return response;
			}
			
			//SET JWT VALUE
			Map<String, Object> token_header = new HashMap<String, Object>();
			token_header.put("alg", prop.getProperty("alg"));
			token_header.put("typ", prop.getProperty("typ"));
			
			String data_header = util.toJson(token_header);
			String data_body = util.toJson(payload);
			String data_bearer = prop.getProperty("api_secret");
			
			String signature = util.hmacSha256(Base64.getUrlEncoder().withoutPadding().encodeToString(data_header.getBytes()) + "." + Base64.getUrlEncoder().withoutPadding().encodeToString(data_body.getBytes()), data_bearer);
			String jwtToken = Base64.getUrlEncoder().withoutPadding().encodeToString(data_header.getBytes()) + "." + Base64.getUrlEncoder().withoutPadding().encodeToString(data_body.getBytes()) + "." + signature;
			log.info("JWT Value -> "+jwtToken);
			
			// INSERT DATA TO ACCOUNT LOGIN JWT
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String created_date = sdf.format(new Date());
			String created_by = "SYSTEM";
			
			int status = db2.insertAccountLoginJwt(username, password, jwtToken, created_date, created_by);
			if (status == 0) {
				log.info("failed insert to database");
			}
			
			response.put("response_code", "00");
			response.put("response_description", "Success");
			response.put("jwt", jwtToken);
			
			
		} catch (Exception e) {
			log.info("Error Caused By : "+e.toString());
			e.printStackTrace();
			
			response.put("response_code", "99");
			response.put("response_description", "General Error");
		}

		return response;
	}
}
