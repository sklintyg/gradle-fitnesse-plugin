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

        project.dependencies {
            fitnesse "org.fitnesse:fitnesse:20140901"
        }

        project.task('fitnesseWiki', type: Fitnesse, dependsOn: project.build) {
            description "Start the Fitnesse Wiki for editing tests"
        }

        project.task('fitnesseTest', type: Fitnesse, dependsOn: project.build) {
            description "Run Fitnesse tests, outputting summary to console"
            outputs.upToDateWhen { false }
            extraArgs = ['-c', 'StatisticsTests?suite&format=text']
        }

        project.tasks.withType(Fitnesse) {
            conventionMapping.port = { extension.port }
            conventionMapping.root = { extension.root }
            conventionMapping.workingDir = { extension.workingDir }
        }
    }

}
