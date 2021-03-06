package sepm.ss13.e0828.gui.therapy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;
import sepm.ss13.e0828.dao.exceptions.UnacceptedValueException;
import sepm.ss13.e0828.domain.Invoice;
import sepm.ss13.e0828.domain.TherapyUnit;
import sepm.ss13.e0828.gui.ButtonLabelConfig;
import sepm.ss13.e0828.gui.MessagesConfig;
import sepm.ss13.e0828.gui.horse.HorseManagerConfig;
import sepm.ss13.e0828.gui.invoice.InvoiceManagerConfig;
import sepm.ss13.e0828.service.TherapyManagementService;
import sepm.ss13.e0828.service.exception.DataSourceAccessException;
import sepm.ss13.e0828.service.exception.IncorrectTherapyUnitsException;
import sepm.ss13.e0828.service.exception.LessValuesException;
import sepm.ss13.e0828.service.exception.NotSameDateException;

public class TherapyunitListingDialog extends JDialog {

	private JPanel panClientinfromationPane = null;
	private JPanel panButtonpane = null;
	private JPanel panNotification = null;
	private JScrollPane panTherapyunitListing = null;

	private JTextArea lblMessage = null;
	private JLabel lblClientfirstName = null;
	private JLabel lblClientsurName = null;
	private JLabel lblAddress = null;
	private JLabel lblPostcode = null;
	private JLabel lblPlace = null;
	private JLabel lblSum = null;
	
	private JTextField txtClientfirstName = null;
	private JTextField txtClientsurName = null;
	private JTextField txtAddress = null;
	private JTextField txtPostcode = null;
	private JTextField txtPlace = null;

	private JButton btnOK = null;
	private JButton btnCancel = null;
	private JButton btnDelete = null;

	private JTable tabTherapyUnitlisting = null;
	private DefaultTableModel tabModel = null;
	
	private TherapyManagementService servicelayer = null;
	private float sum = 0.0f;
	private boolean processSuccess = false;
	private boolean listempty = false;
	
	private DecimalFormat decFormat = null;
	
	private List<TherapyUnit> therapyunits = null;

	public TherapyunitListingDialog( List<TherapyUnit> therapyunits, TherapyManagementService servicelayer ){
		this.servicelayer = servicelayer;
		this.therapyunits = therapyunits;
		
		NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
		decFormat = (DecimalFormat)nf;
		decFormat.applyPattern("###,###.##");
		
		this.init();
		this.fillWithNewData(therapyunits);
		this.lblSum.setText("Summe: " + decFormat.format(sum)); 
	}

	private void init(){
		this.setBounds(300, 250,680,700);
		this.setResizable(false);
		this.setTitle( TherapyManagerConfig.DIATHERAPYUNITLISTING );

		this.setLayout(new MigLayout("wrap"));
		this.setModal(true);
		
		this.add(createClientInformationPane(),"growx");
		this.add(createTherapyunitListPane(),"growx");
		this.add(createNotificationPanel(), "growx");
		this.add(createButtonPane(),"right, growx");
		
	}

	private JScrollPane createTherapyunitListPane() {
		if( panTherapyunitListing == null ){

			tabTherapyUnitlisting =new JTable();
			configureTable(tabTherapyUnitlisting);
			tabTherapyUnitlisting.setPreferredScrollableViewportSize(new Dimension(500, 500));
			tabTherapyUnitlisting.setFillsViewportHeight(true);

			panTherapyunitListing = new JScrollPane(tabTherapyUnitlisting);
			panTherapyunitListing.setBorder(BorderFactory.createLoweredBevelBorder());
		}

		return panTherapyunitListing;
	}
	
