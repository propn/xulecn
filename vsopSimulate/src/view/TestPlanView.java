package view;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.Config;
import util.TestPlanConfigUtil;

public class TestPlanView extends Dialog {

	private static final long serialVersionUID = 1L;
	private Label testPlanLabel = null;
	private JScrollPane testPlanJScrollPane = null;
	private JTable testPlanJTable = null;
	private Button addPlanButton = null;
	private Button deletePlanButton = null;
	private Button updatePlanbButton = null;
	private Button excuPlanButton = null;
	private Button closeButton = null;
	private JScrollPane testPlanSendjScrollPane = null;
	private JTable testPlanSendjTable = null;
	private Label label = null;
	private MainFrame frame = null;
	private Label label1 = null;
	private String bendiName =null;
	/**
	 * @param owner
	 */
	public TestPlanView(Frame owner) {
		super(owner);
	
		initialize();
		this.frame = (MainFrame) owner;
	}

			
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		label1 = new Label();
		label1.setBounds(new Rectangle(24, 31, 195, 23));
		label1.setText("当前省："+Config.getConfig("province"));
		label = new Label();
		label.setBounds(new Rectangle(271, 58, 117, 23));
		label.setText("测试计划交易流程：");
		testPlanLabel = new Label();
		testPlanLabel.setBounds(new Rectangle(25, 58, 88, 23));
		testPlanLabel.setText("测试计划列表：");
		this.setLayout(null);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 664;
		int height = 395;
		this.setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);
		this.setSize(723, 412);
		this.setTitle("测试计划");
		this.add(testPlanLabel, null);
		this.add(getTestPlanJScrollPane(), null);
				this.add(getAddPlanButton(), null);
				this.add(getDeletePlanButton(), null);
				this.add(getUpdatePlanbButton(), null);
				this.add(getExcuPlanButton(), null);
				this.add(getCloseButton(), null);
				this.add(getTestPlanSendjScrollPane(), null);
				this.add(label, null);
				this.add(label1, null);
			this.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					e.getWindow().dispose();
				}
			});
	
	}

	/**
	 * This method initializes testPlanJScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getTestPlanJScrollPane() {
		if (testPlanJScrollPane == null) {
			testPlanJScrollPane = new JScrollPane();
			testPlanJScrollPane.setBounds(new Rectangle(26, 88, 226, 260));
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
			testPlanJTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					DefaultTableModel model = (DefaultTableModel) testPlanSendjTable.getModel();
					DefaultTableModel model1 = (DefaultTableModel) testPlanJTable.getModel();
					int selectRow = testPlanJTable.getSelectedRow();
					String testPlanName = (String) model1.getValueAt(selectRow, 0);
					
					int rowCount = model.getRowCount();
					for(int j=0;j<rowCount;j++){
						model.removeRow(0);
					}
					try {
						List list = TestPlanConfigUtil.getSendList(testPlanName);
						
						for(int i=0;i<list.size();i++){
							String[] sends=(String[]) list.get(i);
							model.addRow(new String[]{String.valueOf(i+1),sends[0],sends[1],sends[2]});
						}
					} catch (Exception e1) {
						
					}
				}
			});
		}
		DefaultTableModel model = (DefaultTableModel) testPlanJTable.getModel();
		model.addColumn("测试计划名称");
		String bendiName = Config.getConfig("province");
		List list = TestPlanConfigUtil.getTestPlanList(bendiName);
		for(int i =0;i<list.size();i++){
			model.addRow(new String[]{(String) list.get(i)});
		}
	
	
		return testPlanJTable;
	}

	/**
	 * This method initializes addPlanButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getAddPlanButton() {
		if (addPlanButton == null) {
			addPlanButton = new Button();
			addPlanButton.setBounds(new Rectangle(60, 370, 99, 23));
			addPlanButton.setLabel("新增测试计划");
			addPlanButton.addActionListener(new Btn_AddPlan_Listener(this));
		}
		return addPlanButton;
	}

	/**
	 * This method initializes deletePlanButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getDeletePlanButton() {
		if (deletePlanButton == null) {
			deletePlanButton = new Button();
			deletePlanButton.setBounds(new Rectangle(180, 370, 106, 23));
			deletePlanButton.setLabel("删除测试计划");
			deletePlanButton.addActionListener(new Btn_DeletePlan_Listener(this));
		}
		return deletePlanButton;
	}

	/**
	 * This method initializes updatePlanbButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getUpdatePlanbButton() {
		if (updatePlanbButton == null) {
			updatePlanbButton = new Button();
			updatePlanbButton.setBounds(new Rectangle(309, 370, 104, 23));
			updatePlanbButton.setLabel("修改测试计划");
			updatePlanbButton.addActionListener(new Btn_UpdatePlan_Listener(this));
		}
		return updatePlanbButton;
	}

	/**
	 * This method initializes excuPlanButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getExcuPlanButton() {
		if (excuPlanButton == null) {
			excuPlanButton = new Button();
			excuPlanButton.setBounds(new Rectangle(437, 370, 106, 23));
			excuPlanButton.setLabel("执行测试计划");
			excuPlanButton.addActionListener(new Btn_ExcuPlan_Listener(this));
		}
		return excuPlanButton;
	}

	/**
	 * This method initializes closeButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCloseButton() {
		if (closeButton == null) {
			closeButton = new Button();
			closeButton.setBounds(new Rectangle(568, 370, 100, 23));
			closeButton.setLabel("关闭");
			closeButton.addActionListener(new Btn_Close_Listener(this));
		}
		return closeButton;
	}
	//新增测试计划
//	增加测试计划
	class Btn_AddPlan_Listener implements ActionListener {
		private TestPlanView main;

		Btn_AddPlan_Listener(TestPlanView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.addPlan();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void addPlan() {
		UpdateTestPlanView view = new UpdateTestPlanView(frame,this);
		view.show();
	}
	//删除测试计划
	class Btn_DeletePlan_Listener implements ActionListener {
		private TestPlanView main;

		Btn_DeletePlan_Listener(TestPlanView main) {
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
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "请选择需要删除的测试计划", "提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) testPlanJTable.getModel();
		String testPlanName = (String) model.getValueAt(row, 0);
		int flag = JOptionPane.showConfirmDialog(this, "你确定删除测试计划吗 你确定吗");
		if(flag==0){
			
			TestPlanConfigUtil.deleteTestPlan(testPlanName);
			
		
			
			DefaultTableModel model1 = (DefaultTableModel) testPlanSendjTable.getModel();
			int rowCount = model1.getRowCount();
			for(int j=0;j<rowCount;j++){
				model1.removeRow(0);
			}
			model.removeRow(row);
			
			
		}
		return;
	}
	//修改测试计划
	class Btn_UpdatePlan_Listener implements ActionListener {
		private TestPlanView main;

		Btn_UpdatePlan_Listener(TestPlanView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.updatePlan();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void updatePlan() {
		int row = testPlanJTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "请选择需要修改的测试计划", "提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) testPlanJTable.getModel();
		String testPlanName = (String) model.getValueAt(row, 0);
		UpdateTestPlanView view = new UpdateTestPlanView(frame,this,testPlanName,"UPDATE");
		view.show();
		return;
	}
	
	//执行测试计划
	class Btn_ExcuPlan_Listener implements ActionListener {
		private TestPlanView main;

		Btn_ExcuPlan_Listener(TestPlanView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.excuPlan();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void excuPlan() {
		int row = testPlanJTable.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "请选择需要执行的测试计划", "提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) testPlanJTable.getModel();
		String testPlanName = (String) model.getValueAt(row, 0);
		excuListView view = new excuListView(frame,this,testPlanName);
		view.show();
		return;
	}
	//关闭窗口
	class Btn_Close_Listener implements ActionListener {
		private TestPlanView main;

		Btn_Close_Listener(TestPlanView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.close();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void close() {
		this.dispose();
	}


	/**
	 * This method initializes testPlanSendjScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getTestPlanSendjScrollPane() {
		if (testPlanSendjScrollPane == null) {
			testPlanSendjScrollPane = new JScrollPane();
			testPlanSendjScrollPane.setBounds(new Rectangle(271, 89, 425, 259));
			testPlanSendjScrollPane.setViewportView(getTestPlanSendjTable());
		}
		return testPlanSendjScrollPane;
	}


	/**
	 * This method initializes testPlanSendjTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getTestPlanSendjTable() {
		if (testPlanSendjTable == null) {
			testPlanSendjTable = new JTable();
		}
		DefaultTableModel model = (DefaultTableModel) testPlanSendjTable.getModel();
		model.addColumn("编号");
		model.addColumn("报文归属");
		model.addColumn("接口名称");
		model.addColumn("场景名称");
		return testPlanSendjTable;
	}
	//增加测试计划
	public void updateList(){
		DefaultTableModel model1 = (DefaultTableModel) testPlanSendjTable.getModel();
		int rowcount = model1.getRowCount();
		for(int i=0;i<rowcount;i++){
			model1.removeRow(0);
		}
		DefaultTableModel model = (DefaultTableModel) testPlanJTable.getModel();
		int count = model.getRowCount();
		for(int i=0;i<count;i++){
			model.removeRow(0);
		}
		
		
		String bendiName = Config.getConfig("province");
		List list = TestPlanConfigUtil.getTestPlanList(bendiName);
		for(int i =0;i<list.size();i++){
			model.addRow(new String[]{(String) list.get(i)});
		}
	}
}  //  @jve:decl-index=0:visual-constraint="20,10"
