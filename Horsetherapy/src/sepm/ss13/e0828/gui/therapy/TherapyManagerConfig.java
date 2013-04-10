package sepm.ss13.e0828.gui.therapy;

public class TherapyManagerConfig {

	private TherapyManagerConfig() {}

	public static final String DIATHERAPYUNITLISTING = "Therapieeinheitenauflistung";
	public static final String BTNPROCESS = "Rechnung Erstellen";
	public static final String BTNDELETE = "Therapieeinheit l\u00F6schen";

	public static final String LBLCLIENTFIRSTNAME = "Vorname";
	public static final String LBLCLIENTSURNAME = "Nachname";
	public static final String LBLADDRESS = "Adresse";
	public static final String LBLPOSTCODE = "Postleitzahl";
	public static final String LBLPLACE = "Ort";


	public static final String[] THERAPYUNITLISTHEADERS ={
		"Pferd", "Therapie", "Dauer", "Preis/h", "Preis gesamt"
	};

	public static final int THERAPYLISTCOLS = THERAPYUNITLISTHEADERS.length;
	
}
