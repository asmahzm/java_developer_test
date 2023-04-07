package com.test.services;

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
import com.test.utils.HTTPComponentGET;
import com.test.utils.UtilsHelper;

@Transactional
@Service
public class GetJobDetailApi {
	
	@Autowired
	ConfigProperties prop;
	
	@Autowired
	UtilsHelper util;
	
	@Autowired
	AccountLoginRepository db1;
	
	@Autowired
	AccountLoginJwtRepository db2;
	
	private Logger log = LogManager.getLogger(GetJobDetailApi.class);
	
	public Map<String, Object> hitGetJobDetailApi(FlowVariables flowVars) {
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		Map<String, Object> headers =  flowVars.getHeaders();
		Map<String, Object> payload = flowVars.getParameters();
		
		try {
			// JWT in HEADER part
			String jwt_value = (String) headers.get("token");
			
			// validate jwt in request and in database
			//// can be more secure if there is validation with expired token (not included yet)
			Map<String, Object> dataAccountLoginJwt = db2.getAccountLoginJwt(jwt_value);
			if (dataAccountLoginJwt.isEmpty()) {
				
				response.put("response_code", "02");
				response.put("response_description", "JWT Not Valid");
				return response;
			}
			
			// parameter ID in BODY part
			String id = (String) payload.get("id");
			
			// request HTTP to get job detail data
			Map<String, Object> requestData = new HashMap<String, Object>();
			
			String api_url = (String) prop.getProperty("job_detail_api_url");
			api_url = api_url.replace("{ID}", id);
			
			HTTPComponentGET http = new HTTPComponentGET();
			http.setURL(api_url);
			http.setCto(Integer.parseInt(prop.getProperty("api_timeout").toString()));
			http.setRto(Integer.parseInt(prop.getProperty("api_timeout").toString()));
			
			Map<String, Object> responseBody = http.sendToHttpGetMap(requestData);
			
			response.put("response_code", "00");
			response.put("response_description", "Success");
			response.put("data", responseBody);
			return response;
			
			
		} catch (Exception e) {
			log.info("Error Caused By : "+e.toString());
			e.printStackTrace();
			
			response.put("response_code", "99");
			response.put("response_description", "General Error");
		}

		return response;
	}
}
