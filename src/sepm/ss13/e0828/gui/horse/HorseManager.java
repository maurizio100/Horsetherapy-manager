package sepm.ss13.e0828.gui.horse;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;
import sepm.ss13.e0828.dao.exceptions.NoClassGivenException;
import sepm.ss13.e0828.domain.Horse;
import sepm.ss13.e0828.domain.TherapyUnit;
import sepm.ss13.e0828.gui.ButtonLabelConfig;
import sepm.ss13.e0828.gui.HorseraceCombo;
import sepm.ss13.e0828.gui.MessagesConfig;
import sepm.ss13.e0828.gui.NotificationDialog;
import sepm.ss13.e0828.gui.TherapytypeCombo;
import sepm.ss13.e0828.gui.exception.NoCorrectFormatException;
import sepm.ss13.e0828.gui.exception.UnknownRaceException;
import sepm.ss13.e0828.gui.exception.UnknownTherapytypeException;
import sepm.ss13.e0828.gui.invoice.InvoiceManagerConfig;
import sepm.ss13.e0828.gui.therapy.TherapyunitListingDialog;
import sepm.ss13.e0828.service.TherapyManagementService;
import sepm.ss13.e0828.service.exception.DataSourceAccessException;
import sepm.ss13.e0828.service.exception.NoTherapytypeGivenException;

public class HorseManager extends JPanel {

	private TherapyManagementService servicelayer = null;
	private List<Horse> horses = null;

	/*================UI components======================= */
	/* panels */
	private JPanel panSearch = null;
	private JPanel panSearchControl = null;
	private JPanel panHorseOperation = null;
	private JPanel panCartOperation = null;
	private JPanel panHorseInformation = null;
	private JPanel panCenter = null;
	private JPanel panEast = null;
	private JScrollPane panHorseList = null;

	/* textfields */
	private JTextField txtSearchName = null;
	private JTextField txtSearchPrice = null;
	private JTextField txtSearchHorseID = null;

	private JTextField txtHorseName = null;
	private JTextField txtHorsePrice = null;
	private JTextField txtHorseTherapytype = null;
	private JTextField txtTherapyDuration = null;
	private JTextField txtHorserace = null;
	
	/* labels */
	private JLabel lblSearchName = null;
	private JLabel lblSearchPrice = null;
	private JLabel lblSearchHorseID = null;
	private JLabel lblSearchTherapyType = null;
	private JLabel lblSearchHorserace = null;
	private JLabel lblPicture = null;
	private JLabel lblTherapyunitamount = null;

	private JLabel lblHorseName = null;
	private JLabel lblTherapytype = null;
	private JLabel lblPriceperHour = null;
	private JLabel lblTherapyduration = null;
	private JLabel lblHorserace = null;
	
	/* buttons */
	private JButton btnSearch = null;
	private JButton btnSearchTherapy = null;
	private JButton btnReset = null;
	private JButton btnNewHorse = null;
	private JButton btnDeleteHorse = null;
	private JButton btnAddToCart = null;
	private JButton btnCheckout = null;
	private JButton btnUpdateHorse = null;

	/* list fields */
	private JComboBox cmbTherapytypes = null;
	private JComboBox cmbHorseraces = null;

	/* Table */
	private JTable tabHorselist = null;
	private HorseTableModel tabModel = null;

	private Image horsePicture = null;

	private List<TherapyUnit> therapyunitCart = null;
	private DecimalFormat decFormat = null;
	
	public HorseManager(TherapyManagementService service){
		this.servicelayer = service;
		this.init();
		this.listAllHorses();
		decFormat = (DecimalFormat) InvoiceManagerConfig.NUMBERFORMAT;
		decFormat.applyPattern(InvoiceManagerConfig.NUMBERPATTERN);		
	}

	private void init() {
		this.setLayout(new MigLayout());
		therapyunitCart = new LinkedList<TherapyUnit>();
		this.add(createCenterPanel(),"grow");
		this.add(createEastPanel(),"grow");
	}

