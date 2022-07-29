package hr.customization.cache;

import java.util.List;

import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.constant.CacheName;
import hr.dao.ScheduleDao;
import hr.repository.IScheduleRepository;
import hr.repository.RepositoryInstanceManager;

public class ScheduleCache extends Cache {
	private IScheduleRepository jobRepository = RepositoryInstanceManager.getScheduleRepositoryInstance();
	
	@Override
	public boolean update() {
		return jobRepository.updateResettingSchedule();
	}
	@Override
	public boolean loadAll() {
		boolean isSuccess = false;
		try {
			List<ScheduleDao> jobList = jobRepository.findAll();
			Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.SCHEDULE_CHECKING_CACHE_NAME);
			cache.removeAll();
			for(ScheduleDao jobDao : jobList) {
				cache.put(jobDao.getJobName() + jobDao.getId(), jobDao);
			}
			isSuccess = true;
		} catch(Exception e) {
			log.error(e);
		}
		return isSuccess;
	}
}
