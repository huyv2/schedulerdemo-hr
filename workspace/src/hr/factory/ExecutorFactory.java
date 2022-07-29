package hr.factory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.config.Config;
import hr.constant.CacheName;
import hr.constant.ParamConstant;
import hr.job.BaseJob;

public class ExecutorFactory {
	private static final Logger log = Logger.getLogger(ExecutorFactory.class);
	
	private static int corePoolSize;
	private static int maxPoolSize;
	private static byte boundQueue;
	//private static long taskTimeout;
	private static ThreadPoolExecutor executor = null;
	private static String endWaitTaskName = "wait";
	
	public static void init() {
		try {
			corePoolSize = Integer.parseInt(Config.getValue("corePoolSize"));
			maxPoolSize = Integer.parseInt(Config.getValue("maxPoolSize"));
			boundQueue = Byte.parseByte(Config.getValue("boundQueue"));
			//taskTimeout = Long.parseLong(Config.getValue("taskTimeout"));
		} catch(Exception e) {
			corePoolSize = 1;
			maxPoolSize = 1;
			boundQueue = ParamConstant.IS_MAX_TASK_QUEUE;
			//taskTimeout = 60L;
		}
		
		try {
			if (boundQueue == ParamConstant.IS_MAX_TASK_QUEUE) {
				executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>()) {
			        @Override
			        protected void afterExecute(Runnable r, Throwable t) {
			            super.afterExecute(r, t);
			            ExecutorFactory.execute(null, r);
			        }
				};
			} else {
				executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 0L, TimeUnit.SECONDS,
						new LinkedBlockingQueue<Runnable>(boundQueue)) {
			        @Override
			        protected void afterExecute(Runnable r, Throwable t) {
			            super.afterExecute(r, t);
			            ExecutorFactory.execute(null, r);
			        }
				};
			}
			//executor.setTimeout(taskTimeout, TimeUnit.SECONDS);
		} catch(Exception e) {
			log.error(e);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
	
	public static synchronized void execute(BaseJob baseJob, Runnable finishRunnable) {
		try {
			Cache taskCache = (Cache) CacheManager.getInstance().getCache(CacheName.RUNNING_TASK_CACHE_NAME);
			if (baseJob != null) {
				if (taskCache.get(baseJob.getClassName()) == null) {
					executor.execute(baseJob);
					taskCache.put(baseJob.getClassName(), baseJob);
				} else {
					String waitKey = baseJob.getClassName() + endWaitTaskName;
					if (taskCache.get(waitKey) == null) {
						taskCache.put(waitKey, baseJob);
					}
				}
			} else if (finishRunnable != null) {
        		BaseJob finishJob = (BaseJob) finishRunnable;
        		taskCache.remove(finishJob.getClassName());
        		String waitKey = finishJob.getClassName() + endWaitTaskName;
        		BaseJob waitJob = (BaseJob) taskCache.get(waitKey);
        		if (waitJob != null) {
        			executor.execute(waitJob);
        			taskCache.remove(waitKey);
        			taskCache.put(waitJob.getClassName(), waitJob);
        		}
			}
		} catch(Exception e) {
			log.error(e);
		} finally {
			baseJob = null;
		}
	}
	
	public static void shutdown() {
		try {
			log.info("Prepare sutting down ThreadPool");
			
			executor.shutdown();
			
			log.info("Please wait for all taks complete");
			while(!executor.awaitTermination(10, TimeUnit.SECONDS)) {
				log.info("Awaiting completation of tasks!");
			}
			log.info("All tasks complete!");
			
			log.info("Shutdown ThreadPool successfully");
		} catch(Exception e) {
			log.error("Shutdown error ", e);
		}
	}
}
