package com.christofmeg.justenoughbreeding.config.integrated;

import com.christofmeg.justenoughbreeding.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinecraftIntegration {

    static final List<String> trustableOnly = new ArrayList<>();
    static final Map<String, String> trustingIngredients = new HashMap<>();
    static final Map<String, Integer> trustingChance = new HashMap<>();

    public static void init() {
        CommonUtils.addTrustingOnly("allay", "*", trustableOnly, trustingIngredients, trustingChance);
    }

}
