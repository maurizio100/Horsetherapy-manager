package sepm.ss13.e0828.domain;


public class TherapyUnit {

	private int therapyID =0;
	private int therapyHorseID = -1;
	private String therapyHorseName = null;
	private float therapyHours =0.0f;
	private float therapyPrice =0.0f;
	private Therapytype therapyType = null;
	
	/* therapyID */
	public int getTherapyID() {
		return therapyID;
	}
	public void setTherapyID(int therapyID) {
		this.therapyID = therapyID;
	}
	
	/* therapyHorseID */
	public int getTherapyHorseID() {
		return therapyHorseID;
	}
	public void setTherapyHorseID(int therapyHorseID) {
		this.therapyHorseID = therapyHorseID;
	}
	
	/* therapyHorseName */
	public String getTherapyHorseName() {
		return therapyHorseName;
	}
	public void setTherapyHorseName(String therapyHorseName) {
		this.therapyHorseName = therapyHorseName;
	}
	
	/* TherapyHours*/
	public float getTherapyHours() {
		return therapyHours;
	}
	public void setTherapyHours(float therapyHours) {
		this.therapyHours = therapyHours;
	}
	/* TherapyPrice */
	public float getTherapyPrice() {
		return therapyPrice;
	}
	public void setTherapyPrice(float therapyPrice) {
		this.therapyPrice = therapyPrice;
	}
	
	/* Therapytype */
	public Therapytype getTherapyType() {
		return therapyType;
	}
	public void setTherapyType(Therapytype therapyType) {
		this.therapyType = therapyType;
	}
	
	
}
