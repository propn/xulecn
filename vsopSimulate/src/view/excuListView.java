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
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import thread.TestPlanThread;
import util.Config;
import util.TestPlanConfigUtil;

public class excuListView extends Dialog {

	private static final long serialVersionUID = 1L;
	private Label excuLabel = null;
	private JScrollPane excuListjScrollPane = null;
	private JTable excuListjTable = null;
	private Button closeButton = null;
	private TestPlanView  tp = null;
	private String testPlanName = null;
	private List sendList = null;  //  @jve:decl-index=0:
	private List rosposeList = null;  //  @jve:decl-index=0:
	private Label label = null;

	/**
	 * @param owner
	 */
	public excuListView(Frame owner,TestPlanView tp,String testPlanName) {
		super(owner);
		this.tp = tp;
		this.testPlanName = testPlanName;
		initialize();
		
		TestPlanThread thread = new TestPlanThread(this,Config.getConfig("province"),testPlanName);
		thread.start();
	}

	
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		label = new Label();
		label.setBounds(new Rectangle(15, 33, 449, 23));
		label.setText("说明：点击场景前的列显示当前行发送报文，点击以后列显示当前行接收报文");
		excuLabel = new Label();
		excuLabel.setBounds(new Rectangle(16, 57, 71, 21));
		excuLabel.setText("执行列表：");
		this.setLayout(null);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 666;
		int height = 390;
		this.setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);
		this.setSize(738, 457);
		this.setTitle("执行");
		this.add(excuLabel, null);
		this.add(getExcuListjScrollPane(), null);
		this.add(getCloseButton(), null);
		this.add(label, null);
	}

	/**
	 * This method initializes excuListjScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getExcuListjScrollPane() {
		if (excuListjScrollPane == null) {
			excuListjScrollPane = new JScrollPane();
			excuListjScrollPane.setBounds(new Rectangle(14, 75, 710, 341));
			excuListjScrollPane.setViewportView(getExcuListjTable());
		}
		
		
		return excuListjScrollPane;
	}

	/**
	 * This method initializes excuListjTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getExcuListjTable() {
		if (excuListjTable == null) {
			excuListjTable = new JTable();
			excuListjTable.setEnabled(false);
			excuListjTable.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					int row = excuListjTable.getSelectedRow();
					int coum = excuListjTable.getSelectedColumn();
					if(row==-1){
						return;
					}
					if(coum==0||coum==1||coum==2||coum==3){
						StringBuffer buffer = new StringBuffer();
						 buffer.append("发送报文：");
						 buffer.append("\n");
						 buffer.append(sendList.get(row));
						 JOptionPane.showMessageDialog(e.getComponent(),buffer.toString(), "提示", JOptionPane.WARNING_MESSAGE);
					}else if(coum==4||coum==5||coum==6){
						StringBuffer buffer = new StringBuffer();
						 buffer.append("返回报文：");
						 buffer.append("\n");
						 buffer.append(rosposeList.get(row));
						JOptionPane.showMessageDialog(e.getComponent(),buffer.toString(), "提示", JOptionPane.WARNING_MESSAGE);
					}else{
						
					}
					 
				}
			});
		}
		DefaultTableModel model = (DefaultTableModel) excuListjTable.getModel();
		model.addColumn("编号");
		model.addColumn("报文归属");
		model.addColumn("接口名称");
		model.addColumn("场景名称");
		model.addColumn("执行状态");
		model.addColumn("返回结果");
		model.addColumn("返回描述");
		
		List list;
		try {
			list = TestPlanConfigUtil.getSendList(testPlanName);
			for(int i=0;i<list.size();i++){
				String[] sends=(String[]) list.get(i);
				model.addRow(new String[]{String.valueOf(i+1),sends[0],sends[1],sends[2],"未执行","",""});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return excuListjTable;
	}

	/**
	 * This method initializes closeButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getCloseButton() {
		if (closeButton == null) {
			closeButton = new Button();
			closeButton.setBounds(new Rectangle(591, 422, 126, 23));
			closeButton.setEnabled(false);
			closeButton.setLabel("关闭");
			closeButton.addActionListener(new Btn_Close_Listener(this));
		}
		return closeButton;
	}
//	关闭窗口
	class Btn_Close_Listener implements ActionListener {
		private excuListView main;

		Btn_Close_Listener(excuListView main) {
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
	
	
	class Btn_ShowXml_Listener implements ActionListener {
		private excuListView main;

		Btn_ShowXml_Listener(excuListView main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.showXml();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void showXml() {
		int row = excuListjTable.getSelectedRow();
		if(row==-1){
			return;
		}
		 StringBuffer buffer = new StringBuffer();
		 buffer.append("发送报文：");
		 buffer.append("\n");
		 buffer.append(sendList.get(row));
		 buffer.append("\n");
		 buffer.append("返回报文：");
		 buffer.append("\n");
		 buffer.append(rosposeList.get(row));
		 JOptionPane.showMessageDialog(this, "测试计划名称已经存在 请换名字", "提示", JOptionPane.WARNING_MESSAGE);
	}
	public void excuFail(){
		JOptionPane.showMessageDialog(this, "执行失败 读取配置文件失败", "提示", JOptionPane.WARNING_MESSAGE);
		closeButton.enable();
	}
	
	public void setState(int row,int column,String str){
		DefaultTableModel model = (DefaultTableModel) excuListjTable.getModel();
		model.setValueAt(str, row, column);
	}
	public void exceEnd(List sendList ,List responseList){
		this.sendList = sendList;
		this.rosposeList = responseList;
		closeButton.enable();
		excuListjTable.enable();
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,-4"
