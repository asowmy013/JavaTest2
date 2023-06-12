package it.ing.cucumber.stepdefs.rest.engine.utilities;

import static com.ing.util.SQLFormatter.table;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ing.guice.artifact.ArtifactFactory;
import com.ing.util.Log4test;
import com.ing.util.SQLBuilder;
import com.ing.util.SQLBuilder.SQLAnd;
import com.ing.util.SQLBuilder.SQLJoinWhere;

/**
 * QueryUtility class is used to perform database operation
 */

public class QueryUtility {

	private static Logger log = LoggerFactory.getLogger(Log4test.class);

	private QueryUtility() {
		throw new IllegalStateException("This is an utility class!");
	}

	private static Connection getConnection() {
		return ArtifactFactory.getConnection(ArtifactFactory.getArtifact("workflow-engine-it-api").getDatabases().get(0));
	}

	public static int countRows(String tableName, Map<String, String> filter) {
		Connection connection = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = getCountSQL(tableName, (HashMap<String, String>) filter);
		int numberRow = 0;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			numberRow = Integer.valueOf(rs.getString("recordNumber"));
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				connection.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
		log.info("DB / COUNT on table {} / filter {} / result: {}", tableName, filter, numberRow);
		return numberRow;
	}

	private static String getCountSQL(String tableName, HashMap<String, String> filter) {
		String sql = "";
		Iterator<Map.Entry<String, String>> iterator;
		if (!filter.isEmpty()) {
			SQLJoinWhere sqlWithWhere = SQLBuilder.buildCount()
					.count()
					.from(table(tableName).as(""));
			iterator = filter.entrySet().iterator();
			SQLAnd where = null;
			while (iterator.hasNext()) {
				Map.Entry<String, String> mapEntryFilter = iterator.next();
				if (where == null) {
					where = sqlWithWhere.where(mapEntryFilter.getKey()).equalTo(mapEntryFilter.getValue());
				} else {
					where.and(mapEntryFilter.getKey()).equalTo(mapEntryFilter.getValue());
				}
			}
			if (where != null) {
				sql = where.getCountSQL();
			} else {
				log.error("Where is null on DELETE sql statement");
			}
		} else {
			SQLJoinWhere sqlWithoutWhere = SQLBuilder.buildCount()
					.count()
					.from(table(tableName).as(""));
			sql = sqlWithoutWhere.getCountSQL();
		}
		return sql;
	}

