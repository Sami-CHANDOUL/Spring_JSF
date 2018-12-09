package com.quaresma.todo.restService;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quaresma.todo.dao.ItemDao;
import com.quaresma.todo.model.Item;
import com.quaresma.todo.restService.response.Response;

@RestController
@RequestMapping("/item")
@CrossOrigin(origins = "*")
public class ItemControllerRest {

	@Autowired
	private ItemDao itemDao;
	
	@GetMapping
	@ResponseBody
	public List<Item> findAll() {

		List<Item> items = new ArrayList<Item>();
		
		items = itemDao.findAll();		
		
		return items;

	}
	
	
}
