package com.keke.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.keke.util.ErrorCodeConstants;


public class OrderExceptionHandler implements HandlerExceptionResolver {  
	
	@Autowired(required = true)
	private MessageSource messageSource;
  
	@Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,  
            Exception e) {  
        
		StringWriter sw = new StringWriter(); 
		e.printStackTrace(new PrintWriter(sw, true)); 
		String error = sw.toString(); 
		System.out.println(error);
		
		Map<String, Object> map = new HashMap<String, Object>();
		if(e instanceof OrderErrorViewException){
			OrderErrorViewException errorView = (OrderErrorViewException) e;
			map.put("success", errorView.isSuccess());
			map.put("data", errorView.getData());
			String errorCode = errorView.getErrorCode();
			String errorMsg = getI18nMsg(errorCode);
			map.put("errorCode", errorCode);
			if (!StringUtils.isEmpty(errorMsg)) {
				map.put("errorMsg", errorMsg);
			} else {
				map.put("errorMsg", error);
			}
		}
		else{
			map.put("success", false);
			map.put("data", error);
			String errorMsg = getI18nMsg(ErrorCodeConstants.ERROR_SYS_ERROR);
			map.put("errorCode", ErrorCodeConstants.ERROR_SYS_ERROR);
			if (!StringUtils.isEmpty(errorMsg)) {
				map.put("errorMsg", errorMsg);
			} else {
				map.put("errorMsg", error);
			}
		}
		
		
		ModelAndView view = new ModelAndView(new MappingJacksonJsonView(), map);
		return view;
      
    }
	
	public String getI18nMsg(String msgName) {
		return messageSource.getMessage(msgName, null, Locale.CHINA);
	}
	
	
}  