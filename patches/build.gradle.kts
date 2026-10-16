group = "com.pigram"

patches {
    about {
        name = "Pigram Patches"
        description = "Pigram: a calm, premium, distraction-free Instagram — keeps DMs, stories, search, profiles and photography, removes reels, explore, ads and noise, with a runtime toggle and an optional permanent lock."
        source = "git@github.com:mykrwt/pigram.git"
        author = "Pigram contributors"
        contact = "https://github.com/mykrwt/pigram/issues"
        website = "https://github.com/mykrwt/pigram"
        license = "GPLv3"
    }
}

dependencies {
    // Provides app.morphe.util.* helpers (DOM, bytecode, references) used by the patches.
    implementation(libs.morphe.patches.library)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}
