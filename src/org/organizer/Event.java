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

	protected int importance;

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
	 * @param importance  Waznosc wydarzenia
	 * @throws EventError Okienko dialogowe. Pokazuje sie jezeli wymagany atrybut
	 *                    jest pusty.
	 */
	public Event(String name, String description, String place, Date dataRozp, Date endDate, Date alarmDate,
			int importance) throws EventError {
		super();
		if (name == "" || name == null || name.isEmpty())
			throw new EventError("Pusta nazwa");
		this.name = name;
		this.description = description;
		this.place = place;

		if (dataRozp == null)
			throw new EventError("Nieprawid³owa data rozpoczecia wydarzenia");
		this.startDate = dataRozp;
		if (endDate == null)
			throw new EventError("Nieprawid³owa data zakonczenia wydarzenia");
		this.endDate = endDate;
		if (endDate.before(dataRozp))
			throw new EventError("Data rozpoczecia wydarzenia musi byc przed jego zakonczeniem");

		this.alarmDate = alarmDate;
		this.importance = importance;
	}

	/**
	 * Getter - Zwraca nazwe wydarzenia.
	 * 
	 * @return Nazwa wydarzenia
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter - Ustawia nazwe wydarzenia.
	 * 
	 * @param name Nazwa wydarzenia
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter - Zwraca opis wydarzenia.
	 * 
	 * @return Opis wydarzenia
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter - Ustawia opis wydarzenia.
	 * 
	 * @param description Opis wydarzenia
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter - Zwraca miejsce wydarzenia.
	 * 
	 * @return Miejsce wydarzenia
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Setter - Ustawia miejsce wydarzenia.
	 * 
	 * @param place Miejsce wydarzenia
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	/**
	 * Getter - Zwraca date rozpoczecia wydarzenia.
	 * 
	 * @return Data rozpoczecia wydarzenia
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Setter - Ustawia date rozpoczecia wydarzenia.
	 * 
	 * @param startDate Data rozpoczecia wydarzenia
	 */
	public void setDataRozp(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getter - Zwraca date zakonczenia wydarzenia.
	 * 
	 * @return endDate Data zakonczenia wydarzenia
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Setter - Ustawia date zakonczenia wydarzenia.
	 * 
	 * @param endDate Data zakonczenia wydarzenia
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Getter - Zwraca date alarmu wydarzenia.
	 * 
	 * @return Data alarmu wydarzenia
	 */
	public Date getAlarmDate() {
		return alarmDate;
	}

	/**
	 * Setter - Ustawia date alarmu wydarzenia.
	 * 
	 * @param alarmDate Data alarmu wydarzenia
	 */
	public void setAlarmDate(Date alarmDate) {
		this.alarmDate = alarmDate;
	}

	/**
	 * Getter - Zwraca waznosc wydarzenia.
	 * 
	 * @return Waznosc wydarzenia
	 */
	public int getImportance() {
		return importance;
	}

	/**
	 * Setter - Ustawia waznosc wydarzenia.
	 * 
	 * @param importance Waznosc wydarzenia
	 */
	public void setImportance(int importance) {
		this.importance = importance;
	}

	/**
	 * Porownuje Wydarzenia na podstawie daty.
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
	public static Comparator<Event> ImportanceComparator = new Comparator<Event>() {
		/**
		 * Porownuje Wydarzenia na podstawie waznosci.
		 */
		@Override
		public int compare(Event E1, Event E2) {
			if (E1.importance == E2.importance)
				return 0;
			if (E1.importance > E2.importance)
				return 1;
			else
				return -1;
		}
	};

	/**
	 * Podsumownaie wydarzenia.
	 */
	@Override
	public String toString() {
		//TODO czy formaty daty nie powinny zostac oddelegowane do Operations??
		String alarm;
		// ver 1
		/*
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		SimpleDateFormat alarmFormat = new SimpleDateFormat("dd-MM-yyyy");
		if (alarmDate != null)
			alarm = alarmFormat.format(alarmDate);
		else
			alarm = "(nie ustawiono)";
		return "Wydarzenie [nazwa=" + name + ", opis=" + description + ", miejsce=" + place + ", data rozpoczecia="
				+ dateFormat.format(startDate) + ", data zakonczenia=" + dateFormat.format(endDate) + ", data alarmu="
				+ alarm + ", waznosc=" + importance + "]";
		*/
		// ver 2
		if (alarmDate != null)
			alarm = Operations.parseDateToString(alarmDate,"dd-MM-yyyy");
		else
			alarm = "(nie ustawiono)";
		return "Wydarzenie [nazwa=" + name + ", opis=" + description + ", miejsce=" + place + 
				", data rozpoczecia=" + Operations.parseDateToString(startDate,"dd-MM-yyyy HH:mm:ss")
				+ ", data zakonczenia=" + Operations.parseDateToString(endDate,"dd-MM-yyyy HH:mm:ss")
				+ ", data alarmu=" + alarm + ", waznosc=" + importance + "]";
	}
}
