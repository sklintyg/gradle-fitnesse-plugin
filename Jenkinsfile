#!groovy

def buildVersion = "1.0.${BUILD_NUMBER}"

stage('checkout') {
    node {
        git url: "https://github.com/sklintyg/gradle-fitnesse-plugin.git", branch: GIT_BRANCH
        util.run { checkout scm }
    }
}

stage('build') {
    node {
        shgradle "--refresh-dependencies clean build -DbuildVersion=${buildVersion}"
    }
}

stage('tag and upload') {
    node {
        shgradle "uploadArchives tagRelease -DbuildVersion=${buildVersion}"
    }
}
