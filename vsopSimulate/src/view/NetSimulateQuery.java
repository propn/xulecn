package view;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import util.Config;
import util.DateUtil;
import config.SubsInstQrySVXMLUtil;

public class NetSimulateQuery extends Dialog {
	private static final String serviceName = "SubsInstQrySV"; //  @jve:decl-index=0:
	private static final long serialVersionUID = 1L;
	private Label StreamingNoLabel = null;
	private TextField streamingNoTextField = null;
	private Label systemIdLabel = null;
	private TextField systemIdTextField = null;
	private Label prodSpecCodeLabel = null;
	private TextField prodSpecCodeLabelTextField = null;
	private Label productNoLabel = null;
	private TextField productNotextField = null;
	private Button submitButton = null;
	private Button cancelButton = null;
	private Button resetButton = null;
	private Label TimeStamplabel = null;
	private TextField TimeStamptextField = null;
	private Button button = null;

	private MainFrame frame;
	private Button button1 = null;

	/**
	 * @param owner
	 */
	public NetSimulateQuery(Frame owner) {
		super(owner);
		frame = (MainFrame) owner;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		TimeStamplabel = new Label();
		TimeStamplabel.setBounds(new Rectangle(16, 121, 187, 23));
		TimeStamplabel.setText("时间戳*（TimeStamp14）：");
		productNoLabel = new Label();
		productNoLabel.setBounds(new Rectangle(380, 85, 162, 23));
		productNoLabel.setText("用户号码*（ProductNo20）：");
		prodSpecCodeLabel = new Label();
		prodSpecCodeLabel.setBounds(new Rectangle(17, 84, 191, 23));
		prodSpecCodeLabel.setText("产品编码*（ProdSpecCode25）：");
		systemIdLabel = new Label();
		systemIdLabel.setBounds(new Rectangle(394, 44, 151, 23));
		systemIdLabel.setText("系统标识*（SystemId3）：");
		StreamingNoLabel = new Label();
		StreamingNoLabel.setBounds(new Rectangle(20, 43, 184, 23));
		StreamingNoLabel.setText("流水号*（StreamingNo60:）：");
		this.setLayout(null);
		this.setSize(735, 202);
		this.setTitle("网厅模拟器订购关系查询");
		this.add(StreamingNoLabel, null);
		this.add(getStreamingNoTextField(), null);
		this.add(systemIdLabel, null);
		this.add(getSystemIdTextField(), null);
		this.add(prodSpecCodeLabel, null);
		this.add(getProdSpecCodeLabelTextField(), null);
		this.add(productNoLabel, null);
		this.add(getProductNotextField(), null);
		this.add(getSubmitButton(), null);
		this.add(getCancelButton(), null);
		this.add(getResetButton(), null);
		this.add(TimeStamplabel, null);
		this.add(getTimeStamptextField(), null);
		this.add(getButton(), null);
		this.add(getButton1(), null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
				e.getWindow().show(false);
			}
		});
		this.setModal(true);
	}

	/**
	 * This method initializes streamingNoTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getStreamingNoTextField() {
		if (streamingNoTextField == null) {
			streamingNoTextField = new TextField();
			streamingNoTextField.setBounds(new Rectangle(207, 44, 128, 23));
		}
		return streamingNoTextField;
	}

	/**
	 * This method initializes systemIdTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getSystemIdTextField() {
		if (systemIdTextField == null) {
			systemIdTextField = new TextField();
			systemIdTextField.setBounds(new Rectangle(547, 43, 180, 23));
		}
		return systemIdTextField;
	}

	/**
	 * This method initializes prodSpecCodeLabelTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getProdSpecCodeLabelTextField() {
		if (prodSpecCodeLabelTextField == null) {
			prodSpecCodeLabelTextField = new TextField();
			prodSpecCodeLabelTextField.setBounds(new Rectangle(208, 84, 164, 23));
		}
		return prodSpecCodeLabelTextField;
	}

	/**
	 * This method initializes productNotextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getProductNotextField() {
		if (productNotextField == null) {
			productNotextField = new TextField();
			productNotextField.setBounds(new Rectangle(546, 83, 180, 23));
		}
		return productNotextField;
	}

	/**
	 * This method initializes submitButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getSubmitButton() {
		if (submitButton == null) {
			submitButton = new Button();
			submitButton.setBounds(new Rectangle(136, 164, 154, 23));
			submitButton.setLabel("确定");
			submitButton.addActionListener(new Btn_OK_ActionListen(this));
		}
		return submitButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new Button();
			cancelButton.setBounds(new Rectangle(488, 164, 150, 23));
			cancelButton.setLabel("取消");
			cancelButton.addActionListener(new Btn_Cancel_ActionListen(this));
		}
		return cancelButton;
	}

	/**
	 * This method initializes resetButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getResetButton() {
		if (resetButton == null) {
			resetButton = new Button();
			resetButton.setBounds(new Rectangle(312, 164, 152, 23));
			resetButton.setLabel("重置");
		}
		return resetButton;
	}

	public void do_btn_OK() {
		String title = "提示";
		int type = JOptionPane.PLAIN_MESSAGE;
		//首先判断必输域是否都有数据
		if ("".equals(streamingNoTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "流水号不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(systemIdTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "系统标识不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(prodSpecCodeLabelTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "产品编码不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(productNotextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "用户号码不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(TimeStamptextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "时间戳不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}

		SubsInstQrySVXMLUtil vo = new SubsInstQrySVXMLUtil();
		vo.setStreamingNo(streamingNoTextField.getText());
		vo.setSystemId(Config.getSystemCode(systemIdTextField.getText()));
		vo.setProdSpecCode(prodSpecCodeLabelTextField.getText());
		vo.setProductNo(productNotextField.getText());
		vo.setTimeStamp(TimeStamptextField.getText());
		frame.setSendXml(vo.getRequestXML());
		frame.setSendURL(serviceName);
		this.show(false);
	}

	public class Btn_OK_ActionListen implements ActionListener {
		private NetSimulateQuery spcpphase;

		public Btn_OK_ActionListen(NetSimulateQuery spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.do_btn_OK();
		}

	}

	public class Btn_Cancel_ActionListen implements ActionListener {
		private NetSimulateQuery spcpphase;

		public Btn_Cancel_ActionListen(NetSimulateQuery spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.show(false);
		}

	}

	/**
	 * This method initializes TimeStamptextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTimeStamptextField() {
		if (TimeStamptextField == null) {
			TimeStamptextField = new TextField();
			TimeStamptextField.setBounds(new Rectangle(208, 121, 127, 23));
		}
		return TimeStamptextField;
	}

	/**
	 * This method initializes button	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton() {
		if (button == null) {
			button = new Button();
			button.setBounds(new Rectangle(350, 122, 50, 23));
			button.setLabel("生成");
			button.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					TimeStamptextField.setText(DateUtil.getDateStr());
				}
			});
		}
		return button;
	}

	/**
	 * This method initializes button1	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton1() {
		if (button1 == null) {
			button1 = new Button();
			button1.setBounds(new Rectangle(342, 44, 41, 23));
			button1.setLabel("生成");
			button1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					streamingNoTextField.setText(DateUtil.getDateStr());
				}
			});
		}
		return button1;
	}
} //  @jve:decl-index=0:visual-constraint="-19,21"
