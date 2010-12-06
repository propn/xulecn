package view;

import java.awt.Dialog;
import java.awt.Frame;

public class OfferInfoSyn extends Dialog {

	private static final long serialVersionUID = 1L;

	/**
	 * @param owner
	 */
	public OfferInfoSyn(Frame owner) {
		super(owner);
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(783, 568);
		this.setTitle("X平台增值销售品同步");
	}

} //  @jve:decl-index=0:visual-constraint="10,10"
