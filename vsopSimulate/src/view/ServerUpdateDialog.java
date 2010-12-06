package view;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import util.Config;
import util.ProptiesFileWriter;
import util.SceneFileUtil;
import util.TemplateFileUtil;

public class ServerUpdateDialog extends Dialog {

	private static final long serialVersionUID = 1L;
	private Label serverCNNamelabel = null;
	private TextField serverCNNameTextField = null;
	private Label serverENNamelabel = null;
	private TextField serverENNameTextField = null;
	private Label serverSendTypelabel = null;
	private Choice serverSendTypechoice = null;
	private Button OK_button = null;
	private Button Cancel_button = null;
	private String flag = "NEW";
	private ConfigDialog configdialog = null;
	private String old_serverCNName;
	private String old_serverENName;
	private String old_sendType;

	/**
	 * @param owner
	 */
	public ServerUpdateDialog(Frame owner, ConfigDialog configdialog, String flag, String serverCNName,
			String serverENName, String sendType) {
		super(owner);
		initialize();
		this.flag = flag;
		serverCNNameTextField.setText(serverCNName);
		serverENNameTextField.setText(serverENName);
		serverSendTypechoice.select(sendType);
		this.old_serverCNName = serverCNName;
		this.old_serverENName = serverENName;
		this.old_sendType = sendType;
		this.configdialog = configdialog;
		this.setTitle("�ӿ��޸�");
	}

