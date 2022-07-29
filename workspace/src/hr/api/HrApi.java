package hr.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.constant.CacheName;
import hr.constant.ErrorCode;
import hr.constant.ParamConstant;
import hr.dto.request.RequestBaseDto;
import hr.dto.response.DefaultResponseBaseDto;
import hr.dto.response.ResponseBaseDto;
import hr.util.HttpUtil;
import hr.util.JsonParserUtil;

public abstract class HrApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public final Logger log = Logger.getLogger(this.getClass());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			resp.setContentType(ParamConstant.JSON_CONTENT_TYPE);
			PrintWriter responseWriter = resp.getWriter();
			
			String responseBody = "";
			ResponseBaseDto responseDto = executeGet(req.getParameterMap());
			responseBody = JsonParserUtil.toJson(responseDto);
			
			responseWriter.println(responseBody);
			responseWriter.flush();
		} catch(Exception e) {
			log.error(e);
			defaultResponse(resp);
		}
	}
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String responseBody = "";
			PrintWriter responseWriter = resp.getWriter();
			RequestBaseDto requestDto = preProcess(req, resp);
			if (requestDto != null) {
				ResponseBaseDto responseDto = executePost(requestDto);
				responseBody = JsonParserUtil.toJson(responseDto);
			} else {
				throw new Exception("Cannot parse request object in servlet!!!");
			}
			
			responseWriter.print(responseBody);
			responseWriter.flush();
		} catch(Exception e) {
			log.error(e);
			defaultResponse(resp);
		}
	}
	
	@Override
	public void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String responseBody = "";
			PrintWriter responseWriter = resp.getWriter();
			RequestBaseDto requestDto = preProcess(req, resp);
			if (requestDto != null) {
				ResponseBaseDto responseDto = executePut(requestDto);
				responseBody = JsonParserUtil.toJson(responseDto);
			} else {
				throw new Exception("Cannot parse request object in servlet!!!");
			}
			
			responseWriter.print(responseBody);
			responseWriter.flush();
		} catch(Exception e) {
			log.error(e);
			defaultResponse(resp);
		}
	}
	
	@Override
	public void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String responseBody = "";
			PrintWriter responseWriter = resp.getWriter();
			RequestBaseDto requestDto = preProcess(req, resp);
			if (requestDto != null) {
				ResponseBaseDto responseDto = executeDelete(requestDto);
				responseBody = JsonParserUtil.toJson(responseDto);
			} else {
				throw new Exception("Cannot parse request object in servlet!!!");
			}
			
			responseWriter.print(responseBody);
			responseWriter.flush();
		} catch(Exception e) {
			log.error(e);
			defaultResponse(resp);
		}
	}
	
	private RequestBaseDto preProcess(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType(ParamConstant.JSON_CONTENT_TYPE);
		
		RequestBaseDto requestDto = null;
		String requestUri = req.getRequestURI();
		Cache literalCache = (Cache) CacheManager.getInstance().getCache(CacheName.LITERAL_CLASS_CACHE_NAME);
		Object tClass = literalCache.get(requestUri);
		if (tClass != null) {
			Object tObject = HttpUtil.of(req.getReader()).toObject((Class<?>) tClass);
			requestDto = (RequestBaseDto) tObject;
		}
		
		return requestDto;
	}
	
	private void defaultResponse(HttpServletResponse resp) {
		try {
			PrintWriter responseWriter = resp.getWriter();
			ResponseBaseDto defaultResponseDto = new DefaultResponseBaseDto();
			defaultResponseDto.setResponseCode(ErrorCode.SYSTEM_ERROR);
			String defaultResponseBody = JsonParserUtil.toJson(defaultResponseDto);
			responseWriter.print(defaultResponseBody);
			responseWriter.flush();
		} catch(Exception e) {
			log.error(e);
		}
	}
	
	protected abstract ResponseBaseDto executeGet(Map<String, String[]> parameterMap);
	protected abstract ResponseBaseDto executePost(RequestBaseDto requestBaseDto);
	protected abstract ResponseBaseDto executePut(RequestBaseDto requestBaseDto);
	protected abstract ResponseBaseDto executeDelete(RequestBaseDto requestBaseDto);
}
