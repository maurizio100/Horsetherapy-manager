package sepm.ss13.e0828.domain;

public enum Horserace {
	ABACO("Abaco-Wildpferd"),
	ABESSINER("Abessiner"),
	ALBANER("Albaner"),
	AMERICANSADDLEBRED("American Saddlebred"),
	ARABPINTO("Araberpinto"),
	BALEARE("Baleare"),
	BAVARIANWARMBLOOD("Bayerisches Warmblut");

	private String text;

	Horserace(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static Horserace fromString(String text) {
		if (text != null) {
			for (Horserace t : Horserace.values()) {
				if (text.equalsIgnoreCase(t.text)) {
					return t;
				}
			}
		}
		return null;
	}
}	
