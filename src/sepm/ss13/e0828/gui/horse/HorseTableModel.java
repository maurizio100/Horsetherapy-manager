package sepm.ss13.e0828.gui.horse;

import java.text.DecimalFormat;
import java.util.List;
import java.util.HashMap;

import javax.swing.table.DefaultTableModel;

import sepm.ss13.e0828.domain.Horse;
import sepm.ss13.e0828.gui.invoice.InvoiceManagerConfig;

public class HorseTableModel extends DefaultTableModel{

	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Horse> horses = null;
	private int rowcount = 0;
	private DecimalFormat decFormat = null;
	
	public HorseTableModel(){
		super(HorseManagerConfig.HORSELISTHEADERS,0);
		horses = new HashMap<Integer, Horse>();

		decFormat = (DecimalFormat) InvoiceManagerConfig.NUMBERFORMAT;
		decFormat.applyPattern(InvoiceManagerConfig.NUMBERPATTERN);		
	}

	public void fillWithNewData(List<Horse> horses) {
		this.setRowCount(0);
		if( horses != null && horses.size() > 0){
			rowcount = 0; 	
			for( Horse h : horses ){
				addHorse(h);
			}
		}
	}

	public void addHorse(Horse h){
		Object[] values = new Object[HorseManagerConfig.HORSELISTCOLS];

		values[0] = h.getHorseName();
		values[1] = h.getHorseTherapytype().getText();
		values[2] = h.getHorseRace().getText();
		values[3] = decFormat.format(h.getHorseTherapyprice());
		values[4] = decFormat.format(h.getUsage());

		this.addRow(values);
		this.horses.put(rowcount, h);		
		rowcount++;

	}

	@Override
	public boolean isCellEditable(int row, int column){
		return false;
	}

	public Horse getHorse( int tablerow ){
		if( horses.containsKey(tablerow) ){
			return horses.get(tablerow);
		}

		return null;
	}


}
