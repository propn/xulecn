package view;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextField;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.DateUtil;

/**
 * <pre>
 * Title:SP/CPҵ������ǩԼ��Ϣͬ��
 * Description: �����ܵ����� 
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class SPSignInfoView extends Dialog {
	public static String serverNmae = "";
	private static final long serialVersionUID = 1L;
	private Label streamingNoLabel = null;
	private TextField streamingNoTextField = null;
	private Button addStreamingNoButton = null;
	private Label timeStampLabel = null;
	private DateChooserJButton dateChooserJButton = null;
	private Label opFlagLabel = null;
	private Choice opFlagChoice = null;
	private Label listLabel = null;
	private JScrollPane spSignInfoJScrollPane = null;
	private JTable spSignInfoJTable = null;
	private Button okButton = null;
	private Button cancelButton = null;
	private Button newSPSignInfoButton = null;
	private Button deleteSPSignInfoButton = null;

	/**
	 * @param owner
	 */
	public SPSignInfoView(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		listLabel = new Label();
		listLabel.setBounds(new Rectangle(23, 150, 114, 23));
		listLabel.setText("ҵ�������б�");
		opFlagLabel = new Label();
		opFlagLabel.setBounds(new Rectangle(23, 107, 72, 23));
		opFlagLabel.setText("��������*��");
		timeStampLabel = new Label();
		timeStampLabel.setBounds(new Rectangle(365, 58, 99, 23));
		timeStampLabel.setText("ʱ���*��");
		streamingNoLabel = new Label();
		streamingNoLabel.setBounds(new Rectangle(24, 57, 76, 23));
		streamingNoLabel.setText("��ˮ��*��");
		this.setLayout(null);
		this.setSize(636, 409);
		this.setTitle("SP/CPҵ������ǩԼ��Ϣͬ��");
		this.add(streamingNoLabel, null);
		this.add(getStreamingNoTextField(), null);
		this.add(getAddStreamingNoButton(), null);
		this.add(timeStampLabel, null);
		this.add(getDateChooserJButton(), null);
		this.add(opFlagLabel, null);
		this.add(getOpFlagChoice(), null);
		this.add(listLabel, null);
		this.add(getSpSignInfoJScrollPane(), null);
		this.add(getOkButton(), null);
		this.add(getCancelButton(), null);
		this.add(getNewSPSignInfoButton(), null);
		this.add(getDeleteSPSignInfoButton(), null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
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
			streamingNoTextField.setBounds(new Rectangle(104, 58, 158, 23));
		}
		return streamingNoTextField;
	}

	/**
	 * This method initializes addStreamingNoButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getAddStreamingNoButton() {
		if (addStreamingNoButton == null) {
			addStreamingNoButton = new Button();
			addStreamingNoButton.setBounds(new Rectangle(270, 59, 58, 23));
			addStreamingNoButton.setLabel("����");
			addStreamingNoButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					String str = DateUtil.getDateStr();
					streamingNoTextField.setText(str);
				}
			});
		}
		return addStreamingNoButton;
	}

	/**
	 * This method initializes dateChooserJButton	
	 * 	
	 * @return view.DateChooserJButton	
	 */
	private DateChooserJButton getDateChooserJButton() {
		if (dateChooserJButton == null) {
			dateChooserJButton = new DateChooserJButton();
			dateChooserJButton.setBounds(new Rectangle(475, 61, 142, 18));
		}
		return dateChooserJButton;
	}

	/**
	 * This method initializes opFlagChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getOpFlagChoice() {
		if (opFlagChoice == null) {
			opFlagChoice = new Choice();
			opFlagChoice.setBounds(new Rectangle(105, 108, 159, 21));
			opFlagChoice.addItem("����");
			opFlagChoice.addItem("�޸�");
			opFlagChoice.addItem("ɾ��");
		}
		return opFlagChoice;
	}

	/**
	 * This method initializes spSignInfoJScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSpSignInfoJScrollPane() {
		if (spSignInfoJScrollPane == null) {
			spSignInfoJScrollPane = new JScrollPane();
			spSignInfoJScrollPane.setBounds(new Rectangle(23, 182, 511, 155));
			spSignInfoJScrollPane.setViewportView(getSpSignInfoJTable());
		}
		return spSignInfoJScrollPane;
	}

	/**
	 * This method initializes spSignInfoJTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getSpSignInfoJTable() {
		if (spSignInfoJTable == null) {
			spSignInfoJTable = new JTable();
			DefaultTableModel model = (DefaultTableModel) spSignInfoJTable.getModel();
			model.addColumn("ҵ������ǩԼ���");
			model.addColumn("CP/SP ����");
			model.addColumn("ҵ����������");
			model.addColumn("��Чʱ��");
			model.addColumn("ʧЧʱ��");

		}
		return spSignInfoJTable;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getOkButton() {
		if (okButton == null) {
			okButton = new Button();
			okButton.setBounds(new Rectangle(156, 359, 111, 23));
			okButton.setLabel("ȷ��");
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
			cancelButton.setBounds(new Rectangle(332, 359, 115, 23));
			cancelButton.setLabel("ȡ��");
		}
		return cancelButton;
	}

	/**
	 * This method initializes newSPSignInfoButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getNewSPSignInfoButton() {
		if (newSPSignInfoButton == null) {
			newSPSignInfoButton = new Button();
			newSPSignInfoButton.setBounds(new Rectangle(538, 183, 83, 23));
			newSPSignInfoButton.setLabel("����");
		}
		return newSPSignInfoButton;
	}

	/**
	 * This method initializes deleteSPSignInfoButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getDeleteSPSignInfoButton() {
		if (deleteSPSignInfoButton == null) {
			deleteSPSignInfoButton = new Button();
			deleteSPSignInfoButton.setBounds(new Rectangle(539, 225, 83, 23));
			deleteSPSignInfoButton.setLabel("ɾ��");
		}
		return deleteSPSignInfoButton;
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
