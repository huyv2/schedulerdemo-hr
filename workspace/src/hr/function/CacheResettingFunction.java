package hr.function;

import java.util.Map;

import hr.api.HrApi;
import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.constant.ErrorCode;
import hr.dto.request.RequestBaseDto;
import hr.dto.request.define.CacheResettingDto;
import hr.dto.response.ResponseBaseDto;

public class CacheResettingFunction extends HrApi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected ResponseBaseDto executeGet(Map<String, String[]> parameterMap) {
		return null;
	}

	@Override
	protected ResponseBaseDto executePost(RequestBaseDto requestBaseDto) {
		return null;
	}

	@Override
	protected ResponseBaseDto executePut(RequestBaseDto requestBaseDto) {
		CacheResettingDto requestDto = (CacheResettingDto) requestBaseDto;
		hr.dto.response.define.CacheResettingDto responseDto = new hr.dto.response.define.CacheResettingDto();
		
		String cacheName = requestDto.getCacheName();
		Cache cache = (Cache) CacheManager.getInstance().getCache(cacheName);
		if (cache != null) {
			if (cache.loadAll()) {
				responseDto.setResponseCode(ErrorCode.SUCCESS);
				log.debug(String.format("Reset %s: %s successfully", cache.getName(), cache.getSize()));
			} else {
				responseDto.setResponseCode(ErrorCode.FAILED);
				log.debug(String.format("Reset %s: %s failed", cache.getName(), cache.getSize()));
			}
		} else {
			responseDto.setResponseCode(ErrorCode.DONOT_EXIST);
			log.debug(String.format("Cache with name as %s does not exist", cacheName));
		}
		
		return responseDto;
	}

	@Override
	protected ResponseBaseDto executeDelete(RequestBaseDto requestBaseDto) {
		return null;
	}
}
