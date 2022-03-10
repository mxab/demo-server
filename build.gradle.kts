import io.quarkus.gradle.extension.QuarkusPluginExtension
import io.quarkus.gradle.tasks.QuarkusBuild
import org.jreleaser.gradle.plugin.JReleaserExtension

plugins {
    java
    id("io.quarkus")
    id("org.jreleaser") version "1.0.0-M3"
    id("nebula.release") version "16.0.0"

}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-qute")
    implementation("io.quarkus:quarkus-resteasy-qute")
    implementation("io.quarkus:quarkus-arc")
    testImplementation("io.quarkus:quarkus-junit5")
}

group = "com.github.mxab.demo"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

configure<JReleaserExtension> {
    project {
        website.set( "https://acme.com/app")
        authors.add( "Duke")
        license.set("Apache-2.0")
        extraProperties.put("inceptionYear", "2021")
    }
    release {
        github {
            owner.set("mxab")

            overwrite.set(true)
        }
    }
    distributions {
        create("app") {
            artifact {
                path.set( file("$buildDir/${project.name.get()}-${project.version.get()}-runner.jar"))
            }

        }
    }
}

tasks.named("release"){
    finalizedBy( "jreleaserFullRelease")
}