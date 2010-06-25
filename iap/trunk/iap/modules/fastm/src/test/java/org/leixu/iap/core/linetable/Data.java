/*
 * Line table
 */
package org.leixu.iap.core.linetable;

/**
 * @author hailong.wang
 *
 */
public class Data {
	public int row = 0; 
	public int col = 0; 

	public int height = 0;
	public int width = 0;
	public int rowspan = 1;
	public int colspan = 1; 

	public int endCol() {return col + colspan - 1; }
}
