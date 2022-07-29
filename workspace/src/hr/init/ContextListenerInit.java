package hr.init;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import hr.cache.Cache;
import hr.cache.CacheManager;
import hr.config.Config;
import hr.config.handler.XmlParserSAX;
import hr.constant.CacheName;
import hr.constant.ParamConstant;
import hr.customization.cache.ConfigCache;
import hr.customization.cache.ScheduleCache;
import hr.customization.cache.TemplateCache;
import hr.dto.request.RequestBaseDto;
import hr.execution.Scheduler;
import hr.factory.ExecutorFactory;
import hr.job.BaseJob;
import hr.util.FileUtil;

public class ContextListenerInit implements ServletContextListener {
	private final Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		initLog(arg0);
		log.info("__________________ ScheduleJob is starting! ________________________");
		
		String configPathFile = arg0.getServletContext().getRealPath("/WEB-INF/web.xml");
		log.info(String.format("configPathFile: %s", configPathFile));
		
		Config.loadConfig();
		
		CacheManager.getInstance().createCache(CacheName.INSTANCE_JOB_CACHE_NAME);
		loadInstancesJobCache();
		CacheManager.getInstance().createCache(CacheName.LITERAL_CLASS_CACHE_NAME);
		loadLiteralDtoCache(configPathFile);
		
		CacheManager.getInstance().createCache(CacheName.RUNNING_TASK_CACHE_NAME);
		
		CacheManager.getInstance().putCache(CacheName.SCHEDULE_CHECKING_CACHE_NAME, new ScheduleCache());
		CacheManager.getInstance().putCache(CacheName.PARAMETER_CONFIG_CACHE_NAME, new ConfigCache());
		CacheManager.getInstance().putCache(CacheName.TEMPLATES_NEEDED_CACHE_NAME, new TemplateCache());
		CacheManager.getInstance().init();
		
		ExecutorFactory.init();
		Scheduler.getInstance().init();
		
		log.info("_____________________ ScheduleJob started ok! _____________________");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		log.info("_______________________ ScheduleJob is stopping! _____________________________");
		
		Scheduler.getInstance().stop();
		ExecutorFactory.shutdown();
		CacheManager.getInstance().destroy();
		
		log.info("_______________________ ScheduleJob stopped! ______________________________");
	}
	
	private void initLog(ServletContextEvent event) {
		String initFile=this.getClass().getResource("log4j.properties").getPath();
		try {
			initFile=java.net.URLDecoder.decode(initFile,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.err.println("initLog4jServlet loading file :"+initFile);
		PropertyConfigurator.configure(initFile);
		try {
			URL url = event.getServletContext().getResource("/");
			String p=url.getProtocol()+"://"+url.getHost()+url.getPort()+url.getPath();
			System.err.println(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		System.err.println("Setting schedule get parameter ...");
		
		System.err.println("initLog4jServlet ...Done");
		
		initConsole();
	}
	private void initConsole() {
		String initFile=this.getClass().getResource("log4j.properties").getPath();
		try {
			initFile=java.net.URLDecoder.decode(initFile,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.err.println("initLog4jServlet loading file :"+initFile);
		PropertyConfigurator.configure(initFile);
	}
	
	@SuppressWarnings("rawtypes")
	private void loadInstancesJobCache() {
		String packageName = ParamConstant.JOBIMPL_PACKAGE;
		String jobEndName = "Job.class";
		try {
			List<Class> literalList = FileUtil.getClasses(packageName, jobEndName, BaseJob.class);
			if (literalList.size() > 0) {
				Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.INSTANCE_JOB_CACHE_NAME);
				for(Class literal : literalList) {
					cache.put(literal.getSimpleName(), literal.newInstance());
				}
			}
		} catch (ClassNotFoundException | IOException e) {
			log.error(e);
		} catch(Exception e) {
			log.error(e);
		}
	}
	@SuppressWarnings("rawtypes")
	private void loadLiteralDtoCache(String configPathFile) {
		try {
			String packageName = "hr.dto.request.define";
			String dtoEndName = "Dto.class";
			Map<String, String> configMap = XmlParserSAX.getUriMappingObject(configPathFile);
			List<Class> literalList = FileUtil.getClasses(packageName, dtoEndName, RequestBaseDto.class);
			if (literalList.size() > 0) {
				Cache cache = (Cache) CacheManager.getInstance().getCache(CacheName.LITERAL_CLASS_CACHE_NAME);
				for(Class literal : literalList) {
					cache.put(configMap.get(literal.getSimpleName()), literal);
				}
			}
		} catch(IOException | ClassNotFoundException e) {
			log.error(e);
		}
	}
}
