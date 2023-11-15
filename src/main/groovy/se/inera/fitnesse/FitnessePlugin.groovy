package se.inera.fitnesse

import org.gradle.api.Plugin
import org.gradle.api.Project

class FitnessePlugin implements Plugin<Project> {

    static String EXTENSION_NAME = 'fitnesse'

    @Override
    void apply(Project project) {
        project.apply(plugin: 'java')

        def extension = project.extensions.create(EXTENSION_NAME, FitnessePluginExtension)

        project.configurations { fitnesse }

        project.tasks.register("fitnesseWiki", FitnesseTask) {
            dependsOn project.build
            setDescription("Start the Fitnesse Wiki for editing tests.")
        }

        project.tasks.register("fitnesseTest", FitnesseTask) {
            dependsOn project.build
            setDescription("Run Fitnesse tests. Output summary to console.")
            outputs.upToDateWhen { false }
            useStartPage = true
            if (project.hasProperty('fileOutput')) {
                outputToFile = true
            }
        }

        project.tasks.withType(FitnesseTask).tap {
            configureEach {
                conventionMapping.fitnesseMainClass = { extension.fitnesseMainClass }
                conventionMapping.port = { extension.port }
                conventionMapping.root = { extension.root }
                conventionMapping.workingDir = { extension.workingDir }
                conventionMapping.extraProperties = { extension.extraProperties }
                conventionMapping.wikiStartPage = { extension.wikiStartPage }
                conventionMapping.outputFormat = { extension.outputFormat }
            }
        }
    }
}
