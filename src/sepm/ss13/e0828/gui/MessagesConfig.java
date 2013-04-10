package sepm.ss13.e0828.gui;

public class MessagesConfig {

	private MessagesConfig(){}
	
	/* --- error messages --- */
	public static final String ERRACCESSPROBLEM = "Kein Zugriff auf die Datenquelle. \nM\u00F6glicherweise " +
			"ist die Verbindung getrennt\noder die Quelle ist nicht vorhanden.";

	public static final String ERRUNKNOWNTHERAPYTYPE = "Der Therapietyp existiert nicht";
	public static final String ERRNOCORRECTFORMATINFIELD = "Das Format des folgenden Feldes ist nicht korrekt: ";
	public static final String ERRNOCORRECTFORMAT = "Das Format eines oder mehrerer Daten ist nicht korrekt!";
	public static final String ERRLESSVALUES = "Es wurden nicht alle notwendigen Felder ausgef\u00FCllt";
	public static final String ERRNOUPDATEVALUES = "Es wurde kein einziger Wert zum Aktualisieren angegeben!\nBitte geben Sie mindestens einen Wert an.";
	public static final String ERRHORSENOTEXISTENT = "Das Pferd, welches Sie versuchen zu l\u00F6schen existiert nicht.";
	public static final String ERRNOHORSEID = "Das Pferd konnte nicht gel\u00F6scht werden.";
	public static final String ERRUNACCEPTEDVALUES = "Einer oder mehrere Werte k\u00F6nnen nicht akzeptiert werden!";
	public static final String ERRIMAGENOTFOUND = "Es konnte kein Bild gefunden werden!";
	public static final String ERRNODURATION = "Sie m\u00FCssen angeben wie lange die Therapieeinheit dauern soll!";
	public static final String ERRNOTENOUGHVALUES = "Sie m\u00FCssen alle Eingabefelder ausf\u00FCllen!";
	public static final String ERRNOCLASS = "Es wurde kein Objekt \u00FCbergeben" ;
	public static final String ERRNOINVOCIEID = "Es wurde keine Rechnungsnummer angegeben!";
	public static final String ERRNOINVOICEID = "Sie haben entweder keine oder \neine negative Rechnungsnummer angegeben!";
	public static final String ERRUNKNOWNHORSERACE = "Die angegebene Rasse ist nicht bekannt!";
	public static final String ERRNOTSAMEDATE = "Das angegebene Datum stimmt nicht mit heute \u00FCberein!";
	public static final String ERRTHERAPYUNITS = "Eine oder mehrere Therapieeinheiten enthalten falsche Werte!\nBitte l\u00F6schen Sie diese!";

	/* --- success messages --- */
	public static final String MSGPROCESSSUCCESSFUL = "Aktion erfolgreich durchgef\u00FChrt";
	public static final String MSGDELETIONSUCCESSFUL = "Das von Ihnen gew\u00E4hlte Pferde wurde erfolgreich gel\u00F6scht!";
	/* --- information messages --- */
	public static final String INFSELECTUNIT = "Sie m\u00FCssen erst eine Therapieeinheit ausw\u00E4hlen!";
	public static final String INFSELECTHORSE = "Sie m\u00FCssen f\u00FCr diese Operation ein Pferd ausw\u00E4hlen!";
	public static final String INFUNITADDED = "Therapieeinheit wurder erfolgreich hinzugef\u00FCgt.";
	public static final String INFNOTHERAPYUNITSSELECTED = "Es sind keine Therapieeinheiten gew\u00E4hlt worden.";
	public static final String INFSELECTINVOICE = "Sie m\u00FCssen zuerst eine Rechnung w\u00E4hlen!";


}
