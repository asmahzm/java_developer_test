package com.test.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlowVariables {

	private HttpServletRequest request;
	
	private Map<String, Object> headers;
	
	private Map<String, Object> parameters;
	
	private Map<String, Object> responseData;
	
	private boolean valid;
}
