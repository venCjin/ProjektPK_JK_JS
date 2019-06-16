package org.organizer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Obs³uguje zapis i odczyt wydarzeñ do i z pliku export.csv
 */
public class CSVData {

	private static final String CSV_SEPARATOR = ",";

	/**
	 * Zapisuje wszystkie wydarzenia do pliku export.csv
	 * 
	 * @param events Lista wszystkich wydarzeñ do zapisania
	 * @throws Exception 
	 */
	static public void writeCSV(List<Event> events) throws Exception {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("export.csv"), "UTF-8"));
			for (Event event : events) {
				StringBuffer oneLine = new StringBuffer();
				oneLine.append(event.getName());
				oneLine.append(CSV_SEPARATOR);
				oneLine.append(event.getDescription());
				oneLine.append(CSV_SEPARATOR);
				oneLine.append(event.getPlace());
				oneLine.append(CSV_SEPARATOR);
				oneLine.append(Operations.parseDateToString(event.getStartDate(), "dd-MM-yyyy 00:00:00"));
				oneLine.append(CSV_SEPARATOR);
				oneLine.append(Operations.parseDateToString(event.getEndDate(), "dd-MM-yyyy 00:00:00"));
				oneLine.append(CSV_SEPARATOR);
				oneLine.append(Operations.parseDateToString(event.getAlarmDate(), "dd-MM-yyyy 00:00:00"));
				bw.write(oneLine.toString());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (UnsupportedEncodingException e) {
			throw new Exception("Nie uda³o siê zapisaæ do pliku export.xml\nOpis: " + e.getMessage());
		} catch (FileNotFoundException e) {
			throw new Exception("Nie uda³o siê zapisaæ do pliku export.xml\nOpis: " + e.getMessage());
		} catch (IOException e) {
			throw new Exception("Nie uda³o siê zapisaæ do pliku export.xml\nOpis: " + e.getMessage());
		}
	}

	/**
	 * Wczytuje i zwraca listê wszystkich wydarzeñ zapisanych w pliku export.csv
	 * 
	 * @return Lista wszystkich wydarzeñ zapisanych w pliku export.csv
	 * @throws Exception 
	 */
	static public List<Event> readCSV() throws Exception {
		BufferedReader br = null;
		String line = "";
		List<Event> set = new ArrayList<>();
		try {
			br = new BufferedReader(new FileReader("export.csv"));
			while ((line = br.readLine()) != null) {
				String[] attributes = line.split(CSV_SEPARATOR);
				Event event = new Event(attributes[0],
						attributes[1],
						attributes[2],
						Operations.parseStringToDate(attributes[3], "dd-MM-yyyy 00:00:00"),
						Operations.parseStringToDate(attributes[4], "dd-MM-yyyy 00:00:00"),
						Operations.parseStringToDate(attributes[5], "dd-MM-yyyy 00:00:00"));
				set.add(event);
			}
		} catch (FileNotFoundException e) {
			throw new Exception("Nie uda³o siê zapisaæ do pliku export.xml\nOpis: " + e.getMessage());
		} catch (IOException e) {
			throw new Exception("Nie uda³o siê zapisaæ do pliku export.xml\nOpis: " + e.getMessage());
		} catch (Exception e) {
			throw new Exception("Nie uda³o siê zapisaæ do pliku export.xml\nOpis: " + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new Exception("Nie uda³o siê zamkn¹æ strumienia odczytu z pliku export.xml\nOpis: " + e.getMessage());
				}
			}
		}
		return set;
	}
}