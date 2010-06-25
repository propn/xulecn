/*
 * Line table
 */
package org.leixu.iap.core.linetable;

import java.util.List;
import java.util.ArrayList;

/**
 * @author hailong.wang
 *
 */
public class LineOrganizer {
	public final ParallelLines hLines = new ParallelLines();
	public final ParallelLines vLines = new ParallelLines();
	public final ParallelLines[] parallels = new ParallelLines[]{hLines, vLines};
	
	/**
	 * 
	 * @param line
	 */
	public void addLine(Line line){
		ParallelLines parallel = parallels[line.orientation];
		parallel.addLine(line);
	}

	/**
	 * is there a line pass this two points ?
	 *  
	 * @return
	 */
	public Line findLine(int orientation, int level, int begin, int end){
		ParallelLines parallel = parallels[orientation];
		return parallel.find(level, begin, end);
	}

	/**
	 * 
	 * @param bottomOpen
	 * @param col
	 * @return
	 */
	public Data findData(List bottomOpen, int col){
		int n = bottomOpen.size();
		for(int i = 0; i < n; i++){
			Data data = (Data)bottomOpen.get(i);
			if(data.col <= col && col <= data.endCol()) return data; 
		}

		return null;
	}

	/**
	 * 
	 *
	 */
	public List calculate(){
		List rows = new ArrayList();
		int[] yCoords = hLines.getLevels();
		int[] xCoords = vLines.getLevels();
		if(yCoords.length <= 0 || xCoords.length <= 0) return rows;

		hLines.snatch(xCoords);
		vLines.snatch(yCoords);

		int lastY = yCoords[0];
		List bottomOpen = new ArrayList();
		for(int i = 1; i < yCoords.length; i++){
			int y = yCoords[i];

			List row = new ArrayList();
			int lastX = xCoords[0];
			Data rightOpen = null;
			for(int j = 1; j < xCoords.length; j++){
				int x = xCoords[j];
				// let's find 4 edges
				Line top = findLine(Line.HORIZENTAL, lastY, lastX, x);
				Line left = findLine(Line.VERTICAL, lastX, lastY, y); 
				Line bottom = findLine(Line.HORIZENTAL, y, lastX, x);
				Line right = findLine(Line.VERTICAL, x, lastY, y);

				int width = x - lastX;
				int height = y - lastY;

				// if rect [lastX, lastY, x, y] exists
				Data data = null;
				if(top != null && left != null){
					data = new Data();
					data.row = i;
					data.col = j;
					data.width = width;
					data.height = height;
					row.add(data);
				}else{
					if(left == null){
						data = rightOpen;
						if(data.row == i){
							data.colspan++;
							data.width += width;
						}
					}else if(top == null) {
						data = findData(bottomOpen, j);
						data.rowspan++;
						data.height += width;
					}
				}
				
				rightOpen = right == null ? data : null;

				if(bottom == null){
					if(!bottomOpen.contains(data)) bottomOpen.add(data);
				}else{
					bottomOpen.remove(data);
				}

				lastX = x;
			}

			rows.add(row);
			lastY = y;
		}
		
		return rows;
	}

	/**
	 * out put simple grid.
	 * for debug
	 * 
	 * @return
	 */
	public List simpleGrid(){
		List rows = new ArrayList();
		int[] yCoords = hLines.getLevels();
		int[] xCoords = vLines.getLevels();

		if(yCoords.length <= 0 || xCoords.length <= 0) return rows;

		hLines.snatch(xCoords);
		vLines.snatch(yCoords);

		int lastY = yCoords[0];
		for(int i = 1; i < yCoords.length; i++){
			int y = yCoords[i];

			List row = new ArrayList();
			int lastX = xCoords[0];
			for(int j = 1; j < xCoords.length; j++){
				int x = xCoords[j];
				int width = x - lastX;
				int height = y - lastY;

				Data data = new Data();
				data.row = i;
				data.col = j;
				data.width = width;
				data.height = height;
				row.add(data);

				lastX = x;
			}

			rows.add(row);
			lastY = y;
		}

		return rows;
	}
}
