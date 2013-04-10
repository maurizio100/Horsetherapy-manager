package sepm.ss13.e0828.gui.horse;


public class HorseManagerConfig {

	private HorseManagerConfig(){}

	/* --- general configurations --- */
	public static final String HORSEPICTUREPATH = "pics/";
	public static final String PATHTODUMMYPIC = "dummy.jpg";

	public static final String DIANEWHORSETITLE = "Neues Pferd anlegen";
	public static final String DIAUPDATEHORSETITLE = "Pferdeangaben \u00E4ndern";
	public static final String DIADELETEHORSETITLE = "Pferd l\u00F6schen";

	public static final String BORDERHORSEINPUT = "Angaben zum Pferd";
	public static final String BORDERCLIENTINPUT = "Kundeninformationen";


	/* --- labels --- */
	public static final String LBLHORSEID = "Pferdnummer";
	public static final String LBLHORSENAME = "Pferdename";
	public static final String LBLHORSEPRICE = "Preis/h";
	public static final String LBLTHERAPYTYPE = "Therapieart";
	public static final String LBLPHOTO = "Foto des Pferdes";
	public static final String LBLHORSEDURATION = "Therapiedauer in h";
	public static final String LBLHORSEWORKLOAD = "Auslastung in h";
	public static final String LBLHORSERACE = "Rasse";
	
	public static final String BTNSEARCH = "Nach Pferd suchen";
	
	public static final String BTNSEARCHTHERAPY = "Nach Auslastung suchen";
	public static final String BTNNEWHORSE = "Neues Pferd anlegen";
	public static final String BTNDELHORSE = "Ausgew\u00E4hltes Pferd l\u00F6schen";
	public static final String BTNUPDATEHORSE = "Ausgew\u00E4hltes Pferd bearbeiten";
	public static final String BTNADDTOCART = "Einheit hinzuf\u00FCgen";
	public static final String BTNCHECKOUT = "Auflisten";
	public static final String BTNPICTURE = "Bild...";

	
	public static final String[] HORSELISTHEADERS ={
		LBLHORSENAME, LBLTHERAPYTYPE, LBLHORSERACE, LBLHORSEPRICE, LBLHORSEWORKLOAD
	};
	
	public static final int HORSELISTCOLS = HORSELISTHEADERS.length;
	public static final String HORSEDELTESURE = "Sind Sie sicher, dass Sie das angegebene Pferd l\u00F6schen wollen?";

	public static final int PICTUREHEIGHT = 200;
	public static final int PICTUREWIDTH = 200;
	public static final String LBLTHERAPYUNITAMOUNTSTART = "Keine Therapieeinheiten";
	public static final String LBLTHERAPYUNITWORD = "Therapieeinheit/en";
}
