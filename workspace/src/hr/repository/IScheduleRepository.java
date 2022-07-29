package hr.repository;

import java.util.List;

import hr.dao.ScheduleDao;

public interface IScheduleRepository extends IGenericRepository<ScheduleDao> {
	List<ScheduleDao> findAll();
	Long insert(ScheduleDao scheduleDao);
	boolean update(ScheduleDao scheduleDao);
	boolean updateDisablingSchedule(ScheduleDao scheduleDao);
	boolean updateEnablingSchedule(ScheduleDao scheduleDao);
	boolean updateResettingSchedule();
	boolean delete(Long id, Object... args);
}
