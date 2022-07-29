package hr.function;

import java.util.Map;

import hr.api.HrApi;
import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.constant.CacheName;
import hr.constant.ErrorCode;
import hr.dao.ScheduleDao;
import hr.dto.request.RequestBaseDto;
import hr.dto.request.define.ScheduleSettingDto;
import hr.dto.response.ResponseBaseDto;
import hr.repository.IScheduleRepository;
import hr.repository.RepositoryInstanceManager;

public class ScheduleSettingFunction extends HrApi {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IScheduleRepository scheduleRepository = RepositoryInstanceManager.getScheduleRepositoryInstance();

	@Override
	protected ResponseBaseDto executePost(RequestBaseDto requestBaseDto) {
		hr.dto.response.define.ScheduleSettingDto responseDto = new hr.dto.response.define.ScheduleSettingDto();
		
		ScheduleSettingDto jobSettingDto = (ScheduleSettingDto) requestBaseDto;
		ScheduleDao scheduleDao = new ScheduleDao();
		scheduleDao.setJobName(jobSettingDto.getJobName());
		scheduleDao.setBeginHourAt(Integer.parseInt(jobSettingDto.getBeginHourAt()));
		scheduleDao.setBeginMinuteAt(Integer.parseInt(jobSettingDto.getBeginMinuteAt()));
		scheduleDao.setEndHourAt(Integer.parseInt(jobSettingDto.getEndHourAt()));
		scheduleDao.setEndMinuteAt(Integer.parseInt(jobSettingDto.getEndMinuteAt()));
		scheduleDao.setInterval(Integer.parseInt(jobSettingDto.getInterval()));
		scheduleDao.setIsRunning(Boolean.parseBoolean(jobSettingDto.getIsRunning()));
		scheduleDao.setDayOfWeek(Integer.parseInt(jobSettingDto.getDayOfWeek()));
		scheduleDao.setDayOfMonth(Integer.parseInt(jobSettingDto.getDayOfMonth()));
		scheduleDao.setType(jobSettingDto.getType());
		
		Long id = scheduleRepository.insert(scheduleDao);
		if (id != null) {
			Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.SCHEDULE_CHECKING_CACHE_NAME);
			scheduleDao.setId(id);
			cache.put(scheduleDao.getJobName() + scheduleDao.getId(), scheduleDao);
			responseDto.setResponseCode(ErrorCode.SUCCESS);
		} else {
			responseDto.setResponseCode(ErrorCode.SYSTEM_ERROR);
		}
		
		responseDto.setId(id.toString());
		return responseDto;
	}

	@Override
	protected ResponseBaseDto executePut(RequestBaseDto requestBaseDto) {
		hr.dto.response.define.ScheduleSettingDto responseDto = new hr.dto.response.define.ScheduleSettingDto();
		
		ScheduleSettingDto jobSettingDto = (ScheduleSettingDto) requestBaseDto;
		Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.SCHEDULE_CHECKING_CACHE_NAME);
		ScheduleDao oldSchedule = (ScheduleDao) cache.get(jobSettingDto.getJobName() + jobSettingDto.getId());
		if (oldSchedule != null) {
			ScheduleDao scheduleDao = new ScheduleDao();
			scheduleDao.setJobName(jobSettingDto.getJobName());
			scheduleDao.setBeginHourAt(Integer.parseInt(jobSettingDto.getBeginHourAt()));
			scheduleDao.setBeginMinuteAt(Integer.parseInt(jobSettingDto.getBeginMinuteAt()));
			scheduleDao.setEndHourAt(Integer.parseInt(jobSettingDto.getEndHourAt()));
			scheduleDao.setEndMinuteAt(Integer.parseInt(jobSettingDto.getEndMinuteAt()));
			scheduleDao.setInterval(Integer.parseInt(jobSettingDto.getInterval()));
			scheduleDao.setIsRunning(Boolean.parseBoolean(jobSettingDto.getIsRunning()));
			scheduleDao.setDayOfWeek(Integer.parseInt(jobSettingDto.getDayOfWeek()));
			scheduleDao.setDayOfMonth(Integer.parseInt(jobSettingDto.getDayOfMonth()));
			scheduleDao.setType(jobSettingDto.getType());
			scheduleDao.setId(Long.parseLong(jobSettingDto.getId()));
			
			if (scheduleRepository.update(scheduleDao)) {
				cache.putOrUpdate(scheduleDao.getJobName() + scheduleDao.getId(), scheduleDao);
				responseDto.setResponseCode(ErrorCode.SUCCESS);
				responseDto.setId(Long.toString(scheduleDao.getId()));
			} else {
				responseDto.setResponseCode(ErrorCode.SYSTEM_ERROR);
			}
		} else {
			responseDto.setResponseCode(ErrorCode.SYSTEM_ERROR);
			log.debug(String.format("Cannot update due to not exist both job name %s and id %s", jobSettingDto.getJobName(), jobSettingDto.getId()));
		}
		
		return responseDto;
	}

	@Override
	protected ResponseBaseDto executeDelete(RequestBaseDto requestBaseDto) {
		hr.dto.response.define.ScheduleSettingDto responseDto = new hr.dto.response.define.ScheduleSettingDto();
		
		ScheduleSettingDto jobSettingDto = (ScheduleSettingDto) requestBaseDto;
		Long id = Long.parseLong(jobSettingDto.getId());
		
		if (scheduleRepository.delete(id, jobSettingDto.getJobName())) {
			Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.SCHEDULE_CHECKING_CACHE_NAME);
			if (cache.remove(jobSettingDto.getJobName() + jobSettingDto.getId())) {
				responseDto.setResponseCode(ErrorCode.SUCCESS);
				responseDto.setId(jobSettingDto.getId());
			} else {
				responseDto.setResponseCode(ErrorCode.SYSTEM_ERROR);
			}
		} else {
			responseDto.setResponseCode(ErrorCode.SYSTEM_ERROR);
		}
		
		return responseDto;
	}

	@Override
	protected ResponseBaseDto executeGet(Map<String, String[]> parameterMap) {
		String jobName = parameterMap.get("jobName")[0];
		String id = parameterMap.get("id")[0];
		hr.dto.response.define.ScheduleInquiryDto responseDto = new hr.dto.response.define.ScheduleInquiryDto();
		Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.SCHEDULE_CHECKING_CACHE_NAME);
		ScheduleDao scheduleDao = (ScheduleDao) cache.get(jobName + id);
		
		if (scheduleDao != null) {
			responseDto.setResponseCode(ErrorCode.SUCCESS);
			responseDto.setType(scheduleDao.getType());
			responseDto.setBeginHourAt(scheduleDao.getBeginHourAt());
			responseDto.setBeginMinuteAt(scheduleDao.getBeginMinuteAt());
			responseDto.setEndHourAt(scheduleDao.getEndHourAt());
			responseDto.setEndMinuteAt(scheduleDao.getEndMinuteAt());
			responseDto.setInterval(scheduleDao.getInterval());
			responseDto.setDayOfWeek(scheduleDao.getDayOfWeek());
			responseDto.setDayOfMonth(scheduleDao.getDayOfMonth());
			if (!scheduleDao.getIsRunning()) {
				responseDto.setIsRunning("false");
			} else {
				responseDto.setIsRunning("true");
			}
		} else {
			responseDto.setResponseCode(ErrorCode.DONOT_EXIST);
		}
		
		return responseDto;
	}
}
