/**
 * 
 */
package com.rii.wp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.rii.wp.model.Simulation;
import com.rii.wp.model.SimulationStatus;

/**
 * @author I
 * 
 */
public class SimulationDAOImpl implements SimulationDAO {

	private static final Logger logger = LoggerFactory.getLogger(SimulationDAOImpl.class);

	@Autowired
	DataSource dataSource;
	
	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.rii.wp.dao.SimulationDAO#setInputParam(java.lang.String)
	 */
	@Override
	public Simulation setSimulationDetails(final Simulation simulation) {

		int simNum = getCurrentSimulationNum(simulation.getUserId(),
				simulation.getStartedOn());
		simulation.setSimNum(simNum + 1);
		logger.info("New SimNum:{}", simNum + 1);

		String sql = "INSERT INTO simulation(sim_num, sim_type, input_param, start_date, started_on, status, user_id)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING sim_id;";

		List<Long> simIds = null;

		// Connection connection = null;
		// PreparedStatement preparedStatement = null;
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			simIds = jdbcTemplate.query(
					sql,
					new Object[] { simulation.getSimNum(),
							simulation.getSimType(),
							simulation.getInputParam(),
							simulation.getStartDate(),
							simulation.getStartedOn(), simulation.getStatus(),
							simulation.getUserId() }, new RowMapper<Long>() {
						public Long mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getLong(1);
						}
					});

