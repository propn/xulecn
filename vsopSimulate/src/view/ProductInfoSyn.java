package view;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.DateUtil;

/**
 * <pre>
 * Title:程序的中文名称
 * Description: 程序功能的描述 增值产品同步 X平台
 * </pre>
 * @author caozj 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ProductInfoSyn extends Dialog {

	private static final long serialVersionUID = 1L;
	private Label streamingNoLabel = null;
	private Label assertLabel = null;
	private TextField streamNoTextField = null;
	private Button selfButton = null;
	private Label systemIdLabel = null;
	private Choice syatemIdChoice = null;
	private Label spIdLabel = null;
	private TextField spIdTextField = null;
	private Label operationActLabel = null;
	private Choice operationActChoice = null;
	private Label productTypeLabel = null;
	private Choice productTypeChoice = null;
	private Label productIdLabel = null;
	private TextField productIdTextField = null;
	private Label nameCNLabel = null;
	private TextField nameCNTextField = null;
	private Label nameENLabel = null;
	private TextField nameENTextField = null;
	private Label descriptionCNLabel = null;
	private TextField descriptionCNTextField = null;
	private Label descriptionENLabel = null;
	private TextField descriptionENTextField = null;
	private Label stateLabel = null;
	private Choice stateChoice = null;
	private Label scopeLabel = null;
	private TextField scopeTextField = null;
	private Label productHostLabel = null;
	private Choice productHostChoice = null;
	private JScrollPane serviceCapabilityJScrollPane = null;
	private JTable serviceCapabilityJTable = null;
	private Label serviceCapabilityLabel = null;
	private Choice addServiceCapabilityChoice = null;
	private Button addServiceCapabilityButton = null;
	private Button cancelButton = null;
	private JScrollPane faltListJScrollPane = null;
	private JTable faltListJTable = null;
	private Label faltListLabel = null;
	private Choice faltListChoice = null;
	private Button addFaltListButton = null;
	private Button reduceFaltListButton = null;
	private JScrollPane productListjScrollPane = null;
	private JTable productListjTable = null;
	private Label productListLabel = null;
	private Button addProductListButton = null;
	private Button deleteProductButton = null;
	private Button submitButton = null;
	private Button resetButton = null;
	private Button cancelbutton = null;
	private Label opTypeLabel = null;
	private Choice opTypeChoice = null;
	private Label relationTypeLabel = null;
	private Choice relationTypeChoice = null;
	private Label rProductIDLabel = null;
	private TextField rProductIDTextField = null;

	/**
	 * @param owner
	 */
	public ProductInfoSyn(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		rProductIDLabel = new Label();
		rProductIDLabel.setBounds(new Rectangle(387, 616, 75, 23));
		rProductIDLabel.setText("关联产品ID：");
		relationTypeLabel = new Label();
		relationTypeLabel.setBounds(new Rectangle(191, 614, 73, 23));
		relationTypeLabel.setText("关系类型：");
		opTypeLabel = new Label();
		opTypeLabel.setBounds(new Rectangle(17, 615, 46, 23));
		opTypeLabel.setText("动作：");
		productListLabel = new Label();
		productListLabel.setBounds(new Rectangle(17, 493, 194, 23));
		productListLabel.setText("增值产品列表：");
		faltListLabel = new Label();
		faltListLabel.setBounds(new Rectangle(15, 373, 131, 23));
		faltListLabel.setText("平台列表：");
		serviceCapabilityLabel = new Label();
		serviceCapabilityLabel.setBounds(new Rectangle(15, 255, 124, 23));
		serviceCapabilityLabel.setText("业务能力列表：");
		productHostLabel = new Label();
		productHostLabel.setBounds(new Rectangle(6, 230, 105, 23));
		productHostLabel.setText("增值产品属主：");
		scopeLabel = new Label();
		scopeLabel.setBounds(new Rectangle(525, 191, 86, 23));
		scopeLabel.setText("业务适用范围：");
		stateLabel = new Label();
		stateLabel.setBounds(new Rectangle(286, 192, 100, 23));
		stateLabel.setText("产品状态*：");
		descriptionENLabel = new Label();
		descriptionENLabel.setBounds(new Rectangle(6, 191, 70, 23));
		descriptionENLabel.setText("英文描述：");
		descriptionCNLabel = new Label();
		descriptionCNLabel.setBounds(new Rectangle(536, 150, 70, 23));
		descriptionCNLabel.setText("中文描述：");
		nameENLabel = new Label();
		nameENLabel.setBounds(new Rectangle(287, 148, 100, 23));
		nameENLabel.setText("英文名：");
		nameCNLabel = new Label();
		nameCNLabel.setBounds(new Rectangle(7, 151, 68, 23));
		nameCNLabel.setText("中文名*：");
		productIdLabel = new Label();
		productIdLabel.setBounds(new Rectangle(537, 109, 68, 23));
		productIdLabel.setText("产品ID*：");
		productTypeLabel = new Label();
		productTypeLabel.setBounds(new Rectangle(289, 106, 99, 23));
		productTypeLabel.setText("产品类型*：");
		operationActLabel = new Label();
		operationActLabel.setBounds(new Rectangle(8, 110, 68, 23));
		operationActLabel.setText("业务动作*：");
		spIdLabel = new Label();
		spIdLabel.setBounds(new Rectangle(538, 68, 63, 23));
		spIdLabel.setText("SP编号*：");
		systemIdLabel = new Label();
		systemIdLabel.setBounds(new Rectangle(290, 69, 97, 23));
		systemIdLabel.setText("发起系统标识*：");
		assertLabel = new Label();
		assertLabel.setBounds(new Rectangle(10, 39, 313, 23));
		assertLabel.setText("流水号点击字动生成");
		streamingNoLabel = new Label();
		streamingNoLabel.setBounds(new Rectangle(9, 69, 60, 23));
		streamingNoLabel.setText("流水号*：");
		this.setLayout(null);
		this.setSize(781, 707);
		this.setTitle("增值产品同步（X平台）模拟器");
		this.add(streamingNoLabel, null);
		this.add(assertLabel, null);
		this.add(getStreamNoTextField(), null);
		this.add(getSelfButton(), null);
		this.add(systemIdLabel, null);
		this.add(getSyatemIdChoice(), null);
		this.add(spIdLabel, null);
		this.add(getSpIdTextField(), null);
		this.add(operationActLabel, null);
		this.add(getOperationActChoice(), null);
		this.add(productTypeLabel, null);
		this.add(getProductTypeChoice(), null);
		this.add(productIdLabel, null);
		this.add(getProductIdTextField(), null);
		this.add(nameCNLabel, null);
		this.add(getNameCNTextField(), null);
		this.add(nameENLabel, null);
		this.add(getNameENTextField(), null);
		this.add(descriptionCNLabel, null);
		this.add(getDescriptionCNTextField(), null);
		this.add(descriptionENLabel, null);
		this.add(getDescriptionENTextField(), null);
		this.add(stateLabel, null);
		this.add(getStateChoice(), null);
		this.add(scopeLabel, null);
		this.add(getScopeTextField(), null);
		this.add(productHostLabel, null);
		this.add(getProductHostChoice(), null);
		this.add(getServiceCapabilityJScrollPane(), null);
		this.add(serviceCapabilityLabel, null);
		this.add(getAddServiceCapabilityChoice(), null);
		this.add(getAddServiceCapabilityButton(), null);
		this.add(getCancelButton(), null);
		this.add(getFaltListJScrollPane(), null);
		this.add(faltListLabel, null);
		this.add(getFaltListChoice(), null);
		this.add(getAddFaltListButton(), null);
		this.add(getReduceFaltListButton(), null);
		this.add(getProductListjScrollPane(), null);
		this.add(productListLabel, null);
		this.add(getAddProductListButton(), null);
		this.add(getDeleteProductButton(), null);
		this.add(getSubmitButton(), null);
		this.add(getResetButton(), null);
		this.add(getCancelbutton(), null);
		this.add(opTypeLabel, null);
		this.add(getOpTypeChoice(), null);
		this.add(relationTypeLabel, null);
		this.add(getRelationTypeChoice(), null);
		this.add(rProductIDLabel, null);
		this.add(getRProductIDTextField(), null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
				e.getWindow().show(false);
			}
		});
	
		this.setModal(true);
	}

	/**
	 * This method initializes streamNoTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getStreamNoTextField() {
		if (streamNoTextField == null) {
			streamNoTextField = new TextField();
			streamNoTextField.setBounds(new Rectangle(77, 69, 158, 23));
		}
		return streamNoTextField;
	}

	/**
	 * This method initializes selfButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getSelfButton() {
		if (selfButton == null) {
			selfButton = new Button();
			selfButton.setBounds(new Rectangle(242, 69, 41, 23));
			selfButton.setLabel("生成");
			selfButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					streamNoTextField.setText(DateUtil.getDateStr());
				}
			});
		}
		return selfButton;
	}

	/**
	 * This method initializes syatemIdChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getSyatemIdChoice() {
		if (syatemIdChoice == null) {
			syatemIdChoice = new Choice();
			syatemIdChoice.setBounds(new Rectangle(399, 69, 113, 21));
			syatemIdChoice.addItem("集团VSOP系统");
			syatemIdChoice.addItem("集团CRM系统");
			syatemIdChoice.addItem("集团PPM系统");
			syatemIdChoice.addItem("集团ISMP_B系统");
			syatemIdChoice.addItem("NMSC（集团ISMP_M）系统");
			syatemIdChoice.addItem("集团ISMP_W系统");
			syatemIdChoice.addItem("省级VSOP");
			syatemIdChoice.addItem("省级CRM系统");
			syatemIdChoice.addItem("省级PPM系统");
			syatemIdChoice.addItem("ISMP_B系统");
			syatemIdChoice.addItem("ISMP_M系统");
			syatemIdChoice.addItem("ISMP_W系统");
			syatemIdChoice.addItem("服务开通系统(PF)");
			syatemIdChoice.addItem("综合激活系统(ISPP)");
			syatemIdChoice.addItem("OCS系统");
			syatemIdChoice.addItem("计费系统(HB)");
			syatemIdChoice.addItem("ODS系统");
			syatemIdChoice.addItem("网厅系统");
			syatemIdChoice.addItem("10000号座席");
			syatemIdChoice.addItem("10000号语音");
			syatemIdChoice.addItem("短信营业厅");
			syatemIdChoice.addItem("WAP营业厅");
		}
		return syatemIdChoice;
	}

	/**
	 * This method initializes spIdTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getSpIdTextField() {
		if (spIdTextField == null) {
			spIdTextField = new TextField();
			spIdTextField.setBounds(new Rectangle(612, 68, 157, 23));
		}
		return spIdTextField;
	}

	/**
	 * This method initializes operationActChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getOperationActChoice() {
		if (operationActChoice == null) {
			operationActChoice = new Choice();
			operationActChoice.setBounds(new Rectangle(78, 111, 93, 21));
			operationActChoice.addItem("增加");
			operationActChoice.addItem("修改");
			operationActChoice.addItem("删除(注销)");
		}
		return operationActChoice;
	}

	/**
	 * This method initializes productTypeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getProductTypeChoice() {
		if (productTypeChoice == null) {
			productTypeChoice = new Choice();
			productTypeChoice.setBounds(new Rectangle(400, 110, 114, 21));
			productTypeChoice.addItem("接入产品");
			productTypeChoice.addItem("增值附属产品");
			productTypeChoice.addItem("能力附属产品");
		}
		return productTypeChoice;
	}

	/**
	 * This method initializes productIdTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getProductIdTextField() {
		if (productIdTextField == null) {
			productIdTextField = new TextField();
			productIdTextField.setBounds(new Rectangle(611, 108, 159, 23));
		}
		return productIdTextField;
	}

	/**
	 * This method initializes nameCNTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getNameCNTextField() {
		if (nameCNTextField == null) {
			nameCNTextField = new TextField();
			nameCNTextField.setBounds(new Rectangle(77, 151, 161, 23));
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
			nameENTextField.setBounds(new Rectangle(400, 151, 130, 23));
		}
		return nameENTextField;
	}

	/**
	 * This method initializes descriptionCNTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getDescriptionCNTextField() {
		if (descriptionCNTextField == null) {
			descriptionCNTextField = new TextField();
			descriptionCNTextField.setBounds(new Rectangle(612, 149, 158, 23));
		}
		return descriptionCNTextField;
	}

	/**
	 * This method initializes descriptionENTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getDescriptionENTextField() {
		if (descriptionENTextField == null) {
			descriptionENTextField = new TextField();
			descriptionENTextField.setBounds(new Rectangle(78, 191, 160, 23));
		}
		return descriptionENTextField;
	}

	/**
	 * This method initializes stateChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getStateChoice() {
		if (stateChoice == null) {
			stateChoice = new Choice();
			stateChoice.setBounds(new Rectangle(399, 193, 113, 21));
			stateChoice.addItem("正常");
			stateChoice.addItem("申请");
			stateChoice.addItem("暂停");
			stateChoice.addItem("预注销");
			stateChoice.addItem("注销");
			stateChoice.addItem("测试");
		}
		return stateChoice;
	}

	/**
	 * This method initializes scopeTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getScopeTextField() {
		if (scopeTextField == null) {
			scopeTextField = new TextField();
			scopeTextField.setBounds(new Rectangle(613, 191, 158, 23));
		}
		return scopeTextField;
	}

	/**
	 * This method initializes productHostChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getProductHostChoice() {
		if (productHostChoice == null) {
			productHostChoice = new Choice();
			productHostChoice.setBounds(new Rectangle(123, 233, 187, 21));
			productHostChoice.addItem("省级VSOP");
			productHostChoice.addItem("ISMP_B系统");
			productHostChoice.addItem("ISMP_M系统");
			productHostChoice.addItem("ISMP_W系统");
		}
		return productHostChoice;
	}

	/**
	 * This method initializes serviceCapabilityJScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getServiceCapabilityJScrollPane() {
		if (serviceCapabilityJScrollPane == null) {
			serviceCapabilityJScrollPane = new JScrollPane();
			serviceCapabilityJScrollPane.setBounds(new Rectangle(15, 281, 453, 81));
			serviceCapabilityJScrollPane.setViewportView(getServiceCapabilityJTable());
		}
		return serviceCapabilityJScrollPane;
	}

	/**
	 * This method initializes serviceCapabilityJTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getServiceCapabilityJTable() {
		if (serviceCapabilityJTable == null) {
			serviceCapabilityJTable = new JTable();
			DefaultTableModel model = (DefaultTableModel) serviceCapabilityJTable.getModel();
			model.addColumn("业务能力ID(必填)");
		}
		return serviceCapabilityJTable;
	}

	/**
	 * This method initializes addServiceCapabilityChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getAddServiceCapabilityChoice() {
		if (addServiceCapabilityChoice == null) {
			addServiceCapabilityChoice = new Choice();
			addServiceCapabilityChoice.setBounds(new Rectangle(484, 280, 119, 21));
			addServiceCapabilityChoice.addItem("SMS");
			addServiceCapabilityChoice.addItem("MMS");
			addServiceCapabilityChoice.addItem("LCS");
			addServiceCapabilityChoice.addItem("Download");
			addServiceCapabilityChoice.addItem("WAP");
			addServiceCapabilityChoice.addItem("Streaming");
			addServiceCapabilityChoice.addItem("IM");
			addServiceCapabilityChoice.addItem("CRBT");
			addServiceCapabilityChoice.addItem("USSD");
			addServiceCapabilityChoice.addItem("PushEmail");
			addServiceCapabilityChoice.addItem("PoC");
			addServiceCapabilityChoice.addItem("MPSS");

		}
		return addServiceCapabilityChoice;
	}

	/**
	 * This method initializes addServiceCapabilityButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getAddServiceCapabilityButton() {
		if (addServiceCapabilityButton == null) {
			addServiceCapabilityButton = new Button();
			addServiceCapabilityButton.setBounds(new Rectangle(617, 279, 109, 23));
			addServiceCapabilityButton.setLabel("新增业务能力");
			addServiceCapabilityButton.addActionListener(new Btn_Addability_ActionListen(this));
		}
		return addServiceCapabilityButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new Button();
			cancelButton.setBounds(new Rectangle(618, 320, 109, 23));
			cancelButton.setLabel("删除");
			cancelButton.addActionListener(new Btn_Deleteability_ActionListen(this));
		}
		return cancelButton;
	}

	/**
	 * This method initializes faltListJScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getFaltListJScrollPane() {
		if (faltListJScrollPane == null) {
			faltListJScrollPane = new JScrollPane();
			faltListJScrollPane.setBounds(new Rectangle(16, 408, 453, 78));
			faltListJScrollPane.setViewportView(getFaltListJTable());
		}
		return faltListJScrollPane;
	}

	/**
	 * This method initializes faltListJTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getFaltListJTable() {
		if (faltListJTable == null) {
			faltListJTable = new JTable();
			DefaultTableModel model = (DefaultTableModel) faltListJTable.getModel();
			model.addColumn("平台列表(必填)");
		}
		return faltListJTable;
	}

	/**
	 * This method initializes faltListChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getFaltListChoice() {
		if (faltListChoice == null) {
			faltListChoice = new Choice();
			faltListChoice.setBounds(new Rectangle(485, 410, 121, 21));
			faltListChoice.addItem("集团VSOP系统");
			faltListChoice.addItem("集团CRM系统");
			faltListChoice.addItem("集团PPM系统");
			faltListChoice.addItem("集团ISMP_B系统");
			faltListChoice.addItem("NMSC（集团ISMP_M）系统");
			faltListChoice.addItem("集团ISMP_W系统");
			faltListChoice.addItem("省级VSOP");
			faltListChoice.addItem("省级CRM系统");
			faltListChoice.addItem("省级PPM系统");
			faltListChoice.addItem("ISMP_B系统");
			faltListChoice.addItem("ISMP_M系统");
			faltListChoice.addItem("ISMP_W系统");
			faltListChoice.addItem("服务开通系统(PF)");
			faltListChoice.addItem("综合激活系统(ISPP)");
			faltListChoice.addItem("OCS系统");
			faltListChoice.addItem("计费系统(HB)");
			faltListChoice.addItem("ODS系统");
			faltListChoice.addItem("网厅系统");
			faltListChoice.addItem("10000号座席");
			faltListChoice.addItem("10000号语音");
			faltListChoice.addItem("短信营业厅");
			faltListChoice.addItem("WAP营业厅");
		}
		return faltListChoice;
	}

	/**
	 * This method initializes addFaltListButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getAddFaltListButton() {
		if (addFaltListButton == null) {
			addFaltListButton = new Button();
			addFaltListButton.setBounds(new Rectangle(620, 407, 107, 23));
			addFaltListButton.setLabel("新增平台");
			addFaltListButton.addActionListener(new Btn_AddFalt_ActionListen(this));
		}
		return addFaltListButton;
	}

	/**
	 * This method initializes reduceFaltListButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getReduceFaltListButton() {
		if (reduceFaltListButton == null) {
			reduceFaltListButton = new Button();
			reduceFaltListButton.setBounds(new Rectangle(622, 451, 105, 23));
			reduceFaltListButton.setLabel("删除");
			reduceFaltListButton.addActionListener(new Btn_DeleteFalt_ActionListen(this));
		}
		return reduceFaltListButton;
	}

	/**
	 * This method initializes productListjScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getProductListjScrollPane() {
		if (productListjScrollPane == null) {
			productListjScrollPane = new JScrollPane();
			productListjScrollPane.setBounds(new Rectangle(16, 519, 576, 87));
			productListjScrollPane.setViewportView(getProductListjTable());
		}
		return productListjScrollPane;
	}

	/**
	 * This method initializes productListjTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getProductListjTable() {
		if (productListjTable == null) {
			productListjTable = new JTable();
			DefaultTableModel model = (DefaultTableModel) productListjTable.getModel();
			model.addColumn("动作(必填)");
			model.addColumn("关系类型(必填)");
			model.addColumn("关联产品ID(必填)");
		}
		return productListjTable;
	}

	/**
	 * This method initializes addProductListButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getAddProductListButton() {
		if (addProductListButton == null) {
			addProductListButton = new Button();
			addProductListButton.setBounds(new Rectangle(603, 615, 117, 23));
			addProductListButton.setLabel("新增");
			addProductListButton.addActionListener(new Btn_AddProduct_ActionListen(this));
		}
		return addProductListButton;
	}

	/**
	 * This method initializes deleteProductButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getDeleteProductButton() {
		if (deleteProductButton == null) {
			deleteProductButton = new Button();
			deleteProductButton.setBounds(new Rectangle(603, 565, 117, 23));
			deleteProductButton.setLabel("删除");
			deleteProductButton.addActionListener(new Btn_DeleteProduct_ActionListen(this));
		}
		return deleteProductButton;
	}

	/**
	 * This method initializes submitButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getSubmitButton() {
		if (submitButton == null) {
			submitButton = new Button();
			submitButton.setBounds(new Rectangle(255, 670, 122, 23));
			submitButton.setLabel("确定");
			submitButton.addActionListener(new Btn_OK_ActionListen(this));
		}
		return submitButton;
	}

	/**
	 * This method initializes resetButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getResetButton() {
		if (resetButton == null) {
			resetButton = new Button();
			resetButton.setBounds(new Rectangle(414, 671, 119, 23));
			resetButton.setLabel("重置");
		}
		return resetButton;
	}

	/**
	 * This method initializes cancelbutton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCancelbutton() {
		if (cancelbutton == null) {
			cancelbutton = new Button();
			cancelbutton.setBounds(new Rectangle(566, 671, 123, 23));
			cancelbutton.setLabel("取消");
			cancelbutton.addActionListener(new Btn_Cancel_ActionListen(this));
		}
		return cancelbutton;
	}

	/**
	 * This method initializes opTypeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getOpTypeChoice() {
		if (opTypeChoice == null) {
			opTypeChoice = new Choice();
			opTypeChoice.setBounds(new Rectangle(70, 616, 94, 21));
			opTypeChoice.addItem("增加");
			opTypeChoice.addItem("删除");
		}
		return opTypeChoice;
	}

	/**
	 * This method initializes relationTypeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getRelationTypeChoice() {
		if (relationTypeChoice == null) {
			relationTypeChoice = new Choice();
			relationTypeChoice.setBounds(new Rectangle(271, 616, 107, 21));
			relationTypeChoice.addItem("互斥关系");
			relationTypeChoice.addItem("依赖关系");
			relationTypeChoice.addItem("替换关系");
			relationTypeChoice.addItem("主附关系");

		}
		return relationTypeChoice;
	}

	/**
	 * This method initializes rProductIDTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getRProductIDTextField() {
		if (rProductIDTextField == null) {
			rProductIDTextField = new TextField();
			rProductIDTextField.setBounds(new Rectangle(468, 616, 126, 23));
		}
		return rProductIDTextField;
	}

	public void do_btn_OK() {
		String title = "提示";
		int type = JOptionPane.PLAIN_MESSAGE;
		//首先判断必输域是否都有数据
		if ("".equals(streamNoTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "流水号不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(syatemIdChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "系统标识不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(spIdTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "SP编号不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(operationActChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "业务动作不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(productTypeChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "产品类型不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(productIdTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "产品ID不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(nameCNTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "产品中文名不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}

	}

	public class Btn_OK_ActionListen implements ActionListener {
		private ProductInfoSyn spcpphase;

		public Btn_OK_ActionListen(ProductInfoSyn spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.do_btn_OK();
		}

	}

	public class Btn_Cancel_ActionListen implements ActionListener {
		private ProductInfoSyn spcpphase;

		public Btn_Cancel_ActionListen(ProductInfoSyn spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.show(false);
		}

	}

	/**
	 * 新增业务能力动作监听类
	 * @author caozj
	 *
	 */
	public class Btn_Addability_ActionListen implements ActionListener {
		private ProductInfoSyn spcpphase;

		public Btn_Addability_ActionListen(ProductInfoSyn spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.addAbility();
		}

	}

	//增加业务能力方法
	public void addAbility() {
		String abilityStr = addServiceCapabilityChoice.getSelectedItem();
		DefaultTableModel model = (DefaultTableModel) serviceCapabilityJTable.getModel();

		model.addRow(new String[] { abilityStr });
	}

	/**
	 * //删除业务能力动作监听类
	 * @author caozj
	 *
	 */
	public class Btn_Deleteability_ActionListen implements ActionListener {
		private ProductInfoSyn spcpphase;

		public Btn_Deleteability_ActionListen(ProductInfoSyn spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.deleteAbility();
		}

	}

	/**
	 * 删除业务动作
	 *
	 */
	public void deleteAbility() {

		int selectColum = serviceCapabilityJTable.getSelectedRow();
		if (selectColum == -1) {
			int type = JOptionPane.WARNING_MESSAGE;
			String message = "请选择需要删除的业务能力";
			JOptionPane.showMessageDialog(this, message, "提示", type);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) serviceCapabilityJTable.getModel();
		model.removeRow(selectColum);
	}

	/**
	 * 新增平台
	 * @author Administrator
	 *
	 */
	public class Btn_AddFalt_ActionListen implements ActionListener {
		private ProductInfoSyn spcpphase;

		public Btn_AddFalt_ActionListen(ProductInfoSyn spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.addFalt();
		}

	}

	public void addFalt() {
		String faltStr = faltListChoice.getSelectedItem();

		DefaultTableModel model = (DefaultTableModel) faltListJTable.getModel();

		model.addRow(new String[] { faltStr });
	}

	/**
	 * 删除平台
	 * @author Administrator
	 *
	 */
	public class Btn_DeleteFalt_ActionListen implements ActionListener {
		private ProductInfoSyn spcpphase;

		public Btn_DeleteFalt_ActionListen(ProductInfoSyn spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.deleteFalt();
		}

	}

	public void deleteFalt() {
		int selectRow = faltListJTable.getSelectedRow();
		if (selectRow == -1) {
			int type = JOptionPane.WARNING_MESSAGE;
			String message = "请选择需要删除的平台";
			JOptionPane.showMessageDialog(this, message, "提示", type);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) faltListJTable.getModel();
		model.removeRow(selectRow);
	}

	/**
	 * 增加产品
	 * @author Administrator
	 *
	 */
	public class Btn_AddProduct_ActionListen implements ActionListener {
		private ProductInfoSyn spcpphase;

		public Btn_AddProduct_ActionListen(ProductInfoSyn spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.addProduct();
		}

	}

	public void addProduct() {
		if (rProductIDTextField.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "产品ID不能为空", "提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		String opType = opTypeChoice.getSelectedItem();
		String relationType = relationTypeChoice.getSelectedItem();
		String productID = rProductIDTextField.getText();
		DefaultTableModel model = (DefaultTableModel) productListjTable.getModel();

		model.addRow(new String[] { opType, relationType, productID });
	}

	public class Btn_DeleteProduct_ActionListen implements ActionListener {
		private ProductInfoSyn spcpphase;

		public Btn_DeleteProduct_ActionListen(ProductInfoSyn spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.deleteProduct();
		}

	}

	public void deleteProduct() {
		int selectRow = productListjTable.getSelectedRow();
		if (selectRow == -1) {
			int type = JOptionPane.WARNING_MESSAGE;
			String message = "请选择需要删除的产品";
			JOptionPane.showMessageDialog(this, message, "提示", type);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) productListjTable.getModel();
		model.removeRow(selectRow);
	}
} //  @jve:decl-index=0:visual-constraint="29,-1"