	private JPanel createCenterPanel(){
		if( panCenter == null ){
			panCenter = new JPanel(new MigLayout("wrap","[center]","[][][]"));

			panCenter.add(createSearchPane(),"growx");
			panCenter.add(createHorseListPane(),"growx,height 500");
			panCenter.add(createHorseOperationPane(),"right");
		}
		return panCenter;

	}

	private JPanel createSearchPane(){
		if( panSearch == null ){
			panSearch = new JPanel(new MigLayout("wrap 2","[][]"));
			panSearch.setBorder(BorderFactory.createTitledBorder("Pferdefilter"));

			this.lblSearchHorseID = new JLabel(HorseManagerConfig.LBLHORSEID);
			this.lblSearchName = new JLabel(HorseManagerConfig.LBLHORSENAME);
			this.lblSearchPrice = new JLabel(HorseManagerConfig.LBLHORSEPRICE);
			this.lblSearchTherapyType = new JLabel(HorseManagerConfig.LBLTHERAPYTYPE);
			this.lblSearchHorserace = new JLabel(HorseManagerConfig.LBLHORSERACE);

			this.txtSearchHorseID = new JTextField();
			this.txtSearchName = new JTextField();
			this.txtSearchPrice = new JTextField();

			this.cmbHorseraces = new HorseraceCombo(true);
			this.cmbTherapytypes = new TherapytypeCombo(true);

			panSearch.add(lblSearchHorseID);
			panSearch.add(txtSearchHorseID,"growx");
			panSearch.add(lblSearchName);
			panSearch.add(txtSearchName,"growx");
			panSearch.add(lblSearchPrice);
			panSearch.add(txtSearchPrice,"growx");
			panSearch.add(lblSearchTherapyType);
			panSearch.add(cmbTherapytypes, "growx");
			panSearch.add(lblSearchHorserace);
			panSearch.add(cmbHorseraces, "growx");
			panSearch.add(createSearchControlPane(), "span");

		}	
		return panSearch;
	}

