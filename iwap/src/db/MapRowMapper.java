package db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

public class MapRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rst, int rowNum) throws Exception {

		ResultSetMetaData rsmd = rst.getMetaData();
		int columnCount = rsmd.getColumnCount();

		Map map = new HashMap();

		for (int i = 1; i <= columnCount; i++) {

			String key = rsmd.getColumnName(i);
			int type = rsmd.getColumnType(i);

			Object obj = null;
			if (type == 1 || type == 12)
				obj = rst.getString(i);
			else if (type == -1)
				obj = rst.getLong(i);
			else if (type == 2005)
				obj = rst.getClob(i);
			else
				obj = rst.getObject(i);

			map.put(key, obj);
		}

		return map;
	}

}
