package com.possible_triangle.sliceanddice.compat

import me.shedaniel.rei.api.client.plugins.REIClientPlugin
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
@Suppress("unused")
class REICompat : REIClientPlugin {

    override fun registerCategories(registry: CategoryRegistry) {
        FarmersDelightCompat.ifLoaded {
            addCatalysts(registry)
        }
    }

}