val mod_name: String by extra

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://maven.minecraftforge.net/") }
        maven { url = uri("https://maven.fabricmc.net/") }
        maven { url = uri("https://maven.shedaniel.me/") }
        maven { url = uri("https://repo.spongepowered.org/repository/maven-public/") }
    }
}

rootProject.name = "SliceAndDice-Fabric"