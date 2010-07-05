package com.asm.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.asm.dao.UserDao;
import com.asm.entity.User;

@Transactional
public class UserDaoImp implements UserDao {
	private JdbcTemplate jdbcTemplate;

	public void setDatasouce(DataSource datasource) {
		jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Override
	public void delete(User user) {
		jdbcTemplate.update("delete from user where id=?", new Object[] { user.getId() },
				new int[] { java.sql.Types.INTEGER });
		//增加如下代码：
		int i=5/0;
		jdbcTemplate.update("delete from user where id=2");
	}

	@Override
	@Transactional(readOnly=true)
	public User get(int id) {
		return (User) jdbcTemplate.queryForObject("select * from user where id=?",
				new Object[] { id }, new int[] { java.sql.Types.INTEGER }, new RowMapper() {
					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
						User user = new User();
						user.setId(rs.getInt("id"));
						user.setName(rs.getString("name"));
						return user;
					}
				});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers() {
		return (List<User>) jdbcTemplate.query("select * from user", new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				return user;
			}
		});
	}

	@Override
	public void save(User user) {
		jdbcTemplate.update("insert into user(name) values(?)", new Object[] { user.getName() },
				new int[] { java.sql.Types.VARCHAR });
	}

	@Override
	public void update(User user) {
		jdbcTemplate.update("update user set name=? where id=?", new Object[] { user.getName(),
				user.getId() }, new int[] { java.sql.Types.VARCHAR, java.sql.Types.INTEGER });
	}

}
