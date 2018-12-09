package com.quaresma.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.quaresma.todo.model.Item;
import com.quaresma.todo.repository.ItemRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TodoNewApplicationTests {

	@Autowired
	private ItemRepository itemRepository;

	/***/
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void createShouldPersistData() {
		Item item = new Item("Era uma vez", false);
		this.itemRepository.save(item);

		assertThat(item.getId()).isNotNull();
		assertThat(item.getNome()).isEqualTo("Era uma vez");
		assertThat(item.getStatus()).isEqualTo(false);
	}

	@Test
	public void deleteShouldRemoveData() {
		Item item = new Item("Era uma vez", false);
		this.itemRepository.save(item);

		itemRepository.delete(item);

		assertThat(itemRepository.findById(item.getId())).isEmpty();
	}

	@Test
	public void updateShouldChangePersistData() {
		Item item = new Item("Era uma vez", false);
		this.itemRepository.save(item);

		item.setNome("Era duas vezes");
		item.setStatus(true);

		itemRepository.save(item);

		assertThat(item.getNome()).isEqualTo("Era duas vezes");
		assertThat(item.getStatus()).isEqualTo(true);
	}

	@Test
	public void checkDataListItemsActives() {
//		Item item = new Item("Era uma vez", false);
//		Item item2 = new Item("Era uma vez dois", true);
//		Item item3 = new Item("Era uma vez tres", true);
//
//		this.itemRepository.save(item);
//		this.itemRepository.save(item2);
//		this.itemRepository.save(item3);

		List<Item> items = itemRepository.findItemsActives();

		assertThat(items.size()).isEqualTo(1);
	}

}
