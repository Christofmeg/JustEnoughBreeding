package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.CommonStrings;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.recipe.TemperRecipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaturalistIntegration {

    static final String MOD = "naturalist";
    static final Map<String, List<TemperRecipe>> temperDataMap = new HashMap<>();

    public static void init() {
        CommonUtils.addTemperAnimal("zebra", new String[]{
                CommonStrings.SUGAR,
                CommonStrings.WHEAT,
                CommonStrings.APPLE,
                CommonStrings.GOLDEN_CARROT,
                CommonStrings.GOLDEN_APPLE,
                CommonStrings.ENCHANTED_GOLDEN_APPLE
        }, new int[]{3, 3, 3, 5, 10, 10}, temperDataMap);
        CommonUtils.addAnimalTempers(temperDataMap, MOD);
    }

}
