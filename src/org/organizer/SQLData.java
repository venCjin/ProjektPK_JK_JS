package org.organizer;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

/**
 * Pozwala zapisywaæ i odczytywaæ wydarzenia z bazy danych MSSQL Organizer.
 */
public class SQLData {
	// Create a variable for the connection string.
	private static final String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=organizer;integratedSecurity=true";

	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	/**
	 * Otwiera po³¹czenie z baz¹ danych MSSQL Organizer.
	 */
	public void connectSQL() {
		try {
			con = DriverManager.getConnection(connectionUrl);
		}
		// Handle any errors that may have occurred.
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Zamyka po³¹czenie z baz¹ danych MSSQL Organizer.
	 */
	public void disconnectSQL() {
		if (con != null) {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Zapisuje wydarzenie do bazy danych MSSQL Organizer.
	 * 
	 * @param event Wydarzenie do zapisania
	 */
	public void writeEventSQL(Event event) {
		connectSQL();
		String querry = "INSERT INTO events VALUES(?, ?, ?, ?, ?, ? ,?);";
		try {
			PreparedStatement stmt = con.prepareStatement(querry);

			stmt.setString(1, event.getName());
			stmt.setString(2, event.getDescription());
			stmt.setString(3, event.getPlace());

			Timestamp startDateTS = new Timestamp(event.getStartDate().getTime());
			stmt.setString(4, startDateTS.toString());

			Timestamp endDateTS = new Timestamp(event.getEndDate().getTime());
			stmt.setString(5, endDateTS.toString());

			Timestamp alarmDateTS = new Timestamp(event.getAlarmDate().getTime());
			stmt.setString(6, alarmDateTS.toString());

			stmt.setLong(7, event.getImportance());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectSQL();
		}
	}

	/**
	 * Usuwa wydarzenie z bazy danych MSSQL Organizer.
	 * 
	 * @param event Wydarzenie do usuniecia
	 */
	public void deleteEventSQL(Event event) {
		connectSQL();

		String querry = "DELETE FROM events WHERE Name = ? AND Description = ? AND Place = ? AND StartDate = ? AND EndDate = ? AND AlarmDate = ? AND Importance = ?;";
		try {
			PreparedStatement stmt = con.prepareStatement(querry);

			stmt.setString(1, event.getName());
			stmt.setString(2, event.getDescription());
			stmt.setString(3, event.getPlace());

			Timestamp startDateTS = new Timestamp(event.getStartDate().getTime());
			stmt.setString(4, startDateTS.toString());

			Timestamp endDateTS = new Timestamp(event.getEndDate().getTime());
			stmt.setString(5, endDateTS.toString());

			Timestamp alarmDateTS = new Timestamp(event.getAlarmDate().getTime());
			stmt.setString(6, alarmDateTS.toString());

			stmt.setLong(7, event.getImportance());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectSQL();
		}
	}

	/**
	 * Zapisuje wydarzenie do bazy danych MSSQL Organizer.
	 * 
	 * @param event Wydarzenie do zapisania
	 */
	public void writeAllEventsSQL(List<Event> events) {
		connectSQL();
		for (Event event : events) {
			String querry = "INSERT INTO events VALUES(?, ?, ?, ?, ?, ? ,?);";
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
				stmt.setLong(7, event.getImportance());

				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				disconnectSQL();
			}
		}
		disconnectSQL();
	}

	/**
	 * Usuwa wszystkie wydarzenia z bazy danych MSSQL Organizer.
	 */
	public void deleteAllEventsSQL() {
		connectSQL();
		String querry = "DELETE FROM events";
		try {
			PreparedStatement stmt = con.prepareStatement(querry);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnectSQL();
		}
	}

	/**
	 * Zwraca listê wszystkich wydarzeñ odczytanych z bazy danych MSSQL Organizer.
	 * 
	 * @return Lista wydarzeñ odczytanych z bazy danych MSSQL Organizer.
	 */
	public List<Event> readAllEventsSQL() {
		connectSQL();
		List<Event> events = new ArrayList<Event>();
		try {
			String querry = "SELECT * FROM events";
			stmt = con.createStatement();
			rs = stmt.executeQuery(querry);
			while (rs.next()) {
				Event eventSQL = new Event(rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5),
						rs.getDate(6), rs.getDate(7), rs.getInt(8));
				events.add(eventSQL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			disconnectSQL();
		}
		return events;
	}
}
