package com.apocalypse.thefall.service.character.rules.util;

import com.apocalypse.thefall.entity.character.stats.enums.SpecialEnum;
import com.apocalypse.thefall.entity.instance.ItemInstance;
import com.apocalypse.thefall.entity.item.Item;
import com.apocalypse.thefall.entity.item.enums.EquippedSlot;
import lombok.extern.slf4j.Slf4j;
import net.objecthunter.exp4j.Expression;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class FormulaUtils {

    public static final String EQUIPPED_PREFIX = "EQUIPPED_";

    public static final Set<String> SPECIAL = Arrays.stream(SpecialEnum.values()).map(Enum::name)
            .collect(Collectors.toUnmodifiableSet());

    // Used to define specific properties for equipped item, for ARMOR or WEAPON.
    public static final Map<EquippedSlot, List<String>> EQUIPPED_PROPERTIES = Map.of(
            EquippedSlot.ARMOR, List.of("ARMOR_CLASS")
    );
    public static final Set<String> EQUIPPED = EQUIPPED_PROPERTIES.entrySet().stream()
            .flatMap(entry -> entry.getValue().stream()
                    .map(prop -> EQUIPPED_PREFIX + entry.getKey().name() + "_" + prop))
            .collect(Collectors.toUnmodifiableSet());

    /**
     * Inject variables from expression
     */
    public static void injectVariables(
            Expression expr,
            Set<String> variableNames,
            Map<SpecialEnum, Integer> specialValues,
            Map<EquippedSlot, ItemInstance> equippedItems
    ) {
        for (String var : variableNames) {
            switch (var) {
                case String varString when SPECIAL.contains(varString) ->
                        injectSpecialVariable(expr, varString, specialValues);
                case String varString when EQUIPPED.contains(varString) ->
                        injectEquippedVariable(expr, varString, equippedItems);
                default -> log.error("Unknown variable in the formula : {}", var);
            }
        }
    }

    /**
     * Injects the special values into the expression if available.
     */
    private static void injectSpecialVariable(
            Expression expr,
            String varString,
            Map<SpecialEnum, Integer> specialValues
    ) {
        try {
            SpecialEnum special = SpecialEnum.valueOf(varString);
            Integer value = specialValues.get(special);
            if (value != null) {
                expr.setVariable(varString, value);
            } else {
                log.warn("Missing value for SPECIAL variable: {}", varString);
            }
        } catch (IllegalArgumentException e) {
            log.error("Invalid SPECIAL enum: {}", varString);
        }
    }

    /**
     * Injects the equipped values into the expression if available.
     */
    public static void injectEquippedVariable(
            Expression expr,
            String varString,
            Map<EquippedSlot, ItemInstance> equippedItems
    ) {
        if (!varString.startsWith(EQUIPPED_PREFIX)) {
            log.warn("EQUIPPED variable does not start with expected prefix '{}': {}", EQUIPPED_PREFIX, varString);
            return;
        }

        String cleanedVar = varString.substring(EQUIPPED_PREFIX.length()); // Removes "EQUIPPED_"

        int underscoreIndex = cleanedVar.indexOf('_');
        if (underscoreIndex < 0) {
            log.warn("Malformed EQUIPPED variable (missing slot or property): {}", varString);
            return;
        }

        String slotName = cleanedVar.substring(0, underscoreIndex);        // ex: "ARMOR"
        String propertyName = cleanedVar.substring(underscoreIndex + 1);   // ex: "ARMOR_CLASS"

        try {
            EquippedSlot slot = EquippedSlot.valueOf(slotName);
            ItemInstance item = equippedItems.get(slot);

            if (item == null) {
                log.info("No item equipped in slot: {}", slot);
                expr.setVariable(varString, 0);
                return;
            }

            int value = extractItemProperty(item, propertyName);
            expr.setVariable(varString, value);

        } catch (IllegalArgumentException e) {
            log.error("Invalid equipped slot name: {}", slotName);
        }
    }

    /**
     * Get corresponding value form the property required by the formula.
     * For example, if itemInstance is an Armor and propertyName is "ARMOR_CLASS", then this method will return the armor class.
     */
    private static int extractItemProperty(ItemInstance itemInstance, String propertyName) {
        try {
            Item item = itemInstance.getItem();
            if (item == null) return 0;

            // Convert "ARMOR_CLASS" → "getArmorClass"
            String[] parts = propertyName.toLowerCase().split("_");
            StringBuilder methodName = new StringBuilder("get");
            for (String part : parts) {
                methodName.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1));
            }

            Object value = item.getClass().getMethod(methodName.toString()).invoke(item);
            return (value instanceof Number num) ? num.intValue() : 0;

        } catch (Exception e) {
            log.warn("Failed to read item property '{}': {}", propertyName, e.getMessage());
            return 0;
        }
    }


}
