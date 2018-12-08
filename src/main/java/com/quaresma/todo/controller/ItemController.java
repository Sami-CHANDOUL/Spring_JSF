package com.quaresma.todo.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.primefaces.event.CellEditEvent;
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

	private int quantidadeItemsAtivos;

	private int quantidadeItemsCompletados;

	@PostConstruct
	public void init() {
		item = new Item();
		loadData();
	}

	public String save() {
		item.setStatus(false);
		itemRepository.save(item);
		item = new Item();
		loadData();

		return "/item-form.xhtml?faces-redirect=true";
	}

	/**
	 * Estas anotações são necessárias para carregar a coleção de dados antes da
	 * renderização da view.
	 */
	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() {
		itemsList = itemRepository.findAll();
	}

	public void changeStatusItem(Item item) {
		if (item.getStatus())
			item.setStatus(false);
		else
			item.setStatus(true);

		itemRepository.save(item);

		quantidadeItemsAtivos = itemRepository.findItemsActives().size();
		quantidadeItemsCompletados = itemRepository.findItemsCompleted().size();

	}

	public void deleteItem(Item item) {
		try {
			itemRepository.delete(item);
			loadData();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void deleteItemsCompleted() {

		List<Item> itemsCompleted = itemRepository.findItemsCompleted();

		for (Item item : itemsCompleted) {
			itemRepository.delete(item);
		}
		
		loadData();
		
	}

	public void findItemActives() {
		itemsList = itemRepository.findItemsActives();
	}

	public void findItemCompleted() {
		itemsList = itemRepository.findItemsCompleted();
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		FacesContext context = FacesContext.getCurrentInstance();
		if (newValue != null && !newValue.equals(oldValue)) {
			Item item = context.getApplication().evaluateExpressionGet(context, "#{item}", Item.class);
			itemRepository.save(item);
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public Item getItem() {
		return item;
	}

	public List<Item> getItemsList() {
		return itemsList;
	}

	public int getQuantidadeItemsAtivos() {
		quantidadeItemsAtivos = itemRepository.findItemsActives().size();
		return quantidadeItemsAtivos;
	}

	public int getQuantidadeItemsCompletados() {
		quantidadeItemsCompletados = itemRepository.findItemsCompleted().size();
		return quantidadeItemsCompletados;
	}

}
