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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import util.Config;
import util.DateUtil;
import util.XmlUtil;
import config.SCPInfoSynSVXMLUtil;

/**
 * <pre>
 * Title:�������������
 * Description: �����ܵ����� 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class SPCPInPhase extends Dialog {

	private static final String serviceName = "SCPInfoSynSV";

	private static final long serialVersionUID = 1L;
	private Label streamingNoLabel = null;
	private TextField streamingNoTextField = null;
	private Label SPsaveLabel = null;
	private Choice SPsaveChoice = null;
	private Label swapSystemIdLabel = null;
	private Label operateTypeLabel = null;
	private Choice operateTypeChoice = null;
	private Label spcpIdLabel = null;
	private TextField spcpIdTextField = null;
	private Label spcpTypeLabel = null;
	private Choice spcpTypeChoice = null;
	private Label stateLabel = null;
	private Choice stateChoice = null;
	private Label webURLLabel = null;
	private TextField WebSiteURLTextField = null;
	private Label nameCNLabel = null;
	private TextField nameCNTextField = null;
	private Label nameENLabel = null;
	private TextField nameENTextField = null;
	private Label payTypeLabel = null;
	private Choice payTypeChoice = null;
	private Label footPercentLabel = null;
	private TextField footPercentTextField = null;
	private Label customerCareLabel = null;
	private TextField customerCareTextField = null;
	private Label isRambleLabel = null;
	private Choice isRamberChoice = null;
	private Label companyAddressLabel = null;
	private TextField companyAddressTextField = null;
	private Label legalRepresentAtiveLabel = null;
	private TextField legalRepresentAtiveTextField = null;
	private Label princiPalLabel = null;
	private TextField princiPalTextField = null;
	private Label princiPalTelLabel = null;
	private TextField princiPalTelTextField = null;
	private Label principalEmailLabel = null;
	private TextField principalEmaiTextField = null;
	private Label licenseLabel = null;
	private TextField licenseTextField = null;
	private Label footCycleLabel = null;
	private Choice footCycleChoice = null;
	private Label pactTimeLabel = null;
	private Label accessNoLabel = null;
	private TextField accessNoTextField = null;
	private Label csWebSiteLabel = null;
	private TextField csWebSiteTextField = null;
	private Label discreptionCNLabel = null;
	private Label discreptionENLabel = null;
	private Label serverManagerFaxLabel = null;
	private TextField serverManagerFaxTextField = null;
	private Label serverManagerLabel = null;
	private TextField serverManagerTextField = null;
	private Label serverManagerTelLabel = null;
	private TextField serverManagerTelTextField = null;
	private Label serverManagerEmailLabel = null;
	private TextField serverManagerEmaiTextField = null;
	private Label serverManagerAddrLabel = null;
	private TextField serverManagerAddrTextField = null;
	private Label serverManagerPCLabel = null;
	private TextField serverManagerPCTextField = null;
	private Button submitButton = null;
	private Button resetButton = null;
	private Button cancelButton = null;
	private Button newStreamingNoButton = null;
	private DateChooserJButton pactTimeDateChooserJButton = null;
	private Choice swapSystemIdChoice = null;

	private MainFrame frame;

	private Label timeStampLabel = null;

	private TextField timeStampTextField = null;

	private Button newTimeStampButton = null;

	private TextArea descCNTextArea = null;

	private TextArea descENTextArea = null;

	private Label platFormLabel = null;

	private JScrollPane platFormJScrollPane = null;

	private JTable platFormJTable = null;

	private Choice platListChoice = null;

	private Button newPlateButton = null;

	private Button deletePlatButton = null;

	/**
	 * @param owner
	 */
	public SPCPInPhase(Frame owner) {
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
		platFormLabel = new Label();
		platFormLabel.setBounds(new Rectangle(17, 558, 71, 23));
		platFormLabel.setText("ƽ̨�б�");
		timeStampLabel = new Label();
		timeStampLabel.setBounds(new Rectangle(338, 43, 77, 23));
		timeStampLabel.setText("ʱ�����");
		serverManagerPCLabel = new Label();
		serverManagerPCLabel.setBounds(new Rectangle(331, 372, 128, 23));
		serverManagerPCLabel.setText("ҵ����ϵ���������룺");
		serverManagerAddrLabel = new Label();
		serverManagerAddrLabel.setBounds(new Rectangle(332, 334, 125, 23));
		serverManagerAddrLabel.setText("ҵ����ϵ��ͨ�ŵ�ַ��");
		serverManagerEmailLabel = new Label();
		serverManagerEmailLabel.setBounds(new Rectangle(9, 333, 120, 23));
		serverManagerEmailLabel.setText("ҵ����ϵ�����䣺");
		serverManagerTelLabel = new Label();
		serverManagerTelLabel.setBounds(new Rectangle(630, 299, 96, 23));
		serverManagerTelLabel.setText("ҵ����ϵ�˵绰��");
		serverManagerLabel = new Label();
		serverManagerLabel.setBounds(new Rectangle(335, 297, 88, 23));
		serverManagerLabel.setText("ҵ����ϵ�ˣ�");
		serverManagerFaxLabel = new Label();
		serverManagerFaxLabel.setBounds(new Rectangle(10, 371, 103, 23));
		serverManagerFaxLabel.setText("ҵ����ϵ�˴��棺");
		discreptionENLabel = new Label();
		discreptionENLabel.setBounds(new Rectangle(444, 480, 86, 23));
		discreptionENLabel.setText("��˾Ӣ��������");
		discreptionCNLabel = new Label();
		discreptionCNLabel.setBounds(new Rectangle(10, 480, 123, 23));
		discreptionCNLabel.setText("��˾����������");
		csWebSiteLabel = new Label();
		csWebSiteLabel.setBounds(new Rectangle(9, 442, 125, 23));
		csWebSiteLabel.setText("�ͷ���ַ��");
		accessNoLabel = new Label();
		accessNoLabel.setBounds(new Rectangle(334, 406, 113, 23));
		accessNoLabel.setText("����ţ�");
		pactTimeLabel = new Label();
		pactTimeLabel.setBounds(new Rectangle(9, 408, 111, 23));
		pactTimeLabel.setText("��ͬ�������ڣ�");
		footCycleLabel = new Label();
		footCycleLabel.setBounds(new Rectangle(633, 259, 79, 23));
		footCycleLabel.setText("�������ڣ�");
		licenseLabel = new Label();
		licenseLabel.setBounds(new Rectangle(642, 407, 71, 23));
		licenseLabel.setText("Ӫҵִ�գ�");
		principalEmailLabel = new Label();
		principalEmailLabel.setBounds(new Rectangle(8, 297, 119, 23));
		principalEmailLabel.setText("���������䣺");
		princiPalTelLabel = new Label();
		princiPalTelLabel.setBounds(new Rectangle(334, 261, 89, 23));
		princiPalTelLabel.setText("�����˵绰��");
		princiPalLabel = new Label();
		princiPalLabel.setBounds(new Rectangle(7, 260, 121, 23));
		princiPalLabel.setText("������������");
		legalRepresentAtiveLabel = new Label();
		legalRepresentAtiveLabel.setBounds(new Rectangle(335, 225, 90, 23));
		legalRepresentAtiveLabel.setText("���˴���");
		companyAddressLabel = new Label();
		companyAddressLabel.setBounds(new Rectangle(334, 443, 121, 23));
		companyAddressLabel.setText("��˾��ַ��");
		isRambleLabel = new Label();
		isRambleLabel.setBounds(new Rectangle(634, 157, 89, 23));
		isRambleLabel.setText("�Ƿ�������Σ�");
		customerCareLabel = new Label();
		customerCareLabel.setBounds(new Rectangle(7, 226, 123, 23));
		customerCareLabel.setText("SP�ͷ��绰��");
		footPercentLabel = new Label();
		footPercentLabel.setBounds(new Rectangle(10, 154, 85, 23));
		footPercentLabel.setText("����ٷֱȣ�");
		payTypeLabel = new Label();
		payTypeLabel.setBounds(new Rectangle(632, 221, 87, 23));
		payTypeLabel.setText("���㸶�ѷ�ʽ��");
		nameENLabel = new Label();
		nameENLabel.setBounds(new Rectangle(337, 188, 96, 23));
		nameENLabel.setText("SP/CPӢ�����ƣ�");
		nameCNLabel = new Label();
		nameCNLabel.setBounds(new Rectangle(8, 190, 123, 23));
		nameCNLabel.setText("���������������*��");
		webURLLabel = new Label();
		webURLLabel.setBounds(new Rectangle(336, 154, 90, 23));
		webURLLabel.setText("���������ַ��");
		stateLabel = new Label();
		stateLabel.setBounds(new Rectangle(634, 189, 70, 23));
		stateLabel.setText("״̬*��");
		spcpTypeLabel = new Label();
		spcpTypeLabel.setBounds(new Rectangle(338, 117, 87, 23));
		spcpTypeLabel.setText("SP/CP����*��");
		spcpIdLabel = new Label();
		spcpIdLabel.setBounds(new Rectangle(8, 117, 122, 23));
		spcpIdLabel.setText("SP/CP���*��");
		operateTypeLabel = new Label();
		operateTypeLabel.setBounds(new Rectangle(636, 46, 85, 23));
		operateTypeLabel.setText("��������*��");
		swapSystemIdLabel = new Label();
		swapSystemIdLabel.setBounds(new Rectangle(8, 78, 123, 23));
		swapSystemIdLabel.setText("���׷���ϵͳ��ʶ*��");
		SPsaveLabel = new Label();
		SPsaveLabel.setBounds(new Rectangle(635, 122, 91, 23));
		SPsaveLabel.setText("SP����ʡID*��");
		streamingNoLabel = new Label();
		streamingNoLabel.setBounds(new Rectangle(8, 40, 119, 23));
		streamingNoLabel.setText("��ˮ��*��");
		this.setLayout(null);
		this.setSize(897, 687);
		this.setTitle("SP/CPͬ��ģ����");
		this.add(streamingNoLabel, null);
		this.add(getStreamingNoTextField(), null);
		this.add(SPsaveLabel, null);
		this.add(getSPsaveChoice(), null);
		this.add(swapSystemIdLabel, null);
		this.add(operateTypeLabel, null);
		this.add(getOperateTypeChoice(), null);
		this.add(spcpIdLabel, null);
		this.add(getSpcpIdTextField(), null);
		this.add(spcpTypeLabel, null);
		this.add(getSpcpTypeChoice(), null);
		this.add(stateLabel, null);
		this.add(getStateChoice(), null);
		this.add(webURLLabel, null);
		this.add(getWebSiteURLTextField(), null);
		this.add(nameCNLabel, null);
		this.add(getNameCNTextField(), null);
		this.add(nameENLabel, null);
		this.add(getNameENTextField(), null);
		this.add(payTypeLabel, null);
		this.add(getPayTypeChoice(), null);
		this.add(footPercentLabel, null);
		this.add(getFootPercentTextField(), null);
		this.add(customerCareLabel, null);
		this.add(getCustomerCareTextField(), null);
		this.add(isRambleLabel, null);
		this.add(getIsRamberChoice(), null);
		this.add(companyAddressLabel, null);
		this.add(getCompanyAddressTextField(), null);
		this.add(legalRepresentAtiveLabel, null);
		this.add(getLegalRepresentAtiveTextField(), null);
		this.add(princiPalLabel, null);
		this.add(getPrinciPalTextField(), null);
		this.add(princiPalTelLabel, null);
		this.add(getPrinciPalTelTextField(), null);
		this.add(principalEmailLabel, null);
		this.add(getPrincipalEmaiTextField(), null);
		this.add(licenseLabel, null);
		this.add(getLicenseTextField(), null);
		this.add(footCycleLabel, null);
		this.add(getFootCycleChoice(), null);
		this.add(pactTimeLabel, null);
		this.add(accessNoLabel, null);
		this.add(getAccessNoTextField(), null);
		this.add(csWebSiteLabel, null);
		this.add(getCsWebSiteTextField(), null);
		this.add(discreptionCNLabel, null);
		this.add(discreptionENLabel, null);
		this.add(serverManagerFaxLabel, null);
		this.add(getServerManagerFaxTextField(), null);
		this.add(serverManagerLabel, null);
		this.add(getServerManagerTextField(), null);
		this.add(serverManagerTelLabel, null);
		this.add(getServerManagerTelTextField(), null);
		this.add(serverManagerEmailLabel, null);
		this.add(getServerManagerEmaiTextField(), null);
		this.add(serverManagerAddrLabel, null);
		this.add(getServerManagerAddrTextField(), null);
		this.add(serverManagerPCLabel, null);
		this.add(getServerManagerPCTextField(), null);
		this.add(getSubmitButton(), null);
		this.add(getResetButton(), null);
		this.add(getCancelButton(), null);
		this.add(getNewStreamingNoButton(), null);
		this.add(getPactTimeDateChooserJButton(), null);
		this.add(getSwapSystemIdChoice(), null);
		this.add(timeStampLabel, null);
		this.add(getTimeStampTextField(), null);
		this.add(getNewTimeStampButton(), null);
		this.add(getDescCNTextArea(), null);
		this.add(getDescENTextArea(), null);
		this.add(platFormLabel, null);
		this.add(getPlatFormJScrollPane(), null);
		this.add(getPlatListChoice(), null);
		this.add(getNewPlateButton(), null);
		this.add(getDeletePlatButton(), null);
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
			streamingNoTextField.setBounds(new Rectangle(147, 40, 130, 23));
		}
		return streamingNoTextField;
	}

	/**
	 * This method initializes SPsaveChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getSPsaveChoice() {
		if (SPsaveChoice == null) {
			SPsaveChoice = new Choice();
			SPsaveChoice.setBounds(new Rectangle(729, 122, 137, 21));
			SPsaveChoice.addItem("");
			List provinceList = Config.getprovinceList();
			for (int i = 0; i < provinceList.size(); i++) {
				SPsaveChoice.addItem((String) provinceList.get(i));
			}
		}
		return SPsaveChoice;
	}

	/**
	 * This method initializes operateTypeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getOperateTypeChoice() {
		if (operateTypeChoice == null) {
			operateTypeChoice = new Choice();
			operateTypeChoice.setBounds(new Rectangle(729, 47, 137, 21));
			operateTypeChoice.addItem("����");
			operateTypeChoice.addItem("�޸�");
			operateTypeChoice.addItem("ɾ��");
		}
		return operateTypeChoice;
	}

	/**
	 * This method initializes spcpIdTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getSpcpIdTextField() {
		if (spcpIdTextField == null) {
			spcpIdTextField = new TextField();
			spcpIdTextField.setBounds(new Rectangle(146, 117, 167, 23));
		}
		return spcpIdTextField;
	}

	/**
	 * This method initializes spcpTypeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getSpcpTypeChoice() {
		if (spcpTypeChoice == null) {
			spcpTypeChoice = new Choice();
			spcpTypeChoice.setBounds(new Rectangle(435, 119, 184, 21));
			spcpTypeChoice.addItem("CP+SP");
			spcpTypeChoice.addItem("CP");
			spcpTypeChoice.addItem("SP");

		}
		return spcpTypeChoice;
	}

	/**
	 * This method initializes stateChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getStateChoice() {
		if (stateChoice == null) {
			stateChoice = new Choice();
			stateChoice.setBounds(new Rectangle(728, 191, 138, 21));
			stateChoice.addItem("����");
			stateChoice.addItem("����");
			stateChoice.addItem("Ԥע��");
			stateChoice.addItem("ע��");
		}
		return stateChoice;
	}

	/**
	 * This method initializes WebSiteURLTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getWebSiteURLTextField() {
		if (WebSiteURLTextField == null) {
			WebSiteURLTextField = new TextField();
			WebSiteURLTextField.setBounds(new Rectangle(435, 155, 183, 23));
		}
		return WebSiteURLTextField;
	}

	/**
	 * This method initializes nameCNTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getNameCNTextField() {
		if (nameCNTextField == null) {
			nameCNTextField = new TextField();
			nameCNTextField.setBounds(new Rectangle(146, 188, 170, 23));
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
			nameENTextField.setBounds(new Rectangle(436, 188, 182, 23));
		}
		return nameENTextField;
	}

	/**
	 * This method initializes payTypeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getPayTypeChoice() {
		if (payTypeChoice == null) {
			payTypeChoice = new Choice();
			payTypeChoice.setBounds(new Rectangle(728, 223, 139, 21));
			payTypeChoice.addItem("һ���Ը���");
			payTypeChoice.addItem("�ֿ�����");
		}
		return payTypeChoice;
	}

	/**
	 * This method initializes footPercentTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getFootPercentTextField() {
		if (footPercentTextField == null) {
			footPercentTextField = new TextField();
			footPercentTextField.setBounds(new Rectangle(145, 154, 168, 23));
		}
		return footPercentTextField;
	}

	/**
	 * This method initializes customerCareTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getCustomerCareTextField() {
		if (customerCareTextField == null) {
			customerCareTextField = new TextField();
			customerCareTextField.setBounds(new Rectangle(145, 225, 172, 23));
		}
		return customerCareTextField;
	}

	/**
	 * This method initializes isRamberChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getIsRamberChoice() {
		if (isRamberChoice == null) {
			isRamberChoice = new Choice();
			isRamberChoice.setBounds(new Rectangle(728, 157, 139, 21));
			isRamberChoice.addItem("������");
			isRamberChoice.addItem("����");
		}
		return isRamberChoice;
	}

	/**
	 * This method initializes companyAddressTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getCompanyAddressTextField() {
		if (companyAddressTextField == null) {
			companyAddressTextField = new TextField();
			companyAddressTextField.setBounds(new Rectangle(467, 444, 418, 23));
		}
		return companyAddressTextField;
	}

	/**
	 * This method initializes legalRepresentAtiveTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getLegalRepresentAtiveTextField() {
		if (legalRepresentAtiveTextField == null) {
			legalRepresentAtiveTextField = new TextField();
			legalRepresentAtiveTextField.setBounds(new Rectangle(435, 225, 183, 23));
		}
		return legalRepresentAtiveTextField;
	}

	/**
	 * This method initializes princiPalTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getPrinciPalTextField() {
		if (princiPalTextField == null) {
			princiPalTextField = new TextField();
			princiPalTextField.setBounds(new Rectangle(146, 259, 171, 23));
		}
		return princiPalTextField;
	}

	/**
	 * This method initializes princiPalTelTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getPrinciPalTelTextField() {
		if (princiPalTelTextField == null) {
			princiPalTelTextField = new TextField();
			princiPalTelTextField.setBounds(new Rectangle(434, 259, 184, 23));
		}
		return princiPalTelTextField;
	}

	/**
	 * This method initializes principalEmaiTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getPrincipalEmaiTextField() {
		if (principalEmaiTextField == null) {
			principalEmaiTextField = new TextField();
			principalEmaiTextField.setBounds(new Rectangle(146, 297, 172, 23));
		}
		return principalEmaiTextField;
	}

	/**
	 * This method initializes licenseTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getLicenseTextField() {
		if (licenseTextField == null) {
			licenseTextField = new TextField();
			licenseTextField.setBounds(new Rectangle(715, 407, 170, 23));
		}
		return licenseTextField;
	}

	/**
	 * This method initializes footCycleChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getFootCycleChoice() {
		if (footCycleChoice == null) {
			footCycleChoice = new Choice();
			footCycleChoice.setBounds(new Rectangle(728, 260, 140, 21));
			footCycleChoice.addItem("����");
			footCycleChoice.addItem("������");
			footCycleChoice.addItem("����");

		}
		return footCycleChoice;
	}

	/**
	 * This method initializes accessNoTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getAccessNoTextField() {
		if (accessNoTextField == null) {
			accessNoTextField = new TextField();
			accessNoTextField.setBounds(new Rectangle(467, 407, 162, 23));
		}
		return accessNoTextField;
	}

	/**
	 * This method initializes csWebSiteTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getCsWebSiteTextField() {
		if (csWebSiteTextField == null) {
			csWebSiteTextField = new TextField();
			csWebSiteTextField.setBounds(new Rectangle(145, 443, 175, 23));
		}
		return csWebSiteTextField;
	}

	/**
	 * This method initializes serverManagerFaxTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getServerManagerFaxTextField() {
		if (serverManagerFaxTextField == null) {
			serverManagerFaxTextField = new TextField();
			serverManagerFaxTextField.setBounds(new Rectangle(146, 371, 172, 23));
		}
		return serverManagerFaxTextField;
	}

	/**
	 * This method initializes serverManagerTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getServerManagerTextField() {
		if (serverManagerTextField == null) {
			serverManagerTextField = new TextField();
			serverManagerTextField.setBounds(new Rectangle(434, 299, 182, 23));
		}
		return serverManagerTextField;
	}

	/**
	 * This method initializes serverManagerTelTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getServerManagerTelTextField() {
		if (serverManagerTelTextField == null) {
			serverManagerTelTextField = new TextField();
			serverManagerTelTextField.setBounds(new Rectangle(729, 300, 155, 23));
		}
		return serverManagerTelTextField;
	}

	/**
	 * This method initializes serverManagerEmaiTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getServerManagerEmaiTextField() {
		if (serverManagerEmaiTextField == null) {
			serverManagerEmaiTextField = new TextField();
			serverManagerEmaiTextField.setBounds(new Rectangle(146, 332, 172, 23));
		}
		return serverManagerEmaiTextField;
	}

	/**
	 * This method initializes serverManagerAddrTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getServerManagerAddrTextField() {
		if (serverManagerAddrTextField == null) {
			serverManagerAddrTextField = new TextField();
			serverManagerAddrTextField.setBounds(new Rectangle(468, 335, 416, 23));
		}
		return serverManagerAddrTextField;
	}

	/**
	 * This method initializes serverManagerPCTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getServerManagerPCTextField() {
		if (serverManagerPCTextField == null) {
			serverManagerPCTextField = new TextField();
			serverManagerPCTextField.setBounds(new Rectangle(469, 374, 283, 23));
		}
		return serverManagerPCTextField;
	}

	/**
	 * This method initializes submitButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getSubmitButton() {
		if (submitButton == null) {
			submitButton = new Button();
			submitButton.setBounds(new Rectangle(225, 645, 136, 23));
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
			resetButton.setBounds(new Rectangle(420, 644, 134, 23));
			resetButton.setLabel("����");
		}
		return resetButton;
	}

	/**
	 * This method initializes cancelButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new Button();
			cancelButton.setBounds(new Rectangle(611, 642, 127, 23));
			cancelButton.setLabel("ȡ��");
		}
		return cancelButton;
	}

	/**
	 * This method initializes newStreamingNoButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getNewStreamingNoButton() {
		if (newStreamingNoButton == null) {
			newStreamingNoButton = new Button();
			newStreamingNoButton.setBounds(new Rectangle(288, 41, 37, 23));
			newStreamingNoButton.setLabel("����");
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
	 * This method initializes pactTimeDateChooserJButton	
	 * 	
	 * @return view.DateChooserJButton	
	 */
	private DateChooserJButton getPactTimeDateChooserJButton() {
		if (pactTimeDateChooserJButton == null) {
			pactTimeDateChooserJButton = new DateChooserJButton();
			pactTimeDateChooserJButton.setBounds(new Rectangle(146, 408, 172, 18));
		}
		return pactTimeDateChooserJButton;
	}

	/**
	 * This method initializes swapSystemIdChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getSwapSystemIdChoice() {
		if (swapSystemIdChoice == null) {
			swapSystemIdChoice = new Choice();
			swapSystemIdChoice.setBounds(new Rectangle(148, 78, 165, 21));
			List list = Config.getSystemList();
			for (int i = 0; i < list.size(); i++) {
				swapSystemIdChoice.addItem((String) list.get(i));
			}
		}
		return swapSystemIdChoice;
	}

	//�ύ��Ϣ�����������Ķ���
	public void do_btn_OK() {
		String title = "��ʾ";
		int type = JOptionPane.PLAIN_MESSAGE;
		//�����жϱ������Ƿ�������
		if ("".equals(streamingNoTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "��ˮ�Ų���Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(SPsaveChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "SP����ʡ����Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(swapSystemIdChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "���׷���ϵͳ��ʶ����Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(operateTypeChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "�������Ͳ���Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(spcpIdTextField.getText())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "SP/CP��Ų���Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(spcpTypeChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "SP/CP���Ͳ���Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		if ("".equals(stateChoice.getSelectedItem())) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "״̬����Ϊ�գ�";
			JOptionPane.showMessageDialog(this, message, title, type);
			return;
		}
		SCPInfoSynSVXMLUtil vo = new SCPInfoSynSVXMLUtil();
		vo.setStreamingNo(streamingNoTextField.getText());
		vo.setTimeStamp(timeStampTextField.getText());
		vo.setSystemId(Config.getSystemCode(swapSystemIdChoice.getSelectedItem()));
		vo.setOPFlag(SCPInfoSynSVXMLUtil.getOPFlag(operateTypeChoice.getSelectedItem()));
		//����SP/CP��Ϣ
		Map map = new HashMap();
		map.put("SPID", spcpIdTextField.getText());
		map.put("Type", SCPInfoSynSVXMLUtil.getCSPType(spcpTypeChoice.getSelectedItem()));
		map.put("NameCN", nameCNTextField.getText());
		map.put("NameEN", nameENTextField.getText());
		map.put("DescriptionCN", descCNTextArea.getText());
		map.put("DescriptionEN", descENTextArea.getText());
		map.put("CustomerCare", customerCareTextField.getText());
		map.put("WebsiteURL", WebSiteURLTextField.getText());
		map.put("ProvinceID", Config.getProvinceCode(SPsaveChoice.getSelectedItem()));
		map.put("RoamProperty", SCPInfoSynSVXMLUtil.getRoamProperty(isRamberChoice.getSelectedItem()));
		map.put("CompanyAddress", companyAddressTextField.getText());
		map.put("LegalRepresentative", legalRepresentAtiveTextField.getText());
		map.put("Principal", princiPalTextField.getText());
		map.put("PrincipalTel", princiPalTelTextField.getText());
		map.put("PrincipalEmail", principalEmaiTextField.getText());
		map.put("ServiceManager", serverManagerTextField.getText());
		map.put("ServiceManagerTel", serverManagerTelTextField.getText());
		map.put("ServiceManagerEmail", serverManagerEmaiTextField.getText());
		map.put("ServiceManagerAddr", serverManagerAddrTextField.getText());
		map.put("ServiceManagerPC", serverManagerPCTextField.getText());
		map.put("ServiceManagerFax", serverManagerFaxTextField.getText());
		map.put("License", licenseTextField.getText());
		map.put("ContractExpireDate", pactTimeDateChooserJButton.getText());
		map.put("AccessNO", accessNoTextField.getText());
		map.put("SettlementCycle", SCPInfoSynSVXMLUtil.getSettlementCycle(footCycleChoice.getSelectedItem()));
		map.put("SettlementPayType", SCPInfoSynSVXMLUtil.getSettlementPayType(payTypeChoice.getSelectedItem()));
		map.put("SettlementPercent", footPercentTextField.getText());
		map.put("CSWebsite", csWebSiteTextField.getText());
		map.put("Status", SCPInfoSynSVXMLUtil.getStatus(stateChoice.getSelectedItem()));
		List list = new ArrayList();
		map.put("PlatForm", list);
		vo.setSPInfo(map);
		XmlUtil a;
		try {
			frame.setSendXml(XmlUtil.formatXML(vo.getRequestXML().getBytes(),"GBK"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.setSendURL(serviceName);
		this.show(false);
	}

	//���ȷ����  ������װ���Ĳ���
	public class Btn_OK_ActionListen implements ActionListener {
		private SPCPInPhase spcpphase;

		public Btn_OK_ActionListen(SPCPInPhase spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.do_btn_OK();
		}

	}

	/**
	 * This method initializes timeStampTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTimeStampTextField() {
		if (timeStampTextField == null) {
			timeStampTextField = new TextField();
			timeStampTextField.setBounds(new Rectangle(437, 43, 140, 23));
		}
		return timeStampTextField;
	}

	/**
	 * This method initializes newTimeStampButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getNewTimeStampButton() {
		if (newTimeStampButton == null) {
			newTimeStampButton = new Button();
			newTimeStampButton.setBounds(new Rectangle(584, 44, 38, 23));
			newTimeStampButton.setLabel("����");
			newTimeStampButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					timeStampTextField.setText(DateUtil.getDateStr());
				}
			});
		}
		return newTimeStampButton;
	}

	/**
	 * This method initializes descCNTextArea	
	 * 	
	 * @return java.awt.TextArea	
	 */
	private TextArea getDescCNTextArea() {
		if (descCNTextArea == null) {
			descCNTextArea = new TextArea();
			descCNTextArea.setBounds(new Rectangle(138, 477, 291, 73));
		}
		return descCNTextArea;
	}

	/**
	 * This method initializes descENTextArea	
	 * 	
	 * @return java.awt.TextArea	
	 */
	private TextArea getDescENTextArea() {
		if (descENTextArea == null) {
			descENTextArea = new TextArea();
			descENTextArea.setBounds(new Rectangle(539, 479, 335, 69));
		}
		return descENTextArea;
	}

	/**
	 * This method initializes platFormJScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getPlatFormJScrollPane() {
		if (platFormJScrollPane == null) {
			platFormJScrollPane = new JScrollPane();
			platFormJScrollPane.setBounds(new Rectangle(138, 558, 453, 72));
			platFormJScrollPane.setViewportView(getPlatFormJTable());
		}
		return platFormJScrollPane;
	}

	/**
	 * This method initializes platFormJTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getPlatFormJTable() {
		if (platFormJTable == null) {
			platFormJTable = new JTable();
		}
		return platFormJTable;
	}

	/**
	 * This method initializes platListChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getPlatListChoice() {
		if (platListChoice == null) {
			platListChoice = new Choice();
			platListChoice.setBounds(new Rectangle(611, 563, 148, 21));
		}
		return platListChoice;
	}

	/**
	 * This method initializes newPlateButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getNewPlateButton() {
		if (newPlateButton == null) {
			newPlateButton = new Button();
			newPlateButton.setBounds(new Rectangle(773, 564, 103, 23));
			newPlateButton.setLabel("����ƽ̨");
		}
		return newPlateButton;
	}

	/**
	 * This method initializes deletePlatButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getDeletePlatButton() {
		if (deletePlatButton == null) {
			deletePlatButton = new Button();
			deletePlatButton.setBounds(new Rectangle(773, 602, 103, 23));
			deletePlatButton.setLabel("ɾ��ƽ̨");
		}
		return deletePlatButton;
	}

} //  @jve:decl-index=0:visual-constraint="183,-11"
