package sepm.ss13.e0828.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import sepm.ss13.e0828.gui.horse.HorseManagerConfig;


import net.miginfocom.swing.MigLayout;

public class NotificationDialog extends JDialog {

	private JPanel panButtonpane = null;
	private JPanel panNotification = null;
	private JTextArea lblMessage = null;
	private JButton btnOK = null;
	
	public enum NotificationMode{
		ERROR,INFO
	}

	public NotificationDialog( String notification, NotificationMode mode ){
		this.init();
		announceNotification(notification, mode);
	}


	private void init(){
		this.setBounds(300, 250,500,150);
		this.setResizable(false);

		this.setTitle(HorseManagerConfig.DIADELETEHORSETITLE);

		this.setModal(true);
		this.setLayout(new MigLayout("wrap","[grow]","[][][]"));
		this.add(createNotificationPanel(), "grow");
		this.add(createButtonPane(),"right");
	}

	private Component createNotificationPanel() {
		if(panNotification == null){
			panNotification = new JPanel(new MigLayout());
			this.lblMessage = new JTextArea(5,1);
			lblMessage.setText(HorseManagerConfig.HORSEDELTESURE);
			lblMessage.setFont(new Font("sans serif", Font.BOLD, 12));
			lblMessage.setEditable(false);
			lblMessage.setOpaque(false);
			panNotification.add(lblMessage);//, "grow");
		}
		return panNotification;
	}

	private Component createButtonPane() {
		if( panButtonpane == null ){
			panButtonpane = new JPanel( new MigLayout() );
			btnOK = new JButton( ButtonLabelConfig.BTNOK );

			btnOK.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					performDialogClose();
				}
			});

			panButtonpane.add(btnOK,"sg 1");

		}
		return panButtonpane;
	}


	private void announceError( String message ){
		this.setTitle("Fehler");
		this.lblMessage.setForeground(Color.red);
		this.lblMessage.setText(message);
	}

	private void announceInfo( String message ){
		this.setTitle("Info");
		this.lblMessage.setForeground(Color.blue);
		this.lblMessage.setText(message);
	}
	
	private void performDialogClose() {
		this.dispose();
	}
	
	private void announceNotification( String message, NotificationMode mode ){
		switch(mode){
		case ERROR: announceError(message); break;
		case INFO: announceInfo(message); break;
		}
	}
}
