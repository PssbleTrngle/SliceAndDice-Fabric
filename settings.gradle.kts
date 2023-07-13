pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://maven.minecraftforge.net/") }
        maven { url = uri("https://maven.fabricmc.net/") }
        maven { url = uri("https://maven.shedaniel.me/") }
        maven { url = uri("https://repo.spongepowered.org/repository/maven-public/") }

        System.getenv()["LOCAL_MAVEN"]?.let { localMaven ->
            maven { url = uri(localMaven) }
        }
    }
}