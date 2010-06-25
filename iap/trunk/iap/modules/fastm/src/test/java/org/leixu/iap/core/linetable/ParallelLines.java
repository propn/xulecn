/*
 * Line table
 */
package org.leixu.iap.core.linetable;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * @author hailong.wang
 *
 */
public class ParallelLines {
	public final SortedMap map = new TreeMap();

	/**
	 * 
	 * @param line
	 */
	public void addLine(Line line){ 
		Object key = new Integer(line.level);

		List lines = (List)map.get(key);
		if(lines == null){
			lines = new ArrayList();
			map.put(key, lines);
		}

		int n = lines.size();
		int index = 0;
		Line merged = null;
		// try to merge lines
		for(int i = 0; i < n; i++){
			Line l = (Line)lines.get(i);
			merged = line.merge(l);
			if(merged != null){
				index = i;
				break;
			}
		}

		if(merged != null){
			lines.set(index, merged);
		}else{
			lines.add(line);
		}
	}
	
	/**
	 * 
	 * @author hailong.wang
	 *
	 */
	public int[] getLevels(){
		Set set = map.keySet();
		int n = set.size();
		int[] levels = new int[n];
		
		int i = 0;
		for(Iterator it = set.iterator();it.hasNext();){
			levels[i] = ((Integer)it.next()).intValue();
			i++;
		}

		return levels;
	}

	/**
	 * is there a line pass this two points ?
	 *  
	 * @return
	 */
	public Line find(int level, int begin, int end){
		Object key = new Integer(level);
		List lines = (List)map.get(key);
		if(lines == null) return null;

		int n = lines.size();
		for(int i = 0; i < n; i++){
			Line line = (Line)lines.get(i);
			if(line.contains(begin, end)) return line;
		}
		
		return null;
	}

	/**
	 * adjust line's begin & end point to the given coord.
	 *
	 */
	public void snatch(int[] coords){
		Iterator it = map.values().iterator();
		for(;it.hasNext();){
			List list = (List)it.next();
			
			int n = list.size();
			for(int i = 0; i < n; i++){
				Line line = (Line)list.get(i); 

				int[] a = line.getBeginEnd();
				match(coords, a);
				line.setBeginEnd(a);
			}
		}
	}

	/**
	 * 
	 * @param coords
	 * @param points
	 * @return
	 */
	public static void match(int[] coords, int[] points){
		int n = points.length;
		int[][] candidates = new int[n][];

		for(int i = 0; i < coords.length; i++){
			int coord = coords[i];
			
			for(int j = 0; j < n; j++){
				int point = points[j];
				int diff = Math.abs(point - coord);
				int good = coord;
				
				int[] candidate = candidates[j];
				if(candidate == null){
					candidate = new int[]{diff, good};
					candidates[j] = candidate;
				}else if(diff < candidate[0]){
					candidate[0] = diff;
					candidate[1] = good;
				}
			}
		}
		
		for(int j = 0; j < n; j++){
			int[] candidate = candidates[j];
			if(candidate == null) continue;
			points[j] = candidate[1]; 
		}
	}
}
