package com.christofmeg.justenoughbreeding.recipe;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.JustEnoughBreeding;
import com.christofmeg.justenoughbreeding.utils.CommonUtils;
import com.christofmeg.justenoughbreeding.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BreedingRecipe extends BaseRecipe {
    private LivingEntity currentLivingEntity = null;
    private long lastEntityCreationTime = 0;

    public final EntityType<?> entityType;
    public Ingredient inputStack;
    public Ingredient spawnEgg;
    @Nullable
    public final Boolean needsToBeTamed;
    public Ingredient resultItemStack;
    public @Nullable Ingredient extraInputStack;
    @Nullable
    public final Boolean animalTrusting;
    public static final int ENTITY_CREATION_INTERVAL = 3000;
    public final String modID;
    public final String animalID;

    public BreedingRecipe(EntityType<?> entityType, Ingredient inputStack, Ingredient spawnEgg, @Nullable Boolean needsToBeTamed, @Nullable Ingredient resultItemStack, @Nullable Ingredient extraInputStack, @Nullable Boolean animalTrusting, String modID, String animalID) {
        this.entityType = entityType;
        this.inputStack = inputStack;
        this.spawnEgg = spawnEgg;
        this.needsToBeTamed = needsToBeTamed;
        this.resultItemStack = resultItemStack;
        this.extraInputStack = extraInputStack;
        this.animalTrusting = animalTrusting;
        this.modID = modID;
        this.animalID = animalID;
    }

    public LivingEntity doRendering() {
        long currentTime = System.currentTimeMillis();
        Level level = Minecraft.getInstance().level;

        if (level != null) {
            if (currentLivingEntity == null) {
                currentLivingEntity = (LivingEntity) entityType.create(level);
                lastEntityCreationTime = currentTime;
            }
            if (currentTime - lastEntityCreationTime >= ENTITY_CREATION_INTERVAL) {
                if (!ModList.get().isLoaded("entity_model_features") && !ModList.get().isLoaded("optifine")) {
                    currentLivingEntity = (LivingEntity) entityType.create(level);
                    lastEntityCreationTime = currentTime;
                }
            }
        }

        if (currentLivingEntity != null) {
            if (currentLivingEntity instanceof TamableAnimal tamableAnimal) {
                tamableAnimal.setTame(true);
            }
        }

        return currentLivingEntity;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return new ResourceLocation(CommonConstants.MOD_ID, "breeding" + "/" + this.modID + "/" + this.animalID);
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return JustEnoughBreeding.BREEDING_PROVIDER_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return JustEnoughBreeding.BREEDING_PROVIDER_TYPE.get();
    }

    public void setInputIngredient(Ingredient ingredient) {
        this.inputStack = ingredient;
    }

    public void setExtraInputIngredient(Ingredient ingredient) {
        this.extraInputStack = ingredient;
    }

    public void setOutputIngredient(Ingredient ingredient) {
        this.resultItemStack = ingredient;
    }

    public void setSpawnEggs(Ingredient ingredient) {
        this.spawnEgg = ingredient;
    }

    public static class Serializer implements RecipeSerializer<BreedingRecipe> {
        @Override
        public @NotNull BreedingRecipe fromJson(@NotNull ResourceLocation jsonPath, @NotNull JsonObject json) {

            JsonArray mobs = json.getAsJsonArray("mobs");
            JsonObject mobObject = mobs.get(0).getAsJsonObject();
            Map.Entry<String, JsonElement> mobEntry = mobObject.entrySet().iterator().next();
            String modID = json.get("mod").getAsString();
            String mobName = mobEntry.getKey();
            String modFolder = jsonPath.getPath().substring(0, jsonPath.getPath().lastIndexOf('/')).replace("breeding/", "");

            if (!JustEnoughBreeding.isModLoaded(modFolder) || !JustEnoughBreeding.isModLoaded(modID)) {
                return new BreedingRecipe(null, null, null, null, null, null, null, modFolder + "_" + modID, mobName);
            }

            EntityType<?> entityType = JustEnoughBreeding.getEntityFromLoaderRegistries(new ResourceLocation(modID, mobName));
            if (!mobName.equals(entityType.toShortString())) {
                return new BreedingRecipe(null, null, null, null, null, null, null, modFolder + "_" + modID, mobName);
            }

            List<Ingredient> inputIngredients = new ArrayList<>();
            List<Ingredient> extraInputIngredients = new ArrayList<>();
            List<Ingredient> outputIngredients = new ArrayList<>();
            List<Ingredient> spawnEggs = new ArrayList<>();
            JsonObject mobData = mobEntry.getValue().getAsJsonObject();
            boolean isTamed = mobData.has("tamed") && mobData.get("tamed").getAsBoolean();
            boolean isTrusting = mobData.has("trusting") && mobData.get("trusting").getAsBoolean();

            addIngredients(mobData, inputIngredients, "inputs");
            addIngredients(mobData, extraInputIngredients, "extra_inputs");
            addIngredients(mobData, outputIngredients, "outputs");

            if (mobData.has("spawn_eggs")) {
                addIngredients(mobData, spawnEggs, "spawn_eggs");
            } else {
                ItemStack spawnEgg = Optional.ofNullable(ForgeSpawnEggItem.fromEntityType(entityType))
                        .map(SpawnEggItem::getDefaultInstance)
                        .orElse(ItemStack.EMPTY);
                spawnEggs = new ArrayList<>(List.of(Ingredient.of(spawnEgg)));
            }

            for (BreedingRecipe existingRecipe : JustEnoughBreeding.breedingRecipes) {
                if (existingRecipe.modID.equals(modID) && existingRecipe.animalID.equals(mobName)) {

                    inputIngredients.add(existingRecipe.inputStack);
                    spawnEggs.add(existingRecipe.spawnEgg);
                    outputIngredients.add(existingRecipe.resultItemStack);
                    extraInputIngredients.add(existingRecipe.extraInputStack);

                    existingRecipe.setInputIngredient(Ingredient.merge(inputIngredients));
                    existingRecipe.setExtraInputIngredient(Ingredient.merge(extraInputIngredients));
                    existingRecipe.setOutputIngredient(Ingredient.merge(outputIngredients));
                    existingRecipe.setSpawnEggs(Ingredient.merge(spawnEggs));

                    return existingRecipe;
                }
            }

            BreedingRecipe newRecipe = new BreedingRecipe(
                    entityType,
                    Ingredient.merge(inputIngredients),
                    Ingredient.merge(spawnEggs),
                    isTamed,
                    Ingredient.merge(outputIngredients),
                    Ingredient.merge(extraInputIngredients),
                    isTrusting,
                    modID,
                    mobName
            );

            JustEnoughBreeding.breedingRecipes.add(newRecipe);
            return newRecipe;
        }

        @Override
        public @Nullable BreedingRecipe fromNetwork(@NotNull ResourceLocation resourceLocation, @NotNull FriendlyByteBuf friendlyByteBuf) {
            return null;
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf friendlyByteBuf, @NotNull BreedingRecipe breedingRecipe) {}

        private void addIngredients(JsonObject mobData, List<Ingredient> ingredientList, String memberName) {
            if (mobData.has(memberName)) {
                for (JsonElement input : mobData.getAsJsonArray(memberName)) {
                    if (input.getAsJsonObject().has("item")) {
                        String ingredient = input.getAsJsonObject().get("item").getAsString();
                        JsonElement amountElement = input.getAsJsonObject().get("amount");
                        if (amountElement != null && amountElement.isJsonObject()) {
                            JsonObject amountObj = amountElement.getAsJsonObject();
                            int min = amountObj.has("min") ? amountObj.get("min").getAsInt() : 1;
                            int max = amountObj.has("max") ? amountObj.get("max").getAsInt() : min;
                            for (int i = min; i <= max; i++) {
                                ingredientList.add(Utils.createCombinedIngredient(ingredient, i));
                            }
                        } else {
                            int amount = input.getAsJsonObject().has("amount") ? input.getAsJsonObject().get("amount").getAsInt() : 1;
                            ingredientList.add(Utils.createCombinedIngredient(ingredient, amount));
                        }
                    } else if (input.getAsJsonObject().has("tag")) {
                        String ingredient = input.getAsJsonObject().get("tag").getAsString();
                        ingredientList.add(Utils.createCombinedIngredientFromTag(ingredient));
                    } else if (input.getAsJsonObject().has("meat")) {
                        boolean meat = input.getAsJsonObject().get("meat").getAsBoolean();
                        ingredientList.add(Utils.createCombinedIngredient(CommonUtils.getEdibleMeatItemNames(meat)));
                    }
                }
            }
        }

    }
}
