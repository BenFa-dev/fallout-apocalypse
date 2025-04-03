package com.apocalypse.thefall.entity.item;

import com.apocalypse.thefall.entity.BaseEntity;
import com.apocalypse.thefall.entity.item.enums.ItemType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "item")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "item_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Item extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ItemType type;

    @Column(columnDefinition = "jsonb", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> names;

    @Column(columnDefinition = "jsonb", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> descriptions;

    @Column(nullable = false)
    private Double weight;

    @Column(name = "base_price", nullable = false)
    private Integer basePrice;

    @Column(name = "path", nullable = false)
    private String path;
}