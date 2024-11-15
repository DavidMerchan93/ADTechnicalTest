// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.android.hilt) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    dependencies {
        classpath("com.google.firebase:firebase-crashlytics-gradle:3.0.2")
    }
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    // Config detekt plugin
    detekt {
        // Set the detekt configuration from previous steps
        config.setFrom(file("$rootDir/config/detekt-config.yml"))

        // Build upon the default detekt configuration, instead of replacing it
        buildUponDefaultConfig = true

        // Do not activate all detekt rules
        allRules = false

        // Enable automatic correction of issues found by detekt
        autoCorrect = true

        // Run detekt in parallel mode for better performance
        parallel = true
    }

    dependencies {
        detektPlugins("io.nlopez.compose.rules:detekt:0.4.4")
    }
}