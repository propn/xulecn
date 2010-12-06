package view;

import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.TextField;

public class IPPanel extends Panel {

	private static final long serialVersionUID = 1L;
	private TextField textField1 = null;
	private Label label1 = null;
	private TextField textField2 = null;
	private TextField textField3 = null;
	private TextField textField4 = null;
	private Label label2 = null;
	private Label label3 = null;

	/**
	 * This is the default constructor
	 */
	public IPPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		label3 = new Label();
		label3.setBounds(new Rectangle(136, 3, 8, 23));
		label3.setText(".");
		label2 = new Label();
		label2.setBounds(new Rectangle(89, 4, 8, 23));
		label2.setText(".");
		label1 = new Label();
		label1.setBounds(new Rectangle(41, 4, 8, 23));
		label1.setText(".");
		this.setSize(190, 33);
		this.setLayout(null);
		this.add(getTextField1(), null);
		this.add(label1, null);
		this.add(getTextField2(), null);
		this.add(getTextField3(), null);
		this.add(getTextField4(), null);
		this.add(label2, null);
		this.add(label3, null);
	}

	/**
	 * This method initializes textField1	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTextField1() {
		if (textField1 == null) {
			textField1 = new TextField();
			textField1.setBounds(new Rectangle(5, 4, 35, 23));
		}
		return textField1;
	}

	/**
	 * This method initializes textField2	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTextField2() {
		if (textField2 == null) {
			textField2 = new TextField();
			textField2.setBounds(new Rectangle(51, 5, 36, 23));
			textField2.setText("");
		}
		return textField2;
	}

	/**
	 * This method initializes textField3	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTextField3() {
		if (textField3 == null) {
			textField3 = new TextField();
			textField3.setBounds(new Rectangle(98, 4, 36, 23));
		}
		return textField3;
	}

	/**
	 * This method initializes textField4	
	 * 	
	 * @return java.awt.TextField	
	 */
	private TextField getTextField4() {
		if (textField4 == null) {
			textField4 = new TextField();
			textField4.setBounds(new Rectangle(146, 4, 38, 23));
		}
		return textField4;
	}

} //  @jve:decl-index=0:visual-constraint="20,10"
