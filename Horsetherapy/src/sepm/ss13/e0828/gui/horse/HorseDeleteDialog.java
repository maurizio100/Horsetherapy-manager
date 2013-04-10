package sepm.ss13.e0828.gui.horse;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import sepm.ss13.e0828.dao.exceptions.HorseNotExistentException;
import sepm.ss13.e0828.dao.exceptions.NoClassGivenException;
import sepm.ss13.e0828.domain.Horse;
import sepm.ss13.e0828.gui.ButtonLabelConfig;
import sepm.ss13.e0828.gui.MessagesConfig;
import sepm.ss13.e0828.gui.exception.NoCorrectFormatException;
import sepm.ss13.e0828.service.TherapyManagementService;
import sepm.ss13.e0828.service.exception.DataSourceAccessException;
import sepm.ss13.e0828.service.exception.NoHorseIdException;

public class HorseDeleteDialog extends JDialog {

	private Horse horse =null;
	private JPanel panAttributes = null;
	private JPanel panButtonpane = null;
	private JPanel panNotification = null;

	private JLabel lblName = null;
	private JLabel lblPrice = null;
	private JLabel lblTherapyType = null;
	private JLabel lblPhoto = null;
	private JLabel lblHorseID = null;
	private JLabel lblHorseRace = null;
	private JTextArea lblMessage = null;

	private JTextField txtName;
	private JTextField txtPrice;
	private JTextField txtTherapytype;
	private JTextField txtPhoto;
	private JTextField txtHorseID;
	private JTextField txtHorseRace;

	private JButton btnOK = null;
	private JButton btnCancel = null;

	private TherapyManagementService servicelayer = null;
	private boolean processsuccessful = false;

	public HorseDeleteDialog( Horse deleteHorse, TherapyManagementService servicelayer ){
		this.servicelayer = servicelayer;
		this.init();

		this.horse = deleteHorse; 
		fillFieldsWithHorseInfromation( deleteHorse ); 
	}


	private void fillFieldsWithHorseInfromation(Horse deleteHorse) {
		txtHorseID.setText(""+deleteHorse.getHorseID());
		txtPrice.setText( "" + deleteHorse.getHorseTherapyprice() );
		txtPhoto.setText(deleteHorse.getHorsePhoto() );
		txtTherapytype.setText( deleteHorse.getHorseTherapytype().getText() );
		txtHorseRace.setText(deleteHorse.getHorseRace().getText());
	}

	private void init(){
		this.setBounds(300, 250,500,260);
		this.setResizable(false);

		this.setTitle(HorseManagerConfig.DIADELETEHORSETITLE);

		this.setModal(true);
		this.setLayout(new MigLayout("fill, wrap","","[][][]"));
		this.add(createHorseAttributeFields(),"grow");
		this.add(createNotificationPanel(), "grow");
		this.add(createButtonPane(),"right");
	}

	private Component createNotificationPanel() {
		if(panNotification == null){
			panNotification = new JPanel(new MigLayout());
			this.lblMessage = new JTextArea(5,50);
			lblMessage.setText(HorseManagerConfig.HORSEDELTESURE);
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
						performDeletion();
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
			panAttributes = new JPanel(new MigLayout("wrap 2"));
			panAttributes.setBorder(BorderFactory.createTitledBorder(HorseManagerConfig.BORDERHORSEINPUT));

			this.lblHorseID = new JLabel(HorseManagerConfig.LBLHORSEID);
			this.lblName = new JLabel(HorseManagerConfig.LBLHORSENAME);
			this.lblPrice = new JLabel(HorseManagerConfig.LBLHORSEPRICE);
			this.lblTherapyType = new JLabel(HorseManagerConfig.LBLTHERAPYTYPE);
			this.lblPhoto = new JLabel(HorseManagerConfig.LBLPHOTO);
			this.lblHorseRace = new JLabel(HorseManagerConfig.LBLHORSERACE);
			
			this.txtHorseID = new JTextField();
			this.txtName = new JTextField();
			this.txtPrice = new JTextField();
			this.txtTherapytype = new JTextField();
			this.txtPhoto  = new JTextField();
			this.txtHorseRace = new JTextField();

			txtHorseID.setEditable(false);
			txtName.setEditable(false);
			txtPrice.setEditable(false);
			txtTherapytype.setEditable(false);
			txtPhoto.setEditable(false);
			txtHorseRace.setEditable(false);

			panAttributes.add(lblHorseID);
			panAttributes.add(txtHorseID,"growx");
			panAttributes.add(lblName);
			panAttributes.add(txtName,"growx");
			panAttributes.add(lblHorseRace);
			panAttributes.add(txtHorseRace,"growx");
			panAttributes.add(lblTherapyType);
			panAttributes.add(txtTherapytype,"growx");
			panAttributes.add(lblPrice);
			panAttributes.add(txtPrice,"growx");
			panAttributes.add(lblPhoto);
			panAttributes.add(txtPhoto, "growx");

		}	
		return panAttributes;		
	}

	private void performDeletion(){
		HorseQueryValueParser helper = new HorseQueryValueParser();
		try {
			helper.setHorseID( ""+horse.getHorseID() );
			servicelayer.deleteHorse(helper.getQuery());
			announceSuccess();

		} catch (NoCorrectFormatException e) { 
			announceError(MessagesConfig.ERRNOCORRECTFORMAT);
		}catch (NoHorseIdException e) {
			announceError(MessagesConfig.ERRNOHORSEID);
		} catch (DataSourceAccessException e) {
			announceError(MessagesConfig.ERRACCESSPROBLEM);
		} catch (HorseNotExistentException e) {
			announceError(MessagesConfig.ERRHORSENOTEXISTENT);
		} catch (NoClassGivenException e) {
			announceError(MessagesConfig.ERRNOCLASS);
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
