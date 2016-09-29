package se.inera.fitnesse

class FitnessePluginExtension {
    def port = 8080
    def root = "FitNesseRoot"
    def workingDir = "src/test/fitnesse"
    def extraProperties = [:]
    def wikiStartPage = ""
    def outputFormat = "text"
}
