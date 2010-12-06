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
 * Title:�������������
 * Description: �����ܵ����� ��ֵ��Ʒͬ�� Xƽ̨
 * </pre>
 * @author caozj 
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
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
		rProductIDLabel.setText("������ƷID��");
		relationTypeLabel = new Label();
		relationTypeLabel.setBounds(new Rectangle(191, 614, 73, 23));
		relationTypeLabel.setText("��ϵ���ͣ�");
		opTypeLabel = new Label();
		opTypeLabel.setBounds(new Rectangle(17, 615, 46, 23));
		opTypeLabel.setText("������");
		productListLabel = new Label();
		productListLabel.setBounds(new Rectangle(17, 493, 194, 23));
		productListLabel.setText("��ֵ��Ʒ�б�");
		faltListLabel = new Label();
		faltListLabel.setBounds(new Rectangle(15, 373, 131, 23));
		faltListLabel.setText("ƽ̨�б�");
		serviceCapabilityLabel = new Label();
		serviceCapabilityLabel.setBounds(new Rectangle(15, 255, 124, 23));
		serviceCapabilityLabel.setText("ҵ�������б�");
		productHostLabel = new Label();
		productHostLabel.setBounds(new Rectangle(6, 230, 105, 23));
		productHostLabel.setText("��ֵ��Ʒ������");
		scopeLabel = new Label();
		scopeLabel.setBounds(new Rectangle(525, 191, 86, 23));
		scopeLabel.setText("ҵ�����÷�Χ��");
		stateLabel = new Label();
		stateLabel.setBounds(new Rectangle(286, 192, 100, 23));
		stateLabel.setText("��Ʒ״̬*��");
		descriptionENLabel = new Label();
		descriptionENLabel.setBounds(new Rectangle(6, 191, 70, 23));
		descriptionENLabel.setText("Ӣ��������");
		descriptionCNLabel = new Label();
		descriptionCNLabel.setBounds(new Rectangle(536, 150, 70, 23));
		descriptionCNLabel.setText("����������");
		nameENLabel = new Label();
		nameENLabel.setBounds(new Rectangle(287, 148, 100, 23));
		nameENLabel.setText("Ӣ������");
		nameCNLabel = new Label();
		nameCNLabel.setBounds(new Rectangle(7, 151, 68, 23));
		nameCNLabel.setText("������*��");
		productIdLabel = new Label();
		productIdLabel.setBounds(new Rectangle(537, 109, 68, 23));
		productIdLabel.setText("��ƷID*��");
		productTypeLabel = new Label();
		productTypeLabel.setBounds(new Rectangle(289, 106, 99, 23));
		productTypeLabel.setText("��Ʒ����*��");
		operationActLabel = new Label();
		operationActLabel.setBounds(new Rectangle(8, 110, 68, 23));
		operationActLabel.setText("ҵ����*��");
		spIdLabel = new Label();
		spIdLabel.setBounds(new Rectangle(538, 68, 63, 23));
		spIdLabel.setText("SP���*��");
		systemIdLabel = new Label();
		systemIdLabel.setBounds(new Rectangle(290, 69, 97, 23));
		systemIdLabel.setText("����ϵͳ��ʶ*��");
		assertLabel = new Label();
		assertLabel.setBounds(new Rectangle(10, 39, 313, 23));
		assertLabel.setText("��ˮ�ŵ���ֶ�����");
		streamingNoLabel = new Label();
		streamingNoLabel.setBounds(new Rectangle(9, 69, 60, 23));
		streamingNoLabel.setText("��ˮ��*��");
		this.setLayout(null);
		this.setSize(781, 707);
		this.setTitle("��ֵ��Ʒͬ����Xƽ̨��ģ����");
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
			selfButton.setLabel("����");
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
			syatemIdChoice.addItem("����VSOPϵͳ");
			syatemIdChoice.addItem("����CRMϵͳ");
			syatemIdChoice.addItem("����PPMϵͳ");
			syatemIdChoice.addItem("����ISMP_Bϵͳ");
			syatemIdChoice.addItem("NMSC������ISMP_M��ϵͳ");
			syatemIdChoice.addItem("����ISMP_Wϵͳ");
			syatemIdChoice.addItem("ʡ��VSOP");
			syatemIdChoice.addItem("ʡ��CRMϵͳ");
			syatemIdChoice.addItem("ʡ��PPMϵͳ");
			syatemIdChoice.addItem("ISMP_Bϵͳ");
			syatemIdChoice.addItem("ISMP_Mϵͳ");
			syatemIdChoice.addItem("ISMP_Wϵͳ");
			syatemIdChoice.addItem("����ͨϵͳ(PF)");
			syatemIdChoice.addItem("�ۺϼ���ϵͳ(ISPP)");
			syatemIdChoice.addItem("OCSϵͳ");
			syatemIdChoice.addItem("�Ʒ�ϵͳ(HB)");
			syatemIdChoice.addItem("ODSϵͳ");
			syatemIdChoice.addItem("����ϵͳ");
			syatemIdChoice.addItem("10000����ϯ");
			syatemIdChoice.addItem("10000������");
			syatemIdChoice.addItem("����Ӫҵ��");
			syatemIdChoice.addItem("WAPӪҵ��");
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
			operationActChoice.addItem("����");
			operationActChoice.addItem("�޸�");
			operationActChoice.addItem("ɾ��(ע��)");
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
			productTypeChoice.addItem("�����Ʒ");
			productTypeChoice.addItem("��ֵ������Ʒ");
			productTypeChoice.addItem("����������Ʒ");
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
			stateChoice.addItem("����");
			stateChoice.addItem("����");
			stateChoice.addItem("��ͣ");
			stateChoice.addItem("Ԥע��");
			stateChoice.addItem("ע��");
			stateChoice.addItem("����");
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
			productHostChoice.addItem("ʡ��VSOP");
			productHostChoice.addItem("ISMP_Bϵͳ");
			productHostChoice.addItem("ISMP_Mϵͳ");
			productHostChoice.addItem("ISMP_Wϵͳ");
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
			model.addColumn("ҵ������ID(����)");
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
			addServiceCapabilityButton.setLabel("����ҵ������");
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
			cancelButton.setLabel("ɾ��");
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
			model.addColumn("ƽ̨�б�(����)");
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
			faltListChoice.addItem("����VSOPϵͳ");
			faltListChoice.addItem("����CRMϵͳ");
			faltListChoice.addItem("����PPMϵͳ");
			faltListChoice.addItem("����ISMP_Bϵͳ");
			faltListChoice.addItem("NMSC������ISMP_M��ϵͳ");
			faltListChoice.addItem("����ISMP_Wϵͳ");
			faltListChoice.addItem("ʡ��VSOP");
			faltListChoice.addItem("ʡ��CRMϵͳ");
			faltListChoice.addItem("ʡ��PPMϵͳ");
			faltListChoice.addItem("ISMP_Bϵͳ");
			faltListChoice.addItem("ISMP_Mϵͳ");
			faltListChoice.addItem("ISMP_Wϵͳ");
			faltListChoice.addItem("����ͨϵͳ(PF)");
			faltListChoice.addItem("�ۺϼ���ϵͳ(ISPP)");
			faltListChoice.addItem("OCSϵͳ");
			faltListChoice.addItem("�Ʒ�ϵͳ(HB)");
			faltListChoice.addItem("ODSϵͳ");
			faltListChoice.addItem("����ϵͳ");
			faltListChoice.addItem("10000����ϯ");
			faltListChoice.addItem("10000������");
			faltListChoice.addItem("����Ӫҵ��");
			faltListChoice.addItem("WAPӪҵ��");
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
			addFaltListButton.setLabel("����ƽ̨");
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
			reduceFaltListButton.setLabel("ɾ��");
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
			model.addColumn("����(����)");
			model.addColumn("��ϵ����(����)");
			model.addColumn("������ƷID(����)");
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
			addProductListButton.setLabel("����");
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
			deleteProductButton.setLabel("ɾ��");
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
			submitButton.setLabel("ȷ��");
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
			resetButton.setLabel("����");
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
			cancelbutton.setLabel("ȡ��");
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
			opTypeChoice.addItem("����");
			opTypeChoice.addItem("ɾ��");
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
			relationTypeChoice.addItem("�����ϵ");
			relationTypeChoice.addItem("������ϵ");
			relationTypeChoice.addItem("�滻��ϵ");
			relationTypeChoice.addItem("������ϵ");

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
		String title = "��ʾ";
		int type = JOptionPane.PLAIN_MESSAGE;
		//�����жϱ������Ƿ�������
		if ("".equals(streamNoTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "��ˮ�Ų���Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(syatemIdChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "ϵͳ��ʶ����Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(spIdTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "SP��Ų���Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(operationActChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "ҵ��������Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(productTypeChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "��Ʒ���Ͳ���Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(productIdTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "��ƷID����Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(nameCNTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "��Ʒ����������Ϊ�գ�";
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
	 * ����ҵ����������������
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

	//����ҵ����������
	public void addAbility() {
		String abilityStr = addServiceCapabilityChoice.getSelectedItem();
		DefaultTableModel model = (DefaultTableModel) serviceCapabilityJTable.getModel();

		model.addRow(new String[] { abilityStr });
	}

	/**
	 * //ɾ��ҵ����������������
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
	 * ɾ��ҵ����
	 *
	 */
	public void deleteAbility() {

		int selectColum = serviceCapabilityJTable.getSelectedRow();
		if (selectColum == -1) {
			int type = JOptionPane.WARNING_MESSAGE;
			String message = "��ѡ����Ҫɾ����ҵ������";
			JOptionPane.showMessageDialog(this, message, "��ʾ", type);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) serviceCapabilityJTable.getModel();
		model.removeRow(selectColum);
	}

	/**
	 * ����ƽ̨
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
	 * ɾ��ƽ̨
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
			String message = "��ѡ����Ҫɾ����ƽ̨";
			JOptionPane.showMessageDialog(this, message, "��ʾ", type);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) faltListJTable.getModel();
		model.removeRow(selectRow);
	}

	/**
	 * ���Ӳ�Ʒ
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
			JOptionPane.showMessageDialog(this, "��ƷID����Ϊ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
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
			String message = "��ѡ����Ҫɾ���Ĳ�Ʒ";
			JOptionPane.showMessageDialog(this, message, "��ʾ", type);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) productListjTable.getModel();
		model.removeRow(selectRow);
	}
} //  @jve:decl-index=0:visual-constraint="29,-1"
