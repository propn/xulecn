package org.leixu.iap.core.fastm.samples.linetable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.leixu.iap.core.fastm.samples.Common;
import org.leixu.iap.core.linetable.Line;
import org.leixu.iap.core.linetable.LineOrganizer;

public class LineTableDemo {
	static int[][] lines1 = {
		{Line.HORIZENTAL, 0, 0, 400}, 
		{Line.HORIZENTAL, 100, 0, 400}, 
		{Line.HORIZENTAL, 200, 0, 100}, 
		{Line.HORIZENTAL, 200, 300, 100}, 
		{Line.HORIZENTAL, 300, 0, 400}, 
		{Line.HORIZENTAL, 400, 0, 400}, 

		{Line.VERTICAL, 0, 0, 400}, 
		{Line.VERTICAL, 100, 0, 400}, 
		{Line.VERTICAL, 200, 0, 100}, 
		{Line.VERTICAL, 200, 300, 100}, 
		{Line.VERTICAL, 300, 0, 400}, 
		{Line.VERTICAL, 400, 0, 400}, 
	};

	static int[][] lines2 = {
		{Line.HORIZENTAL, 0, 0, 300}, 
		{Line.HORIZENTAL, 100, 0, 300}, 
		{Line.HORIZENTAL, 200, 0, 100}, 
		{Line.HORIZENTAL, 200, 200, 100}, 
		{Line.HORIZENTAL, 300, 0, 200}, 
		{Line.HORIZENTAL, 400, 0, 300}, 

		{Line.VERTICAL, 0, 0, 400}, 
		{Line.VERTICAL, 100, 0, 400}, 
		{Line.VERTICAL, 200, 100, 300}, 
		{Line.VERTICAL, 300, 0, 400}, 
	};

	/**
	 * 
	 * @param lines
	 */
	public static Object getTable(int[][] lines){
		LineOrganizer lineOrg = new LineOrganizer();

		// read config to lines
		for(int i = 0; i < lines.length; i++){
			int[] info = lines[i];
			Line line = new Line();
			line.orientation = info[00];
			line.level = info[1];
			line.begin = info[2];
			line.length = info[3];

			lineOrg.addLine(line);
		}

		List rows = lineOrg.calculate();
		Map table = new HashMap();
		table.put("rows", rows);
		return table;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Object getTables(){
		Object[] tables = {getTable(lines1), getTable(lines2)}; 
		return tables; 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Object tables = getTables();
		Map model = new HashMap();
		model.put("tables", tables);
		Common.write(
				"template/linetable/table.html", 
				model,
				"template/linetable/table.m.html" 
				);
	}

}
