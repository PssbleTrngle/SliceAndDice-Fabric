val mod_id: String by extra
val mod_version: String by extra
val qm_version: String by extra
val parchment_version: String by extra
val mc_version: String by extra
val create_version: String by extra
val night_config_version: String by extra
val forge_config_port_version: String by extra
val architectury_version: String by extra
val rei_version: String by extra
val farmers_delight_version: String by extra
val kubejs_version: String by extra

plugins {
    id("net.somethingcatchy.gradle") version ("0.0.7")
}

withKotlin()

fabric {
    enableMixins()
    dataGen()

    mappings {
        layered {
            mappings("org.quiltmc:quilt-mappings:${mc_version}+build.${qm_version}:intermediary-v2")
            parchment("org.parchmentmc.data:parchment-${mc_version}:${parchment_version}@zip")
            officialMojangMappings { nameSyntheticMembers = false }
        }
    }
}

configure<BasePluginExtension> {
    archivesName.set("$mod_id-fabric-${mod_version}")
}

apply(from = "https://raw.githubusercontent.com/PssbleTrngle/GradleHelper/main/repositories/create-fabric.build.kts")

repositories {
    curseMaven()
    modrinthMaven()

    maven {
        url = uri("https://maven.quiltmc.org/repository/release")
        content {
            includeGroup("org.quiltmc")
        }
    }

    maven {
        url = uri("https://maven.parchmentmc.org")
        content {
            includeGroup("org.parchmentmc.data")
        }
    }

    maven {
        url = uri("https://maven.shedaniel.me/")
        content {
            includeGroup("me.shedaniel")
            includeGroup("me.shedaniel.cloth")
        }
    }

    maven {
        url = uri("https://maven.architectury.dev/")
        content {
            includeGroup("dev.architectury")
        }
    }

    maven {
        url = uri("https://maven.saps.dev/minecraft")
        content {
            includeGroup("dev.latvian.mods")
        }
    }

    maven {
        url = uri("https://maven.theillusivec4.top/")
        content {
            includeGroup("top.theillusivec4.curios")
        }
    }
}

mod {
    includedMods.set(
        listOf(
            "com.electronwill.night-config:core:${night_config_version}",
            "com.electronwill.night-config:toml:${night_config_version}",
            "net.minecraftforge:forgeconfigapiport-fabric:${forge_config_port_version}",
        )
    )
}

dependencies {
    modCompileOnly("me.shedaniel:RoughlyEnoughItems-api-fabric:${rei_version}")
    modCompileOnly("me.shedaniel:RoughlyEnoughItems-default-plugin-fabric:${rei_version}")

    if (!env.isCI) {
        modRuntimeOnly("dev.architectury:architectury-fabric:${architectury_version}")
        modRuntimeOnly("me.shedaniel:RoughlyEnoughItems-fabric:${rei_version}")
    }

    modApi("com.simibubi.create:create-fabric-${mc_version}:${create_version}")

    modApi("curse.maven:farmers-delight-482834:${farmers_delight_version}")

    modCompileOnly("dev.latvian.mods:kubejs-forge:${kubejs_version}")
}

enablePublishing {
    repositories {
        githubPackages(project)
    }
}

uploadToCurseforge {
    dependencies {
        required("create-fabric")
        optional("farmers-delight-fabric")
    }
}

uploadToModrinth {
    dependencies {
        required("Xbc0uyRg")
        optional("4EakbH8e")
    }
}