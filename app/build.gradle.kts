plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.hqumath.github" //影响R类生成
    compileSdk = 33

    defaultConfig {
        applicationId = "com.hqumath.demo" //影响AndroidManifest中package
        minSdk = 21
        targetSdk = 33
        versionCode = 100001
        versionName = "1.0.1_SignedRelease"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
        dataBinding = true
    }
    /*signingConfigs {
        create("release") {
            storeFile = file { "../key.jks" }
            storePassword = ""
            keyAlias = ""
            keyPassword = ""
            enableV1Signing = true
            enableV2Signing = true
        }
    }*/
    buildTypes {
        debug {
            //signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            //signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    //配置自定义打包名称
    applicationVariants.configureEach {
        outputs.configureEach {
            // 确保只处理 APK 输出（避免处理 App Bundle 等其他类型）
            if (this is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                outputFileName = "android_demo_${defaultConfig.versionName}_${defaultConfig.versionCode}.apk"
            }
        }
    }
}

dependencies {
    //implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    
    implementation(libs.androidx.core.ktx) //Kotlin 扩展库
    implementation(libs.androidx.appcompat) //多语言切换需1.3.0及以上版本
    implementation(libs.androidx.datastore.preferences) //存储结构
    implementation(libs.material)
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    //room
    val room_version = "2.3.0"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version") //注解处理器
    ////////////////网络模块相关////////////////
    //rxjava
    implementation("io.reactivex.rxjava2:rxjava:2.2.9")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")
    //network
    implementation("com.squareup.okhttp3:okhttp:3.12.1")
    implementation("com.squareup.retrofit2:retrofit:2.5.0")
    implementation("com.squareup.retrofit2:converter-gson:2.4.0") //返回数据转换器-Gson
    //implementation("com.squareup.retrofit2:converter-scalars:2.4.0") //返回数据转换器-String
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.4.0") //网络请求适配器

    ////////////////系统功能相关////////////////
    //权限获取
    implementation("com.yanzhenjie:permission:2.0.3")
    //屏幕适配
    implementation("com.github.JessYanCoding:AndroidAutoSize:v1.2.1")
    //live-event-bus
    implementation("io.github.jeremyliao:live-event-bus-x:1.8.0")
    //app升级
    implementation("com.github.jenly1314.AppUpdater:app-updater:1.2.0")
    //异常捕获
    implementation ("com.iqiyi.xcrash:xcrash-android-lib:3.1.0")
    //相机
    val camerax_version = "1.2.3"
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-view:${camerax_version}") // 如果需要使用 CameraView
    ////////////////UI组件相关////////////////
    //二维码
    implementation("com.github.jenly1314:zxing-lite:3.0.1")
    //下拉刷新
    implementation("io.github.scwang90:refresh-layout-kernel:3.0.0-alpha") //核心必须依赖
    implementation("io.github.scwang90:refresh-header-classics:3.0.0-alpha") //经典刷新头
    //侧滑菜单
    implementation("com.yanzhenjie.recyclerview:x:1.3.2")
    //picture
    implementation("com.github.bumptech.glide:glide:4.12.0")
}
