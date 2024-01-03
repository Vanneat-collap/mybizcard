pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyBizCard_Rebuild"
include(":app",":smart_lib")
//project(':smart_lib').projectDir = new File(rootDir, 'SMART_LIB_STUDIO_CAMBODIA/smart_lib/')
project(":smart_lib").projectDir = File(rootDir,"SMART_LIB_STUDIO_CAMBODIA/smart_lib/")
 