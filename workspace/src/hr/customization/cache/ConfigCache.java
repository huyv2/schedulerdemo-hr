package hr.customization.cache;

import java.util.Properties;

import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.common.parameter.Parameter;
import hr.constant.CacheName;

public class ConfigCache extends Cache {
	@Override
	public boolean loadAll() {
		boolean isSuccess = false;
		Properties props = Parameter.loadParameters();
		if (props.size() > 0) {
			try {
				Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.PARAMETER_CONFIG_CACHE_NAME);
				cache.removeAll();
				for(Object o : props.keySet()){
					cache.put(o.toString(), props.getProperty(o.toString()));
					log.info(String.format("PARAMETER_CONFIG_CACHE_NAME: %s = %s", o.toString(), props.getProperty(o.toString())));
				}
				isSuccess = true;
			} catch(Exception e) {
				log.error(e);
			}
		}
		return isSuccess;
	}
}