	private void configureTable(JTable tabTherapyUnitlisting) {
		tabTherapyUnitlisting.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabModel = new DefaultTableModel(InvoiceManagerConfig.THERAPYUNITLISTHEADERS, 0){
			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		tabTherapyUnitlisting.setModel(tabModel);
		
		tabTherapyUnitlisting.getTableHeader().setReorderingAllowed(false);
		tabTherapyUnitlisting.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabTherapyUnitlisting.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tabTherapyUnitlisting.setFillsViewportHeight(true);
		TableColumnModel colmod = tabTherapyUnitlisting.getColumnModel();
		
		for( int i =0; i < tabModel.getColumnCount(); i++ ){
			colmod.getColumn(i).setResizable(false);
		}
		
		colmod.getColumn(0).setPreferredWidth(110);
		colmod.getColumn(1).setPreferredWidth(150);
		colmod.getColumn(2).setPreferredWidth(50);
		colmod.getColumn(3).setPreferredWidth(50);
		colmod.getColumn(4).setPreferredWidth(50);
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment( JLabel.CENTER );
		colmod.getColumn(2).setCellRenderer( renderer );
		colmod.getColumn(3).setCellRenderer( renderer );
		colmod.getColumn(4).setCellRenderer( renderer );
		
	}
	

	private JPanel createNotificationPanel() {
		if(panNotification == null){
			panNotification = new JPanel(new MigLayout());
			this.lblMessage = new JTextArea(5,50);
			lblMessage.setFont(new Font("sans serif", Font.BOLD, 12));
			lblMessage.setEditable(false);
			lblMessage.setOpaque(false);
			panNotification.add(lblMessage, "grow");
		}
		return panNotification;
	}

	private JPanel createButtonPane() {
		if( panButtonpane == null ){
			panButtonpane = new JPanel( new MigLayout("wrap 3","[][right]","[][]") );
			lblSum = new JLabel();
			lblSum.setAlignmentX(RIGHT_ALIGNMENT);
			
			btnDelete = new JButton( TherapyManagerConfig.BTNDELETE);
			btnOK = new JButton( TherapyManagerConfig.BTNPROCESS );
			btnCancel = new JButton( ButtonLabelConfig.BTNCANCEL );

			btnOK.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if(processSuccess || listempty ){
						performDialogClose();
					}else{
						createInvoice();
					}
				}
			});

			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					performDialogClose();
				}
			});	
			
			btnDelete.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					deleteTherapyUnit();	
				}
			});
			
			panButtonpane.add(lblSum, "right, span");
			panButtonpane.add(btnCancel,"sg 1");
			panButtonpane.add(btnDelete, "sg 1");
			panButtonpane.add(btnOK,"sg 1");

		}
		return panButtonpane;
	}

	private JPanel createClientInformationPane(){
		if( panClientinfromationPane == null ){
			panClientinfromationPane = new JPanel(new MigLayout("", "[][grow][]"));
			
			panClientinfromationPane.setBorder(BorderFactory.createTitledBorder(HorseManagerConfig.BORDERCLIENTINPUT));

			this.lblClientfirstName = new JLabel(TherapyManagerConfig.LBLCLIENTFIRSTNAME);
			this.lblClientsurName = new JLabel(TherapyManagerConfig.LBLCLIENTSURNAME);
			this.lblAddress = new JLabel(TherapyManagerConfig.LBLADDRESS);
			this.lblPostcode = new JLabel(TherapyManagerConfig.LBLPOSTCODE);
			this.lblPlace = new JLabel(TherapyManagerConfig.LBLPLACE);

			txtClientfirstName = new JTextField();
			txtClientsurName = new JTextField();
			txtAddress = new JTextField();
			txtPostcode = new JTextField();
			txtPlace = new JTextField();


			panClientinfromationPane.add(lblClientfirstName);
			panClientinfromationPane.add(txtClientfirstName,"growx, wrap");
			panClientinfromationPane.add(lblClientsurName);
			panClientinfromationPane.add(txtClientsurName,"growx, wrap");
			
			panClientinfromationPane.add(lblAddress);
			panClientinfromationPane.add(txtAddress,"growx, wrap");
			panClientinfromationPane.add(lblPostcode);
			panClientinfromationPane.add(txtPostcode,"growx, wrap");
			panClientinfromationPane.add(lblPlace);
			panClientinfromationPane.add(txtPlace,"growx,wrap"); 
		}	
		return panClientinfromationPane;		
	}


	private void announceError( String message ){
		this.lblMessage.setForeground(Color.red);
		this.lblMessage.setText(message);
	}

	private void announceSuccess(){
		this.lblMessage.setForeground(Color.BLUE);
		this.lblMessage.setText(MessagesConfig.MSGPROCESSSUCCESSFUL);
		btnOK.setText(ButtonLabelConfig.BTNCLOSE);
	}

	private void performDialogClose() {
		this.dispose();
	}
	
	
	private void createInvoice(){
		Invoice inv = new Invoice();
		inv.setInvoiceClientfirstname(txtClientfirstName.getText());
		inv.setInvoiceClientsurname(txtClientsurName.getText());
		inv.setInvoiceAddress(txtAddress.getText());
		inv.setInvoicePostcode(txtPostcode.getText());
		inv.setInvoicePlace(txtPlace.getText());
		inv.setInvoiceDate(new Date().getTime());
		inv.setInvoiceSum(sum);
		
		for( TherapyUnit unit : therapyunits ){
			inv.setTherapyUnit(unit);
		}
		
		try {
			servicelayer.createInvoice(inv);
			announceSuccess();
			therapyunits.removeAll(therapyunits);
			processSuccess = true;
		} catch (LessValuesException e) {
			announceError(MessagesConfig.ERRNOTENOUGHVALUES);
		} catch (DataSourceAccessException e) {
			announceError(MessagesConfig.ERRACCESSPROBLEM);
		} catch (NotSameDateException e) {
			announceError(MessagesConfig.ERRNOTSAMEDATE);
		} catch (IncorrectTherapyUnitsException e) {
			announceError(MessagesConfig.ERRTHERAPYUNITS);
		} catch (UnacceptedValueException e) {
			announceError(MessagesConfig.ERRUNACCEPTEDVALUES);
		}
		
	}
	
	private void fillWithNewData(List<TherapyUnit> units) {
		if( units != null && units.size() > 0){	
			for( TherapyUnit unit : units ){
				addTherapyUnit(unit);
			}
		}
	}

	private void addTherapyUnit(TherapyUnit unit){
		Object[] values = new Object[TherapyManagerConfig.THERAPYLISTCOLS];

		values[0] = unit.getTherapyHorseName();
		values[1] = unit.getTherapyType().getText();
		values[2] = unit.getTherapyHours();
		values[3] = unit.getTherapyPrice();
		values[4] = decFormat.format( (unit.getTherapyPrice() * unit.getTherapyHours()) );

		tabModel.addRow(values);
		sum += (unit.getTherapyPrice() * unit.getTherapyHours());
	}
	
	private void deleteTherapyUnit(){
		int row = tabTherapyUnitlisting.getSelectedRow();
		if( row < 0 ){
			announceError(MessagesConfig.INFSELECTUNIT);
		}else{
			TherapyUnit unit = therapyunits.get(row);
			therapyunits.remove(row);
			tabModel.removeRow(row);
			sum -= (unit.getTherapyPrice() * unit.getTherapyHours());
			lblSum.setText("Summe: " + decFormat.format(sum));
			
			if( therapyunits.size() <= 0 ){
				btnOK.setText(ButtonLabelConfig.BTNCLOSE);
				listempty = true;
			}
		}	
	}
}
