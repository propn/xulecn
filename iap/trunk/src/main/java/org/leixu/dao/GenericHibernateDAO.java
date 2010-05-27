package org.leixu.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class GenericHibernateDAO <T, ID extends Serializable>
implements GenericDAO<T, ID> {

	
	
	 	private Class<T> persistentClass;
	 
	    public GenericHibernateDAO() {
	        this.persistentClass = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	     }
	 
	 
	    public Class<T> getPersistentClass() {
	        return persistentClass;
	    }


		public T add(T object) {
			// TODO Auto-generated method stub
			return null;
		}


		public void delete(ID id) {
			// TODO Auto-generated method stub
			
		}


		public boolean exists(ID id) {
			// TODO Auto-generated method stub
			return false;
		}


		public List<T> findByNamedQuery(String queryName,
				Map<String, Object> queryParams) {
			// TODO Auto-generated method stub
			return null;
		}


		public T get(ID id) {
			// TODO Auto-generated method stub
			return null;
		}


		public List<T> query(T object) {
			// TODO Auto-generated method stub
			return null;
		}
	    
	

}