	private JPanel createSearchControlPane(){
		if( panSearchControl == null ){
			panSearchControl = new JPanel( new MigLayout() );
			btnReset = new JButton(ButtonLabelConfig.BTNRESET);
			btnSearch = new JButton(HorseManagerConfig.BTNSEARCH);
			btnSearchTherapy = new JButton(HorseManagerConfig.BTNSEARCHTHERAPY);

			panSearchControl.add(btnReset,"sg 1");
			panSearchControl.add(btnSearchTherapy,"sg 1");
			panSearchControl.add(btnSearch, "sg 1");


			btnReset.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					resetSearch();
				}
			});

			btnSearchTherapy.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					performSearchByTherapy();
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

			HorseQueryValueParser packer = new HorseQueryValueParser();
			packer.setHorseID(txtSearchHorseID.getText());
			packer.setHorseTherapyprice(txtSearchPrice.getText());
			packer.setHorseName(txtSearchName.getText() );
			packer.setTherapytype((String)(cmbTherapytypes.getSelectedItem()));
			packer.setHorserace((String)(cmbHorseraces.getSelectedItem()));
			horses = servicelayer.searchHorse(packer.getQuery());
			tabModel.fillWithNewData(horses);

		} catch (UnknownTherapytypeException e) {
			announceError(MessagesConfig.ERRUNKNOWNTHERAPYTYPE);
		} catch (NoCorrectFormatException e) {
			announceError(MessagesConfig.ERRNOCORRECTFORMAT);
		} catch (DataSourceAccessException e) {
			announceError(MessagesConfig.ERRACCESSPROBLEM);
		} catch (NoClassGivenException e) {
			announceError(MessagesConfig.ERRNOCLASS);
		} catch (UnknownRaceException e) {
			announceError(MessagesConfig.ERRUNKNOWNHORSERACE);
		}
	}

	private void performSearchByTherapy() {
		try {
			HorseQueryValueParser packer = new HorseQueryValueParser();
			packer.setTherapytype((String)(cmbTherapytypes.getSelectedItem()));
			horses = servicelayer.listHorsebyTherapy(packer.getQuery());
			tabModel.fillWithNewData(horses);

		} catch (UnknownTherapytypeException e) {
			announceError(MessagesConfig.ERRUNKNOWNTHERAPYTYPE);
		} catch (NoTherapytypeGivenException e) {
			announceError(MessagesConfig.ERRUNKNOWNTHERAPYTYPE);
		} catch (DataSourceAccessException e) {
			announceError(MessagesConfig.ERRACCESSPROBLEM);
		} catch (NoClassGivenException e) {
			announceError(MessagesConfig.ERRNOCLASS);
		} 

	}

	private void resetSearch() {
		listAllHorses();
	}

	private JScrollPane createHorseListPane(){
		if( panHorseList == null ){
			tabHorselist =new JTable();
			configureTable( tabHorselist );

			tabHorselist.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent arg0) { setInformationPane( tabHorselist.getSelectedRow() );	}

			});

			panHorseList = new JScrollPane(tabHorselist);
			panHorseList.setBorder(BorderFactory.createLoweredBevelBorder());
		}

		return panHorseList;
	}

	private void configureTable(JTable tabHorselist) {
		tabModel = new HorseTableModel();
		tabHorselist.setModel(tabModel);

		tabHorselist.getTableHeader().setReorderingAllowed(false);
		tabHorselist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabHorselist.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tabHorselist.setFillsViewportHeight(true);
		TableColumnModel colmod = tabHorselist.getColumnModel();

		for( int i =0; i < tabModel.getColumnCount(); i++ ){
			colmod.getColumn(i).setResizable(false);
		}

		colmod.getColumn(0).setPreferredWidth(80);
		colmod.getColumn(1).setPreferredWidth(200);
		colmod.getColumn(2).setPreferredWidth(150);
		colmod.getColumn(3).setPreferredWidth(50);
		colmod.getColumn(4).setPreferredWidth(100);
		
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment( JLabel.CENTER );
		colmod.getColumn(3).setCellRenderer( renderer );
		colmod.getColumn(4).setCellRenderer( renderer );
	}

	private void setInformationPane(int selectedRow) {
		if( selectedRow >= 0 ){
			Horse h = tabModel.getHorse(selectedRow);
			txtHorseName.setText(h.getHorseName());
			txtHorsePrice.setText(decFormat.format(h.getHorseTherapyprice()));
			txtHorseTherapytype.setText(h.getHorseTherapytype().getText());
			txtHorserace.setText(h.getHorseRace().getText());
			
			try {
				horsePicture = ImageIO.read(new File(HorseManagerConfig.HORSEPICTUREPATH + h.getHorsePhoto()));
			} catch (IOException e) {
				try {
					System.out.println("NOT FOUND!");
					horsePicture = ImageIO.read(new File(HorseManagerConfig.HORSEPICTUREPATH + HorseManagerConfig.PATHTODUMMYPIC));
				} catch (IOException e1) {}
			}

			if(horsePicture != null ){	

				horsePicture = horsePicture.getScaledInstance(HorseManagerConfig.PICTUREWIDTH, HorseManagerConfig.PICTUREHEIGHT, Image.SCALE_AREA_AVERAGING);
				lblPicture.setIcon(new ImageIcon(horsePicture)); 
			}
		}else{
			resetInformationPane();
		}
	}

	private void resetInformationPane() {
		txtHorseName.setText("");
		txtHorsePrice.setText("");
		txtHorseTherapytype.setText("");
		txtHorserace.setText("");
		try {
			horsePicture = ImageIO.read(new File(HorseManagerConfig.HORSEPICTUREPATH + HorseManagerConfig.PATHTODUMMYPIC));
			horsePicture = horsePicture.getScaledInstance(HorseManagerConfig.PICTUREWIDTH, HorseManagerConfig.PICTUREHEIGHT, Image.SCALE_AREA_AVERAGING);
			lblPicture.setIcon(new ImageIcon(horsePicture)); 
		} catch (IOException e) {
			announceError(MessagesConfig.ERRIMAGENOTFOUND);
		}
	}

	private Component createHorseOperationPane() {
		if( panHorseOperation == null){
			panHorseOperation = new JPanel(new MigLayout());
			btnNewHorse = new JButton(HorseManagerConfig.BTNNEWHORSE);
			btnDeleteHorse = new JButton(HorseManagerConfig.BTNDELHORSE);
			btnUpdateHorse = new JButton(HorseManagerConfig.BTNUPDATEHORSE);

			btnNewHorse.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) { openCreateHorseDialog(); }
			});

			btnDeleteHorse.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) { deleteHorse(); }
			});

			btnUpdateHorse.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) { openUpdateHorseDialog( ); }
			});

			panHorseOperation.add(btnDeleteHorse, "sg 1");
			panHorseOperation.add(btnUpdateHorse, "sg 1");
			panHorseOperation.add(btnNewHorse, "sg 1");
		}
		return panHorseOperation;
	}

	private void deleteHorse() {
		int selrow =tabHorselist.getSelectedRow();

		if(selrow >= 0 ){
			HorseDeleteDialog dialog = new HorseDeleteDialog(tabModel.getHorse(selrow), servicelayer);
			dialog.setVisible(true);
			listAllHorses();
		}else{
			announceInfo(MessagesConfig.INFSELECTHORSE);
		}
	}

	private void openUpdateHorseDialog() {
		int selrow =tabHorselist.getSelectedRow();
		if(selrow >= 0 ){
			Horse updateHorse = tabModel.getHorse( selrow );
			new HorseCreateUpdateDialog(updateHorse, servicelayer, true).setVisible(true);
			listAllHorses();
		}else{
			announceInfo(MessagesConfig.INFSELECTHORSE);
		}

	}

	private void openCreateHorseDialog() {
		Horse newHorse = null;
		new HorseCreateUpdateDialog(newHorse, servicelayer, false).setVisible(true);
		listAllHorses();
	}

	private JPanel createEastPanel(){
		if( panEast == null ){
			panEast = new JPanel(new MigLayout( "",               
					"[]",             
					"[]:push[]"));

			panEast.setBorder(BorderFactory.createTitledBorder("Therapieeinheiten"));
			panEast.add(createHorseInformationPane(),"wrap,center");
			panEast.add(createCartControlPane());

		}
		return panEast;
	}

	private Component createHorseInformationPane() {
		if( panHorseInformation == null ){

			panHorseInformation = new JPanel( new MigLayout("wrap",""));
			try {
				JPanel picpane = new JPanel();
				lblPicture = new JLabel();
				lblPicture.setPreferredSize(new Dimension(HorseManagerConfig.PICTUREWIDTH, HorseManagerConfig.PICTUREHEIGHT));

				horsePicture = ImageIO.read(new File(HorseManagerConfig.HORSEPICTUREPATH + HorseManagerConfig.PATHTODUMMYPIC));
				horsePicture = horsePicture.getScaledInstance(HorseManagerConfig.PICTUREWIDTH, HorseManagerConfig.PICTUREHEIGHT, Image.SCALE_AREA_AVERAGING);
				lblPicture.setIcon(new ImageIcon(horsePicture));

				picpane.add(lblPicture);
				panHorseInformation.add(picpane, "grow,center");
			} catch (IOException e) {
				announceError(MessagesConfig.ERRIMAGENOTFOUND);
			}

			lblHorseName = new JLabel(HorseManagerConfig.LBLHORSENAME);
			lblTherapytype = new JLabel(HorseManagerConfig.LBLTHERAPYTYPE);
			lblPriceperHour = new JLabel(HorseManagerConfig.LBLHORSEPRICE);
			lblHorserace = new JLabel(HorseManagerConfig.LBLHORSERACE);
			lblTherapyduration = new JLabel(HorseManagerConfig.LBLHORSEDURATION);

			
			txtHorseName = new JTextField();
			txtHorsePrice = new JTextField();
			txtHorseTherapytype = new JTextField();
			txtHorserace = new JTextField();
			txtTherapyDuration = new JTextField();
			
			txtHorserace.setEditable(false);
			txtHorseName.setEditable(false);
			txtHorsePrice.setEditable(false);
			txtHorseTherapytype.setEditable(false);

			panHorseInformation.add(lblHorseName);
			panHorseInformation.add(txtHorseName,"grow");

			panHorseInformation.add(lblTherapytype);
			panHorseInformation.add(txtHorseTherapytype,"grow");

			panHorseInformation.add(lblPriceperHour);
			panHorseInformation.add(txtHorsePrice,"grow");

			panHorseInformation.add(lblHorserace);
			panHorseInformation.add(txtHorserace,"grow");
			
			panHorseInformation.add(lblTherapyduration);
			panHorseInformation.add(txtTherapyDuration,"grow");


		}
		return panHorseInformation;
	}

	private JPanel createCartControlPane() {
		if(panCartOperation == null ){
			panCartOperation = new JPanel( new MigLayout() );
			btnAddToCart = new JButton(HorseManagerConfig.BTNADDTOCART);
			btnCheckout = new JButton(HorseManagerConfig.BTNCHECKOUT);
			lblTherapyunitamount = new JLabel(HorseManagerConfig.LBLTHERAPYUNITAMOUNTSTART);
			
			btnAddToCart.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					addNewTherapyUnit();
				}
			});

			btnCheckout.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					openTherapyunitListing();
				}
			});

			panCartOperation.add(lblTherapyunitamount,"wrap");
			panCartOperation.add(btnAddToCart);
			panCartOperation.add(btnCheckout);
		}
		return panCartOperation;
	}

	private void addNewTherapyUnit(){
		int row = tabHorselist.getSelectedRow();
		String duration = txtTherapyDuration.getText();
		try{
			if( row < 0 ){
				announceInfo(MessagesConfig.INFSELECTHORSE);

			}else if( duration == null || duration.isEmpty() ){
				announceError(MessagesConfig.ERRNODURATION);
			}else{
				TherapyUnit unit = new TherapyUnit();
				Horse h = tabModel.getHorse(row);
				float numDuration = Float.parseFloat(duration);

				unit.setTherapyHorseID( h.getHorseID() );
				unit.setTherapyHorseName( h.getHorseName() );
				unit.setTherapyPrice( h.getHorseTherapyprice() );
				unit.setTherapyType( h.getHorseTherapytype() );
				unit.setTherapyHours(numDuration);

				therapyunitCart.add(unit);
				announceInfo(MessagesConfig.INFUNITADDED);
				lblTherapyunitamount.setText(therapyunitCart.size() + " " + HorseManagerConfig.LBLTHERAPYUNITWORD);
			}		
		}catch(NumberFormatException nfe){
			announceError(MessagesConfig.ERRNOCORRECTFORMAT);
		}
	}

	private void listAllHorses() {
		try {
			horses = this.servicelayer.listAllHorses();
			tabModel.fillWithNewData(horses);
		} catch (DataSourceAccessException e) {
			announceError(MessagesConfig.ERRACCESSPROBLEM);
		}
	}

	private void openTherapyunitListing(){
		if( therapyunitCart.size() <= 0 ){
			announceInfo(MessagesConfig.INFNOTHERAPYUNITSSELECTED);
		}else{
			new TherapyunitListingDialog(therapyunitCart, servicelayer).setVisible(true);
			if( therapyunitCart.size() > 0 ){
				lblTherapyunitamount.setText(therapyunitCart.size() + " " + HorseManagerConfig.LBLTHERAPYUNITWORD);
			}else{
				lblTherapyunitamount.setText(HorseManagerConfig.LBLTHERAPYUNITAMOUNTSTART);
			}
		}
	}

	private void announceError(String message){
		new NotificationDialog(message,NotificationDialog.NotificationMode.ERROR).setVisible(true);			
	}

	private void announceInfo(String message){
		new NotificationDialog(message,NotificationDialog.NotificationMode.INFO).setVisible(true);			
	}
}
