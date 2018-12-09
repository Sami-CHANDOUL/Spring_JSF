package com.quaresma.todo.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {

	void saveOrUpdate(T object);

	void delete(T object);

	T findById(ID id);

	List<T> findAll();

}
