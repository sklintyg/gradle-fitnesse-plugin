package se.inera.fitnesse

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class Fitnesse extends DefaultTask {
    def port
    def root
    def workingDir
    def extraProperties
    def extraArgs = []
    def wikiStartPage
    def outputFormat
    def boolean useStartPage = false
    def boolean outputToFile = false

    @TaskAction
    def runFitnesse() {
        def startArgs
        if (!useStartPage) {
            startArgs = ['-p', getPort(), '-e', '0', '-d', getWorkingDir(), '-r', getRoot(), '-o'] + extraArgs
        } else {
            startArgs = ['-p', getPort(), '-e', '0', '-d', getWorkingDir(), '-c', getWikiStartPage() + "?suite&format=" + getOutputFormat(), '-r', getRoot(), '-o'] + extraArgs
        }

        if (outputToFile) {
	    def fileFormat
	    switch (getOutputFormat()) {
    	      case 'html':
	        fileFormat = 'html'
	        break
 	      case 'text':
	        fileFormat = 'txt'
     	        break
	      default:
	        fileFormat = 'xml'
  	        break
	    }
	    startArgs.addAll(['-b', 'fitnesse-results.' + fileFormat])
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
        // All jars built in this project plus fitnesse dependency jars
        (project.configurations.archives.artifacts.files + project.configurations.fitnesse.asFileTree).collect { file ->
            "!path ${file.path}"
        }.join("\n")
    }

}
