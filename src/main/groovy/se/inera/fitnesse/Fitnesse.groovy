package se.inera.fitnesse

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class Fitnesse extends DefaultTask {
    def port
    def root
    def workingDir
    def extraArgs = []

    @TaskAction
    def runFitnesse() {
        // Defining this variable is a workaround for "getWorkingDir()" returning empty when used within javaexec
        def currentWorkDir = getWorkingDir()
        project.javaexec {
            main = "fitnesse.FitNesse"
            classpath = project.configurations.fitnesse
            systemProperties = ["maven.classpath": mavenPathAsWikiPaths()]
            args = ['-p', getPort(), '-e', '0', '-d', currentWorkDir, '-r', getRoot(), '-o'] + extraArgs
        }
    }

    def mavenPathAsWikiPaths() {
        // All jars built in this project plus dependency jars
        (project.configurations.archives.artifacts.files + project.configurations.runtime.asFileTree).collect { file ->
            "!path ${file.path}"
        }.join("\n")
    }

}
