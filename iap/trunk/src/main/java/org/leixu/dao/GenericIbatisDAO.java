package org.leixu.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;


public class GenericIbatisDAO<T, PK extends Serializable> implements GenericDAO<T, PK >
{

	private Class<T> persistentClass;
	
	public GenericIbatisDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T add(T object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(PK id) {
		// TODO Auto-generated method stub
		
	}

	public boolean exists(PK id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<T> findByNamedQuery(String queryName,
			Map<String, Object> queryParams) {
		// TODO Auto-generated method stub
		return null;
	}

	public T get(PK id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<T> query(T object) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
