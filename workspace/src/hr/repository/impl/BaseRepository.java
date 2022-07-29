package hr.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import hr.mapper.DaoMapper;
import hr.repository.IGenericRepository;

public class BaseRepository<T> implements IGenericRepository<T> {
	public final Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public Connection getConnection() {
                // You manually implement getConnection function
		return DbUtil.getConnection();
	}

	@Override
	public List<T> query(String sql, DaoMapper<T> daoMapper, Object... parameters) {
		List<T> results = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sql);
			setParameter(statement, parameters);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				results.add(daoMapper.mapRow(resultSet));
			}
		} catch (SQLException e) {
			results = null;
			log.error(e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				results = null;
			}
		}
		return results;
	}
	
	private void setParameter(PreparedStatement statement, Object... parameters) {
		try {
			for (int i = 0; i < parameters.length; i++) {
				Object parameter = parameters[i];
				int index = i + 1;
				if (parameter instanceof Long) {
					statement.setLong(index, (Long) parameter);
				} else if (parameter instanceof String) {
					statement.setString(index, (String) parameter);
				} else if (parameter instanceof Integer) {
					statement.setInt(index, (Integer) parameter);
				} else if (parameter instanceof Timestamp) {
					statement.setTimestamp(index, (Timestamp) parameter);
				}
			}
		} catch (SQLException e) {
			log.error(e);
		}
	}

	@Override
	public Long insert(String sql, String sqlIdentifier, Object... parameters) {
		Long id = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection();
			statement = connection.prepareStatement(sqlIdentifier);
			synchronized(this) {
				resultSet = statement.executeQuery();
				if (resultSet.next()) {
					id = resultSet.getLong(1);
					statement.close();
					resultSet.close();
				}
				if (id != null) {
					connection.setAutoCommit(false);
					statement = connection.prepareStatement(sql);
					setParameter(statement, parameters);
					statement.setLong(parameters.length + 1, id);
					statement.executeUpdate();
					connection.commit();
				}
			}
		} catch (SQLException e) {
			id = null;
			log.error(e);
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					log.error(e1);
				}
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e2) {
				log.error(e2);
			}
		}
		return id;
	}

	@Override
	public boolean update(String sql, Object... parameters) {
		boolean isSuccess = true;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection();
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			setParameter(statement, parameters);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			isSuccess = false;
			log.error(e);
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					log.error(e1);
				}
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e2) {
				log.error(e2);
			}
		}
		return isSuccess;
	}
}
