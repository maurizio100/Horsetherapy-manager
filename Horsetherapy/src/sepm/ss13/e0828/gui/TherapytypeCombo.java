package sepm.ss13.e0828.gui;

import java.awt.Color;

import javax.swing.JComboBox;

import sepm.ss13.e0828.domain.Therapytype;

public class TherapytypeCombo extends JComboBox {

	private boolean nullValue;
	
	public TherapytypeCombo(boolean nullValue){
		this.nullValue = nullValue;
		Object[] types = getTherapytypes();
		for( int i = 0; i < types.length; i++ ){
			this.addItem(types[i]);
		}
		this.setBackground(Color.white);
	}
	
	
	private Object[] getTherapytypes(){
		Therapytype[] types = Therapytype.values();
		String strTypes[] = null;

		int i = 0;
		int subtractor = 0;
		
		if( nullValue ) {
			strTypes = new String[(types.length + 1)];
			strTypes[i] = null; i = 1; subtractor = 1;
		}else{
			strTypes = new String[(types.length)];		
		}
		
		for( ; i < strTypes.length; i++ ){
			strTypes[i] = types[(i-subtractor)].getText();
		}

		return strTypes;
	}
}
