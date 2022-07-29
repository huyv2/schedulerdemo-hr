package hr.customization.cache;

import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.constant.CacheName;

public class TemplateCache extends Cache {
	@Override
	public boolean loadAll() {
		boolean isSuccess = true;
		try {
			Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.TEMPLATES_NEEDED_CACHE_NAME);
			cache.removeAll();
		} catch(Exception e) {
			log.error(e);
			isSuccess = false;
		}
		return isSuccess;
	}
}
