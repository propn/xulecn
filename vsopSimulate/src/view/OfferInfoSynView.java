package view;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;

public class OfferInfoSynView extends Dialog {

	private static final long serialVersionUID = 1L;
	private Label streamingNoLabel = null;
	private TextField streamingNoTextField = null;
	private Button newStreamingNoButton = null;
	private Label systemIdLabel = null;
	private Choice systemIdChoice = null;
	private Label opTypeLabel = null;
	private Choice opTypeChoice = null;
	private Label offerTypeLabel = null;
	private Choice offerTypeChoice = null;
	private Label offerIdLabel = null;
	private TextField offerIdTextField = null;
	private Label nameCNLabel = null;
	private TextField nameCNTextField = null;
	private Label nameENLabel = null;
	private TextField nameENTextField = null;
	private Label descriptionCNLabel = null;
	private TextField descriptionCNTextField = null;
	private Label descriptionENLabel = null;
	private TextField descreptionENTextField = null;
	private Label stateLabel = null;
	private Choice stateChoice = null;

	/**
	 * @param owner
	 */
	public OfferInfoSynView(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		stateLabel = new Label();
		stateLabel.setBounds(new Rectangle(10, 193, 71, 25));
		stateLabel.setText("状态*：");
		descriptionENLabel = new Label();
		descriptionENLabel.setBounds(new Rectangle(546, 152, 88, 23));
		descriptionENLabel.setText("英文描述：");
		descriptionCNLabel = new Label();
		descriptionCNLabel.setBounds(new Rectangle(269, 153, 99, 23));
		descriptionCNLabel.setText("中文描述：");
		nameENLabel = new Label();
		nameENLabel.setBounds(new Rectangle(11, 152, 92, 23));
		nameENLabel.setText("英文名：");
		nameCNLabel = new Label();
		nameCNLabel.setBounds(new Rectangle(575, 108, 56, 23));
		nameCNLabel.setText("中文名*：");
		offerIdLabel = new Label();
		offerIdLabel.setBounds(new Rectangle(270, 108, 89, 23));
		offerIdLabel.setText("销售品ID*：");
		offerTypeLabel = new Label();
		offerTypeLabel.setBounds(new Rectangle(11, 107, 114, 23));
		offerTypeLabel.setText("增值销售品类型*：");
		opTypeLabel = new Label();
		opTypeLabel.setBounds(new Rectangle(564, 61, 65, 23));
		opTypeLabel.setText("业务动作*：");
		systemIdLabel = new Label();
		systemIdLabel.setBounds(new Rectangle(309, 59, 118, 23));
		systemIdLabel.setText("交易发起系统标识*：");
		streamingNoLabel = new Label();
		streamingNoLabel.setBounds(new Rectangle(13, 59, 65, 23));
		streamingNoLabel.setText("流水号*：");
		this.setLayout(null);
		this.setSize(815, 541);
		this.setTitle("增值销售品同步（X平台）");
		this.add(streamingNoLabel, null);
		this.add(getStreamingNoTextField(), null);
		this.add(getNewStreamingNoButton(), null);
		this.add(systemIdLabel, null);
		this.add(getSystemIdChoice(), null);
		this.add(opTypeLabel, null);
		this.add(getOpTypeChoice(), null);
		this.add(offerTypeLabel, null);
		this.add(getOfferTypeChoice(), null);
		this.add(offerIdLabel, null);
		this.add(getOfferIdTextField(), null);
		this.add(nameCNLabel, null);
		this.add(getNameCNTextField(), null);
		this.add(nameENLabel, null);
		this.add(getNameENTextField(), null);
		this.add(descriptionCNLabel, null);
		this.add(getDescriptionCNTextField(), null);
		this.add(descriptionENLabel, null);
		this.add(getDescreptionENTextField(), null);
		this.add(stateLabel, null);
		this.add(getStateChoice(), null);
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
			streamingNoTextField.setBounds(new Rectangle(103, 59, 138, 23));
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
			newStreamingNoButton.setBounds(new Rectangle(250, 59, 45, 23));
			newStreamingNoButton.setLabel("生成");
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
			systemIdChoice.setBounds(new Rectangle(435, 60, 100, 21));
		}
		return systemIdChoice;
	}

	/**
	 * This method initializes opTypeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getOpTypeChoice() {
		if (opTypeChoice == null) {
			opTypeChoice = new Choice();
			opTypeChoice.setBounds(new Rectangle(643, 62, 118, 21));
		}
		return opTypeChoice;
	}

	/**
	 * This method initializes offerTypeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getOfferTypeChoice() {
		if (offerTypeChoice == null) {
			offerTypeChoice = new Choice();
			offerTypeChoice.setBounds(new Rectangle(135, 109, 123, 21));
		}
		return offerTypeChoice;
	}

	/**
	 * This method initializes offerIdTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getOfferIdTextField() {
		if (offerIdTextField == null) {
			offerIdTextField = new TextField();
			offerIdTextField.setBounds(new Rectangle(373, 108, 162, 23));
		}
		return offerIdTextField;
	}

	/**
	 * This method initializes nameCNTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getNameCNTextField() {
		if (nameCNTextField == null) {
			nameCNTextField = new TextField();
			nameCNTextField.setBounds(new Rectangle(643, 109, 153, 23));
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
			nameENTextField.setBounds(new Rectangle(104, 152, 137, 23));
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
			descriptionCNTextField.setBounds(new Rectangle(372, 154, 163, 23));
		}
		return descriptionCNTextField;
	}

	/**
	 * This method initializes descreptionENTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getDescreptionENTextField() {
		if (descreptionENTextField == null) {
			descreptionENTextField = new TextField();
			descreptionENTextField.setBounds(new Rectangle(643, 151, 154, 23));
		}
		return descreptionENTextField;
	}

	/**
	 * This method initializes stateChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getStateChoice() {
		if (stateChoice == null) {
			stateChoice = new Choice();
			stateChoice.setBounds(new Rectangle(103, 195, 137, 21));
		}
		return stateChoice;
	}

} //  @jve:decl-index=0:visual-constraint="-73,7"
