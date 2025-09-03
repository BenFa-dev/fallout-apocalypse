package com.apocalypse.thefall.util

object MapUtils {

    /**
     * Extracts a map from a collection by applying key and value extractors.
     * Null elements and null keys are ignored.
     *
     * Example usage:
     * ```kotlin
     * val perkMap = MapUtils.extractMap(
     *     character.perks,
     *     { it.perk?.code },
     *     { it.value }
     * )
     * ```
     *
     * @param collection     the source collection (List, Set, etc.)
     * @param keyExtractor   function to extract the key from each element (can return null to skip)
     * @param valueExtractor function to extract the value from each element
     * @return a map of extracted key-value pairs, or an empty map if input is null or empty
     */
    fun <T, K, V> extractMap(
        collection: Collection<T>?,
        keyExtractor: (T) -> K?,
        valueExtractor: (T) -> V
    ): Map<K, V> {
        if (collection.isNullOrEmpty()) {
            return emptyMap() // return an immutable empty map if input is null or empty
        }

        val result = mutableMapOf<K, V>()
        for (item in collection) {
            val key = keyExtractor(item) ?: continue
            val value = valueExtractor(item)
            result[key] = value
        }

        return result
    }
}
