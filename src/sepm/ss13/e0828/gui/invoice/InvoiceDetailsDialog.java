package sepm.ss13.e0828.gui.invoice;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;
import sepm.ss13.e0828.domain.Invoice;
import sepm.ss13.e0828.domain.TherapyUnit;
import sepm.ss13.e0828.gui.ButtonLabelConfig;
import sepm.ss13.e0828.gui.horse.HorseManagerConfig;

public class InvoiceDetailsDialog extends JDialog {

	private DateFormat df = new SimpleDateFormat(InvoiceManagerConfig.DATE_FORMAT);

	
	private JPanel panClientinfromationPane = null;
	private JPanel panButtonpane = null;
	private JScrollPane panTherapyunitListing = null;

	private JLabel lblInvoiceID = null;
	private JLabel lblDate = null;
	private JLabel lblClient = null;
	private JLabel lblAddress = null;
	private JLabel lblPlace = null;
	private JLabel lblSum = null;
	
	private JTextField txtInvoiceID = null;
	private JTextField txtDate = null;
	private JTextField txtClient = null;
	private JTextField txtAddress = null;
	private JTextField txtPlace = null;

	private JButton btnClose = null;

	private JTable tabTherapyUnitlisting = null;
	private DefaultTableModel tabModel = null;
	
	private float sum = 0.0f;
	
	private DecimalFormat decFormat = null;
	
	private Invoice inv = null;

	public InvoiceDetailsDialog( Invoice inv ){
		this.inv = inv;
		
		decFormat = (DecimalFormat) InvoiceManagerConfig.NUMBERFORMAT;
		decFormat.applyPattern(InvoiceManagerConfig.NUMBERPATTERN);
		
		this.init();
		this.fillWithNewData(inv.getTherapyunits());
		this.lblSum.setText("Summe: " + decFormat.format(sum)); 
	}

	private void init(){
		this.setBounds(300, 250,750,700);
		this.setResizable(false);
		this.setTitle( InvoiceManagerConfig.DIAINVOICEOVERVIEW );

		this.setLayout(new MigLayout("fill, wrap"));
		this.setModal(true);
		
		this.add(createClientInformationPane(),"growx");
		this.add(createTherapyunitListPane(),"growx");
		this.add(createButtonPane(),"right");
		
	}

	private JScrollPane createTherapyunitListPane() {
		if( panTherapyunitListing == null ){

			tabTherapyUnitlisting =new JTable();
			configureTable(tabTherapyUnitlisting);
		
			
			tabTherapyUnitlisting.setModel(tabModel);
			
			tabTherapyUnitlisting.setPreferredScrollableViewportSize(new Dimension(500, 500));
			tabTherapyUnitlisting.setFillsViewportHeight(true);

			panTherapyunitListing = new JScrollPane(tabTherapyUnitlisting);
			panTherapyunitListing.setBorder(BorderFactory.createLoweredBevelBorder());
		}

		return panTherapyunitListing;
	}
	
	private void configureTable(JTable tabInvoicelist) {
		tabTherapyUnitlisting.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabModel = new DefaultTableModel(InvoiceManagerConfig.THERAPYUNITLISTHEADERS, 0){
			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		tabInvoicelist.setModel(tabModel);

		tabInvoicelist.getTableHeader().setReorderingAllowed(false);
		tabInvoicelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabInvoicelist.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tabInvoicelist.setFillsViewportHeight(true);
		TableColumnModel colmod = tabInvoicelist.getColumnModel();
		
		for( int i =0; i < tabModel.getColumnCount(); i++ ){
			colmod.getColumn(i).setResizable(false);
		}
		
		colmod.getColumn(0).setPreferredWidth(110);
		colmod.getColumn(1).setPreferredWidth(150);
		colmod.getColumn(2).setPreferredWidth(50);
		colmod.getColumn(3).setPreferredWidth(50);
		colmod.getColumn(4).setPreferredWidth(50);
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		colmod.getColumn(2).setCellRenderer( renderer );
		colmod.getColumn(3).setCellRenderer( renderer );
		colmod.getColumn(4).setCellRenderer( renderer );
		
	}


	private JPanel createButtonPane() {
		if( panButtonpane == null ){
			panButtonpane = new JPanel( new MigLayout("wrap") );
			lblSum = new JLabel();
			lblSum.setAlignmentX(RIGHT_ALIGNMENT);
			
		
			btnClose = new JButton( ButtonLabelConfig.BTNCLOSE );

			btnClose.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					performDialogClose();
				}
			});	
				
