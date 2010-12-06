package view;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import message.Send;
import util.Config;
import util.SceneFileUtil;
import util.TemplateFileUtil;
import util.XmlUtil;

/**
 * <pre>
 * Title:VSOP模拟器主界面
 * Description: VSOP模拟器主界面
 * </pre>
 * @author caozj  cao.zhijun3@zte.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class MainFrame extends Frame {

	private String selectServerName = ""; //  当前选择的接口  //  @jve:decl-index=0:
	private static final long serialVersionUID = 1L;
	private TextArea sendXMLTextArea = null;
	private TextArea messageTextArea = null;
	private TextField URLtextField = null;
	private Label label1 = null;
	private Button sendButton = null;
	private Button clearButton = null;
	private Label label2 = null;
	private Button button2 = null;
	private Button button3 = null;
	private Button button4 = null;
	private Button button5 = null;
	private Button button6 = null;
	private Button button7 = null;
	private Choice xmlTempletChoice = null;
	private Label label = null;
	private Button configButton = null;
	private Label messageLabel = null;
	private Button netSimulateButton = null;
	private Button button9 = null;
	private Button button10 = null;
	NetSimulate ns = null;
	NetSimulateQuery nsq = null;
	SPCPInPhase spcpi = null;
	ProductInfoSyn pis = null;
	BindOfferInfoSyn bois = null;
	SPSignInfoView spsigninfo = null;
	private Button spcpInpraseButton = null;
	private Button button = null;
	private Label label3 = null;
	private Choice sceneChoice = null;
	private Button saveXMLButton = null;
	private Label label4 = null;
	private Button newTestPlanButton = null;
	private Choice bendichoice = null;
	/**
	 * This is the default constructor
	 */
	public MainFrame() {
		super();
		ns = new NetSimulate(this);
		nsq = new NetSimulateQuery(this);
		spcpi = new SPCPInPhase(this);
		pis = new ProductInfoSyn(this);
		bois = new BindOfferInfoSyn(this);
		spsigninfo = new SPSignInfoView(this);
		initialize();

	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		label4 = new Label();
		label4.setBounds(new Rectangle(3, 117, 36, 23));
		label4.setText("省份：");
		label3 = new Label();
		label3.setBounds(new Rectangle(430, 116, 75, 23));
		label3.setText("请选择场景：");
		messageLabel = new Label();
		messageLabel.setBounds(new Rectangle(467, 145, 69, 23));
		messageLabel.setText("返回消息：");
		label = new Label();
		label.setBounds(new Rectangle(148, 117, 97, 23));
		label.setText("请选择接口名称：");
		label2 = new Label();
		label2.setBounds(new Rectangle(8, 147, 98, 18));
		label2.setText("发送XML报文：");
		label1 = new Label();
		label1.setBounds(new Rectangle(16, 699, 45, 23));
		label1.setText("URL：");
		this.setLayout(null);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 918;
		int height = 752;
		this.setBounds((d.width - width) / 2, (d.height - height) / 2, width, height);
		this.setSize(918, 738);
		this.setTitle("VSOP模拟器");

		this.add(getTextArea(), null);
		this.add(getTextArea1(), null);
		this.add(getURLtextField(), null);
		this.add(label1, null);
		this.add(getButton(), null);
		this.add(getButton1(), null);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
				System.exit(0);
			}
		});

		this.setBackground(new Color(230, 220, 220));
		this.add(label2, null);

		this.add(getButton2(), null);
		this.add(getButton3(), null);
		this.add(getButton4(), null);
		this.add(getButton5(), null);
		this.add(getButton6(), null);
		this.add(getButton7(), null);
		this.add(getXmlTempletChoice(), null);
		this.add(label, null);
		this.add(getConfigButton(), null);
		this.add(messageLabel, null);
		this.add(getNetSimulateButton(), null);
		this.add(getButton9(), null);
		this.add(getButton10(), null);
		this.add(getSpcpInpraseButton(), null);
		this.add(getButton8(), null);
		this.add(label3, null);
		this.add(getSceneChoice(), null);
		this.add(getSaveXMLButton(), null);
		this.setResizable(false);

		this.add(label4, null);
		this.add(getNewTestPlanButton(), null);
		this.add(getBendichoice(), null);
	}

	/**
	 * This method initializes textArea	
	 * 	
	 * @return java.awt.TextArea	
	 */
	private TextArea getTextArea() {
		if (sendXMLTextArea == null) {
			sendXMLTextArea = new TextArea();
			sendXMLTextArea.setBounds(new Rectangle(6, 166, 447, 521));
			sendXMLTextArea.setForeground(Color.blue);
			sendXMLTextArea.setFont(new Font("Dialog", Font.BOLD, 14));
		}
		return sendXMLTextArea;
	}

	/**
	 * This method initializes textArea1	
	 * 	
	 * @return java.awt.TextArea	
	 */
	private TextArea getTextArea1() {
		if (messageTextArea == null) {
			messageTextArea = new TextArea();
			messageTextArea.setBounds(new Rectangle(465, 167, 434, 522));
			messageTextArea.setForeground(Color.red);
			messageTextArea.setFont(new Font("Book Antiqua", Font.BOLD, 14));
			messageTextArea.setText("消息");
		}
		return messageTextArea;
	}

	/**
	 * This method initializes URLtextField	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getURLtextField() {
		if (URLtextField == null) {
			URLtextField = new TextField();
			URLtextField.setBounds(new Rectangle(63, 700, 498, 23));
			URLtextField.setText(Config.getConfig("URL"));
		}
		return URLtextField;
	}

	/**
	 * This method initializes button	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton() {
		if (sendButton == null) {
			sendButton = new Button();
			sendButton.setBounds(new Rectangle(577, 700, 99, 23));
			sendButton.setLabel("发送");
			//sendButton.addMouseListener(new Btn_Send_Listener(this) );
			sendButton.addActionListener(new Btn_Send_Listener(this));
		}
		return sendButton;
	}

	/**
	 * This method initializes button1	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton1() {
		if (clearButton == null) {
			clearButton = new Button();
			clearButton.setBounds(new Rectangle(693, 699, 96, 23));
			clearButton.setLabel("清除");
			clearButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					sendXMLTextArea.setText("");
					messageTextArea.setText("");
				}
			});
		}
		return clearButton;
	}

	/**
	* This method initializes button2	
	* 	
	* @return java.awt.Button	
	*/
	private Button getButton2() {
		if (button2 == null) {
			button2 = new Button();
			button2.setBounds(new Rectangle(11, 34, 127, 23));
			button2.setLabel("增值产品同步X平台");
			button2.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					pis.show(true);
				}
			});
		}
		return button2;
	}

	/**
	 * This method initializes button3	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton3() {
		if (button3 == null) {
			button3 = new Button();
			button3.setBounds(new Rectangle(140, 34, 126, 23));
			button3.setLabel("增值销售品同步X平台");
			button3.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					bois.show(true);
				}
			});
		}
		return button3;
	}

	/**
	 * This method initializes button4	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton4() {
		if (button4 == null) {
			button4 = new Button();
			button4.setBounds(new Rectangle(396, 34, 126, 23));
			button4.setLabel("ISMP订购关系同步");
		}
		return button4;
	}

	/**
	 * This method initializes button5	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton5() {
		if (button5 == null) {
			button5 = new Button();
			button5.setBounds(new Rectangle(269, 35, 126, 23));
			button5.setLabel("用户状态同步X平台");
		}
		return button5;
	}

	/**
	 * This method initializes button6	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton6() {
		if (button6 == null) {
			button6 = new Button();
			button6.setBounds(new Rectangle(524, 34, 125, 23));
			button6.setLabel("ISMP产品同步");
		}
		return button6;
	}

	/**
	 * This method initializes button7	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton7() {
		if (button7 == null) {
			button7 = new Button();
			button7.setBounds(new Rectangle(652, 34, 127, 23));
			button7.setLabel("ISMP销售品同步");
		}
		return button7;
	}

	/**
	 * This method initializes xmlTempletChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getXmlTempletChoice() {
		if (xmlTempletChoice == null) {
			xmlTempletChoice = new Choice();
			xmlTempletChoice.setBounds(new Rectangle(247, 117, 182, 21));
			//选择报文模板事件  加载报文模板至发送窗口  报文说明文档到返回端口
			xmlTempletChoice.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {

					String itemStr = xmlTempletChoice.getSelectedItem();
					String serverName = Config.getTemplateConfig(itemStr);
					selectServerName = serverName;

					String templateStr = TemplateFileUtil.readTempFile(serverName);

					String descStr = TemplateFileUtil.readDescFile(serverName);
					try {
						sendXMLTextArea.setText(XmlUtil.formatXML(templateStr.getBytes(), "GBK"));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					messageTextArea.setText(descStr);
					//重新修改发送URL
					String oldStr = URLtextField.getText();
					String str = "";
					String sendType = Config.getSendType(itemStr) == null ? "services" : Config.getSendType(itemStr);

					str = Config.getConfig("URL") + sendType + "/";
					
					URLtextField.setText(str + serverName);

					//根据服务名加载场景列表
					List list = SceneFileUtil.getSceneList(serverName);
					//清除所有场景
					sceneChoice.removeAll();
					sceneChoice.addItem(Config.SCENE_STR);
					for (int i = 0; i < list.size(); i++) {
						sceneChoice.addItem((String) list.get(i));
					}
				}
			});
			List list = Config.getTemplateList();
			for (int i = 0; i < list.size(); i++) {
				xmlTempletChoice.addItem((String) list.get(i));
				/*System.out.println(list.get(i));*/
			}

		}
		return xmlTempletChoice;
	}

	/**
	 * This method initializes configButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getConfigButton() {
		if (configButton == null) {
			configButton = new Button();
			configButton.setBounds(new Rectangle(809, 117, 102, 23));
			configButton.setLabel("接口配置");
			configButton.addActionListener(new Btn_Config_Listener(this));
		}
		return configButton;
	}

	/**
	 * This method initializes netSimulateButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getNetSimulateButton() {
		if (netSimulateButton == null) {
			netSimulateButton = new Button();
			netSimulateButton.setBounds(new Rectangle(10, 62, 127, 23));
			netSimulateButton.setLabel("网厅订购申请");
			netSimulateButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					ns.show(true);
				}
			});
		}
		return netSimulateButton;
	}

	/**
	 * This method initializes button9	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton9() {
		if (button9 == null) {
			button9 = new Button();
			button9.setBounds(new Rectangle(138, 62, 126, 23));
			button9.setLabel("网厅订购关系查询");
			button9.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					nsq.show(true);
				}
			});
		}
		return button9;
	}

	/**
	 * This method initializes button10	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton10() {
		if (button10 == null) {
			button10 = new Button();
			button10.setBounds(new Rectangle(266, 62, 127, 23));
			button10.setLabel("短信营业厅模拟器");
		}
		return button10;
	}

	/**
	 * This method initializes spcpInpraseButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getSpcpInpraseButton() {
		if (spcpInpraseButton == null) {
			spcpInpraseButton = new Button();
			spcpInpraseButton.setBounds(new Rectangle(782, 33, 129, 23));
			spcpInpraseButton.setLabel("SP/CP同步");
			spcpInpraseButton.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					spcpi.show(true);
				}
			});
		}
		return spcpInpraseButton;
	}

	/**
	 * This method initializes button	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getButton8() {
		if (button == null) {
			button = new Button();
			button.setBounds(new Rectangle(396, 62, 127, 23));
			button.setLabel("spcp业务能力信息同步");
			button.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					spsigninfo.show(true);
				}
			});
		}
		return button;
	}

	/**
	 * 发送数据
	 * @param e
	 * @throws IOException
	 */
	public void btnOK_actionPerformed(ActionEvent e) throws IOException {
		// 发送数据按钮

		String title = "提示";
		String serverCNName = xmlTempletChoice.getSelectedItem();
		String sceneName = sceneChoice.getSelectedItem();
		int type = JOptionPane.PLAIN_MESSAGE;
		if (URLtextField.getText().equals("")) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "通讯地址不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
		} else if (sendXMLTextArea.getText().equals("")) {
			type = JOptionPane.WARNING_MESSAGE;
			String message = "请求报文不能为空！";
			JOptionPane.showMessageDialog(this, message, title, type);
		} else {
			String requestXml = sendXMLTextArea.getText();
			String sendURL = URLtextField.getText();
			String responseXML = "";
			try {
				messageTextArea.setText("发送请求！");
				responseXML = Send.send(requestXml, sendURL, serverCNName, sceneName);
			} catch (Exception e1) {
				messageTextArea.setText("请求错误！\n原因： 请求报文错误或URL错误");
				e1.printStackTrace();
			}

			messageTextArea.setText(responseXML);
			messageTextArea.enable();
		}

	}

	class Btn_Send_Listener implements ActionListener {
		private MainFrame main;

		Btn_Send_Listener(MainFrame main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				main.btnOK_actionPerformed(e);
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	class Btn_SaveXML_Listener implements ActionListener {
		private MainFrame main;

		Btn_SaveXML_Listener(MainFrame main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				main.btnSaveXML_actionPerformed(e);
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void btnSaveXML_actionPerformed(ActionEvent e) throws IOException {
		String serverCNName = xmlTempletChoice.getSelectedItem();
		SaveXMLView view = new SaveXMLView(this, serverCNName);
		view.show();
	}

	class Btn_Config_Listener implements ActionListener {
		private MainFrame main;

		Btn_Config_Listener(MainFrame main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.btnConfig_actionPerformed(e);
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void btnConfig_actionPerformed(ActionEvent e) throws IOException {
		System.out.println("hello");
		ConfigDialog view = new ConfigDialog(this);
		view.show();
	}

	/**
	 * This method initializes jJToolBarBar	
	 * 	
	 * @return javax.swing.JToolBar	
	 */
	public void setSendXml(String sendXML) {

		try {
			String str = XmlUtil.formatXML(sendXML.getBytes(), "GBK");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendXMLTextArea.setText(sendXML);
	}

	public void setSendURL(String serviceName) {
		String oldStr = URLtextField.getText();
		String str = "";
		if ("".equals(oldStr)) {
			// 如果为空则重新加载地址
			str = Config.getConfig("URL");
		} else {
			str = oldStr.substring(0, oldStr.lastIndexOf('/') + 1);
		}
		URLtextField.setText(str + serviceName);
	}

	/**
	 * This method initializes sceneChoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getSceneChoice() {
		if (sceneChoice == null) {
			sceneChoice = new Choice();
			sceneChoice.setBounds(new Rectangle(507, 117, 187, 21));
			sceneChoice.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					//场景选择 加载场景报文

					String sceneName = sceneChoice.getSelectedItem();
					String serverName = selectServerName;
					if (sceneName.equals(Config.SCENE_STR)) {
						sendXMLTextArea.setText(Config.SCENE_STR);
					} else {
						String str = SceneFileUtil.getSceneFileStr(serverName, sceneName);
						sendXMLTextArea.setText(str);

					}

				}
			});

		}
		return sceneChoice;
	}

	/**
	 * This method initializes saveXMLButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getSaveXMLButton() {
		if (saveXMLButton == null) {
			saveXMLButton = new Button();
			saveXMLButton.setBounds(new Rectangle(702, 117, 101, 23));
			saveXMLButton.setLabel("保存报文为场景");
			saveXMLButton.addActionListener(new Btn_SaveXML_Listener(this));
		}
		return saveXMLButton;
	}

	public String getXmlStr() {
		String xmlStr = sendXMLTextArea.getText();
		return xmlStr;
	}

	/**
	 * This method initializes newTestPlanButton	
	 * 	
	 * @return java.awt.Button	
	 */
	private Button getNewTestPlanButton() {
		if (newTestPlanButton == null) {
			newTestPlanButton = new Button();
			newTestPlanButton.setBounds(new Rectangle(812, 699, 89, 23));
			newTestPlanButton.setLabel("测试计划");
			newTestPlanButton.addActionListener(new Btn_PlanConfig_Listener(this));
		}
		return newTestPlanButton;
	}
	/**
	 * This method initializes bendichoice	
	 * 	
	 * @return java.awt.Choice	
	 */
	private Choice getBendichoice() {
		if (bendichoice == null) {
			bendichoice = new Choice();
			bendichoice.setBounds(new Rectangle(51, 117, 81, 21));
			bendichoice.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					String bendiName = bendichoice.getSelectedItem();
					Config.setConfig("province", bendiName);
				}
			});
		}
		List list = SceneFileUtil.getBendiList();
		for(int i=0;i<list.size();i++){
			bendichoice.addItem((String) list.get(i));
		}
		bendichoice.select(Config.getConfig("province"));
		return bendichoice;
	}

	//程序入口函数
	public static void main(String[] args) {
		new MainFrame().show();
	}

	public void updateDate() {
		List list = Config.getTemplateList();
		xmlTempletChoice.removeAll();
		for (int i = 0; i < list.size(); i++) {
			xmlTempletChoice.addItem((String) list.get(i));
			/*System.out.println(list.get(i));*/
		}
	}
	
	
	//关闭窗口
	class Btn_PlanConfig_Listener implements ActionListener {
		private MainFrame main;

		Btn_PlanConfig_Listener(MainFrame main) {
			this.main = main;
		}

		public void actionPerformed(ActionEvent e) {
			try {

				main.planConfig();
			} catch (Exception ex) {
				e.paramString();
			}

		}

	}

	public void planConfig() {

		TestPlanView view = new TestPlanView(this);
		view.show();
	}
} //  @jve:decl-index=0:visual-constraint="16,-73"