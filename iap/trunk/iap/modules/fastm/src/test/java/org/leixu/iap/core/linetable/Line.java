/*
 * Line Table
 */
package org.leixu.iap.core.linetable;


/**
 * @author hailong.wang
 *
 * this must be a vertical or horizental line.
 * 
 */
public class Line{
	public static final int HORIZENTAL = 0; 
	public static final int VERTICAL = 1; 

	public int orientation = 0; 
	public int level = 0;
	public int begin = 0;
	public int length = 0;

	/**
	 * 
	 * @return
	 */
	public int getEnd(){
		return begin + length;
	}

	/**
	 * 
	 */
	public void copyFrom(Line line){
		orientation = line.orientation; 
		level = line.length;
		begin = line.begin;
		length = line.length;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setXY(int x, int y){
		switch(orientation){
			case HORIZENTAL:
				level = y;
				begin = x;
				break;
			case VERTICAL:
				level = x;
				begin = y;
				break;
		}
	}

	/**
	 * 
	 * @param begin
	 * @param end
	 */
	public void setBeginEnd(int[] coords){
		begin = coords[0];
		length = coords[1] - begin;
	}

	/**
	 * 
	 * @return
	 */
	public int[] getBeginEnd(){
		return new int[]{begin, getEnd()};
	}

	/**
	 * 
	 * @return
	 */
	public boolean contains(int begin, int end){
		return this.begin <= begin && getEnd() >= end;
	}

	/**
	 * 
	 * @param line
	 * @return
	 */
	public boolean isParallel(Line line){
		return orientation == line.orientation;
	}

	/**
	 * vertical lines with same X
	 * horizental line with same Y
	 * 
	 * @param line
	 * @return
	 */
	public boolean isSameLevel(Line line){
		if(!isParallel(line)) return false;
		return level == line.level;
	}

	/**
	 * try to merge two lines as one
	 * @param line
	 * @return
	 */
	public Line merge(Line line){
		if(!isSameLevel(line)) return null;

		Line line1 = null;
		Line line2 = null;
		
		if(begin <= line.begin){
			line1 = this;
			line2 = line;
		}else{
			line1 = line;
			line2 = this;
		}
		
		int end1 = line1.getEnd();
		if(end1 < line2.begin) return null;
		
		int end2 = line2.getEnd();
		if(end1 > end2) return line1; // line1 contains line2
		
		Line merged = new Line();
		merged.copyFrom(line1);
		merged.length = end2 - merged.begin;
		return merged;
	}
}
