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
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.Config;
import util.SceneFileUtil;
import util.TestPlanConfigUtil;

public class UpdateTestPlanView extends Dialog {

	private static final long serialVersionUID = 1L;
	private Label label = null;
	private TextField testPlanNameTextField = null;
	private JScrollPane testPlanJScrollPane = null;
	private JTable testPlanJTable = null;
	private Label label1 = null;
	private Label label2 = null;
	private Choice choice = null;
	private Label label3 = null;
	private Choice serverNameChoice = null;
	private Label label4 = null;
	private Choice sceneNameChoice = null;
	private Button addSceneButton = null;
	private Button deleteButton = null;
	private Button removeAllButton = null;
	private Button closeButton = null;
	private Button OKButton = null;
	private TestPlanView  tp = null;
	private String testPlanName = null;
	private String flag = "NEW";  //  @jve:decl-index=0:
	/**
	 * @param owner
	 */
	public UpdateTestPlanView(Frame owner,TestPlanView tp) {
		super(owner);
		this.tp = tp;
		initialize();
	}
	
	public UpdateTestPlanView(Frame owner,TestPlanView tp,String testPlanName,String flag) {
		super(owner);
		this.tp = tp;
		this.testPlanName = testPlanName;
		this.flag = flag;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		label4 = new Label();
		label4.setBounds(new Rectangle(474, 434, 59, 23));
		label4.setText("��������");
		label3 = new Label();
		label3.setBounds(new Rectangle(203, 434, 59, 23));
		label3.setText("�ӿ�����");
		label2 = new Label();
		label2.setBounds(new Rectangle(20, 435, 94, 23));
		label2.setText("���ı��ػ�ѡ��");
		label1 = new Label();
		label1.setBounds(new Rectangle(20, 72, 111, 23));
		label1.setText("���Խ��������б�");
		label = new Label();
		label.setBounds(new Rectangle(19, 42, 115, 23));
		label.setText(" ���Լƻ�����       ��");
		this.setLayout(null);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 722;
		int height = 509;
		this.setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);
		this.setSize(722, 509);
		if(flag.equals("NEW")){
			this.setTitle("���Լƻ�����");
		}else{
			this.setTitle("���Լƻ��޸�");
		}
		this.add(label, null);
		this.add(getTestPlanNameTextField(), null);
		this.add(getTestPlanJScrollPane(), null);
		this.add(label1, null);
		this.add(label2, null);
		this.add(getChoice(), null);
		this.add(label3, null);
		this.add(getServerNameChoice(), null);
		this.add(label4, null);
		this.add(getSceneNameChoice(), null);
		this.add(getAddSceneButton(), null);
		this.add(getDeleteButton(), null);
		this.add(getRemoveAllButton(), null);
		this.add(getCloseButton(), null);
		this.add(getOKButton(), null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				e.getWindow().dispose();
			}
		});
	
	}

	/**
	 * This method initializes testPlanNameTextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTestPlanNameTextField() {
		if (testPlanNameTextField == null) {
			testPlanNameTextField = new TextField();
			testPlanNameTextField.setBounds(new Rectangle(135, 43, 270, 23));
		}
		if(!flag.equals("NEW")){
			testPlanNameTextField.setText(this.testPlanName);
		}
		return testPlanNameTextField;
	}

	/**
	 * This method initializes testPlanJScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getTestPlanJScrollPane() {
		if (testPlanJScrollPane == null) {
			testPlanJScrollPane = new JScrollPane();
			testPlanJScrollPane.setBounds(new Rectangle(20, 104, 675, 319));
			testPlanJScrollPane.setViewportView(getTestPlanJTable());
		}
		return testPlanJScrollPane;
	}

	/**
	 * This method initializes testPlanJTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTestPlanJTable() {
		if (testPlanJTable == null) {
			testPlanJTable = new JTable();
			testPlanJTable.getTableHeader().setReorderingAllowed(false);


		}
		DefaultTableModel model = (DefaultTableModel) testPlanJTable.getModel();
		model.addColumn("ִ��˳����");
		model.addColumn("���ػ���");
		model.addColumn("�ӿ���");
		model.addColumn("������");
		if(!flag.equals("NEW")){
			
			List list=null;
			try {
				list = TestPlanConfigUtil.getSendList(testPlanName);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "���ز�������ʧ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
				return testPlanJTable;
			}
			
			for(int i=0;i<list.size();i++){
				String[] sends=(String[]) list.get(i);
				model.addRow(new String[]{String.valueOf(i+1),sends[0],sends[1],sends[2]});
			}
		}
		return testPlanJTable;
	}

	/**
	 * This method initializes choice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getChoice() {
		if (choice == null) {
			choice = new Choice();
			choice.setBounds(new Rectangle(121, 436, 75, 21));
		}
		
		choice.addItem(Config.PUBLIC_TEMPTATE);
		if(!Config.PUBLIC_TEMPTATE.equals(Config.getConfig("province"))){
			choice.addItem(Config.getConfig("province"));
		}
		return choice;
	}

	/**
	 * This method initializes serverNameChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getServerNameChoice() {
		if (serverNameChoice == null) {
			serverNameChoice = new Choice();
			
			serverNameChoice.setBounds(new Rectangle(270, 435, 195, 21));
			serverNameChoice.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					String itemStr = serverNameChoice.getSelectedItem();
					String bendiName = choice.getSelectedItem();
					String serverName = Config.getTemplateConfig(itemStr);
//					���ݷ��������س����б�
					List list = SceneFileUtil.getBendSceneList(serverName,bendiName);
					//������г���
					sceneNameChoice.removeAll();
					sceneNameChoice.addItem(Config.SCENE_STR);
					for (int i = 0; i < list.size(); i++) {
						sceneNameChoice.addItem((String) list.get(i));
					}
				}
			});
		}
		List list = Config.getTemplateList();
		for(int i=0;i<list.size();i++){
			serverNameChoice.addItem((String) list.get(i));
		}
		return serverNameChoice;
	}

	/**
	 * This method initializes sceneNameChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getSceneNameChoice() {
		if (sceneNameChoice == null) {
			sceneNameChoice = new Choice();
			sceneNameChoice.setBounds(new Rectangle(537, 436, 176, 21));
		}
		return sceneNameChoice;
	}

	/**
	 * This method initializes addSceneButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getAddSceneButton() {
		if (addSceneButton == null) {
			addSceneButton = new Button();
			addSceneButton.setBounds(new Rectangle(103, 467, 106, 23));
			addSceneButton.setLabel("��������");
			addSceneButton.addActionListener(new Btn_AddPlan_Listener(this));
		}
		return addSceneButton;
	}

	/**
	 * This method initializes deleteButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new Button();
			deleteButton.setBounds(new Rectangle(224, 468, 109, 23));
			deleteButton.setLabel("ɾ������");
			deleteButton.addActionListener(new Btn_DeletePlan_Listener(this));
		}
		return deleteButton;
	}

	/**
	 * This method initializes removeAllButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getRemoveAllButton() {
		if (removeAllButton == null) {
			removeAllButton = new Button();
			removeAllButton.setBounds(new Rectangle(348, 468, 106, 23));
			removeAllButton.setLabel("������н���");
			removeAllButton.addActionListener(new Btn_RemoveAll_Listener(this));
		}
		return removeAllButton;
	}

	/**
	 * This method initializes closeButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCloseButton() {
		if (closeButton == null) {
			closeButton = new Button();
			closeButton.setBounds(new Rectangle(597, 469, 112, 23));
			closeButton.setLabel("ȡ��");
			closeButton.addActionListener(new Btn_cancel_Listener(this));
		}
		return closeButton;
	}

	/**
	 * This method initializes OKButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getOKButton() {
		if (OKButton == null) {
			OKButton = new Button();
			OKButton.setBounds(new Rectangle(471, 468, 107, 23));
			OKButton.setLabel("ȷ��");
			OKButton.addActionListener(new Btn_OK_Listener(this));
		}
		return OKButton;
	}
	//��������
	class Btn_AddPlan_Listener implements ActionListener {
		private UpdateTestPlanView main;

		Btn_AddPlan_Listener(UpdateTestPlanView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println("aaaaaaaaaaaaaaaa");
				main.addPlan();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void addPlan() {
		String bendiStr = choice.getSelectedItem();
		String serverCNName = serverNameChoice.getSelectedItem();
		String sceneName = sceneNameChoice.getSelectedItem();
		if(sceneName==null||"".equals(sceneName)||Config.SCENE_STR.equals(sceneName)){
			JOptionPane.showMessageDialog(this, "��ѡ�񳡾�", "��ʾ", JOptionPane.WARNING_MESSAGE);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) testPlanJTable.getModel();
		int rowcount = model.getRowCount();
		int row = testPlanJTable.getSelectedRow();
		if(row==-1){
			model.addRow(new String[]{String.valueOf(++rowcount),bendiStr,serverCNName,sceneName});
		}else{
			List list = new ArrayList();
			List startlist = new ArrayList();
			for(int i=0;i<row;i++){
				String[] str={(String) model.getValueAt(i, 1),(String) model.getValueAt(i, 2),(String) model.getValueAt(i, 3)};
				startlist.add(str);
			}
			for(int i=row;i<rowcount;i++){
				String[] str={(String) model.getValueAt(i, 1),(String) model.getValueAt(i, 2),(String) model.getValueAt(i, 3)};
				list.add(str);
			}
			for(int a=0;a<rowcount;a++){
				model.removeRow(0);
			}
			int j=1;
			for(int c=0;c<startlist.size();c++,j++){
				String[] str = (String[]) startlist.get(c);
				model.addRow(new String[]{String.valueOf(j),str[0],str[1],str[2]});
			}
			model.addRow(new String[]{String.valueOf(j++),bendiStr,serverCNName,sceneName});
			
			for(int i=0;i<list.size();i++,j++){
				String[] str = (String[]) list.get(i);
				model.addRow(new String[]{String.valueOf(j),str[0],str[1],str[2]});
			}
		}
	}
	
	
//	ɾ������
	class Btn_DeletePlan_Listener implements ActionListener {
		private UpdateTestPlanView main;

		Btn_DeletePlan_Listener(UpdateTestPlanView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				
				main.deletePlan();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}
	public void deletePlan() {
		int row = testPlanJTable.getSelectedRow();
		if(row==-1){
			JOptionPane.showMessageDialog(this, "��ѡ����Ҫɾ���Ľ���", "��ʾ", JOptionPane.WARNING_MESSAGE);
			return ;
		}else{
			DefaultTableModel model = (DefaultTableModel) testPlanJTable.getModel();
			int rowcount = model.getRowCount();
			List list = new ArrayList();

			for(int i=row+1;i<rowcount;i++){
				String[] str={(String) model.getValueAt(i, 1),(String) model.getValueAt(i, 2),(String) model.getValueAt(i, 3)};
				list.add(str);
			}
			for(int a=row;a<rowcount;a++){
				model.removeRow(row);
			}
			int j=row+1;
			
			for(int i=0;i<list.size();i++,j++){
				String[] str = (String[]) list.get(i);
				model.addRow(new String[]{String.valueOf(j),str[0],str[1],str[2]});
			}
		}
		
	}
	
	
//	ɾ������
	class Btn_RemoveAll_Listener implements ActionListener {
		private UpdateTestPlanView main;

		Btn_RemoveAll_Listener(UpdateTestPlanView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				
				main.removeAll();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}
	public void removeAll() {
		int rowcount = testPlanJTable.getRowCount();
		DefaultTableModel model = (DefaultTableModel) testPlanJTable.getModel();
		for(int a=0;a<rowcount;a++){
			model.removeRow(0);
		}
		
	}
	
	
//	ɾ������
	class Btn_OK_Listener implements ActionListener {
		private UpdateTestPlanView main;

		Btn_OK_Listener(UpdateTestPlanView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				
				main.okExcu();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}
	public void okExcu(){
		String bendiStr = Config.getConfig("province");
		String testPlanName=testPlanNameTextField.getText();
		if(null==testPlanName||"".equals(testPlanName)){
			JOptionPane.showMessageDialog(this, "���Լƻ����Ʋ���Ϊ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if(!flag.equals("NEW")){
			if(!this.testPlanName.equals(testPlanName)){
				//�����Ƿ�ı�
				if(TestPlanConfigUtil.isTestPlanExits(bendiStr, testPlanName)){
					JOptionPane.showMessageDialog(this, "���Լƻ������Ѿ����� �뻻����", "��ʾ", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
			TestPlanConfigUtil.deleteTestPlan(this.testPlanName);
		}else{
			if(TestPlanConfigUtil.isTestPlanExits(bendiStr, testPlanName)){
				JOptionPane.showMessageDialog(this, "���Լƻ������Ѿ����� �뻻����", "��ʾ", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
		
		StringBuffer buffer = new StringBuffer();
		DefaultTableModel model = (DefaultTableModel) testPlanJTable.getModel();
		int rowcount = model.getRowCount();
		
		for(int i=0;i<rowcount;i++){
			buffer.append(model.getValueAt(i, 1));
			buffer.append("|");
			buffer.append(model.getValueAt(i, 2));
			buffer.append("|");
			buffer.append(model.getValueAt(i, 3));
			buffer.append("\n");
		}
		try {
			TestPlanConfigUtil.writerTestPlanConfigFile(bendiStr, testPlanName, buffer.toString());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "���Ӳ��Լƻ�ʧ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
		}
		tp.updateList();
		this.dispose();
		
	}
	
	
	//ȡ��
	class Btn_cancel_Listener implements ActionListener {
		private UpdateTestPlanView main;

		Btn_cancel_Listener(UpdateTestPlanView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println("aaaaaaaaaaaaaaaa");
				main.cancel();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}
	public void cancel(){
		this.dispose();
	}
}  //  @jve:decl-index=0:visual-constraint="9,7"
