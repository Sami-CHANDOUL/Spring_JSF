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

import com.quaresma.todo.dao.ItemDao;
import com.quaresma.todo.messageUtils.Messages;
import com.quaresma.todo.model.Item;

@Scope(value = "session")
@Component(value = "itemController")
@ELBeanName(value = "itemController")
@Join(path = "/", to = "/item-form.jsf")
public class ItemController {

	@Autowired
	private ItemDao itemDao;

	private Item item;

	private List<Item> itemsList;

	private int quantidadeItemsAtivos;

	private int quantidadeItemsCompletados;

	@PostConstruct
	public void init() {
		item = new Item();
		loadData();
	}

	/**
	 * Estas anotações são necessárias para carregar a coleção de dados antes da
	 * renderização da view.
	 */
	@Deferred
	@RequestAction
	@IgnorePostback
	public void loadData() {
		itemsList = itemDao.findAll();
	}

	public String save() {

		if (item.getNome() == null || item.getNome().trim() == "") {
			Messages.generateMessageInfo(FacesMessage.SEVERITY_ERROR, "Campo está vazio.",
					"Campo: \"O que precisa ser feito\", precisa ser preenchido.");
			return null;
		} else {
			item.setStatus(false);
			try {
				itemDao.saveOrUpdate(item);
				item = new Item();
				loadData();
			} catch (Exception e) {
				Messages.generateMessageInfo(FacesMessage.SEVERITY_ERROR, "Erro de persistência.", e.getMessage());
			}

			return "/item-form.xhtml?faces-redirect=true";
		}

	}

	public void changeStatusItem(Item item) {
		if (item.getStatus())
			item.setStatus(false);
		else
			item.setStatus(true);

		itemDao.saveOrUpdate(item);

		quantidadeItemsAtivos = itemDao.findItemsActives().size();
		quantidadeItemsCompletados = itemDao.findItemsCompleted().size();

	}

	public void deleteItem(Item item) {
		try {
			itemDao.delete(item);
			loadData();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void deleteItemsCompleted() {

		List<Item> itemsCompleted = itemDao.findItemsCompleted();

		for (Item item : itemsCompleted) {
			itemDao.delete(item);
		}

		loadData();

	}

	public void findItemActives() {
		itemsList = itemDao.findItemsActives();
	}

	public void findItemCompleted() {
		itemsList = itemDao.findItemsCompleted();
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		FacesContext context = FacesContext.getCurrentInstance();
		if (newValue != null && !newValue.equals(oldValue)) {
			Item item = context.getApplication().evaluateExpressionGet(context, "#{item}", Item.class);
			itemDao.saveOrUpdate(item);
			Messages.generateMessageInfo(FacesMessage.SEVERITY_INFO, "Item alterado",
					"Antigo: " + oldValue + ", Novo:" + newValue);
		}
	}

	public Item getItem() {
		return item;
	}

	public List<Item> getItemsList() {
		return itemsList;
	}

	public int getQuantidadeItemsAtivos() {
		quantidadeItemsAtivos = itemDao.findItemsActives().size();
		return quantidadeItemsAtivos;
	}

	public int getQuantidadeItemsCompletados() {
		quantidadeItemsCompletados = itemDao.findItemsCompleted().size();
		return quantidadeItemsCompletados;
	}

}
