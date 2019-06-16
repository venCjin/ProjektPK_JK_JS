package org.organizer;

import java.io.Serializable;
//import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Event implements Comparable<Event>, Serializable {
	/**
	 * Klasa reprezentujaca pojedyncze wydarzenie dodawane do kalendarza.
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String description;
	private String place;

	protected Date startDate;
	protected Date endDate;
	protected Date alarmDate;

	/**
	 * Konstruktor bezparametrowy wymagany do serializacji do i z formatu XML. lub
	 * Konstruktor bezparametrowy wymagany do importu i eksportu do formatu XML.
	 */
	public Event() {
	}

	/**
	 * Konstruktor tworzacy nowy obiekt z podanymi parametrami.
	 * 
	 * @param name        Nazwa wydarzenia
	 * @param description Opis wydarzenia
	 * @param place       Miejsce wydarzenia
	 * @param startDate   Data rozpoczecia wydarzenia
	 * @param endDate     Data zakonczenia wydarzenia
	 * @param alarmDate   Data alarmu wydarzenia
	 */
	public Event(String name, String description, String place, Date startDate, Date endDate, Date alarmDate) {
		super();
		this.name = name;
		this.description = description;
		this.place = place;
		this.startDate = startDate;
		this.endDate = endDate;
		this.alarmDate = alarmDate;
	}

	/**
	 * Zwraca nazwe wydarzenia.
	 * 
	 * @return Nazwa wydarzenia
	 */
	public String getName() {
		return name;
	}

	/**
	 * Ustawia nazwe wydarzenia.
	 * 
	 * @param name Nazwa wydarzenia
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Zwraca opis wydarzenia.
	 * 
	 * @return Opis wydarzenia
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Ustawia opis wydarzenia.
	 * 
	 * @param description Opis wydarzenia
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Zwraca miejsce wydarzenia.
	 * 
	 * @return Miejsce wydarzenia
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Ustawia miejsce wydarzenia.
	 * 
	 * @param place Miejsce wydarzenia
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * Zwraca date rozpoczecia wydarzenia.
	 * 
	 * @return Data rozpoczecia wydarzenia
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Ustawia date rozpoczecia wydarzenia.
	 * 
	 * @param startDate Data rozpoczecia wydarzenia
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Zwraca date zakonczenia wydarzenia.
	 * 
	 * @return endDate Data zakonczenia wydarzenia
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Ustawia date zakonczenia wydarzenia.
	 * 
	 * @param endDate Data zakonczenia wydarzenia
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Zwraca date alarmu wydarzenia.
	 * 
	 * @return Data alarmu wydarzenia
	 */
	public Date getAlarmDate() {
		return alarmDate;
	}

	/**
	 * Ustawia date alarmu wydarzenia.
	 * 
	 * @param alarmDate Data alarmu wydarzenia
	 */
	public void setAlarmDate(Date alarmDate) {
		this.alarmDate = alarmDate;
	}

	/**
	 * Porownuje Wydarzenia na podstawie daty rozpoczecia.
	 */
	@Override
	public int compareTo(Event E) {
		if (this.startDate.equals(E.startDate))
			return 0;
		if (this.startDate.after(E.startDate))
			return 1;
		else
			return -1;
	}

	/**
	 * Komparator waznosci Wydarzenia.
	 */
	public static Comparator<Event> NameComparator = new Comparator<Event>() {
		/**
		 * Porownuje Wydarzenia na podstawie waznosci.
		 */
		@Override
		public int compare(Event e1, Event e2) {
			return e1.getName().compareTo(e2.getName());
		}
	};

	/**
	 * Podsumowuje wydarzenie.
	 */
	@Override
	public String toString() {
		String alarm;
		if (alarmDate != null)
			alarm = Operations.parseDateToString(alarmDate, "dd-MM-yyyy");
		else
			alarm = "(nie ustawiono)";
		return "Nazwa: " + name + ", opis: " + description + ", miejsce: " + place + ", data rozpoczecia: "
				+ Operations.parseDateToString(startDate, "dd-MM-yyyy HH:mm:ss") + ", data zakonczenia: "
				+ Operations.parseDateToString(endDate, "dd-MM-yyyy HH:mm:ss") + ", data alarmu: " + alarm;
	}
}
