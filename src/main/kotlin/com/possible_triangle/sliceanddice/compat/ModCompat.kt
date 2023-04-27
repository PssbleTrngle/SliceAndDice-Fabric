package com.possible_triangle.sliceanddice.compat

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry
import com.possible_triangle.sliceanddice.SliceAndDice
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Recipe
import net.minecraft.world.level.ItemLike
import java.util.function.BiConsumer

interface IRecipeInjector {
    fun injectRecipes(existing: Map<ResourceLocation, Recipe<*>>, add: BiConsumer<ResourceLocation, Recipe<*>>)
}

object ModCompat : IRecipeInjector {

    const val FARMERS_DELIGHT = "farmersdelight"

    fun <T> ifLoaded(mod: String, runnable: () -> T): T? {
        return if (FabricLoader.getInstance().isModLoaded(mod)) {
            runnable()
        } else null
    }

    override fun injectRecipes(
        existing: Map<ResourceLocation, Recipe<*>>,
        add: BiConsumer<ResourceLocation, Recipe<*>>,
    ) {
        SliceAndDice.LOGGER.info("Injecting recipes")
        FarmersDelightCompat.ifLoaded { injectRecipes(existing, add) }
    }

    val exampleTool
        get(): ItemLike {
            return ifLoaded(FARMERS_DELIGHT) { ItemsRegistry.IRON_KNIFE.get() } ?: Items.IRON_AXE
        }

    val exampleInput
        get(): ItemLike {
            return ifLoaded(FARMERS_DELIGHT) { Items.CAKE } ?: Items.BIRCH_LOG
        }

    val exampleOutput
        get(): ItemLike {
            return ifLoaded(FARMERS_DELIGHT) { ItemsRegistry.CAKE_SLICE.get() } ?: Items.STRIPPED_BIRCH_LOG
        }

}