package com.possible_triangle.sliceanddice.compat

import com.possible_triangle.sliceanddice.SliceAndDice
import mezz.jei.api.IModPlugin
import mezz.jei.api.JeiPlugin
import mezz.jei.api.registration.IRecipeCatalystRegistration
import net.minecraft.resources.ResourceLocation

@JeiPlugin
@Suppress("unused")
class JEICompat : IModPlugin {

    override fun getPluginUid() = ResourceLocation(SliceAndDice.MOD_ID, "jei")

    override fun registerRecipeCatalysts(registration: IRecipeCatalystRegistration) {
        FarmersDelightCompat.ifLoaded {
            //addCatalysts(registration)
        }
    }

}