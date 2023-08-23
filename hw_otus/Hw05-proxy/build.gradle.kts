import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id ("java")
    id ("com.github.johnrengelman.shadow")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("Hw05-proxy")
        archiveVersion.set("0.1")
        archiveClassifier.set("")
        manifest {
            attributes(mapOf("Main-Class" to "ru.homework.App"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}