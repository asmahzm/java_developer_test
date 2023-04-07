package com.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigProperties {

	@Value("${api.job-list-api.url}")
	private String job_list_api_url;
	
	@Value("${api.job-detail-api.url}")
	private String job_detail_api_url;
	
	@Value("${api.timeout}")
	private String api_timeout;
	
	@Value("${alg}")
	private String alg;
	
	@Value("${typ}")
	private String typ;
	
	@Value("${api_secret}")
	private String api_secret;
	
	public String getProperty (String propName) {
		
		if (propName.equals("job_list_api_url")) {
			return job_list_api_url;
		}
		
		if (propName.equals("job_detail_api_url")) {
			return job_detail_api_url;
		}
		
		if (propName.equals("api_timeout")) {
			return api_timeout;
		}
		
		if (propName.equals("alg")) {
			return alg;
		}
		
		if (propName.equals("typ")) {
			return typ;
		}
		
		if (propName.equals("api_secret")) {
			return api_secret;
		}
		
		return null;
	}
}
