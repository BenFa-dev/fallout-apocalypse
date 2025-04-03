package com.apocalypse.thefall.repository.item;

import com.apocalypse.thefall.entity.instance.ItemInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInstanceRepository extends JpaRepository<ItemInstance, Long> {
}