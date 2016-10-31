package se.inera.fitnesse

import org.gradle.api.Plugin
import org.gradle.api.Project

class FitnessePlugin implements Plugin<Project> {

    static String EXTENSION_NAME = 'fitnesse'

    @Override
    def void apply(Project project) {
        project.apply(plugin: 'java')

        def extension = project.extensions.create(EXTENSION_NAME, FitnessePluginExtension)

        project.configurations {
            fitnesse
        }

        project.task('fitnesseWiki', type: FitnesseTask, dependsOn: project.build) {
            description "Start the Fitnesse Wiki for editing tests."
        }

        project.task('fitnesseTest', type: FitnesseTask, dependsOn: project.build) {
            description "Run Fitnesse tests. Output summary to console."
            outputs.upToDateWhen { false }
            useStartPage = true
            if (project.hasProperty('fileOutput')) {
                outputToFile = true
            }
        }

        project.tasks.withType(FitnesseTask) {
            conventionMapping.port = { extension.port }
            conventionMapping.root = { extension.root }
            conventionMapping.workingDir = { extension.workingDir }
            conventionMapping.extraProperties = { extension.extraProperties }
            conventionMapping.wikiStartPage = { extension.wikiStartPage }
            conventionMapping.outputFormat = { extension.outputFormat }
        }
    }

}