			panButtonpane.add(lblSum);
			panButtonpane.add(btnClose);
			
		}
		return panButtonpane;
	}

	private JPanel createClientInformationPane(){
		if( panClientinfromationPane == null ){
			panClientinfromationPane = new JPanel(new MigLayout("", "[][grow][]"));
			
			panClientinfromationPane.setBorder(BorderFactory.createTitledBorder(HorseManagerConfig.BORDERCLIENTINPUT));

			this.lblInvoiceID = new JLabel(InvoiceManagerConfig.LBLINVOICEID);
			this.lblDate = new JLabel(InvoiceManagerConfig.LBLINVOICEDATE);
			this.lblClient = new JLabel(InvoiceManagerConfig.LBLCLIENT);
			this.lblAddress = new JLabel(InvoiceManagerConfig.LBLADDRESS);
			this.lblPlace = new JLabel(InvoiceManagerConfig.LBLPLACEPOSTCODE);
			
			
			this.txtInvoiceID = new JTextField();
			this.txtInvoiceID.setText(inv.getInvoiceID() + "");
			this.txtDate = new JTextField( df.format( new Date( inv.getInvoiceDate() )));
			this.txtClient = new JTextField(inv.getInvoiceClientfirstname() + " " + inv.getInvoiceClientsurname() );
			this.txtAddress = new JTextField(inv.getInvoiceAddress());
			this.txtPlace = new JTextField(inv.getInvoicePostcode() + " " + inv.getInvoicePlace());
		
			txtInvoiceID.setEditable(false);
			txtDate.setEditable(false);
			txtClient.setEditable(false);
			txtAddress.setEditable(false);
			txtPlace.setEditable(false);
			
			panClientinfromationPane.add(lblInvoiceID);
			panClientinfromationPane.add(txtInvoiceID,"growx, wrap");
			
			panClientinfromationPane.add(lblDate);
			panClientinfromationPane.add(txtDate,"growx, wrap");
			
			panClientinfromationPane.add(lblClient);
			panClientinfromationPane.add(txtClient,"growx, wrap");
		
			panClientinfromationPane.add(lblAddress);
			panClientinfromationPane.add(txtAddress,"growx, wrap");
			
			panClientinfromationPane.add(lblPlace);
			panClientinfromationPane.add(txtPlace,"growx,wrap"); 
		}	
		return panClientinfromationPane;		
	}


	private void performDialogClose() {
		this.dispose();
	}
	
	
	private void fillWithNewData(List<TherapyUnit> units) {
		if( units != null && units.size() > 0){	
			for( TherapyUnit unit : units ){
				addTherapyUnit(unit);
			}
		}
	}

	private void addTherapyUnit(TherapyUnit unit){
		Object[] values = new Object[InvoiceManagerConfig.THERAPYLISTCOLS];

		values[0] = unit.getTherapyHorseName();
		values[1] = unit.getTherapyType().getText();
		values[2] = decFormat.format( unit.getTherapyHours());
		values[3] = decFormat.format( unit.getTherapyPrice());
		values[4] = decFormat.format((unit.getTherapyPrice() * unit.getTherapyHours()));

		tabModel.addRow(values);
		sum += (unit.getTherapyPrice() * unit.getTherapyHours());
	}
	
}
