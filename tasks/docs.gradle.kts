/**
 * Generate docs
 */
tasks.register("docs") {
    doLast {
        exec {
            commandLine = listOf("./gradlew", "spotlessApply")
        }
    }
    doLast {
        exec {
            commandLine = listOf("./gradlew", "dokkaHtmlMultiModule")
        }
    }
    doLast {
        exec {
            commandLine = listOf("mkdocs", "build")
        }
    }
    doLast {
        exec {
            commandLine = listOf("rm", "-rf", "src/main/resources/api")
        }
    }
    doLast {
        exec {
            commandLine = listOf("rm", "-rf", "src/main/resources/site")
        }
    }
    doLast {
        exec {
            commandLine = listOf("mv", "api", "src/main/resources/")
        }
    }
    doLast {
        exec {
            commandLine = listOf("mv", "site", "src/main/resources/")
        }
    }
}