	public static HashMap<Integer, HashMap<String, String>> selectRows(String tableName, Map<String, String> filter) {
		Connection connection = getConnection();
		Statement stmt = null;
		String sql = getSelectSQL(tableName, (HashMap<String, String>) filter);
		ResultSet row = null;
		HashMap<Integer, HashMap<String, String>> hmRow = new HashMap<>();
		if (!sql.contains("Error")) {
			try {
				Integer crow = 0;
				stmt = connection.createStatement();
				row = stmt.executeQuery(sql);
				while (row.next()) {
					hmRow.put(++crow, new HashMap<String, String>());
					ResultSetMetaData rsmd = row.getMetaData();
					for (int ccn = 1; ccn <= rsmd.getColumnCount(); ccn++) {
						hmRow.get(crow).put(rsmd.getColumnName(ccn), row.getString(ccn));
					}
				}
			} catch (SQLException e) {
				log.error(e.getMessage());
			} finally {
				try {
					if (row != null) {
						row.close();
					}
					if (stmt != null) {
						stmt.close();
					}
					connection.close();
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
		}
		log.info("DB / SELECT on table {} / filter {}", tableName, filter);
		return hmRow;
	}
	
	
	private static String getSelectSQL(String tableName, HashMap<String, String> filter) {
		String sql = "";
		Iterator<Map.Entry<String, String>> iterator;
		if (!filter.isEmpty()) {
			SQLJoinWhere sqlWithWhere = SQLBuilder.build()
					.select("*")
					.from(table(tableName).as("t"));
			iterator = filter.entrySet().iterator();
			SQLAnd where = null;
			while (iterator.hasNext()) {
				Map.Entry<String, String> mapEntryFilter = iterator.next();
				if (where == null) {
					where = sqlWithWhere.where("t." + mapEntryFilter.getKey()).equalTo(mapEntryFilter.getValue());
				} else {
					where.and("t." + mapEntryFilter.getKey()).equalTo(mapEntryFilter.getValue());
				}
			}
			if (where != null) {
				sql = where.getSQL();
			} else {
				log.error("Where is null on SELECT sql statement");
			}
		} else {
			sql = "Error: filter is empty";
			log.error("Empty filter on SELECT sql statement");
		}
		return sql;
	}
	
	public static Integer insertInto (String tableName,String columnId,String sequenceId,Map<String, String> filter) {
		Connection connection = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		Integer id = 0;
		String sql = createInsert(tableName,columnId,sequenceId,filter);
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			id=1;
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				connection.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
		return id;
	}

	private static String createInsert (String tableName,String columnId,String sequenceId,Map<String, String> filter) {
		Iterator<Map.Entry<String, String>> iterator = filter.entrySet().iterator();
		List<String> columns = new ArrayList<>();
		List<String> values = new ArrayList<>();
		if(StringUtils.isNotBlank(columnId)) {
			columns.add(columnId);
			values.add(sequenceId);
		}
		while(iterator.hasNext()) {
			Map.Entry<String, String> mapEntryFilter = iterator.next();
			columns.add(mapEntryFilter.getKey());
			values.add(mapEntryFilter.getValue());
		}
		StringBuilder insert = new StringBuilder("INSERT INTO ");
		insert.append(tableName);
		insert.append(" (");
		for(int i=0;i<columns.size();i++) {
			if(i==(columns.size()-1)) {
				insert.append(columns.get(i)+")");
			}else {
				insert.append(columns.get(i)+",");
			}
		}
		insert.append(" VALUES (");
		for(int i=0;i<values.size();i++) {
			if(i==(values.size()-1)) {
				insert.append("'"+values.get(i)+"')");
			}else {
				insert.append("'"+values.get(i)+"',");
			}
		}
		return insert.toString();		
	}
	
	public static Integer getSequenceNextVal(String sequence) {
		Connection connection = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = SQLBuilder.build().select(sequence.concat(".nextval")).from("DUAL").getSQL();
		Integer currentVal = 0;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			currentVal = Integer.valueOf(rs.getString("NEXTVAL"));
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				connection.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}

		return currentVal;
	}
	
	private static String createDelete (String tableName,Map<String, String> filter) {
		Iterator<Map.Entry<String, String>> iterator = filter.entrySet().iterator();
		List<String> columns = new ArrayList<>();
		List<String> values = new ArrayList<>();
		while(iterator.hasNext()) {
			Map.Entry<String, String> mapEntryFilter = iterator.next();
			columns.add(mapEntryFilter.getKey());
			values.add(mapEntryFilter.getValue());
		}
		StringBuilder insert = new StringBuilder("DELETE FROM ");
		insert.append(tableName);
		insert.append(" WHERE ");
		for(int i=0;i<columns.size();i++) {
			if(i==(columns.size()-1)) {
				insert.append(columns.get(i)+"= '"+values.get(i)+"'");
			}else {
				insert.append(columns.get(i)+"= '"+values.get(i)+"' AND ");
			}
		}
		return insert.toString();                               
	}

	
	public static void deleteFrom (String tableName,Map<String, String> filter) {
		Connection connection = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = createDelete(tableName,filter);
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				connection.close();
				log.info(sql);
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	public static Map<Integer, HashMap<String, String>> selectWithRetryNoTemp(String tableName, Map<String, String> filter) {
		Map<Integer, HashMap<String, String>> righe = null;
		for (int ctt = 0; ctt < 10; ctt++) {
			righe = QueryUtility.selectRows(tableName, (HashMap<String, String>) filter);
			if (righe.get(1) == null ) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					log.warn("Interrupt in sleep thread for select with retry");
					Thread.currentThread().interrupt();
				}
			} else {
				break;
			}
		}
		return righe;
	}

}
