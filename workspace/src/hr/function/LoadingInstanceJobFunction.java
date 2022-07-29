package hr.function;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import hr.api.HrApi;
import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.constant.CacheName;
import hr.constant.ErrorCode;
import hr.constant.ParamConstant;
import hr.dto.request.RequestBaseDto;
import hr.dto.request.define.LoadingInstanceJobDto;
import hr.dto.response.ResponseBaseDto;
import hr.job.BaseJob;
import hr.util.FileUtil;

public class LoadingInstanceJobFunction extends HrApi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	@Override
	protected ResponseBaseDto executePost(RequestBaseDto requestBaseDto) {
		LoadingInstanceJobDto requestDto = (LoadingInstanceJobDto) requestBaseDto;
		hr.dto.response.define.LoadingInstanceJobDto responseDto = new hr.dto.response.define.LoadingInstanceJobDto();
		String packageName = ParamConstant.JOBIMPL_PACKAGE;
		String className = new StringBuilder(packageName).append('.').append(requestDto.getInstanceJobName()).toString();
		Class classLiteral = null;
		try {
			classLiteral = Class.forName(className);
		} catch (ClassNotFoundException e) {
			log.error("Cannot get literal class: ", e);
		} catch(Exception e) {
			log.error("Cannot get literal class: ", e);
		}
		responseDto.setResponseCode(ErrorCode.FAILED);
		if (classLiteral != null) {
			try {
				if (BaseJob.class.isAssignableFrom(Class.forName(className))) {
					Class<?> literal = Class.forName(className).asSubclass(BaseJob.class);
					Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.INSTANCE_JOB_CACHE_NAME);
					if (cache.get(requestDto.getInstanceJobName()) == null) {
						cache.put(requestDto.getInstanceJobName(), literal.newInstance());
						responseDto.setResponseCode(ErrorCode.SUCCESS);
					} else {
						responseDto.setResponseCode(ErrorCode.EXISTED);
					}
				}
			} catch (ClassNotFoundException e) {
				log.error(e);
			} catch(Exception e) {
				log.error(e);
			}
		}
		return responseDto;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected ResponseBaseDto executePut(RequestBaseDto requestBaseDto) {
		LoadingInstanceJobDto requestDto = (LoadingInstanceJobDto) requestBaseDto;
		hr.dto.response.define.LoadingInstanceJobDto responseDto = new hr.dto.response.define.LoadingInstanceJobDto();
		String packageName = ParamConstant.JOBIMPL_PACKAGE;
		String className = new StringBuilder(packageName).append('.').append(requestDto.getInstanceJobName()).toString();
		Class classLiteral = null;
		try {
			classLiteral = Class.forName(className);
		} catch (ClassNotFoundException e) {
			log.error("Cannot get literal class: ", e);
		} catch(Exception e) {
			log.error("Cannot get literal class: ", e);
		}
		responseDto.setResponseCode(ErrorCode.FAILED);
		if (classLiteral != null) {
			try {
				if (BaseJob.class.isAssignableFrom(Class.forName(className))) {
					Class<?> literal = Class.forName(className).asSubclass(BaseJob.class);
					Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.INSTANCE_JOB_CACHE_NAME);
					cache.putOrUpdate(requestDto.getInstanceJobName(), literal.newInstance());
					responseDto.setResponseCode(ErrorCode.SUCCESS);
				}
			} catch (ClassNotFoundException e) {
				log.error(e);
			} catch(Exception e) {
				log.error(e);
			}
		}
		return responseDto;
	}

	@Override
	protected ResponseBaseDto executeDelete(RequestBaseDto requestBaseDto) {
		LoadingInstanceJobDto requestDto = (LoadingInstanceJobDto) requestBaseDto;
		hr.dto.response.define.LoadingInstanceJobDto responseDto = new hr.dto.response.define.LoadingInstanceJobDto();
		Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.INSTANCE_JOB_CACHE_NAME);
		if (cache.get(requestDto.getInstanceJobName()) != null) {
			cache.remove(requestDto.getInstanceJobName());
			responseDto.setResponseCode(ErrorCode.SUCCESS);
		} else {
			responseDto.setResponseCode(ErrorCode.DONOT_EXIST);
		}
		return responseDto;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected ResponseBaseDto executeGet(Map<String, String[]> parameterMap) {
		hr.dto.response.define.LoadingInstanceJobDto responseDto = new hr.dto.response.define.LoadingInstanceJobDto();
		String packageName = ParamConstant.JOBIMPL_PACKAGE;
		String jobEndName = "Job.class";
		try {
			List<Class> literalList = FileUtil.getClasses(packageName, jobEndName, BaseJob.class);
			if (literalList.size() > 0) {
				Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.INSTANCE_JOB_CACHE_NAME);
				for(Class literal : literalList) {
					if (cache.get(literal.getSimpleName()) == null) {
						cache.put(literal.getSimpleName(), literal.newInstance());
						responseDto.add(literal.getSimpleName());
					}
				}
			}
			responseDto.setResponseCode(ErrorCode.SUCCESS);
		} catch (ClassNotFoundException | IOException e) {
			log.error(e);
		} catch(Exception e) {
			log.error(e);
		}
		return responseDto;
	}
}
