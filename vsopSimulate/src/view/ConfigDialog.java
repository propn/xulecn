package view;

import java.awt.Button;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.Config;
import util.ProptiesFileWriter;
import util.SceneFileUtil;

public class ConfigDialog extends Dialog {

	private static final long serialVersionUID = 1L;
	private MainFrame main = null;
	private Label ServerURLlabel = null;
	private TextField ServerURLTextField = null;
	private Label jiekouListlabel = null;
	private JScrollPane serverListjScrollPane = null;
	private JTable serverListjTable = null;
	private Button newServerButton = null;
	private Button deleteServerButton = null;
	private Button deleteButton = null;
	private Button updateURLbutton = null;
	private Button closeButton = null;

	/**
	 * @param owner
	 */
	public ConfigDialog(Frame owner) {
		super(owner);
		this.main = (MainFrame) owner;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		jiekouListlabel = new Label();
		jiekouListlabel.setBounds(new Rectangle(24, 88, 104, 23));
		jiekouListlabel.setText("服务器接口列表：");
		ServerURLlabel = new Label();
		ServerURLlabel.setBounds(new Rectangle(25, 46, 86, 23));
		ServerURLlabel.setText("服务器地址：");
		this.setLayout(null);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 720;
		int height = 413;
		this.setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);
		this.setSize(720, 413);
		this.setVisible(false);
		this.setModal(true);
		this.setTitle("模拟器配置");
		this.add(ServerURLlabel, null);
		this.add(getServerURLTextField(), null);

