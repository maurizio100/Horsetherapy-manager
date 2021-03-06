package sepm.ss13.e0828.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import sepm.ss13.e0828.gui.horse.HorseManager;
import sepm.ss13.e0828.gui.invoice.InvoiceManager;
import sepm.ss13.e0828.service.TherapyManagementService;

public class MainWindow extends JFrame {
	
	private TherapyManagementService servicelayer = null;
	
	private JTabbedPane tabCenterPanel = null;
	private JPanel panHorsemanagement = null;
	private JPanel panInvoicemanagement = null;
	private JPanel panCenter = null;
	
	public MainWindow(){
		this.init();
	}
	
	public MainWindow(TherapyManagementService service){
		this.servicelayer = service;
		this.init();
	}
	
	private void init(){
		this.setTitle("SEPM - Simpel Effizienter Pferde Manager");
		this.setBounds(300, 150, 1150,700);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.add(createCenterPanel(), BorderLayout.CENTER);
	}

	private JPanel createCenterPanel() {

		if(panCenter == null){
			panCenter = new JPanel( new BorderLayout() );
			tabCenterPanel = new JTabbedPane();
			
			panHorsemanagement = new HorseManager(servicelayer);
			panInvoicemanagement = new InvoiceManager(servicelayer);
			
			
			tabCenterPanel.addTab("Pferdeverwaltung", panHorsemanagement);
			tabCenterPanel.addTab("Rechnungs\u00FCbersicht", panInvoicemanagement);
			
			panCenter.add(tabCenterPanel, BorderLayout.CENTER);
		}
		
		
		return panCenter;
	}
	
	
	
}
