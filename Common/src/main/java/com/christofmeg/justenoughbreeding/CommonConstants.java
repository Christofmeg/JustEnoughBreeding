package com.christofmeg.justenoughbreeding;

import java.util.HashMap;
import java.util.Map;

public class CommonConstants {

	public static final String MOD_ID = "justenoughbreeding";

	public static Map<String, String> sharedGetSpawnEggFromEntity = new HashMap<>();

	public static Map<String, String> breedingIngredients = new HashMap<>();
	public static Map<String, String> breedingExtraIngredients = new HashMap<>();
	public static Map<String, String> breedingGetSpawnEggFromItem = new HashMap<>();
	public static Map<String, String> breedingGetMobFromString = new HashMap<>();
	public static Map<String, String> breedingEggResult = new HashMap<>();
	public static Map<String, Integer> breedingEggResultMinAmount = new HashMap<>();
	public static Map<String, Integer> breedingEggResultMaxAmount = new HashMap<>();
	public static Map<String, Boolean> breedingNeedsToBeTamed = new HashMap<>();
	public static Map<String, Boolean> breedingNeedsToBeTrusting = new HashMap<>();
	public static Map<String, Integer> breedingIngredientAmount = new HashMap<>();
	public static Map<String, Integer> breedingExtraIngredientAmount = new HashMap<>(); //Or use ItemStack with size
	public static Map<String, Integer> breedingCooldown = new HashMap<>();


	public static Map<String, String> tamingIngredients = new HashMap<>();
	public static Map<String, String> tamingExtraIngredients = new HashMap<>();
	public static Map<String, Integer> tamingChance = new HashMap<>();


	public static Map<String, String> temperIngredients = new HashMap<>();
	public static Map<String, String> temperValueIngredientsAdd = new HashMap<>();


	public static Map<String, String> trustingIngredients = new HashMap<>(); //TODO add ALL items from registries if "*" is the item. Needed in minecraft:allay and Trusting
	public static Map<String, Integer> trustingChance = new HashMap<>();


	/*TODO
	https://www.curseforge.com/minecraft/mc-mods/quark #Parrots alteast
	https://www.curseforge.com/minecraft/mc-mods/quark-oddities
	https://www.curseforge.com/minecraft/mc-mods/ice-and-fire-dragons
	https://www.curseforge.com/minecraft/mc-mods/aether
	https://www.curseforge.com/minecraft/mc-mods/aether-redux
	*/
}