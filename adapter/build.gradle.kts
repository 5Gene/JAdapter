plugins {
    id("com.android.library")
    alias(vcl.plugins.gene.android)
}

group = "io.github.5gene"
version = "1.0"

android {
    namespace = "sparkj.adapter"
}

dependencies {
    implementation(vcl.androidx.recyclerview)
}