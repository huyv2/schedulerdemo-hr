package hr.mapper;

import java.sql.ResultSet;

public interface DaoMapper<T> {
	T mapRow(ResultSet rs);
}
