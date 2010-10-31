package db;

import java.sql.ResultSet;

public interface RowMapper {

	public Object mapRow(ResultSet rs, int rowNum) throws Exception;

}
