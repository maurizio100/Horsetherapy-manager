package sepm.ss13.e0828.gui.invoice;

import java.text.NumberFormat;
import java.util.Locale;



public class InvoiceManagerConfig {

	private InvoiceManagerConfig(){}

	/* --- general configuration --- */
	public static final String DIAINVOICEOVERVIEW = "Rechnungs\u00FCbersicht";
	public static final String DATE_FORMAT = "dd.MM.yyyy";
	public static final String NUMBERPATTERN = "###,###.##";
	public static final NumberFormat NUMBERFORMAT = NumberFormat.getNumberInstance(Locale.GERMAN);;

	public static final String INVOICESEARCH = "Rechnungssuche";
		
	public static final String LBLCLIENTFIRSTNAME = "Vorname";
	public static final String LBLCLIENTSURNAME = "Nachname";
	public static final String LBLADDRESS = "Adresse";
	public static final String LBLPOSTCODE = "Postleitzahl";
	public static final String LBLPLACE = "Ort";
	public static final String LBLINVOICEID = "Rechnungsnummer";
	public static final String LBLINVOICEDATE = "Rechnungsdatum";
	public static final String LBLCLIENT = "Kunde";
	public static final String LBLPLACEPOSTCODE = "Wohnort";
	
	public static final String BTNSEARCH = "Nach Rechnung Suchen";
	public static final String BTNSHOWINVOICEDETAILS = "Details anzeigen";

	public static final String INVOICELISTHEADERS[] ={
		"Rechnungsnummer", "Datum", "Kunde", "Rechnungsbetrag"
	};

	public static final int INVOICELISTCOLS = INVOICELISTHEADERS.length;

	public static final String[] THERAPYUNITLISTHEADERS ={
		"Pferd", "Therapie", "Dauer", "Preis/h", "Preis gesamt"
	};
	
	public static final int THERAPYLISTCOLS = THERAPYUNITLISTHEADERS.length;
}
