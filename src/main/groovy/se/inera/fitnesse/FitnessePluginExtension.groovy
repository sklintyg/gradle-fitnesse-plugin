package se.inera.fitnesse

class FitnessePluginExtension {
    def port = 8080
    def root = "FitNesseRoot"
    def workingDir = "src/test/fitnesse"
    def extraProperties = [:]
    // Default to StatisticsTests to preserve compability with Statistik-project.
    def wikiStartPage = "StatisticsTests"
}
