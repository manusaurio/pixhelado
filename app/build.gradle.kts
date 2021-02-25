import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    application
}

repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("org.hexworks.zircon:zircon.core-jvm:2020.2.0-RELEASE")
    implementation("org.hexworks.zircon:zircon.jvm.libgdx:2020.2.0-RELEASE")
}

application {
    mainClass.set("dev.manusaurio.app.CoreKt")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}