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
import java.awt.image.ImageObserver;
import java.util.List;

import javax.swing.JOptionPane;

import util.Config;
import util.SceneFileUtil;

public class SaveXMLView extends Dialog {

	private static final long serialVersionUID = 1L;
	private Label serverNamelabel = null;
	private Choice serverNameChoice = null;
	private Label sieneNameLabel = null;
	private TextField sieneNameTextField = null;
	private Button saveButton = null;
	private Button cancleButton = null;
	private MainFrame main;
	private Label label = null;
	private String  serverCNName = null;
	/**
	 * @param owner
	 */
	public SaveXMLView(Frame owner,String  serverCNName) {
		super(owner);
		this.main = (MainFrame) owner;
		this.serverCNName=serverCNName;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		label = new Label();
		label.setBounds(new Rectangle(23, 37, 114, 23));
		label.setText("当前省："+Config.getConfig("province"));
		sieneNameLabel = new Label();
		sieneNameLabel.setBounds(new Rectangle(22, 102, 110, 23));
		sieneNameLabel.setText("请输入场景名称：");
		serverNamelabel = new Label();
		serverNamelabel.setBounds(new Rectangle(22, 66, 107, 23));
		serverNamelabel.setText("请选择服务接口：");
		this.setLayout(null);
		//居中显示设置
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 435;
		int height = 176;
		this.setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);

		this.setSize(435, 186);
		this.setTitle("保存报文");
		this.add(serverNamelabel, null);
		this.add(getServerNameChoice(), null);
		this.add(sieneNameLabel, null);
		this.add(getSieneNameTextField(), null);
		this.add(getSaveButton(), null);
		this.add(getCancleButton(), null);
		this.setModal(true);
		this.setVisible(false);
		this.add(label, null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				e.getWindow().dispose();
			}
		});

	}

	/**
	 * This method initializes serverNameChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getServerNameChoice() {
		if (serverNameChoice == null) {
			serverNameChoice = new Choice();
			serverNameChoice.setBounds(new Rectangle(155, 68, 180, 21));
			List list = Config.getTemplateList();
			for (int i = 0; i < list.size(); i++) {
				serverNameChoice.addItem((String) list.get(i));
			}
			
		}
		serverNameChoice.select(this.serverCNName);
		return serverNameChoice;
	}

	/**
	 * This method initializes sieneNameTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getSieneNameTextField() {
		if (sieneNameTextField == null) {
			sieneNameTextField = new TextField();
			sieneNameTextField.setBounds(new Rectangle(154, 103, 180, 23));
		}
		return sieneNameTextField;
	}

	/**
	 * This method initializes saveButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getSaveButton() {
		if (saveButton == null) {
			saveButton = new Button();
			saveButton.setBounds(new Rectangle(89, 147, 96, 23));
			saveButton.setLabel("保存");
			saveButton.addActionListener(new Btn_SaveScene_Listener(this));
		}
		return saveButton;
	}

	/**
	 * This method initializes cancleButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCancleButton() {
		if (cancleButton == null) {
			cancleButton = new Button();
			cancleButton.setBounds(new Rectangle(232, 146, 98, 23));
			cancleButton.setLabel("取消");
			cancleButton.addActionListener(new Btn_Cancel_Listener(this));
		}
		return cancleButton;
	}

	class Btn_Cancel_Listener implements ActionListener {
		private SaveXMLView main;

		Btn_Cancel_Listener(SaveXMLView main) {
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

	class Btn_SaveScene_Listener implements ActionListener {
		private SaveXMLView main;

		Btn_SaveScene_Listener(SaveXMLView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				main.Btn_SaveScene();
			} catch (Exception ex) {
				e.paramString();
			}
		}
	}

	public void Btn_SaveScene() {
		//保存报文
		String title = "提示";
		int type = JOptionPane.PLAIN_MESSAGE;
		if (sieneNameTextField.getText().equals("")) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "场景名不能为空！";
			JOptionPane.showMessageDialog(main, message, title, type);
			return;
		}
		//判断场景是否存在
		String serverName = Config.getTemplateConfig(serverNameChoice.getSelectedItem());
		String sceneName = sieneNameTextField.getText();
		if (SceneFileUtil.isSceneExits(serverName, sceneName)) {

			String message = "场景已存在，是否覆盖！";
			int flag = JOptionPane.showConfirmDialog(main, message);
			if (flag == 0) {
				//覆盖
				String xmlStr = main.getXmlStr();
				try {
					SceneFileUtil.writerSceneFile(serverName, sceneName, xmlStr);
					JOptionPane.showMessageDialog(main, "场景保存成功！", title, JOptionPane.PLAIN_MESSAGE);
					this.dispose();
				} catch (Exception e1) {
					String mes = "场景保存失败！/n原因：" + e1.getMessage();
					JOptionPane.showMessageDialog(main, mes, title, ImageObserver.ERROR);
				}
			}
			if (flag == 1) {
				//不覆盖  退出
				return;
			}
			if (flag == 2) {
				this.dispose();
			}

		} else {
			String xmlStr = main.getXmlStr();
			try {
				SceneFileUtil.writerSceneFile(serverName, sceneName, xmlStr);
				JOptionPane.showMessageDialog(main, "场景保存成功！", title, JOptionPane.PLAIN_MESSAGE);
				this.show(false);
			} catch (Exception e1) {
				String mes = "场景保存失败！/n原因：" + e1.getMessage();
				JOptionPane.showMessageDialog(main, mes, title, ImageObserver.ERROR);
			}
		}

	}
} //  @jve:decl-index=0:visual-constraint="29,-3"
