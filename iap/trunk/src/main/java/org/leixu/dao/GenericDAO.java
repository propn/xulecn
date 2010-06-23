package org.leixu.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDAO<T, PK extends Serializable> {

	T add(T object);

	void delete(PK id);

	List<T> query(T object);

	T get(PK id);

	List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

	boolean exists(PK id);
}