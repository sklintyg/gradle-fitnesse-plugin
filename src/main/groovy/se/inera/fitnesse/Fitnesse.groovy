package se.inera.fitnesse

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.internal.SystemProperties;

class Fitnesse extends DefaultTask {
    def port
    def root
    def workingDir
    def extraProperties
    def extraArgs = []
    def wikiStartPage
    def boolean useStartPage = false

    @TaskAction
    def runFitnesse() {
        def startArgs

        if (!useStartPage) {
            startArgs = ['-p', getPort(), '-e', '0', '-d', getWorkingDir(),'-r', getRoot(), '-o'] + extraArgs
        } else {
            startArgs = ['-p', getPort(), '-e', '0', '-d', getWorkingDir(), '-c', getWikiStartPage()+'?suite&format=text','-r', getRoot(), '-o'] + extraArgs
        }

        project.javaexec {
            main = "fitnesse.FitNesse"
            classpath = project.configurations.fitnesse
            systemProperties = ["maven.classpath": mavenPathAsWikiPaths()]
            systemProperties << getExtraProperties()
            args = startArgs
        }
    }

    def mavenPathAsWikiPaths() {
        // All jars built in this project plus dependency jars
        (project.configurations.archives.artifacts.files + project.configurations.runtime.asFileTree).collect { file ->
            "!path ${file.path}"
        }.join("\n")
    }

}
