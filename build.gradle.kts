import net.darkhax.curseforgegradle.TaskPublishCurseForge
import java.time.LocalDateTime

val mod_id: String by extra
val qm_version: String by extra
val parchment_version: String by extra
val mc_version: String by extra
val create_version: String by extra
val night_config_version: String by extra
val forge_config_port_version: String by extra
val repository: String by extra
val mod_version: String by extra
val mod_name: String by extra
val mod_author: String by extra
val release_type: String by extra
val modrinth_project_id: String by extra
val curseforge_project_id: String by extra
val jei_version: String by extra
val farmers_delight_version: String by extra
val kubejs_version: String by extra

val localEnv = file(".env").takeIf { it.exists() }?.readLines()?.associate {
    val (key, value) = it.split("=")
    key to value
} ?: emptyMap()

val env = System.getenv() + localEnv
val isCI = env["CI"] == "true"

plugins {
    id("fabric-loom") version ("1.0-SNAPSHOT")
    id("idea")
    id("maven-publish")
    id("net.darkhax.curseforgegradle") version ("1.0.8")
    id("com.modrinth.minotaur") version ("2.+")
    id("org.jetbrains.kotlin.jvm") version ("1.8.21")
}

apply(plugin = "kotlin")

val artifactGroup = "com.possible_triangle"
base {
    archivesName.set("$mod_id-fabric-$mod_version")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withSourcesJar()
    withJavadocJar()
}

loom {
    accessWidenerPath.set(file("src/main/resources/${mod_id}.accesswidener"))
    sourceSets {
        main {
            resources {
                srcDir("src/generated/resources")
                exclude("src/generated/resources/.cache")
            }
        }
    }

    runs {
        create("datagen") {
            client()

            configName = "Data Generation"
            vmArg("-Dfabric-api.datagen")
            vmArg("-Dfabric-api.datagen.output-dir=${file("src/generated/resources")}")
            vmArg("-Dfabric-api.datagen.modid=${mod_id}")
            vmArg("-Dporting_lib.datagen.existing_resources=${file("src/main/resources")}")
        }
    }
}

repositories {
    maven {
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
        content {
            includeGroup("thedarkcolour")
        }
    }

    maven {
        url = uri("https://www.cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }

    maven {
        url = uri("https://api.modrinth.com/maven")
        content {
            includeGroup("maven.modrinth")
        }
    }

    maven {
        url = uri("https://maven.blamejared.com/")
        content {
            includeGroup("mezz.jei")
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

    maven {
        url = uri("https://mvn.devos.one/snapshots/")
        content {
            includeGroup("com.simibubi.create")
            includeGroup("io.github.fabricators_of_create.Porting-Lib")
            includeGroup("io.github.tropheusj")
            includeGroup("com.tterrag.registrate_fabric")
        }
    }

    maven {
        url = uri("https://maven.tterrag.com/")
        content {
            includeGroup("com.jozufozu.flywheel")
        }
    }

    maven {
        url = uri("https://maven.jamieswhiteshirt.com/libs-release")
        content {
            includeGroup("com.jamieswhiteshirt")
        }
    }

    maven {
        url = uri("https://jitpack.io")
        content {
            includeGroup("com.github.LlamaLad7")
            includeGroup("com.github.Chocohead")
        }
    }

    maven {
        url = uri("https://maven.blamejared.com")
        content {
            includeGroup("com.faux.ingredientextension")
        }
    }

    maven {
        url = uri("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
        content {
            includeGroup("net.minecraftforge")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${mc_version}")
    mappings(loom.layered {
        mappings("org.quiltmc:quilt-mappings:${mc_version}+build.${qm_version}:intermediary-v2")
        parchment("org.parchmentmc.data:parchment-${mc_version}:${parchment_version}@zip")
        officialMojangMappings { nameSyntheticMembers = false }
    })

    modImplementation("net.fabricmc:fabric-language-kotlin:1.9.4+kotlin.1.8.21")

    modCompileOnly("mezz.jei:jei-${mc_version}-common-api:${jei_version}")
    modCompileOnly("mezz.jei:jei-${mc_version}-fabric-api:${jei_version}")
    modRuntimeOnly("mezz.jei:jei-${mc_version}-fabric:${jei_version}")

    modApi("com.simibubi.create:create-fabric-${mc_version}:${create_version}")

    modApi(include("com.electronwill.night-config:core:${night_config_version}")!!)
    modApi(include("com.electronwill.night-config:toml:${night_config_version}")!!)
    modApi(include("net.minecraftforge:forgeconfigapiport-fabric:${forge_config_port_version}")!!)

    modApi("curse.maven:farmers-delight-482834:${farmers_delight_version}")

    modCompileOnly("dev.latvian.mods:kubejs-forge:${kubejs_version}")
}

tasks.withType<Jar> {
    from(rootProject.file("LICENSE")) {
        rename { "${it}_${mod_id}" }
    }

    manifest {
        attributes(
            mapOf(
                "Specification-Title" to mod_id,
                "Specification-Vendor" to "examplemodsareus",
                "Specification-Version" to "1",
                "Implementation-Title" to name,
                "Implementation-Version" to mod_version,
                "Implementation-Vendor" to "examplemodsareus",
                "Implementation-Timestamp" to LocalDateTime.now().toString(),
                "MixinConfigs" to "${mod_id}.mixins.json",
            )
        )
    }
}

tasks.withType<ProcessResources> {
    // this will ensure that this task is redone when the versions change.
    inputs.property("version", version)

    filesMatching(listOf("fabric.mod.json", "pack.mcmeta", "${mod_id}.mixins.json")) {
        expand(
            mapOf(
                "mod_version" to mod_version,
                "mod_name" to mod_name,
                "mod_id" to mod_id,
                "mod_author" to mod_author,
                "repository" to repository,
            )
        )
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/${repository}")
            version = version
            credentials {
                username = env["GITHUB_ACTOR"]
                password = env["GITHUB_TOKEN"]
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            groupId = artifactGroup
            artifactId = mod_id
            version = mod_version
            from(components["java"])
        }
    }
}

env["CURSEFORGE_TOKEN"]?.let { token ->
    tasks.register<TaskPublishCurseForge>("curseforge") {
        dependsOn(tasks.jar)

        apiToken = token

        upload(curseforge_project_id, tasks.jar.get().archiveFile).apply {
            changelogType = "html"
            changelog = env["CHANGELOG"]
            releaseType = release_type
            addModLoader("Fabric")
            addGameVersion(mc_version)
            displayName = "Version $mod_version"

            addRelation("create-fabric", "requiredDependency")
            addRelation("fabric-language-kotlin", "requiredDependency")
            addRelation("farmers-delight-fabric", "optionalDependency")
        }
    }
}

env["MODRINTH_TOKEN"]?.let { modrinthToken ->
    modrinth {
        token.set(modrinthToken)
        projectId.set(modrinth_project_id)
        versionNumber.set(mod_version)
        versionName.set("Version $mod_version")
        changelog.set(env["CHANGELOG"])
        gameVersions.set(listOf(mc_version))
        loaders.set(listOf("fabric"))
        versionType.set(release_type)
        uploadFile.set(tasks.jar.get())
        dependencies {
            required.project("Xbc0uyRg")
            required.project("Ha28R6CL")
            optional.project("4EakbH8e")
        }
    }
}