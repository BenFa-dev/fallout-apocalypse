package com.apocalypse.thefall.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MapUtils {

    /**
     * Extracts a map from a collection by applying key and value extractors.
     * Null elements and null keys are ignored.
     *
     * <p>Example usage:</p>
     * <pre>{@code
     * Map<PerkEnum, Integer> perkMap = MapUtils.extractMap(
     *     character.getPerks(),
     *     instance -> instance.getPerk() != null ? instance.getPerk().getCode() : null,
     *     PerkInstance::getValue
     * );
     * }</pre>
     *
     * @param collection     the source collection (List, Set, etc.)
     * @param keyExtractor   function to extract the key from each element (can return null to skip)
     * @param valueExtractor function to extract the value from each element
     * @param <T>            type of elements in the collection
     * @param <K>            type of keys in the resulting map
     * @param <V>            type of values in the resulting map
     * @return a map of extracted key-value pairs, or an empty map if input is null or empty
     */
    public static <T, K, V> Map<K, V> extractMap(
            Collection<T> collection,
            Function<T, K> keyExtractor,
            Function<T, V> valueExtractor
    ) {
        if (collection == null || collection.isEmpty()) {
            return Map.of(); // return an immutable empty map if input is null or empty
        }

        Map<K, V> result = new HashMap<>();
        for (T item : collection) {
            if (item == null) continue;

            K key = keyExtractor.apply(item);
            if (key == null) continue;

            V value = valueExtractor.apply(item);
            result.put(key, value);
        }

        return result;
    }
}



