package hr.repository;

import java.util.List;

import hr.dao.EAbcTransactionDao;

public interface IEAbcTransactionRepository extends IGenericRepository<EAbcTransactionDao> {
	List<EAbcTransactionDao> findByDate(String date);
}
