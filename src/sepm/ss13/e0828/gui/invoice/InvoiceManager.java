package sepm.ss13.e0828.gui.invoice;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;
import sepm.ss13.e0828.domain.Invoice;
import sepm.ss13.e0828.gui.ButtonLabelConfig;
import sepm.ss13.e0828.gui.MessagesConfig;
import sepm.ss13.e0828.gui.NotificationDialog;
import sepm.ss13.e0828.service.TherapyManagementService;
import sepm.ss13.e0828.service.exception.DataSourceAccessException;
import sepm.ss13.e0828.service.exception.NoInvoiceIDException;

public class InvoiceManager extends JPanel {

	private TherapyManagementService servicelayer = null;
	private List<Invoice> invoices = null;

	/*================UI components======================= */
	/* panels */
	private JPanel panSearch = null;
	private JPanel panSearchControl = null;
	private JPanel panInvoiceOperation = null;
	private JPanel panCenter = null;
	private JScrollPane panInvoiceList = null;

	/* textfields */
	private JTextField txtSearchInvoiceId = null;

	/* labels */
	private JLabel lblSearchInvoiceId = null;

	/* buttons */
	private JButton btnSearch = null;
	private JButton btnReset = null;
	private JButton btnShowInvoice = null;

	/* Table */
	private JTable tabInvoicelist = null;
	private InvoiceTableModel tabModel = null;

	public InvoiceManager(TherapyManagementService service){
		this.servicelayer = service;
		this.init();
		this.listAllInvoices();
	}

	private void init() {
		this.setLayout(new MigLayout("fill",""));
		this.add(createCenterPanel(),"growx");
	}

	private JPanel createCenterPanel(){
		if( panCenter == null ){
			panCenter = new JPanel(new MigLayout("fill, wrap"));

			panCenter.add(createSearchPane(),"growx");
			panCenter.add(createInvoiceListPane(),"growx,height 500");
			panCenter.add(createInvoiceOperationPane(),"right");
		}
		return panCenter;

	}

	private JPanel createSearchPane(){
		if( panSearch == null ){
			panSearch = new JPanel(new MigLayout("wrap 2"));
			panSearch.setBorder(BorderFactory.createTitledBorder(InvoiceManagerConfig.INVOICESEARCH));

			this.lblSearchInvoiceId = new JLabel(InvoiceManagerConfig.LBLINVOICEID);

			this.txtSearchInvoiceId = new JTextField();
			panSearch.add(lblSearchInvoiceId);
			panSearch.add(txtSearchInvoiceId,"growx");
			panSearch.add(createSearchControlPane(), "span");

		}	
		return panSearch;
	}

	private JPanel createSearchControlPane(){
		if( panSearchControl == null ){
			panSearchControl = new JPanel( new MigLayout() );

			btnReset = new JButton(ButtonLabelConfig.BTNRESET);
			btnSearch = new JButton(InvoiceManagerConfig.BTNSEARCH);

			panSearchControl.add(btnReset,"sg 1");
			panSearchControl.add(btnSearch, "sg 1");

			btnReset.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					resetSearch();
				}
			});

			btnSearch.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					performSearch();
				}
			});
		}
		return panSearchControl;

	}

	private void performSearch() {
		try {
			int invoiceid = Integer.parseInt(txtSearchInvoiceId.getText());

			Invoice searchInvoice = new Invoice();
			searchInvoice.setInvoiceID(invoiceid);

			Invoice found = servicelayer.getInvoiceinformation(searchInvoice);
			List<Invoice> invoices = new LinkedList<Invoice>();
			invoices.add(found);

			tabModel.fillWithNewData(invoices);

		} catch(NumberFormatException nfe){
			announceError(MessagesConfig.ERRNOCORRECTFORMAT);
		} catch (NoInvoiceIDException e) {
			announceError(MessagesConfig.ERRNOINVOICEID);
		} catch (DataSourceAccessException e) {
			announceError(MessagesConfig.ERRACCESSPROBLEM);
		}
	}

	private void resetSearch() {
		listAllInvoices();
	}

	private JScrollPane createInvoiceListPane(){
		if( panInvoiceList == null ){

			tabInvoicelist =new JTable();
			configureTable(tabInvoicelist);
			
			panInvoiceList = new JScrollPane(tabInvoicelist);
			panInvoiceList.setBorder(BorderFactory.createLoweredBevelBorder());
		}

		return panInvoiceList;
	}

	private void configureTable(JTable tabInvoicelist) {
		tabModel = new InvoiceTableModel();
		
		tabInvoicelist.getTableHeader().setReorderingAllowed(false);
		tabInvoicelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabInvoicelist.setModel(tabModel);
		tabInvoicelist.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tabInvoicelist.setFillsViewportHeight(true);
		TableColumnModel colmod = tabInvoicelist.getColumnModel();
		
		for( int i =0; i < tabModel.getColumnCount(); i++ ){
			colmod.getColumn(i).setResizable(false);
		}
		
		colmod.getColumn(0).setPreferredWidth(20);
		colmod.getColumn(1).setPreferredWidth(50);
		colmod.getColumn(2).setPreferredWidth(500);
		colmod.getColumn(3).setPreferredWidth(100);
		

		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment( JLabel.CENTER );
		colmod.getColumn(3).setCellRenderer( renderer );
		
	}

	private JPanel createInvoiceOperationPane() {
		if( panInvoiceOperation == null){
			panInvoiceOperation = new JPanel(new MigLayout());
			btnShowInvoice = new JButton(InvoiceManagerConfig.BTNSHOWINVOICEDETAILS);

			btnShowInvoice.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) { openInvoiceDetailDialog( ); }
			});

			panInvoiceOperation.add(btnShowInvoice, "sg 1");

		}
		return panInvoiceOperation;
	}

	private void openInvoiceDetailDialog() {
		int selrow =tabInvoicelist.getSelectedRow();
		try {
			if(selrow >= 0 ){
				Invoice inv = servicelayer.getInvoiceinformation( tabModel.getInvoice(selrow) );
				new InvoiceDetailsDialog(inv).setVisible(true);
			}else{
				announceInfo(MessagesConfig.INFSELECTINVOICE);
			}
		} catch (NoInvoiceIDException e) {
			announceError(MessagesConfig.ERRNOINVOCIEID);
		} catch (DataSourceAccessException e) {
			announceError(MessagesConfig.ERRACCESSPROBLEM);
		}
	}


	private void listAllInvoices() {
		try {
			invoices = this.servicelayer.listAllInvoices();
			tabModel.fillWithNewData(invoices);
		} catch (DataSourceAccessException e) {
			announceError(MessagesConfig.ERRACCESSPROBLEM);
		}
	}

	private void announceError(String message){
		new NotificationDialog(message,NotificationDialog.NotificationMode.ERROR).setVisible(true);			
	}

	private void announceInfo(String message){
		new NotificationDialog(message,NotificationDialog.NotificationMode.INFO).setVisible(true);			
	}
}
