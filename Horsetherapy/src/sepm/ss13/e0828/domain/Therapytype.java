package sepm.ss13.e0828.domain;

public enum Therapytype {
	HIPPOTHERAPIE("Hippotherapie"),
	VOLTIGIEREN("Heilpädagogisches Voltigieren"),
	REITEN("Heilpädagogisches Reiten");

	private String text;

	Therapytype(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static Therapytype fromString(String text) {
		if (text != null) {
			for (Therapytype t : Therapytype.values()) {
				if (text.equalsIgnoreCase(t.text)) {
					return t;
				}
			}
		}
		return null;
	}
}	
