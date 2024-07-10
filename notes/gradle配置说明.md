
##  根目录下 build.gradle 
```js
buildscript {

    repositories {  //repositories闭包
        google()
        jcenter() //代码托管库：设置之后可以在项目中轻松引用jcenter上的开源项目
    }
    dependencies {  //dependencies闭包
        classpath 'com.android.tools.build:gradle:3.0.0' 声明gradle插件，插件版本号为3.0.0
        //gradle是一个强大的项目构建工具，不仅可以构建Android，还可以构建java，C++等
        //此处引用android的插件
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter() //代码托管库：设置之后可以在项目中轻松引用jcenter上的开源项目
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```
## app 目录下的build.gradle
```js
 // 声明是Android程序，
   // com.android.application 表示这是一个应用程序模块,可直接运行
   // com.android.library 标识这是一个库模块，是依附别的应用程序运行
  apply plugin: 'com.android.application
   
  android {
       // 程序在编译的时候会检查lint，有任何错误提示会停止build，我们可以关闭这个开关
       lintOptions {
           // 即使报错也不会停止打包
          abortOnError false
          // 打包release版本的时候是否进行检测
          checkReleaseBuilds false
      }
  
      // 编译sdk的版本，也就是API Level，例如API-19、API-20、API-21等等。
     compileSdkVersion 27
      // build tools的版本，其中包括了打包工具aapt、dx等等。
      // 这个工具的目录位于你的sdk目录/build-tools/下
     buildToolsVersion '27.0.3'
 
      //关闭Android Studio的PNG合法性检查
      aaptOptions.cruncherEnabled = false
      aaptOptions.useNewCruncher = false
  
      defaultConfig {  // 默认配置
          applicationId "com.demo.test" // 应用程序的包名
          minSdkVersion 22 /最小sdk版本，如果设备小于这个版本或者大于maxSdkVersion将无法安装这个应用
          targetSdkVersion 27 // 目标sdk版本，充分测试过的版本（建议版本）
          versionCode 1  // 版本号，第一版是1，之后每更新一次加1
          versionName "1.0" // 版本名，显示给用户看到的版本号
  
          archivesBaseName = "demo-$versionName" // 指定打包成Jar文件时候的文件名称
          ndk {
              moduleName "testwifisafe"                   // 设置库(so)文件名称
              ldLibs "log", "z", "m", "jnigraphics", "android"
              // 引入库，比如要用到的__android_log_print
              abiFilters "armeabi", "x86", "armeabi-v7a"    // "x86"显示指定支持的ABIs
              cFlags "-std=c++11 -fexceptions"                // C++11
              stl "gnustl_static"
          }
  
          // 当方法数超过65535(方法的索引使用的是一个short值，
          // 而short最大值是65535)的时候允许打包成多个dex文件，动态加载dex。这里面坑很深啊
          multiDexEnabled true
  
          // Instrumentation单元测试
          testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
      }
  
      // 默认的一些文件路径的配置
      sourceSets {
          main {
              manifest.srcFile 'AndroidManifest.xml' // 指定清单文件
              res.srcDirs = ['res'] // 指定res资源目录
              assets.srcDirs = ['assets']    // asset资源目录
              jni.srcDirs 'src/main/jni'     // jni代码目录
              jniLibs.srcDir 'src/main/jniLibs' // jni库目录
              java.srcDirs = ['src'] // 指定java源代码目录
              resources.srcDirs = ['src'] // 指定resource目录
              aidl.srcDirs = ['src'] // 指定aidl目录
              renderscript.srcDirs = ['src'] // 指定source目录
          }
          debug.setRoot('build-types/debug') // 指定debug模式的路径
          release.setRoot('build-types/release') // 指定release模式的路径
      }
  
      // multiDex的一些相关配置，这样配置可以让你的编译速度更快
      dexOptions {
          // 让它不要对Lib做preDexing
          preDexLibraries = false
          // 开启incremental dexing,优化编译效率，这个功能android studio默认是关闭的。
          incremental true
          javaMaxHeapSize "4g" // 增加java堆内存大小
      }
  
      signingConfigs {// 签名配置
          release {// 发布版签名配置
              storeFile file("fk.keystore") // 密钥文件路径
              storePassword "123456" // 密钥文件密码
              keyAlias "fk" // key别名
              keyPassword "123456" // key密码
          }
          debug {// debug版签名配置
              storeFile file("fk.keystore")
              storePassword "123456"
              keyAlias "fk"
              keyPassword "123456"
          }
      }
  
      // 指定生成安装文件的配置，常有两个子包:release,debug，注：直接运行的都是debug安装文件
      buildTypes {
          // release版本的配置，即生成正式版安装文件的配置
          release {
              zipAlignEnabled true  // 是否支持zip
              shrinkResources true  // 移除无用的resource文件
              minifyEnabled false // 是否对代码进行混淆，true表示混淆
              // 指定混淆时使用的规则文件；
              // proguard-android.txt指所有项目通用的混淆规则，proguard-rules.pro当前项目特有的混淆规
             // release的Proguard默认为Module下的proguard-rules.pro文件
          proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
             debuggable false  //是否支持调试
             //ndk的一些配置
             ndk {
                 // cFlags "-std=c++11 -fexceptions -O3 -D__RELEASE__" // C++11
                 // platformVersion = "19"
                 moduleName "hebbewifisafe" // 设置库(so)文件名称
                 ldLibs "log", "z", "m", "jnigraphics", "android"
                 // 引入库，比如要用到的__android_log_print
                 abiFilters "armeabi", "x86", "armeabi-v7a"// "x86"
                 cFlags "-std=c++11 -fexceptions" // C++11
                 stl "gnustl_static"
             }
             // 采用动态替换字符串的方式生成不同的 release.apk （3.0之后版本的修改方式）
             applicationVariants.all { variant ->
                 variant.outputs.all { output ->
                     if (!variant.buildType.isDebuggable()) {
                         // 获取签名的名字 variant.signingConfig.name
                         // 要被替换的源字符串
                      def sourceFile = "app-release";
                         // 替换的字符串
　　　　　　　　　　　　　def replaceFile = "Demo-V${variant.versionName}.${releaseTime()}"
　　　　　　　　　　　　　outputFileName = output.outputFile.name.replace(sourceFile, replaceFile)
                     }
                 }
             }
             jniDebuggable false  // 关闭jni调试
         }
         debug { // debug版本的配置
             minifyEnabled false
             zipAlignEnabled true
             shrinkResources true // 移除无用的resource文件
             proguardFiles getDefaultProguardFile('proguard-android.txt')
, 'proguard-rules.pro'
             debuggable true
 //          jniDebuggable true
             ndk {
                 cFlags "-std=c++11 -fexceptions -g -D __DEBUG__" // C++11
             }
             jniDebuggable true
         }
     }
 
     packagingOptions {
         exclude 'META-INF/ASL2.0'
         exclude 'META-INF/LICENSE'
         exclude 'META-INF/NOTICE'
         exclude 'META-INF/MANIFEST.MF'
     }
     
     compileOptions {
         // 在这里你可以进行 Java 的版本配置，
         // 以便使用对应版本的一些新特性
     }
     productFlavors {
         // 在这里你可以设置你的产品发布的一些东西，
         // 比如你现在一共软件需要发布到不同渠道，
         // 且不同渠道中的包名不同，那么可以在此进行配置；
         // 甚至可以设置不同的 AndroidManifest.xml 文件。
         hebbe {
         }
         googlePlay {
         }
         solo {
         }
     }
     productFlavors.all {
         flavor -> flavor.manifestPlaceholders = [UMENG_CHANNEL_VALUE: name]
     }
     // 所谓ProductFlavors其实就是可定义的产品特性，
     // 配合 manifest merger 使用的时候就可以达成在一次编译
     // 过程中产生多个具有自己特性配置的版本。
     // 上面这个配置的作用就是，为每个渠道包产生不同的 UMENG_CHANNEL_VALUE 的值。
 }
 
 // 指定当前项目的所有依赖关系：本地依赖、库依赖、远程依赖
 // 本地依赖：可以对本地 Jar 包或目录添加依赖关系
 // 库依赖：可以对项目中的库模块添加依赖关系
 // 远程依赖：可以对 jcenter 库上的开源项目添加依赖
 // 标准的远程依赖格式是 域名:组织名:版本号
 dependencies {
     implementation fileTree(dir: 'libs', include: ['*.jar']) // 本地依赖
     // 远程依赖，com.android.support是域名部分，appcompat-v7是组名称，26.1.0是版本号
     implementation 'com.android.support:appcompat-v7:26.1.0'
     implementation 'com.android.support.constraint:constraint-layout:1.0.2'
     implementation project(':hello') // 库依赖
     testImplementation 'junit:junit:4.12' // 声明测试用列库
     androidTestImplementation 'com.android.support.test:runner:1.0.1'
     androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.1'
 }
 
 // 声明是要使用谷歌服务框架
 apply plugin: 'com.google.gms.google-services'
 
 // 第三方依赖库的本地缓存路径
 task showMeCache << {
     configurations.compile.each { println it }
 }
 // 使用maven仓库。android有两个标准的library文件服务器，一个jcenter一个maven。两者毫无关系。
 // jcenter有的maven可能没有，反之亦然。
 // 如果要使用jcenter的话就把mavenCentral()替换成jcenter()
 repositories {
     mavenCentral()
 }

 // 获取日期方法
 def releaseTime() {
    return new Date().format("MMdd.HHmm")
 }

```