#!groovy

def buildVersion = "1.1.1"

stage('checkout') {
    node {
        git url: "https://github.com/sklintyg/gradle-fitnesse-plugin.git", branch: GIT_BRANCH
        util.run { checkout scm }
    }
}

stage('build') {
    node {
        shgradle11 "--refresh-dependencies clean build -DbuildVersion=${buildVersion}"
    }
}

stage('tag and upload') {
    node {
        shgradle11 "publishPluginMavenPublicationToMavenRepository tagRelease -DbuildVersion=${buildVersion}"
    }
}