			// connection = dataSource.getConnection();
			// preparedStatement = connection.prepareStatement(sql);
			// preparedStatement.setBlob(1,bais);
			// preparedStatement.execute();
		} catch (Exception e) {
			logger.error("setSimulationDetails : Exception: {}, {}",
					e.getMessage(), e.getCause());
		}
		long simId = 0;
		if (simIds != null && simIds.size() == 1) {
			simId = simIds.get(0);
		}
		logger.info("SimId:{}", simId);
		simulation.setSimId(simId);
		return simulation;
	}

	@Override
	public int getCurrentSimulationNum(final long userId, final Date inputDate) {
		String sql = "SELECT max(sim_num) FROM simulation where user_id = ? and start_date = ? group by user_id, start_date;";

		List<Integer> simNums = null;

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			simNums = jdbcTemplate.query(sql,
					new Object[] { userId, inputDate },
					new RowMapper<Integer>() {
						public Integer mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return rs.getInt(1);
						}
					});
		} catch (Exception e) {
			logger.error("getCurrentSimulationNum : Exception: {}, {}",
					e.getMessage(), e.getCause());
		}
		int simNum = 0;
		if (simNums != null && simNums.size() == 1) {
			simNum = simNums.get(0);
		}
		logger.info("Current SimNum:{}", simNum);
		return simNum;
	}

	@Override
	public List<Simulation> getSimulationDetailsByUser(final long userId, final Date fromDate,
			final Date toDate, final int fetchCount) {
		String sql = "SELECT sim_id, user_id, sim_num, s.sim_type, sim_desc, start_date, completion_date, "
				+ " started_on, completed_on, status, input_param, output, addtional_msg "
				+ "FROM simulation s, simulation_type st where user_id = ? and start_date between ? and ? "
				+ "and status in ('COMPLETED', 'FAILED') and s.sim_type = st.sim_type ORDER BY started_on DESC, sim_num DESC LIMIT ?;";

		List<Simulation> simulations = new LinkedList<Simulation>();
	
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			simulations = jdbcTemplate.query(sql,
					new Object[] { userId, fromDate, toDate, fetchCount },
					new RowMapper<Simulation>() {
						public Simulation mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return simulationRowMapper(rs);
						}
					});
		} catch (Exception e) {
			logger.error("getSimulationDetailsById : Exception: {}, {}",
					e.getMessage(), e.getCause());
		}
		return simulations;
	}

	/**
	 * SELECT sim_id, user_id, sim_num, sim_type, start_date, completion_date, 
       started_on, completed_on, status, input_param, output, addtional_msg
  	   FROM simulation;
	 */
	@Override
	public Simulation getSimulationDetailsById(final long simId) {
		String sql = "SELECT sim_id, user_id, sim_num, s.sim_type, sim_desc, start_date, completion_date, "
					+ " started_on, completed_on, status, input_param, output, addtional_msg "
					+ "FROM simulation s, simulation_type st where sim_id = ? and s.sim_type = st.sim_type;";

		List<Simulation> simulations = null;

		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			simulations = jdbcTemplate.query(sql,
					new Object[] { simId },
					new RowMapper<Simulation>() {
						public Simulation mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return simulationRowMapper(rs);
						}
					});
		} catch (Exception e) {
			logger.error("getSimulationDetailsById : Exception: {}, {}",
					e.getMessage(), e.getCause());
		}
		Simulation simulation = null;
		if (simulations != null && simulations.size() == 1) {
			simulation = simulations.get(0);
		}
		logger.info("simulation:{}", simulation);
		return simulation;
	}

	@Override
	public void updateSimulationDetails(final Simulation simulation) {
		String sql = "UPDATE simulation SET completion_date=?, completed_on=?, status=?, output=?, addtional_msg=? "
				+ "WHERE sim_id = ?;";
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			jdbcTemplate.update(
					sql,
					new Object[] { simulation.getCompletionDate(), simulation.getCompletedOn(),
							simulation.getStatus(), simulation.getOutput(),
							simulation.getAddtionalMsg(), simulation.getSimId()});

		} catch (Exception e) {
			logger.error("updateSimulationDetails : Exception: {}, {}",
					e.getMessage(), e.getCause());
		}
		
	}

	@Override
	public void setSimulationStatus(final SimulationStatus simulationStatus) {
		String sql = "INSERT INTO simulation_status(sim_id, sim_stage, status_at) VALUES (?,?,?); ";
		try{
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			int rowCount = jdbcTemplate.update(sql,
			    new Object[] {simulationStatus.getSimId(), simulationStatus.getSimStage(), simulationStatus.getStatusAt()});
			if(rowCount <= 0 ){
				logger.error("Error inserting Simulation status- Id:"
						+ simulationStatus.getSimId() + ", Stage:"
						+ simulationStatus.getSimStage() + ", At:"
						+ simulationStatus.getStatusAt());
			}
		}
		catch(Exception e){
			logger.error("setSimulationStatus : Exception: {}, {}",
					e.getMessage(), e.getCause());
		}
	}

	@Override
	public List<SimulationStatus> getSimulationStatusBySimId(final long simId) {
		String sql = "SELECT sim_id, sim_stage, status_at FROM simulation_status where sim_id = ?;";

		List<SimulationStatus> simulationStatuses = new LinkedList<SimulationStatus>();
	
		try {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			simulationStatuses = jdbcTemplate.query(sql,
					new Object[] { simId },
					new RowMapper<SimulationStatus>() {
						public SimulationStatus mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							return simulationStatusRowMapper(rs);
						}
					});
		} catch (Exception e) {
			logger.error("getSimulationStatusBySimId : Exception: {}, {}",
					e.getMessage(), e.getCause());
		}
		return simulationStatuses;
	}

	private Simulation simulationRowMapper(ResultSet rs)
			throws SQLException {
		Simulation simulation = new Simulation();
		simulation.setAddtionalMsg(rs.getString("addtional_msg"));
		simulation.setCompletedOn(rs.getDate("completed_on"));
		simulation.setCompletionDate(rs.getDate("completion_date"));
		simulation.setInputParam(rs.getString("input_param"));
		simulation.setOutput(rs.getString("output"));
		simulation.setSimId(rs.getLong("sim_id"));
		simulation.setSimNum(rs.getInt("sim_num"));
		simulation.setSimType(rs.getString("sim_type"));
		simulation.setSimDesc(rs.getString("sim_desc"));
		simulation.setStartDate(rs.getDate("start_date"));
		simulation.setStartedOn(rs.getDate("started_on"));
		simulation.setStatus(rs.getString("status"));
		simulation.setUserId(rs.getLong("user_id"));
		
		return simulation;
	}
	
	private SimulationStatus simulationStatusRowMapper(
			ResultSet rs) throws SQLException {
		SimulationStatus simulationStatus = new SimulationStatus();
		simulationStatus.setSimId(rs.getLong("sim_id"));
		simulationStatus.setSimStage(rs.getString("sim_stage"));
		simulationStatus.setStatusAt(rs.getDate("status_at"));
		
		return simulationStatus;
	}

}
