package com.quaresma.todo.controller;

import java.util.List;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.quaresma.todo.model.Item;
import com.quaresma.todo.repository.ItemRepository;

@Scope(value = "session")
@Component(value = "itemController")
@ELBeanName(value = "itemController")
@Join(path = "/item", to = "/item-form.jsf")
public class ItemController {

	@Autowired
	private ItemRepository itemRepository;

	private Item item;

	private List<Item> itemsList;

	public String save() {
		item.setStatus(false);
		itemRepository.save(item);
		item = new Item();

		return "/item-form.xhtml?faces-redirect=true";
	}

	public Item getItem() {
		return item;
	}
	
	
	/**Estas anotações são necessárias para carregar a coleção de dados antes da renderização da view.*/
	@Deferred
	@RequestAction
	@IgnorePostback
	public List<Item> getItemsList() {
		return itemsList;
	}

}
