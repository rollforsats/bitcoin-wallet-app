enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

// Composite build: resolve the wallet-core library by its published coordinate
// (com.bitcoin.wallet.kmp:shared) via automatic dependency substitution. This is
// addressed by group:artifact, NOT by project path, so it does not collide with
// this build's own :shared module.
includeBuild("../bitcoin-wallet-kmp")

rootProject.name = "bitcoin-wallet-app"
include(":androidApp")
include(":desktopApp")
include(":shared")