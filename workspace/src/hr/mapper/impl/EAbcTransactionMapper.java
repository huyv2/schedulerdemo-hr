package hr.mapper.impl;

import java.sql.ResultSet;

import org.apache.log4j.Logger;

import hr.dao.EAbcTransactionDao;
import hr.mapper.DaoMapper;

public class EAbcTransactionMapper implements DaoMapper<EAbcTransactionDao> {
	public final Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public EAbcTransactionDao mapRow(ResultSet rs) {
		EAbcTransactionDao eAbcTransactionDao = new EAbcTransactionDao();
		try {
			// Map here
		} catch(Exception e) {
			log.error(e);
			
		}
		return eAbcTransactionDao;
	}
}
