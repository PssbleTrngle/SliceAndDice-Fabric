package com.possible_triangle.sliceanddice.compat

import com.nhoryzon.mc.farmersdelight.integration.rei.FarmersDelightModREI
import com.nhoryzon.mc.farmersdelight.recipe.CookingPotRecipe
import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe
import com.possible_triangle.sliceanddice.Content
import com.possible_triangle.sliceanddice.SliceAndDice
import com.possible_triangle.sliceanddice.config.Configs
import com.possible_triangle.sliceanddice.recipe.CuttingProcessingRecipe
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe
import com.simibubi.create.content.processing.recipe.HeatCondition
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import me.shedaniel.rei.api.common.util.EntryStacks
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.Recipe
import java.util.function.BiConsumer

private fun CuttingBoardRecipe.toBasin(): CuttingProcessingRecipe {
    val basinId = ResourceLocation(SliceAndDice.MOD_ID, "${id.namespace}_${id.path}")
    val builder = ProcessingRecipeBuilder(::CuttingProcessingRecipe, basinId)
    ingredients.forEach { builder.require(it) }
    rollableResults.forEach { builder.output(it.chance, it.stack) }
    return builder.build().copy(tool = tool)
}

class FarmersDelightCompat private constructor() : IRecipeInjector {

    companion object {
        private val INSTANCE = FarmersDelightCompat()

        fun ifLoaded(runnable: FarmersDelightCompat.() -> Unit) {
            ModCompat.ifLoaded(ModCompat.FARMERS_DELIGHT) {
                runnable(INSTANCE)
            }
        }
    }

    fun addCatalysts(registration: CategoryRegistry) {
        registration.addWorkstations(FarmersDelightModREI.CUTTING, EntryStacks.of(Content.SLICER_BLOCK.get()))
    }

    private fun shouldConvert(key: ResourceLocation): Boolean {
        return !key.path.endsWith("_manual_only")
    }

    override fun injectRecipes(existing: Map<ResourceLocation, Recipe<*>>, add: BiConsumer<ResourceLocation, Recipe<*>>) {
        basinCookingRecipes(existing, add)
        processingCutting(existing, add)
    }

    private fun processingCutting(
        recipes: Map<ResourceLocation, Recipe<*>>,
        add: BiConsumer<ResourceLocation, Recipe<*>>,
    ) {
        val cuttingRecipes = recipes
            .filterKeys { shouldConvert(it) }
            .filterValues { it is CuttingBoardRecipe }
            .mapValues { it.value as CuttingBoardRecipe }

        SliceAndDice.LOGGER.debug("Found {} cutting recipes", cuttingRecipes.size)

        cuttingRecipes.forEach { (originalID, recipe) ->
            val id = ResourceLocation(SliceAndDice.MOD_ID, "cutting/${originalID.namespace}/${originalID.path}")
            add.accept(id, recipe.toBasin())
        }
    }

    private fun basinCookingRecipes(
        recipes: Map<ResourceLocation, Recipe<*>>,
        add: BiConsumer<ResourceLocation, Recipe<*>>,
    ) {
        if (!Configs.SERVER.BASIN_COOKING.get()) return

        val emptyingRecipes = recipes.values.filterIsInstance<EmptyingRecipe>()
        val cookingRecipes = recipes
            .filterKeys { shouldConvert(it) }
            .filterValues { it is CookingPotRecipe }
            .mapValues { it.value as CookingPotRecipe }

        SliceAndDice.LOGGER.debug("Found {} cooking recipes", cookingRecipes.size)

        return cookingRecipes.forEach { (originalID, recipe) ->
            val id = ResourceLocation(SliceAndDice.MOD_ID, "cooking/${originalID.namespace}/${originalID.path}")
            val builder = ProcessingRecipeBuilder(::LazyMixingRecipe, id)
            builder.duration(recipe.cookTime)
            builder.requiresHeat(HeatCondition.HEATED)

            recipe.ingredients.forEach { ingredient ->
                builder.require(ingredient)
            }

            if (recipe.container != null && !recipe.container.isEmpty) {
                builder.require(Ingredient.of(recipe.container))
            }

            builder.output(recipe.resultItem)
            add.accept(id, builder.build().withRecipeLookup(emptyingRecipes))
        }
    }

}