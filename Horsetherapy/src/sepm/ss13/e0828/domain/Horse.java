package sepm.ss13.e0828.domain;

public class Horse {
	
	private int horseID =0;
	private String horsePhoto ="";
	private String horseName ="";
	private float horseTherapyprice = 0.0f;
	private Therapytype horseTherapytype = null;
	private float usage = 0.0f;
	private Horserace horseRace = null;
	
	public int getHorseID() {
		return horseID;
	}
	public void setHorseID(int horseID) {
		this.horseID = horseID;
	}
	public String getHorsePhoto() {
		return horsePhoto;
	}
	public float getUsage(){
		return usage;
	}
	public Horserace getHorseRace(){
		return horseRace;
	}
	
	public void setHorsePhoto(String horsePhoto) {
		this.horsePhoto = horsePhoto;
	}
	public String getHorseName() {
		return horseName;
	}
	public void setHorseName(String horseName) {
		this.horseName = horseName;
	}
	public float getHorseTherapyprice() {
		return horseTherapyprice;
	}
	public void setHorseTherapyprice(float horseTherapyprice) {
		this.horseTherapyprice = horseTherapyprice;
	}
	public Therapytype getHorseTherapytype() {
		return horseTherapytype;
	}
	public void setHorseTherapytype(Therapytype horseTherapytype) {
		this.horseTherapytype = horseTherapytype;
	}
	public void setHorseUsage(float usage) {
		this.usage = usage;
	}
	public void setHorseRace( Horserace race ){
		this.horseRace = race;
	}

}
