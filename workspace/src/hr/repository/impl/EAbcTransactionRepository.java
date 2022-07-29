package hr.repository.impl;

import java.util.List;

import hr.dao.EAbcTransactionDao;
import hr.mapper.impl.EAbcTransactionMapper;
import hr.repository.IEAbcTransactionRepository;

public class EAbcTransactionRepository extends BaseRepository<EAbcTransactionDao> implements IEAbcTransactionRepository {

	@Override
	public List<EAbcTransactionDao> findByDate(String date) {
		String sql = "";
		return query(sql, new EAbcTransactionMapper(), date);
	}
}
