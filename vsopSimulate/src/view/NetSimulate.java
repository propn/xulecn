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
public class NetSimulate extends Dialog {

	private static final long serialVersionUID = 1L;
	private Label StreamNolabel = null;
	private Label label = null;
	private TextField StreamNoTextField = null;
	private Label systemIdLabel = null;
	private Choice systemIdChoice = null;
	private Label actionTypeLabel = null;
	private Choice actionTypeChoice = null;
	private Label orderIdLabel = null;
	private TextField orderIdTextField = null;
	private Label prodSpecCodeLabel = null;
	private TextField prodSpecCodeTextField = null;
	private Label productNoLabel = null;
	private TextField productNoLabelTextField = null;
	private Label xiaoshoupinLabelList = null;
	private Button addButton = null;
	private Button clearButton = null;
	private Button okButton = null;
	private Button cancleButton = null;
	private Button clearAllButton = null;
	private JScrollPane jScrollPane = new JScrollPane();
	private JTable jTable = new JTable();
	private Button button = null;

	/**
	 * @param owner
	 */
	public NetSimulate(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jTable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {

				System.out.println("%%%%%%%%%%%%%%%%%%%%"); // TODO Auto-generated Event stub mouseClicked()
				int s = -1;
				s = jTable.getSelectedColumn();
				if (jTable.getValueAt(jTable.getSelectedRow(), 0) != null) {
					s = jTable.getSelectedRow(); //��ȡ��ѡ�е��еĵ�һ��λ�õ����ݣ���Ȼ��Ҳ����ָ������ĸ��еڼ���
				}
				System.out.println(String.valueOf("222222222222222222:" + s));
			}
		});
		xiaoshoupinLabelList = new Label();
		xiaoshoupinLabelList.setBounds(new Rectangle(16, 182, 297, 23));
		xiaoshoupinLabelList.setText("����Ʒ�б�������0-N����");
		productNoLabel = new Label();
		productNoLabel.setBounds(new Rectangle(404, 143, 199, 23));
		productNoLabel.setText("�û�����*(ProductNo:20)��");
		prodSpecCodeLabel = new Label();
		prodSpecCodeLabel.setBounds(new Rectangle(17, 143, 179, 23));
		prodSpecCodeLabel.setText("��Ʒ����*(ProdSpecCode:25)��");
		orderIdLabel = new Label();
		orderIdLabel.setBounds(new Rectangle(404, 102, 200, 23));
		orderIdLabel.setText("��ϵͳ������ʶ*(OrderId:60)��");
		actionTypeLabel = new Label();
		actionTypeLabel.setBounds(new Rectangle(18, 100, 178, 23));
		actionTypeLabel.setText("ҵ����*��ActionType:1����");
		systemIdLabel = new Label();
		systemIdLabel.setBounds(new Rectangle(405, 61, 199, 23));
		systemIdLabel.setText("���׷���ϵͳ��ʶ*(SystemId)��");
		label = new Label();
		label.setBounds(new Rectangle(16, 34, 270, 18));
		label.setText("��*Ϊ�����ֶΣ���ˮ���Զ����ɣ������޸�");
		StreamNolabel = new Label();
		StreamNolabel.setBounds(new Rectangle(17, 60, 178, 18));
		StreamNolabel.setText("��ˮ��*����StreamingNo:60����");
		this.setLayout(null);
		this.setSize(819, 362);
		this.setTitle("����ģ����(��������)");
		this.add(StreamNolabel, null);
		this.add(label, null);
		this.add(getStreamNoTextField(), null);
		this.add(systemIdLabel, null);
		this.add(getSystemIdChoice(), null);
		this.add(actionTypeLabel, null);
		this.add(getActionTypeChoice(), null);
		this.add(orderIdLabel, null);
		this.add(getOrderIdTextField(), null);
		this.add(prodSpecCodeLabel, null);
		this.add(getProdSpecCodeTextField(), null);
		this.add(productNoLabel, null);
		this.add(getProductNoLabelTextField(), null);
		this.add(xiaoshoupinLabelList, null);
		this.add(getAddButton(), null);
		this.add(getClearButton(), null);
		this.add(getOkButton(), null);
		this.add(getCancleButton(), null);
		this.add(getClearAllButton(), null);
		this.add(getJScrollPane(), null);
		this.add(getButton(), null);

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println(e.getWindow().getName());
				e.getWindow().show(false);

			}
		});
		this.setModal(true);

	}

	/**
	 * This method initializes StreamNoTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getStreamNoTextField() {
		if (StreamNoTextField == null) {
			StreamNoTextField = new TextField();
			StreamNoTextField.setBounds(new Rectangle(205, 59, 131, 23));
		}
		return StreamNoTextField;
	}

	/**
	 * This method initializes systemIdChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getSystemIdChoice() {
		if (systemIdChoice == null) {
			systemIdChoice = new Choice();
			systemIdChoice.setBounds(new Rectangle(611, 61, 178, 21));
		}
		return systemIdChoice;
	}

	/**
	 * This method initializes actionTypeChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getActionTypeChoice() {
		if (actionTypeChoice == null) {
			actionTypeChoice = new Choice();
			actionTypeChoice.addItem("����");
			actionTypeChoice.addItem("�޸�");
			actionTypeChoice.addItem("ɾ��");
			actionTypeChoice.setBounds(new Rectangle(204, 101, 185, 21));
		}
		return actionTypeChoice;
	}

	/**
	 * This method initializes orderIdTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getOrderIdTextField() {
		if (orderIdTextField == null) {
			orderIdTextField = new TextField();
			orderIdTextField.setBounds(new Rectangle(611, 101, 177, 23));
		}
		return orderIdTextField;
	}

	/**
	 * This method initializes prodSpecCodeTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getProdSpecCodeTextField() {
		if (prodSpecCodeTextField == null) {
			prodSpecCodeTextField = new TextField();
			prodSpecCodeTextField.setBounds(new Rectangle(203, 142, 186, 23));
		}
		return prodSpecCodeTextField;
	}

	/**
	 * This method initializes productNoLabelTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getProductNoLabelTextField() {
		if (productNoLabelTextField == null) {
			productNoLabelTextField = new TextField();
			productNoLabelTextField.setBounds(new Rectangle(610, 143, 179, 23));
		}
		return productNoLabelTextField;
	}

	/**
	 * This method initializes addButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getAddButton() {
		if (addButton == null) {
			addButton = new Button();
			addButton.setBounds(new Rectangle(691, 212, 96, 23));
			addButton.setLabel("��������Ʒ");

			addButton.addActionListener(new AddTableColumnListener(jTable));
		}
		return addButton;
	}

	/**
	 * This method initializes clearButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getClearButton() {
		if (clearButton == null) {
			clearButton = new Button();
			clearButton.setBounds(new Rectangle(692, 247, 96, 23));
			clearButton.setLabel("��������б���");
			clearButton.addActionListener(new DeleteTableColumnListener(jTable));
		}
		return clearButton;
	}

	/**
	 * This method initializes okButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getOkButton() {
		if (okButton == null) {
			okButton = new Button();
			okButton.setBounds(new Rectangle(158, 321, 125, 23));
			okButton.setLabel("ȷ��");
			//�����¼���Ӧ
			okButton.addActionListener(new BtnOK_actionAdapter(this));
			okButton.addActionListener(new Btn_OK_ActionListen(this));
		}
		return okButton;
	}

	/**
	 * This method initializes cancleButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCancleButton() {
		if (cancleButton == null) {
			cancleButton = new Button();
			cancleButton.setBounds(new Rectangle(341, 320, 126, 23));
			cancleButton.setLabel("ȡ��");
			cancleButton.addActionListener(new Btn_Cancel_ActionListen(this));
		}
		return cancleButton;
	}

	/**
	 * This method initializes clearAllButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getClearAllButton() {
		if (clearAllButton == null) {
			clearAllButton = new Button();
			clearAllButton.setBounds(new Rectangle(525, 320, 126, 23));
			clearAllButton.setLabel("���������");
		}
		return clearAllButton;
	}

	class BtnOK_actionAdapter implements ActionListener {
		NetSimulate ns;

		public BtnOK_actionAdapter(NetSimulate ns) {
			this.ns = ns;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ns.show(false);

		}

	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();

			jScrollPane.setBounds(new Rectangle(19, 213, 636, 96));
			jScrollPane.setViewportView(getJTable());
		} else {
			jScrollPane.setBounds(new Rectangle(19, 213, 636, 96));
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable() {
		if (jTable == null) {
			jTable = new JTable();
			jTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {

					int s = -1;
					s = jTable.getSelectedColumn();
					if (jTable.getValueAt(jTable.getSelectedRow(), 0) != null) {
						s = jTable.getSelectedColumn(); //��ȡ��ѡ�е��еĵ�һ��λ�õ����ݣ���Ȼ��Ҳ����ָ������ĸ��еڼ���
					}

				}
			});
			DefaultTableModel model = (DefaultTableModel) jTable.getModel();
			model.addColumn("��ֵ����Ʒ����");
			model.addColumn("����ƷID");
			model.addColumn("��Чʱ��");
			model.addColumn("ʧЧʱ��");
			model.addRow(new String[] { "", "" });
			//jTable.set
		} else {
			DefaultTableModel model = (DefaultTableModel) jTable.getModel();
			model.addColumn("��ֵ����Ʒ����");
			model.addColumn("����ƷID");
			model.addColumn("��Чʱ��");
			model.addColumn("ʧЧʱ��");
		}
		return jTable;
	}

	public class AddTableColumnListener implements ActionListener {
		JTable jTable;

		public AddTableColumnListener(JTable jTable) {
			this.jTable = jTable;
		}

		public void actionPerformed(ActionEvent arg0) {

			DefaultTableModel model = (DefaultTableModel) jTable.getModel();

			model.addRow(new String[] { "", "" });

		}

	}

	public class DeleteTableColumnListener implements ActionListener {
		JTable jTable;

		public DeleteTableColumnListener(JTable jTable) {
			this.jTable = jTable;
		}

		public void actionPerformed(ActionEvent arg0) {

			DefaultTableModel model = (DefaultTableModel) jTable.getModel();

			int count = model.getRowCount();
			if (count > 0) {
				for (int i = 0; i < count; i++) {
					model.removeRow(0);
				}
			}

		}

	}

	/**
	 * This method initializes button	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton() {
		if (button == null) {
			button = new Button();
			button.setBounds(new Rectangle(346, 59, 44, 23));
			button.setLabel("����");
			button.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					StreamNoTextField.setText(DateUtil.getDateStr());
				}
			});
		}
		return button;
	}

	public void do_btn_OK() {
		this.show(false);
	}

	public class Btn_OK_ActionListen implements ActionListener {
		private NetSimulate spcpphase;

		public Btn_OK_ActionListen(NetSimulate spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.do_btn_OK();
		}

	}

	public class Btn_Cancel_ActionListen implements ActionListener {
		private NetSimulate spcpphase;

		public Btn_Cancel_ActionListen(NetSimulate spcpphase) {
			this.spcpphase = spcpphase;
		}

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			spcpphase.show(false);
		}

	}
}