		this.add(jiekouListlabel, null);
		this.add(getServerListjScrollPane(), null);
		this.add(getNewServerButton(), null);
		this.add(getDeleteServerButton(), null);
		this.add(getDeleteButton(), null);
		this.add(getUpdateURLbutton(), null);
		this.add(getCloseButton(), null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				e.getWindow().dispose();
			}
		});
	}

	/**
	 * This method initializes ServerURLTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getServerURLTextField() {
		if (ServerURLTextField == null) {
			ServerURLTextField = new TextField();
			ServerURLTextField.setBounds(new Rectangle(121, 48, 348, 23));
			ServerURLTextField.setText(Config.getConfig("URL"));
		}
		return ServerURLTextField;
	}

	/**
	 * This method initializes serverListjScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getServerListjScrollPane() {
		if (serverListjScrollPane == null) {
			serverListjScrollPane = new JScrollPane();
			serverListjScrollPane.setBounds(new Rectangle(22, 124, 515, 213));
			serverListjScrollPane.setViewportView(getServerListjTable());
		}
		return serverListjScrollPane;
	}

	/**
	 * This method initializes serverListjTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getServerListjTable() {
		if (serverListjTable == null) {
			serverListjTable = new JTable();
			DefaultTableModel model = (DefaultTableModel) serverListjTable.getModel();
			model.addColumn("接口中文描述");
			model.addColumn("接口名");
			model.addColumn("报文发送地址类型");
			List list = Config.getTemplateList();
			for (int i = 0; i < list.size(); i++) {
				String serverCNName = (String) list.get(i);
				String sengType = Config.getSendType(serverCNName) == null ? "services" : Config
						.getSendType(serverCNName);
				model.addRow(new String[] { serverCNName, Config.getTemplateConfig(serverCNName), sengType });
			}

		}
		return serverListjTable;
	}

	/**
	 * This method initializes newServerButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getNewServerButton() {
		if (newServerButton == null) {
			newServerButton = new Button();
			newServerButton.setBounds(new Rectangle(563, 141, 130, 23));
			newServerButton.setLabel("新增接口");
			newServerButton.addActionListener(new Btn_NewServer_Listener(this));
		}
		return newServerButton;
	}

	/**
	 * This method initializes deleteServerButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getDeleteServerButton() {
		if (deleteServerButton == null) {
			deleteServerButton = new Button();
			deleteServerButton.setBounds(new Rectangle(564, 185, 131, 23));
			deleteServerButton.setLabel("修改接口");
			deleteServerButton.addActionListener(new Btn_UpdateServer_Listener(this));
		}
		return deleteServerButton;
	}

	/**
	 * This method initializes deleteButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new Button();
			deleteButton.setBounds(new Rectangle(565, 231, 129, 23));
			deleteButton.setLabel("删除接口");
			deleteButton.addActionListener(new Btn_DeleteServer_Listener(this));
		}
		return deleteButton;
	}

	/**
	 * This method initializes updateURLbutton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getUpdateURLbutton() {
		if (updateURLbutton == null) {
			updateURLbutton = new Button();
			updateURLbutton.setBounds(new Rectangle(485, 48, 122, 23));
			updateURLbutton.setLabel("修改地址");
			updateURLbutton.addActionListener(new Btn_UpdateURL_Listener(this));
		}
		return updateURLbutton;
	}

	/**
	 * This method initializes closeButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCloseButton() {
		if (closeButton == null) {
			closeButton = new Button();
			closeButton.setBounds(new Rectangle(566, 369, 132, 23));
			closeButton.setLabel("关闭");
			closeButton.addActionListener(new Btn_Close_Listener(this));
		}
		return closeButton;
	}

	public void addServerList(String serverCNName, String serverENName, String serverSendType) {
		DefaultTableModel model = (DefaultTableModel) serverListjTable.getModel();
		model.addRow(new String[] { serverCNName, serverENName, serverSendType });
	}

	public void updateServerList(String serverCNName, String serverENName, String serverSendType) {
		int row = serverListjTable.getSelectedRow();

		DefaultTableModel model = (DefaultTableModel) serverListjTable.getModel();
		model.removeRow(row);
		model.addRow(new String[] { serverCNName, serverENName, serverSendType });
	}

	class Btn_NewServer_Listener implements ActionListener {
		private ConfigDialog main;

		Btn_NewServer_Listener(ConfigDialog main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.newServer();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void newServer() {
		ServerUpdateDialog dialog = new ServerUpdateDialog(main, this);
		dialog.show();
	}

	class Btn_UpdateServer_Listener implements ActionListener {
		private ConfigDialog main;

		Btn_UpdateServer_Listener(ConfigDialog main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.updateServer();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void updateServer() {
		int row = serverListjTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "请选择需要修改的接口", "提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) serverListjTable.getModel();
		String serverCNName = (String) model.getValueAt(row, 0);
		String serverENName = (String) model.getValueAt(row, 1);
		String serverSendType = (String) model.getValueAt(row, 2);
		ServerUpdateDialog dialog = new ServerUpdateDialog(main, this, "UPDATE", serverCNName, serverENName,
				serverSendType);
		dialog.show();
	}

	class Btn_DeleteServer_Listener implements ActionListener {
		private ConfigDialog main;

		Btn_DeleteServer_Listener(ConfigDialog main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.deleteServer();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void deleteServer() {
		String bendiName = Config.getConfig("province");
		int row = serverListjTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "请选择需要删除的接口", "提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) serverListjTable.getModel();
		String serverCNName = (String) model.getValueAt(row, 0);

		int flag = JOptionPane.showConfirmDialog(this, "删除接口将会删除接口所有报文以及报文模板 你确定吗");
		if (flag == 0) {

			ProptiesFileWriter.deleteProperties(Config.templateFileName, serverCNName);
			ProptiesFileWriter.deleteProperties(Config.serverSendTypeConfigFile, serverCNName);
			SceneFileUtil.deleteFileDir(Config.TEMPLATE_FILE_PATH +bendiName+"/"+ Config.getTemplateConfig(serverCNName));
			SceneFileUtil.deleteFileDir(Config.TEMPLATE_FILE_PATH +bendiName+"/"+ Config.getTemplateConfig(serverCNName) + ".xml");
			SceneFileUtil.deleteFileDir(Config.TEMPLATE_FILE_PATH +bendiName+"/"+ Config.getTemplateConfig(serverCNName) + ".txt");
			int selectrow = serverListjTable.getSelectedRow();

			model.removeRow(selectrow);

			Config.init();
			return;
		}
	}

	class Btn_Close_Listener implements ActionListener {
		private ConfigDialog main;

		Btn_Close_Listener(ConfigDialog main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.closeWindow();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void closeWindow() {
		main.updateDate();
		this.dispose();
	}

	class Btn_UpdateURL_Listener implements ActionListener {
		private ConfigDialog main;

		Btn_UpdateURL_Listener(ConfigDialog main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.updateUrl();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void updateUrl() {
		String url = ServerURLTextField.getText();
		if (url.trim().equals("")) {
			JOptionPane.showMessageDialog(this, "地址不能为空", "提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String configFile = Config.configFileName;
		ProptiesFileWriter.writeProperties(configFile, "URL", url);
		JOptionPane.showMessageDialog(this, "修改成功", "提示", JOptionPane.WARNING_MESSAGE);
	}
} //  @jve:decl-index=0:visual-constraint="29,-110"
