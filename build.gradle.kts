// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("com.android.test") version "8.1.0" apply false
    id("com.android.library") version "8.1.0" apply false
    id("com.google.dagger.hilt.android") version "2.45" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false  // Update if using a newer Kotlin version
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20" apply false // Match this with your Kotlin version
    id("org.jlleitschuh.gradle.ktlint") version "11.4.2" apply false
}
