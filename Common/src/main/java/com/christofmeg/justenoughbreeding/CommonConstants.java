package com.christofmeg.justenoughbreeding;

//import net.minecraftforge.common.ForgeConfigSpec;

import java.util.HashMap;
import java.util.Map;

public class CommonConstants {

	public static final String MOD_ID = "justenoughbreeding";

	public static Map<String, String> ingredientConfigs = new HashMap<>();
	public static Map<String, String> extraBreedingIngredientConfigs = new HashMap<>();
	public static Map<String, String> extraTamingIngredientConfigs = new HashMap<>();
	public static Map<String, String> spawnEggConfigs = new HashMap<>();
	public static Map<String, Integer> ingredientAmountConfigs = new HashMap<>();
	public static Map<String, String> eggResultConfigs = new HashMap<>();
	public static Map<String, Integer> eggMinAmountConfigs = new HashMap<>();
	public static Map<String, Integer> eggMaxAmountConfigs = new HashMap<>();

	public static Map<String, Integer> breedingCooldown = new HashMap<>();

	public static Map<String, Boolean> animalTamedConfigs = new HashMap<>();
	public static Map<String, Boolean> animalTrustingConfigs = new HashMap<>();

	public static Map<String, String> tamingIngredientConfigs = new HashMap<>();
	public static Map<String, Integer> tamingChanceConfigs = new HashMap<>();
	public static Map<String, String> temperIngredientConfigs = new HashMap<>();
	public static Map<String, String> temperValueConfigs = new HashMap<>();
	public static Map<String, String> trustingIngredientConfigs = new HashMap<>(); //TODO add ALL items from registries if "*" is the item. Needed in minecraft:allay and Trusting
	public static Map<String, Integer> trustingChanceConfigs = new HashMap<>();
}