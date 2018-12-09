package com.quaresma.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.quaresma.todo.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

	//@Query(value = "SELECT * FROM todos_esig.item i WHERE i.status = false", nativeQuery = true)
	@Query(value = "SELECT * FROM item i WHERE i.status = false", nativeQuery = true)
	List<Item> findItemsActives();

	//@Query(value = "SELECT * FROM todos_esig.item i WHERE i.status = true", nativeQuery = true)
	@Query(value = "SELECT * FROM item i WHERE i.status = true", nativeQuery = true)
	List<Item> findItemsCompleted();

}