	public ServerUpdateDialog(Frame owner, ConfigDialog configdialog) {
		super(owner);
		initialize();
		this.configdialog = configdialog;
		this.setTitle("�ӿ�����");
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		serverSendTypelabel = new Label();
		serverSendTypelabel.setBounds(new Rectangle(18, 122, 107, 23));
		serverSendTypelabel.setText("���ķ��͵�ַ���ͣ�");
		serverENNamelabel = new Label();
		serverENNamelabel.setBounds(new Rectangle(19, 84, 99, 23));
		serverENNamelabel.setText("�ӿ����ƣ�");
		serverCNNamelabel = new Label();
		serverCNNamelabel.setBounds(new Rectangle(19, 47, 97, 23));
		serverCNNamelabel.setText("�ӿ�����������");
		this.setLayout(null);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 379;
		int height = 200;
		this.setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);
		this.setSize(379, 200);
		this.setVisible(false);
		this.setModal(true);
		this.add(serverCNNamelabel, null);
		this.add(getServerCNNameTextField(), null);
		this.add(serverENNamelabel, null);
		this.add(getServerENNameTextField(), null);
		this.add(serverSendTypelabel, null);
		this.add(getServerSendTypechoice(), null);
		this.add(getOK_button(), null);
		this.add(getCancel_button(), null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				e.getWindow().dispose();
			}
		});

	}

	/**
	 * This method initializes serverCNNameTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getServerCNNameTextField() {
		if (serverCNNameTextField == null) {
			serverCNNameTextField = new TextField();
			serverCNNameTextField.setBounds(new Rectangle(125, 47, 184, 23));
		}
		return serverCNNameTextField;
	}

	/**
	 * This method initializes serverENNameTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getServerENNameTextField() {
		if (serverENNameTextField == null) {
			serverENNameTextField = new TextField();
			serverENNameTextField.setBounds(new Rectangle(125, 86, 184, 23));
		}
		return serverENNameTextField;
	}

	/**
	 * This method initializes serverSendTypechoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getServerSendTypechoice() {
		if (serverSendTypechoice == null) {
			serverSendTypechoice = new Choice();
			serverSendTypechoice.setBounds(new Rectangle(126, 122, 108, 21));
			serverSendTypechoice.addItem("service");
			serverSendTypechoice.addItem("services");

		}
		return serverSendTypechoice;
	}

	/**
	 * This method initializes OK_button	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getOK_button() {
		if (OK_button == null) {
			OK_button = new Button();
			OK_button.setBounds(new Rectangle(81, 161, 88, 23));
			OK_button.setLabel("ȷ��");
			OK_button.addActionListener(new Btn_OK_Listener(this));
		}
		return OK_button;
	}

	/**
	 * This method initializes Cancel_button	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCancel_button() {
		if (Cancel_button == null) {
			Cancel_button = new Button();
			Cancel_button.setBounds(new Rectangle(218, 159, 87, 23));
			Cancel_button.setLabel("ȡ��");
			Cancel_button.addActionListener(new Btn_CANLEL_Listener(this));
		}
		return Cancel_button;
	}

	class Btn_OK_Listener implements ActionListener {
		private ServerUpdateDialog main;

		Btn_OK_Listener(ServerUpdateDialog main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.btn_OK();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void btn_OK() {
		String serverCNName = serverCNNameTextField.getText();
		String serverENName = serverENNameTextField.getText();
		String sendType = serverSendTypechoice.getSelectedItem();
		if (serverCNName.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "�ӿ���������������д", "��ʾ", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (serverENName.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "�ӿ����ֱ�����д", "��ʾ", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (this.flag.equals("NEW")) {
			if (Config.getTemplateConfig(serverCNName) != null) {
				JOptionPane.showMessageDialog(this, "�ӿ��������Ѿ����� ��������", "��ʾ", JOptionPane.WARNING_MESSAGE);
				return;
			}
			List list = Config.getTemplateList();
			for (int i = 0; i < list.size(); i++) {
				if (Config.getTemplateConfig((String) list.get(i)).equals(serverENName)) {
					JOptionPane.showMessageDialog(this, "�ӿ��Ѿ����� ��������", "��ʾ", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			// ���ӽӿ�
			String templateConfigFile = Config.templateFileName;
			String sendTypeConfigFile = Config.serverSendTypeConfigFile;
			ProptiesFileWriter.writeProperties(templateConfigFile, serverCNName, serverENName);
			ProptiesFileWriter.writeProperties(sendTypeConfigFile, serverCNName, sendType);
			try {
				TemplateFileUtil.writerFile(Config.TEMPLATE_FILE_PATH + serverENName + ".xml", "�����뷢�ͱ��ģ�");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Config.init();
			configdialog.addServerList(serverCNName, serverENName, sendType);
			this.dispose();
		} else {
			if (serverCNName.equals(this.old_serverCNName) && serverENName.equals(this.old_serverENName)
					&& sendType.equals(this.old_sendType)) {
				this.dispose();
				return;
			}
			if (serverCNName.equals(this.old_serverCNName)) {
				//��������û���޸�
				if (serverENName.equals(this.old_serverENName)) {
					//�Д�ӿ����Пo�޸�
				} else {
					//�Д�ӿ����Пo����
					List list = Config.getTemplateList();
					for (int i = 0; i < list.size(); i++) {
						if (Config.getTemplateConfig((String) list.get(i)).equals(serverENName)) {
							JOptionPane
									.showMessageDialog(this, "�ӿ��Ѿ����� �����޸�Ϊ�Ѵ��ڽӿ���", "��ʾ", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}
			} else {
				//�ж����������Ƿ��Ѿ�����
				if (Config.getTemplateConfig(serverCNName) != null) {
					JOptionPane.showMessageDialog(this, "�ӿ��������Ѿ����� �����޸�Ϊ�Ѵ��ڽӿ�����", "��ʾ", JOptionPane.WARNING_MESSAGE);
					return;
				}

			}
			if (serverENName.equals(this.old_serverENName)) {
				//�Д�ӿ����Пo�޸�
			} else {
				//�Д�ӿ����Пo����
				List list = Config.getTemplateList();
				for (int i = 0; i < list.size(); i++) {
					if (Config.getTemplateConfig((String) list.get(i)).equals(serverENName)) {
						JOptionPane.showMessageDialog(this, "�ӿ��Ѿ����� �����޸�Ϊ�Ѵ��ڽӿ���", "��ʾ", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
			}

		}
		String bendiName = Config.getConfig("province");
		//ɾ��ԭ������
		ProptiesFileWriter.deleteProperties(Config.templateFileName, this.old_serverCNName);
		ProptiesFileWriter.deleteProperties(Config.serverSendTypeConfigFile, this.old_serverCNName);
		//�����޸ĺ�����
		ProptiesFileWriter.writeProperties(Config.templateFileName, serverCNName, serverENName);
		ProptiesFileWriter.writeProperties(Config.serverSendTypeConfigFile, serverCNName, sendType);
		//�޸������ļ���
		SceneFileUtil.renameFile(Config.TEMPLATE_FILE_PATH +bendiName+"/"+ this.old_serverENName + ".xml", Config.TEMPLATE_FILE_PATH
				+bendiName+"/"+ serverENName + ".xml");
		SceneFileUtil.renameFile(Config.TEMPLATE_FILE_PATH +bendiName+"/"+ this.old_serverENName + ".txt", Config.TEMPLATE_FILE_PATH
				+bendiName+"/"+ serverENName + ".txt");
		SceneFileUtil.renameFile(Config.TEMPLATE_FILE_PATH +bendiName+"/"+ this.old_serverENName, Config.TEMPLATE_FILE_PATH
				+bendiName+"/"+ serverENName);

		configdialog.updateServerList(serverCNName, serverENName, sendType);
		Config.init();
		this.dispose();
	}

	class Btn_CANLEL_Listener implements ActionListener {
		private ServerUpdateDialog main;

		Btn_CANLEL_Listener(ServerUpdateDialog main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				main.dispose();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}
} //  @jve:decl-index=0:visual-constraint="10,10"
