package org.organizer;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Pozwala zapisywa� i odczytywa� wydarzenia z bazy danych MSSQL Organizer.
 */
public class SQLData {

	private static final String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=organizer;integratedSecurity=true";

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	/**
	 * Otwiera po��czenie z baz� danych MSSQL Organizer.
	 * 
	 * @throws SQLException
	 */
	public void connectSQL() throws SQLException {
		con = DriverManager.getConnection(connectionUrl);
	}

	/**
	 * Zamyka po��czenie z baz� danych MSSQL Organizer.
	 * 
	 * @throws SQLException
	 */
	public void disconnectSQL() throws SQLException {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			throw new SQLException("Nie uda�o si� zamkn�� po��czenia z baz� danych\nOpis: " + e.getMessage());
		}
	}

	/**
	 * Zapisuje list� podanych wydarze� do bazy danych MSSQL Organizer.
	 * 
	 * @param events Lista wydarze� do zapisania
	 * @throws SQLException
	 */
	public void writeAllEventsSQL(List<Event> events) throws SQLException {
		connectSQL();
		for (Event event : events) {
			String querry = "INSERT INTO events VALUES(?, ?, ?, ?, ?, ?);";
			try {
				PreparedStatement stmt = con.prepareStatement(querry);

				stmt.setString(1, event.getName());
				stmt.setString(2, event.getDescription());
				stmt.setString(3, event.getPlace());

				Timestamp startDateTS = new Timestamp(event.getStartDate().getTime());
				stmt.setString(4, startDateTS.toString());

				Timestamp endDateTS = new Timestamp(event.getEndDate().getTime());
				stmt.setString(5, endDateTS.toString());

				if (event.getAlarmDate() == null) {
					stmt.setNull(6, Types.TIMESTAMP);
				} else {
					Timestamp alarmDateTS = new Timestamp(event.getAlarmDate().getTime());
					stmt.setString(6, alarmDateTS.toString());
				}

				stmt.executeUpdate();
			} catch (SQLException e) {
				disconnectSQL();
				throw new SQLException("Nie uda�o si� zapisa� do bazy danych SQL");
			}
		}
		disconnectSQL();
	}

	/**
	 * Usuwa wszystkie wydarzenia z bazy danych MSSQL Organizer.
	 * 
	 * @throws SQLException
	 */
	public void deleteAllEventsSQL() throws SQLException {
		connectSQL();
		String querry = "DELETE FROM events";
		try {
			PreparedStatement stmt = con.prepareStatement(querry);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Nie uda�o si� wyczy�ci� bazy danych SQL");
		} finally {
			disconnectSQL();
		}
	}

	/**
	 * Zwraca list� wszystkich wydarze� odczytanych z bazy danych MSSQL Organizer.
	 * 
	 * @return Lista wydarze� odczytanych z bazy danych MSSQL Organizer.
	 * @throws SQLException
	 */
	public List<Event> readAllEventsSQL() throws SQLException {
		connectSQL();
		List<Event> events = new ArrayList<Event>();
		try {
			String querry = "SELECT * FROM events";
			stmt = con.createStatement();
			rs = stmt.executeQuery(querry);
			while (rs.next()) {
				Event eventSQL = new Event(rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5),
						rs.getDate(6), rs.getDate(7));
				events.add(eventSQL);
			}
		} catch (SQLException e) {
			throw new SQLException("Nie uda�o si� podbra� wydarze� z bazy danych SQL");
		} finally {
			disconnectSQL();
		}
		return events;
	}
}
