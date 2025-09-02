package com.apocalypse.thefall.service.inventory

import com.apocalypse.thefall.entity.item.Item
import com.apocalypse.thefall.exception.GameException
import com.apocalypse.thefall.repository.item.ItemRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class ItemService(
    private val itemRepository: ItemRepository
) {

    @Transactional(readOnly = true)
    open fun getItemById(itemId: Long): Item {
        return itemRepository.findById(itemId)
            .orElseThrow { GameException("error.game.item.notFound", HttpStatus.NOT_FOUND) }
    }
}
