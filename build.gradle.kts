plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("application")
}

group = "dev.cheercode.connectfour"
//version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.fusesource.jansi:jansi:2.4.1")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("dev.cheercode.connectfour.MainForJar")
}

tasks.test {
    useJUnitPlatform()
}

// Конфигурация для shadowJar
tasks.shadowJar {
    manifest {
        from("src/main/resources/META-INF/MANIFEST.MF")
        attributes(
                "Main-Class" to application.mainClass.get(),
        )
    }
}

val boardSizes = listOf("row6_column7", "row7_column8", "row7_column9", "row7_column10")

tasks.register("generatedShapesList") {
    doLast {
        boardSizes.forEach {
            size ->
            val inputDir = file("src/main/resources/board_masks/$size")
            if (inputDir.exists() && inputDir.isDirectory) {
                val txtFiles = inputDir
                        .listFiles { file -> file.extension == "txt" }
                        ?.sortedBy { it.name }
                        ?.toList() ?: emptyList()

                val outputDir = file("${buildDir}/generated-resources/board_masks/$size")
                outputDir.mkdirs()
                val outputFile = File(outputDir, "shapes.list")
                outputFile.writeText(txtFiles.joinToString(separator = "\n") { it.name })
                println("Generated ${outputFile.absolutePath}")
            } else {
                println("Directory not found or is not a directory: ${inputDir.absolutePath}")
            }
        }
    }
}

sourceSets {
    getByName("main") {
        resources {
            srcDir("$buildDir/generated-resources")
        }
    }
}

tasks.named("processResources") {
    dependsOn("generatedShapesList")
}