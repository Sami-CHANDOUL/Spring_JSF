package com.quaresma.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.quaresma.todo.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{
	
}
