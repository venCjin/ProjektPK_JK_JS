package org.organizer;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Obs³uguje zapis i odczyt wydarzeñ do i z pliku export.xml
 */
public class XMLData {

	/**
	 * Zapisuje wszystkie wydarzenia do pliku export.xml
	 * 
	 * @param events Lista wszystkich wydarzeñ do zapisania
	 * @throws Exception 
	 */
	public static void writeXML(List<Event> events) throws Exception {
		try {
			FileOutputStream fos = new FileOutputStream("export.xml");
			XMLEncoder fileEncoder = new XMLEncoder(fos);
			fileEncoder.writeObject(events);
			fileEncoder.flush();
			fileEncoder.close();
			fos.close();
		} catch (FileNotFoundException e) {
			throw new Exception("Nie uda³o siê zapisaæ do pliku export.xml\nOpis: " + e.getMessage());
		} catch (IOException e) {
			throw new Exception("Nie uda³o siê zapisaæ do pliku export.xml\nOpis: " + e.getMessage());
		} catch (Exception e) {
			throw new Exception("Nie uda³o siê zapisaæ do pliku export.xml\nOpis: " + e.getMessage());
		}
	}

	/**
	 * Wczytuje i zwraca listê wszystkich wydarzeñ zapisanych w pliku export.xml
	 * 
	 * @return Lista wszystkich wydarzeñ zapisanych w pliku export.xml
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static List<Event> readXML() throws Exception {
		List<Event> events = new ArrayList<Event>();
		try {
			FileInputStream fis = new FileInputStream("export.xml");
			XMLDecoder fileDecoder = new XMLDecoder(fis);
			events = (ArrayList<Event>) fileDecoder.readObject();
			fileDecoder.close();
			fis.close();
		} catch (FileNotFoundException e) {
			throw new Exception("Nie uda³o siê odczytaæ z pliku export.xml\nOpis: " + e.getMessage());
		} catch (IOException e) {
			throw new Exception("Nie uda³o siê odczytaæ z pliku export.xml\nOpis: " + e.getMessage());
		} catch (Exception e) {
			throw new Exception("Nie uda³o siê odczytaæ z pliku export.xml\nOpis: " + e.getMessage());
		}
		return events;
	}
}
