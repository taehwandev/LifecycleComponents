import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.get

plugins {
    id("java-platform")
    id("maven-publish")
}

javaPlatform {
    allowDependencies()
}

afterEvaluate {
    val artifactName: String = name
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.android-alatan.lifecyclecomponents"
                artifactId = artifactName
                afterEvaluate { from(components["javaPlatform"]) }
            }
        }
    }
}