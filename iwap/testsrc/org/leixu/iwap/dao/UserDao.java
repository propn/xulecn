/**
 * 
 */
package org.leixu.iwap.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator
 * @param <T>
 * 
 */
public class UserDao extends BaseDao<BaseEntity> {

	public List<BaseEntity> getAll() throws Exception {
		String sql = "select * from BaseEntity";
		return this.query(sql);
	}

	public BaseEntity getBaseEntity(Integer entityId) throws Exception {
		String sql = "select * from BaseEntity where id = ?";
		return this.get(sql, entityId);
	}

	public void save(BaseEntity videoEntity) throws Exception {
		String sql = "INSERT INTO BaseEntity(url,site,pubDate,inDate) VALUES (?,?,?,?)";

		Object[] insertParams = { videoEntity.getUrl(), videoEntity.getSite(),
				videoEntity.getPubDate(), videoEntity.getInDate() };

		this.add(sql, insertParams);
	}

	public void save(List<BaseEntity> entityList) throws Exception {
		for (BaseEntity entity : entityList) {
			this.save(entity);
		}
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		UserDao userDao = new UserDao();
		
		BaseEntity videoEntity=new BaseEntity();
		videoEntity.setId(1);
		videoEntity.setInDate(new Date());
		videoEntity.setPubDate(new Date());
		videoEntity.setSite("qqqqqqqqq");
		videoEntity.setUrl("11111111");
		
		userDao.save(videoEntity);
		
		List<BaseEntity> list=userDao.getAll();
		
		for(Iterator<BaseEntity> it=list.iterator();it.hasNext();){
			BaseEntity e=it.next();
			System.out.println(e.getSite());
			System.out.println(e.getUrl());
			System.out.println(e.getClass());
			System.out.println(e.getId());
			System.out.println(e.getInDate());
			System.out.println(e.getPubDate());
		}

	}

}
