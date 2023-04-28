[KOTLIN_FORGE]: https://www.curseforge.com/minecraft/mc-mods/kotlin-for-forge
[KOTLIN_FABRIC]: https://www.curseforge.com/minecraft/mc-mods/fabric-language-kotlin
[CREATE_FORGE]: https://www.curseforge.com/minecraft/mc-mods/create
[CREATE_FABRIC]: https://www.curseforge.com/minecraft/mc-mods/create-fabric
[FARMERS_DELIGHT_FORGE]: https://www.curseforge.com/minecraft/mc-mods/farmers-delight
[FARMERS_DELIGHT_FABRIC]: https://www.curseforge.com/minecraft/mc-mods/farmers-delight-fabric
[OVERWEIGHT_FARMING]: https://www.curseforge.com/minecraft/mc-mods/overweight-farming
[NEAPOLITAN]: https://www.curseforge.com/minecraft/mc-mods/neapolitan
[DOWNLOAD]: https://www.curseforge.com/minecraft/mc-mods/slice-and-dice/files
[CURSEFORGE]: https://www.curseforge.com/minecraft/mc-mods/slice-and-dice
[MODRINTH]: https://modrinth.com/mod/slice-and-dice
[ISSUES]: https://github.com/PssbleTrngle/SliceAndDice/issues

<!-- modrinth_exclude.start -->
# Create Slice & Dice

[Looking for the Forge version?](https://github.com/PssbleTrngle/SliceAndDice)

[![Release](https://img.shields.io/github/v/release/PssbleTrngle/SliceAndDice?label=Version&sort=semver)][DOWNLOAD]
[![Downloads](http://cf.way2muchnoise.eu/full_659674_downloads.svg)][CURSEFORGE]
[![Version](http://cf.way2muchnoise.eu/versions/659674.svg)][DOWNLOAD]
[![Issues](https://img.shields.io/github/issues/PssbleTrngle/SliceAndDice?label=Issues)][ISSUES]
[![Modrinth](https://img.shields.io/modrinth/dt/GmjmRQ0A?color=green&logo=modrinth&logoColor=green)][MODRINTH]
<!-- modrinth_exclude.end -->

[![](https://img.shields.io/badge/FORGE%20%20REQUIRES-1e2a41?labelColor=gray&style=for-the-badge)][KOTLIN_FORGE]
[![](https://img.shields.io/badge/KOTLIN%20FOR%20FORGE-blue?logo=kotlin&labelColor=gray&style=for-the-badge)][KOTLIN_FORGE]
[![](https://img.shields.io/badge/CREATE-ae7c38?logo=curseforge&labelColor=gray&style=for-the-badge)][CREATE_FORGE]

[![](https://img.shields.io/badge/FABRIC%20REQUIRES-c6bca5?labelColor=gray&style=for-the-badge)][KOTLIN_FABRIC]
[![](https://img.shields.io/badge/FABRIC%20LANGUAGE%20KOTLIN-blue?logo=kotlin&labelColor=gray&style=for-the-badge)][KOTLIN_FABRIC]
[![](https://img.shields.io/badge/CREATE%20FABRIC-ae7c38?logo=curseforge&labelColor=gray&style=for-the-badge)][CREATE_FABRIC]

### Slicer

This mod enables a variety of features to create better compatibility between mostly [Farmer's Delight][FARMERS_DELIGHT_FABRIC] and [Create][CREATE_FABRIC].
While it is designed to work with Farmer's Delight, it does work without it and also adds some compatibility features for other mods.

![](https://raw.githubusercontent.com/PssbleTrngle/SliceAndDice/1.19.x/screenshots/slicer.png)

### Automatic Cutting

The Main feature of the mod is the _Slicer_, a machine similar to the _Mechanical Mixer_ or _Mechanical Press_ from Create.
It automatically registers all cutting recipes from Farmer's Delight. In that sense, it is an automatic _Cutting Board_.  
In order to use it, the correct tool has to be placed into the machine, using `Right-Click`. 
By default, only knives and axes are allowed, but this behaviour can be overwritten by modifying the `sliceanddice:allowed_tools` item tag.
An example datapack which adds shears to this tag can be found [here](https://github.com/PssbleTrngle/SliceAndDice/raw/1.19.x/example_datapack.zip)

### Automatic Cooking

All recipes from Farmer's delight requiring the Cooking Pot are added as heated mixing recipes.

![](https://raw.githubusercontent.com/PssbleTrngle/SliceAndDice/1.19.x/screenshots/cooking.png)
