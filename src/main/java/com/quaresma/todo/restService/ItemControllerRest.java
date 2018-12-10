package com.quaresma.todo.restService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.quaresma.todo.dao.ItemDao;
import com.quaresma.todo.model.Item;

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

	@RequestMapping(method = RequestMethod.POST,
	        produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public void create(@RequestBody Item item) {
		itemDao.saveOrUpdate(item);
	}

}
