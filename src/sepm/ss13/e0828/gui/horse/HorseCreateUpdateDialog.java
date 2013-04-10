package sepm.ss13.e0828.gui.horse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import sepm.ss13.e0828.dao.exceptions.HorseNotExistentException;
import sepm.ss13.e0828.dao.exceptions.NoClassGivenException;
import sepm.ss13.e0828.dao.exceptions.UnacceptedValueException;
import sepm.ss13.e0828.domain.Horse;
import sepm.ss13.e0828.gui.ButtonLabelConfig;
import sepm.ss13.e0828.gui.HorseraceCombo;
import sepm.ss13.e0828.gui.MessagesConfig;
import sepm.ss13.e0828.gui.TherapytypeCombo;
import sepm.ss13.e0828.gui.exception.NoCorrectFormatException;
import sepm.ss13.e0828.gui.exception.UnknownRaceException;
import sepm.ss13.e0828.gui.exception.UnknownTherapytypeException;
import sepm.ss13.e0828.service.TherapyManagementService;
import sepm.ss13.e0828.service.exception.DataSourceAccessException;
import sepm.ss13.e0828.service.exception.LessValuesException;

public class HorseCreateUpdateDialog extends JDialog {

	private Horse horse =null;
	private JPanel panAttributes = null;
	private JPanel panButtonpane = null;
	private JPanel panNotification = null;

	private JLabel lblName = null;
	private JLabel lblPrice = null;
	private JLabel lblTherapyType = null;
	private JLabel lblPhoto = null;
	private JLabel lblRace = null;
	private JTextArea lblMessage = null;

	private JTextField txtName = null;
	private JTextField txtPrice = null;
	private JComboBox cmbTherapytypes = null;
	private JComboBox cmbRaces = null;
	
	private JButton btnOK = null;
	private JButton btnCancel = null;
	private JButton btnPictureSelect = null;
	private JTextField txtPhoto = null;

	private TherapyManagementService servicelayer = null;
	private boolean update = false;
	private boolean processsuccessful = false;

	public HorseCreateUpdateDialog( Horse horse, TherapyManagementService servicelayer,boolean update  ){
		this.servicelayer = servicelayer;
		this.update = update;
		this.init();

		if( update ){ this.horse = horse; fillFieldsWithHorseInfromation( horse ); }
		else{ this.horse = new Horse(); }
	}


	private void fillFieldsWithHorseInfromation(Horse updateHorse) {
		txtName.setText(updateHorse.getHorseName());
		txtPrice.setText( "" + updateHorse.getHorseTherapyprice() );
		txtPhoto.setText(updateHorse.getHorsePhoto() );
		cmbTherapytypes.setSelectedIndex( updateHorse.getHorseTherapytype().ordinal() );
	}

	private void init(){
		this.setBounds(300, 250,500,260);
		this.setResizable(false);

		if( update ){this.setTitle(HorseManagerConfig.DIAUPDATEHORSETITLE);}
		else{this.setTitle(HorseManagerConfig.DIANEWHORSETITLE);}

		this.setLayout(new MigLayout("wrap","[grow]","[][]"));
		this.setModal(true);
		this.add(createHorseAttributeFields(),"grow");
		this.add(createNotificationPanel(), "grow");
		this.add(createButtonPane(),"right");
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

	private Component createButtonPane() {
		if( panButtonpane == null ){
			panButtonpane = new JPanel( new MigLayout() );
			btnOK = new JButton( ButtonLabelConfig.BTNPROCESS );
			btnCancel = new JButton( ButtonLabelConfig.BTNCANCEL );

			btnOK.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if( processsuccessful ){ performDialogClose(); }
					else{
						if( update ){ performUpdate(); }
						else{ performCreation(); }
					}
				}

			});

			btnCancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					performDialogClose();
				}
			});	

			panButtonpane.add(btnCancel,"sg 1");
			panButtonpane.add(btnOK,"sg 1");

		}
		return panButtonpane;
	}

	private JPanel createHorseAttributeFields(){
		if( panAttributes == null ){
			panAttributes = new JPanel(new MigLayout("wrap 3","[][][]"));
			panAttributes.setBorder(BorderFactory.createTitledBorder(HorseManagerConfig.BORDERHORSEINPUT));

			this.lblName = new JLabel(HorseManagerConfig.LBLHORSENAME);
			this.lblPrice = new JLabel(HorseManagerConfig.LBLHORSEPRICE);
			this.lblTherapyType = new JLabel(HorseManagerConfig.LBLTHERAPYTYPE);
			this.lblPhoto = new JLabel(HorseManagerConfig.LBLPHOTO);
			this.lblRace = new JLabel(HorseManagerConfig.LBLHORSERACE);
			
			this.txtName = new JTextField();
			this.txtPrice = new JTextField();
			this.cmbTherapytypes = new TherapytypeCombo(false);
			this.txtPhoto  = new JTextField(20);
			this.txtPhoto.setEditable(false);
			
			this.cmbRaces = new HorseraceCombo(false);
			
			this.btnPictureSelect = new JButton(HorseManagerConfig.BTNPICTURE);

			if(update){
				txtName.setEditable(false);
			}

			panAttributes.add(lblName);
			panAttributes.add(txtName,"growx,span 2");
			panAttributes.add(lblTherapyType);
			panAttributes.add(cmbTherapytypes,"growx, span 2");
			panAttributes.add(lblRace);
			panAttributes.add(cmbRaces,"growx, span 2");
			panAttributes.add(lblPrice);
			panAttributes.add(txtPrice,"growx, span 2");
			panAttributes.add(lblPhoto);
			panAttributes.add(txtPhoto);
			panAttributes.add(btnPictureSelect);
			
			btnPictureSelect.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JFileChooser fc = new JFileChooser("./pics");
					fc.showOpenDialog(null);
					txtPhoto.setText(fc.getSelectedFile().getName());
				}
			});
			
		}	
		return panAttributes;		
	}

	private void performCreation() {
		try {
			HorseQueryValueParser packer = new HorseQueryValueParser();
			packer.setHorseName(txtName.getText());
			packer.setHorseTherapyprice(txtPrice.getText());
			packer.setHorsePhoto(txtPhoto.getText());
			packer.setTherapytype(cmbTherapytypes.getSelectedItem().toString());
			packer.setHorserace(cmbRaces.getSelectedItem().toString());
			servicelayer.createHorse(packer.getQuery());
			announceSuccess();

		} catch (NoCorrectFormatException e) {
			announceError(MessagesConfig.ERRNOCORRECTFORMAT);
		} catch (UnknownTherapytypeException e) {
			announceError(MessagesConfig.ERRUNKNOWNTHERAPYTYPE);
		} catch (LessValuesException e) {
			announceError(MessagesConfig.ERRLESSVALUES);
		} catch (DataSourceAccessException e) {
			announceError(MessagesConfig.ERRACCESSPROBLEM);
		} catch (NoClassGivenException e) {
			announceError(MessagesConfig.ERRNOCLASS);
		} catch (UnacceptedValueException e) {
			announceError(MessagesConfig.ERRNOCORRECTFORMAT);
		} catch (UnknownRaceException e) {
			announceError(MessagesConfig.ERRUNKNOWNHORSERACE);
		}
	}

	private void performUpdate() {
		try {

			HorseQueryValueParser packer = new HorseQueryValueParser();
			packer.setHorseID( "" + horse.getHorseID() );
			packer.setHorseName(txtName.getText());
			packer.setHorseTherapyprice(txtPrice.getText());
			packer.setHorsePhoto(txtPhoto.getText());
			packer.setTherapytype(cmbTherapytypes.getSelectedItem().toString());
			packer.setHorserace(cmbRaces.getSelectedItem().toString());
			servicelayer.updateHorse(packer.getQuery());
			announceSuccess();

		} catch (NoCorrectFormatException e) {
			announceError(MessagesConfig.ERRNOCORRECTFORMAT);
		}catch (UnknownTherapytypeException e) {
			announceError(MessagesConfig.ERRUNKNOWNTHERAPYTYPE);
		} catch (DataSourceAccessException e) {
			announceError(MessagesConfig.ERRACCESSPROBLEM);
		} catch (HorseNotExistentException e) {
			announceError(MessagesConfig.ERRHORSENOTEXISTENT);
		} catch (UnacceptedValueException e) {
			announceError(MessagesConfig.ERRUNACCEPTEDVALUES);
		}catch (NoClassGivenException e) { 
			announceError(MessagesConfig.ERRNOCLASS);	
		} catch (UnknownRaceException e) {
			announceError(MessagesConfig.ERRUNKNOWNHORSERACE);
		}
	}

	private void announceError( String message ){
		this.lblMessage.setForeground(Color.red);
		this.lblMessage.setText(message);
	}

	private void announceSuccess(){
		this.lblMessage.setForeground(Color.BLUE);
		this.lblMessage.setText(MessagesConfig.MSGPROCESSSUCCESSFUL);
		this.processsuccessful = true;
		btnOK.setText(ButtonLabelConfig.BTNCLOSE);

	}

	private void performDialogClose() {
		this.dispose();
	}
}
