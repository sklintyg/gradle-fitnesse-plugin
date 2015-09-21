package se.inera.fitnesse

import spock.lang.Specification
import org.gradle.api.*
import org.gradle.testfixtures.ProjectBuilder

class FitnessePluginSpec extends Specification {
    static final WIKI_TASK_NAME = 'fitnesseWiki'
    static final TEST_TASK_NAME = 'fitnesseTest'
    Project project

    def setup() {
        project = ProjectBuilder.builder().build()
    }

    def "Applies plugin and sets extension values"() {
        expect:
        project.tasks.findByName(WIKI_TASK_NAME) == null
        project.tasks.findByName(TEST_TASK_NAME) == null
        when:
        project.apply plugin: 'inera-fitnesse'
        project.fitnesse {
            port = 8125
            root = 'myFitNesseRoot'
            workingDir = 'src/test/fitnesseStart'
        }
        then:
        project.plugins.hasPlugin(FitnessePlugin)
        project.extensions.findByName(FitnessePlugin.EXTENSION_NAME) != null

        Task wikiTask = project.tasks.findByName(WIKI_TASK_NAME)
        wikiTask != null
        wikiTask.description == "Start the Fitnesse Wiki for editing tests"
        wikiTask.getPort() == 8125
        wikiTask.getRoot() == "myFitNesseRoot"
        wikiTask.getWorkingDir() == "src/test/fitnesseStart"

        Task testTask = project.tasks.findByName(TEST_TASK_NAME)
        testTask != null
        testTask.description == "Run Fitnesse tests, outputting summary to console"
        testTask.getPort() == 8125
        testTask.getRoot() == "myFitNesseRoot"
        testTask.getWorkingDir() == "src/test/fitnesseStart"
    }

}