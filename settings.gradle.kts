//插件
pluginManagement {
    repositories {
        //阿里云Maven镜像 https://developer.aliyun.com/mvn/guide
        maven { url = uri("https://maven.aliyun.com/repository/gradle-plugin") } //gradle-plugin 源地址 https://plugins.gradle.org/m2/
        maven { url = uri("https://maven.aliyun.com/repository/public") } //central仓和jcenter仓的聚合仓 源地址 https://repo1.maven.org/maven2/ http://jcenter.bintray.com/
        maven { url = uri("https://maven.aliyun.com/repository/google") } //google 源地址 https://maven.google.com/
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
//项目依赖库
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        //阿里云Maven镜像 https://developer.aliyun.com/mvn/guide
        maven { url = uri("https://maven.aliyun.com/repository/public") } //central仓和jcenter仓的聚合仓 源地址 https://repo1.maven.org/maven2/ http://jcenter.bintray.com/
        maven { url = uri("https://maven.aliyun.com/repository/google") } //google 源地址 https://maven.google.com/
        maven { url = uri("https://jitpack.io") }
        google()
        mavenCentral()
    }
}

//rootProject.name = "My Application"
include(":app")