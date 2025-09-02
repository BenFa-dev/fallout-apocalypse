package com.apocalypse.thefall.service.inventory;

import com.apocalypse.thefall.entity.item.Item;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public Item getItemById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new GameException("error.game.item.notFound", HttpStatus.NOT_FOUND));
    }

}