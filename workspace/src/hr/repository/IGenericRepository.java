package hr.repository;

import java.sql.Connection;
import java.util.List;

import hr.mapper.DaoMapper;

public interface IGenericRepository<T> {
	Connection getConnection();
	List<T> query(String sql, DaoMapper<T> daoMapper, Object...parameters);
	Long insert (String sql, String sqlIdentifier, Object... parameters);
	boolean update (String sql, Object... parameters);
}
