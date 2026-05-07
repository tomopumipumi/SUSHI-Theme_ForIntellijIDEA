plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
    id("org.jetbrains.intellij.platform") version "2.1.0"
}

kotlin {
    jvmToolchain(17)
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

group = "com.example.theme"
version = "1.1.1"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2023.3.6")
        instrumentationTools()
    }
}

intellijPlatform {
    pluginConfiguration {
        name = "SUSHI-Theme"

        ideaVersion {
            sinceBuild = "233"
            untilBuild = "261.*"
        }
    }
}

sourceSets {
    main {
        resources.srcDir("resources")
    }
}