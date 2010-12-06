package view;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.DateUtil;

/**
 * <pre>
 * Title:捆绑销售品同步
 * Description: 捆绑销售品同步
 * </pre>
 * @author caozj 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class BindOfferInfoSyn extends Dialog {

	private static final long serialVersionUID = 1L;
	private Label streamingNoLabel = null;
	private Label descriptionLabel = null;
	private TextField streamingNoTextField = null;
	private Button newStreamingNoButton = null;
	private Label systemIdLabel = null;
	private Choice systemIdChoice = null;
	private Label offerIdLabel = null;
	private TextField offerIdTextField = null;
	private Label opTypeLabel = null;
	private Choice opTypeChoice = null;
	private Label nameCNLabel = null;
	private TextField nameCNTextField = null;
	private Label nameENLabel = null;
	private TextField nameENTextField = null;
	private Label descriptionCNLabel = null;
	private TextArea descriptionCNTextArea = null;
	private Label descriptionENLabel = null;
	private TextArea descriptionENTextArea = null;
	private Label stateLabel = null;
	private Choice stateChoice = null;
	private Label payDescriptionLabel = null;
	private TextArea payDescriptionTextArea = null;
	private Label payIdLabel = null;
	private TextField payIdTextField = null;
	private Label subOptionLabel = null;
	private Choice subOptionChoice = null;
	private Label presentLabel = null;
	private Choice presentChoice = null;
	private Label corpOnlyLabel = null;
	private Choice corpOnlyChoice = null;
	private Label productIDLabel = null;
	private JScrollPane productIDJScrollPane = null;
	private JTable productIDJTable = null;
	private Button newProductIDButton = null;
	private Button deleteProductIDButton = null;
	private Label scopeLabel = null;
	private Choice scopeChoice = null;
	private Label prodOfferRelationLabel = null;
	private JScrollPane prodOfferRelationJScrollPane = null;
	private JTable prodOfferRelationJTable = null;
	private Button newProdOfferRelationButton = null;
	private Button deleteProdOfferRelationButton = null;
	private Button okButton = null;
	private Button cancelButton = null;
	private Button resetButton = null;
	private TextField newProdOfferRelationtextField = null;
	private MainFrame frame;

	/**
	 * @param owner
	 */
	public BindOfferInfoSyn(Frame owner) {
		super(owner);
		this.frame = (MainFrame) owner;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		prodOfferRelationLabel = new Label();
		prodOfferRelationLabel.setBounds(new Rectangle(32, 611, 134, 23));
		prodOfferRelationLabel.setText("关联的增值销售品列表：");
		scopeLabel = new Label();
		scopeLabel.setBounds(new Rectangle(77, 577, 87, 23));
		scopeLabel.setText("业务适用范围：");
		productIDLabel = new Label();
		productIDLabel.setBounds(new Rectangle(60, 498, 105, 23));
		productIDLabel.setText("增值产品ID列表*：");
		corpOnlyLabel = new Label();
		corpOnlyLabel.setBounds(new Rectangle(413, 460, 91, 23));
		corpOnlyLabel.setText("是否集团专用：");
		presentLabel = new Label();
		presentLabel.setBounds(new Rectangle(89, 460, 73, 23));
		presentLabel.setText("是否可赠送：");
		subOptionLabel = new Label();
		subOptionLabel.setBounds(new Rectangle(411, 422, 90, 23));
		subOptionLabel.setText("是否需要订购：");
		payIdLabel = new Label();
		payIdLabel.setBounds(new Rectangle(78, 421, 85, 23));
		payIdLabel.setText("计费策略标识：");
		payDescriptionLabel = new Label();
		payDescriptionLabel.setBounds(new Rectangle(72, 355, 91, 23));
		payDescriptionLabel.setText("计费策略描述：");
		stateLabel = new Label();
		stateLabel.setBounds(new Rectangle(118, 317, 41, 23));
		stateLabel.setText("状态：");
		descriptionENLabel = new Label();
		descriptionENLabel.setBounds(new Rectangle(48, 254, 108, 23));
		descriptionENLabel.setText("组合产品英文描述：");
		descriptionCNLabel = new Label();
		descriptionCNLabel.setBounds(new Rectangle(48, 191, 111, 23));
		descriptionCNLabel.setText("组合产品中文描述：");
		nameENLabel = new Label();
		nameENLabel.setBounds(new Rectangle(384, 153, 112, 23));
		nameENLabel.setText("组合产品英文名称：");
		nameCNLabel = new Label();
		nameCNLabel.setBounds(new Rectangle(42, 148, 118, 23));
		nameCNLabel.setText("产品组合中文名称*：");
		opTypeLabel = new Label();
		opTypeLabel.setBounds(new Rectangle(450, 111, 53, 23));
		opTypeLabel.setText("动作*：");
		offerIdLabel = new Label();
		offerIdLabel.setBounds(new Rectangle(16, 111, 150, 23));
		offerIdLabel.setText("增值业务捆绑销售品ID*：");
		systemIdLabel = new Label();
		systemIdLabel.setBounds(new Rectangle(426, 66, 78, 23));
		systemIdLabel.setText("系统标识*：");
		descriptionLabel = new Label();
		descriptionLabel.setBounds(new Rectangle(17, 37, 375, 23));
		descriptionLabel.setText("带（*）号为必须填写，流水号点击生成可以按时间自动生成流水号");
		streamingNoLabel = new Label();
		streamingNoLabel.setBounds(new Rectangle(101, 72, 65, 23));
		streamingNoLabel.setText("流水号*：");
		this.setLayout(null);
		this.setSize(715, 748);
		this.setTitle("纯增值捆绑销售品同步");
		this.add(streamingNoLabel, null);
		this.add(descriptionLabel, null);
		this.add(getStreamingNoTextField(), null);
		this.add(getNewStreamingNoButton(), null);
		this.add(systemIdLabel, null);
		this.add(getSystemIdChoice(), null);
		this.add(offerIdLabel, null);
		this.add(getOfferIdTextField(), null);
		this.add(opTypeLabel, null);
		this.add(getOpTypeChoice(), null);
		this.add(nameCNLabel, null);
		this.add(getNameCNTextField(), null);
		this.add(nameENLabel, null);
		this.add(getNameENTextField(), null);
		this.add(descriptionCNLabel, null);
		this.add(getDescriptionCNTextArea(), null);
		this.add(descriptionENLabel, null);
		this.add(getDescriptionENTextArea(), null);
		this.add(stateLabel, null);
		this.add(getStateChoice(), null);
		this.add(payDescriptionLabel, null);
		this.add(getPayDescriptionTextArea(), null);
		this.add(payIdLabel, null);
		this.add(getPayIdTextField(), null);
		this.add(subOptionLabel, null);
		this.add(getSubOptionChoice(), null);
		this.add(presentLabel, null);
		this.add(getPresentChoice(), null);
		this.add(corpOnlyLabel, null);
		this.add(getCorpOnlyChoice(), null);
		this.add(productIDLabel, null);
		this.add(getProductIDJScrollPane(), null);
		this.add(getNewProductIDButton(), null);
		this.add(getDeleteProductIDButton(), null);
		this.add(scopeLabel, null);
		this.add(getScopeChoice(), null);
		this.add(prodOfferRelationLabel, null);
		this.add(getProdOfferRelationJScrollPane(), null);
		this.add(getNewProdOfferRelationButton(), null);
		this.add(getDeleteProdOfferRelationButton(), null);
		this.add(getOkButton(), null);
		this.add(getCancelButton(), null);
		this.add(getResetButton(), null);
		this.add(getNewProdOfferRelationtextField(), null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
				e.getWindow().show(false);
			}
		});
	}

	/**
	 * This method initializes streamingNoTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getStreamingNoTextField() {
		if (streamingNoTextField == null) {
			streamingNoTextField = new TextField();
			streamingNoTextField.setBounds(new Rectangle(168, 69, 121, 23));
		}
		return streamingNoTextField;
	}

	/**
	 * This method initializes newStreamingNoButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getNewStreamingNoButton() {
		if (newStreamingNoButton == null) {
			newStreamingNoButton = new Button();
			newStreamingNoButton.setBounds(new Rectangle(297, 70, 52, 23));
			newStreamingNoButton.setLabel("生成");
			newStreamingNoButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					streamingNoTextField.setText(DateUtil.getDateStr());
				}
			});
		}
		return newStreamingNoButton;
	}

	/**
	 * This method initializes systemIdChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getSystemIdChoice() {
		if (systemIdChoice == null) {
			systemIdChoice = new Choice();
			systemIdChoice.setBounds(new Rectangle(513, 67, 123, 21));
			systemIdChoice.addItem("");
			systemIdChoice.addItem("");
			systemIdChoice.addItem("");
			systemIdChoice.addItem("");
			systemIdChoice.addItem("");
			systemIdChoice.addItem("");

		}
		return systemIdChoice;
	}

	/**
	 * This method initializes offerIdTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getOfferIdTextField() {
		if (offerIdTextField == null) {
			offerIdTextField = new TextField();
			offerIdTextField.setBounds(new Rectangle(168, 111, 183, 23));
		}
		return offerIdTextField;
	}

	/**
	 * This method initializes opTypeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getOpTypeChoice() {
		if (opTypeChoice == null) {
			opTypeChoice = new Choice();
			opTypeChoice.setBounds(new Rectangle(513, 113, 125, 21));
			opTypeChoice.addItem("增加");
			opTypeChoice.addItem("修改");
			opTypeChoice.addItem("删除");
		}
		return opTypeChoice;
	}

	/**
	 * This method initializes nameCNTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getNameCNTextField() {
		if (nameCNTextField == null) {
			nameCNTextField = new TextField();
			nameCNTextField.setBounds(new Rectangle(168, 151, 182, 23));
		}
		return nameCNTextField;
	}

	/**
	 * This method initializes nameENTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getNameENTextField() {
		if (nameENTextField == null) {
			nameENTextField = new TextField();
			nameENTextField.setBounds(new Rectangle(513, 153, 162, 23));
		}
		return nameENTextField;
	}

	/**
	 * This method initializes descriptionCNTextArea	
	 * 	
	 * @return java.awt.TextArea	
	 */
	private TextArea getDescriptionCNTextArea() {
		if (descriptionCNTextArea == null) {
			descriptionCNTextArea = new TextArea();
			descriptionCNTextArea.setBounds(new Rectangle(168, 192, 508, 49));
		}
		return descriptionCNTextArea;
	}

	/**
	 * This method initializes descriptionENTextArea	
	 * 	
	 * @return java.awt.TextArea	
	 */
	private TextArea getDescriptionENTextArea() {
		if (descriptionENTextArea == null) {
			descriptionENTextArea = new TextArea();
			descriptionENTextArea.setBounds(new Rectangle(166, 258, 508, 49));
		}
		return descriptionENTextArea;
	}

	/**
	 * This method initializes stateChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getStateChoice() {
		if (stateChoice == null) {
			stateChoice = new Choice();
			stateChoice.setBounds(new Rectangle(165, 319, 142, 21));
			stateChoice.addItem("正常");
			stateChoice.addItem("申请");
			stateChoice.addItem("暂停");
			stateChoice.addItem("注销");
			stateChoice.addItem("预注销");
			stateChoice.addItem("注销");
		}
		return stateChoice;
	}

	/**
	 * This method initializes payDescriptionTextArea	
	 * 	
	 * @return java.awt.TextArea	
	 */
	private TextArea getPayDescriptionTextArea() {
		if (payDescriptionTextArea == null) {
			payDescriptionTextArea = new TextArea();
			payDescriptionTextArea.setBounds(new Rectangle(165, 355, 509, 50));
		}
		return payDescriptionTextArea;
	}

	/**
	 * This method initializes payIdTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getPayIdTextField() {
		if (payIdTextField == null) {
			payIdTextField = new TextField();
			payIdTextField.setBounds(new Rectangle(165, 422, 146, 23));
		}
		return payIdTextField;
	}

	/**
	 * This method initializes subOptionChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getSubOptionChoice() {
		if (subOptionChoice == null) {
			subOptionChoice = new Choice();
			subOptionChoice.setBounds(new Rectangle(510, 423, 125, 21));
			subOptionChoice.addItem("需要订购");
			subOptionChoice.addItem("无需订购");
		}
		return subOptionChoice;
	}

	/**
	 * This method initializes presentChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getPresentChoice() {
		if (presentChoice == null) {
			presentChoice = new Choice();
			presentChoice.setBounds(new Rectangle(164, 462, 137, 21));
			presentChoice.addItem("是");
			presentChoice.addItem("否");
		}
		return presentChoice;
	}

	/**
	 * This method initializes corpOnlyChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getCorpOnlyChoice() {
		if (corpOnlyChoice == null) {
			corpOnlyChoice = new Choice();
			corpOnlyChoice.setBounds(new Rectangle(510, 462, 125, 21));
			corpOnlyChoice.addItem("个人用户专用");
			corpOnlyChoice.addItem("集团专用");
			corpOnlyChoice.addItem("个人和集团都用");

		}
		return corpOnlyChoice;
	}

	/**
	 * This method initializes productIDJScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getProductIDJScrollPane() {
		if (productIDJScrollPane == null) {
			productIDJScrollPane = new JScrollPane();
			productIDJScrollPane.setBounds(new Rectangle(166, 498, 252, 66));
			productIDJScrollPane.setViewportView(getProductIDJTable());
		}
		return productIDJScrollPane;
	}

	/**
	 * This method initializes productIDJTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getProductIDJTable() {
		if (productIDJTable == null) {
			productIDJTable = new JTable();
			DefaultTableModel model = (DefaultTableModel) productIDJTable.getModel();
			model.addColumn("增值销售品ID列表");
		}
		return productIDJTable;
	}

	/**
	 * This method initializes newProductIDButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getNewProductIDButton() {
		if (newProductIDButton == null) {
			newProductIDButton = new Button();
			newProductIDButton.setBounds(new Rectangle(434, 502, 123, 23));
			newProductIDButton.setLabel("新增增值品ID");
		}
		return newProductIDButton;
	}

	/**
	 * This method initializes deleteProductIDButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getDeleteProductIDButton() {
		if (deleteProductIDButton == null) {
			deleteProductIDButton = new Button();
			deleteProductIDButton.setBounds(new Rectangle(434, 531, 124, 23));
			deleteProductIDButton.setLabel("删除增值品ID");
		}
		return deleteProductIDButton;
	}

	/**
	 * This method initializes scopeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getScopeChoice() {
		if (scopeChoice == null) {
			scopeChoice = new Choice();
			scopeChoice.setBounds(new Rectangle(166, 578, 126, 21));
		}
		return scopeChoice;
	}

	/**
	 * This method initializes prodOfferRelationJScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getProdOfferRelationJScrollPane() {
		if (prodOfferRelationJScrollPane == null) {
			prodOfferRelationJScrollPane = new JScrollPane();
			prodOfferRelationJScrollPane.setBounds(new Rectangle(167, 611, 412, 84));
			prodOfferRelationJScrollPane.setViewportView(getProdOfferRelationJTable());
		}
		return prodOfferRelationJScrollPane;
	}

	/**
	 * This method initializes prodOfferRelationJTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getProdOfferRelationJTable() {
		if (prodOfferRelationJTable == null) {
			prodOfferRelationJTable = new JTable();
			DefaultTableModel model = (DefaultTableModel) prodOfferRelationJTable.getModel();
			model.addColumn("关联销售品ID");
			model.addColumn("动作");
			model.addColumn("关系类型");
		}
		return prodOfferRelationJTable;
	}

	/**
	 * This method initializes newProdOfferRelationButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getNewProdOfferRelationButton() {
		if (newProdOfferRelationButton == null) {
			newProdOfferRelationButton = new Button();
			newProdOfferRelationButton.setBounds(new Rectangle(598, 612, 107, 23));
			newProdOfferRelationButton.setLabel("新增销售品");
		}
		return newProdOfferRelationButton;
	}

	/**
	 * This method initializes deleteProdOfferRelationButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getDeleteProdOfferRelationButton() {
		if (deleteProdOfferRelationButton == null) {
			deleteProdOfferRelationButton = new Button();
			deleteProdOfferRelationButton.setBounds(new Rectangle(597, 646, 105, 23));
			deleteProdOfferRelationButton.setLabel("删除销售品");
		}
		return deleteProdOfferRelationButton;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getOkButton() {
		if (okButton == null) {
			okButton = new Button();
			okButton.setBounds(new Rectangle(180, 711, 115, 23));
			okButton.setLabel("确定");
			okButton.addActionListener(new Btn_OK_ActionListen(this));
		}
		return okButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new Button();
			cancelButton.setBounds(new Rectangle(493, 710, 101, 23));
			cancelButton.setLabel("取消");
			cancelButton.addActionListener(new Btn_OK_ActionListen(this));
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
			resetButton.setBounds(new Rectangle(342, 711, 103, 23));
			resetButton.setLabel("重置");
		}
		return resetButton;
	}

	/**
	 * This method initializes newProdOfferRelationtextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getNewProdOfferRelationtextField() {
		if (newProdOfferRelationtextField == null) {
			newProdOfferRelationtextField = new TextField();
			newProdOfferRelationtextField.setBounds(new Rectangle(566, 503, 131, 23));
			newProdOfferRelationtextField.setText("请填写销售品ID");
		}
		return newProdOfferRelationtextField;
	}

	public void do_btn_OK() {
		this.show(false);
	}

	//点击确定后  进行组装报文操作
	public class Btn_OK_ActionListen implements ActionListener {
		private BindOfferInfoSyn spcpphase;

		public Btn_OK_ActionListen(BindOfferInfoSyn spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.do_btn_OK();
		}

	}

	public class Btn_Cancel_ActionListen implements ActionListener {
		private BindOfferInfoSyn spcpphase;

		public Btn_Cancel_ActionListen(BindOfferInfoSyn spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.show(false);
		}

	}
} //  @jve:decl-index=0:visual-constraint="12,7"
