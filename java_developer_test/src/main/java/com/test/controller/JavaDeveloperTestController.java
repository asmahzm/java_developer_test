package com.test.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.test.config.FlowVariables;
import com.test.config.ConfigProperties;
import com.test.services.GetJobDetailApi;
import com.test.services.GetJobListApi;
import com.test.services.LoginApi;
import com.test.utils.UtilsHelper;

@RestController
public class JavaDeveloperTestController {

	@Autowired
	ConfigProperties prop;
	
	@Autowired
	UtilsHelper util;
	
	@Autowired
	LoginApi loginApi;
	
	@Autowired
	GetJobListApi getJobListApi;
	
	@Autowired
	GetJobDetailApi getJobDetailApi;
	
	private Logger log = LogManager.getLogger(JavaDeveloperTestController.class);
	
	@PostMapping("${server.path.login-api}")
	public Map<String, Object> LoginApi (HttpServletRequest request,
			@RequestHeader Map<String, Object> header, @RequestBody Map<String, Object> parameters) {
		
		FlowVariables flowVars = new FlowVariables();
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			log.info("=============== LOGIN API PROCESS ===============");
			log.info("Request Message -> "+parameters);
			
			// SET REQUEST
			flowVars.setRequest(request);
			flowVars.setHeaders(header);
			flowVars.setParameters(parameters);
			
			// HIT SERVICE
			response = loginApi.hitLoginApi(flowVars);
			
			log.info("Response Message -> "+response);
			log.info("=============== END PROCESS ===============");
			
			return response;
			
			
		} catch (Exception e) {
			log.info("Error Caused By : "+e.toString());
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	@PostMapping("${server.path.job-list-api}")
//	public List<Map<String, Object>> GetJobListApi (HttpServletRequest request, 
	public Map<String, Object> GetJobListApi (HttpServletRequest request, 
			@RequestHeader Map<String, Object> header, @RequestBody Map<String, Object> parameters) {
		
		FlowVariables flowVars = new FlowVariables();
//		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			log.info("=============== GET JOB LIST API PROCESS ===============");
			log.info("Request Message -> "+parameters);
			
			// SET REQUEST
			flowVars.setRequest(request);
			flowVars.setHeaders(header);
			flowVars.setParameters(parameters);
			
			// HIT SERVICE
			response = getJobListApi.hitGetJobListApi(flowVars);

			log.info("Response Message -> "+response);
			log.info("=============== END PROCESS ===============");
			
			return response;
			
			
		} catch (Exception e) {
			log.info("Error Caused By : "+e.toString());
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	@PostMapping("${server.path.job-detail-api}")
	public Map<String, Object> GetJobDetailApi (HttpServletRequest request, 
			@RequestHeader Map<String, Object> header, @RequestBody Map<String, Object> parameters) {
		
		FlowVariables flowVars = new FlowVariables();
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			log.info("=============== GET JOB DETAIL API PROCESS ===============");
			log.info("Request Message -> "+parameters);
			
			// SET REQUEST
			flowVars.setRequest(request);
			flowVars.setHeaders(header);
			flowVars.setParameters(parameters);
			
			// HIT SERVICE
			response = getJobDetailApi.hitGetJobDetailApi(flowVars);
			
			log.info("Response Message -> "+response);
			log.info("=============== END PROCESS ===============");
			
			return response;
			
			
		} catch (Exception e) {
			log.info("Error Caused By : "+e.toString());
			e.printStackTrace();
		}
		
		return null;
		
	}
}
