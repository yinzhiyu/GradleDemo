# GradleDemo
Gradle模块化配置，生产/测试环境打包切换，多渠道打包
本文当时Android Studio版本 3.0，Gradle也是3.0.0。
可能在项目编译的时候发现Gradle中报错，错误如下：
```
Error:(60, 0) Cannot set the value of read-only property 'outputFile' for ApkVariantOutputImpl_Decorated{apkData=Main{type=MAIN, fullName=xiaomiRelease, filters=[]}} of type com.android.build.gradle.internal.api.ApkVariantOutputImpl.
```
output.outputFile变成了只读属性，不能再往里面写东西了，以下是3.0之前的配置：
```
applicationVariants.all { variant ->    //批量修改Apk名字
    variant.outputs.each { output ->
        def outputFile = output.outputFile
        if (outputFile != null && outputFile.name.endsWith('.apk') && 'release'.equals(variant.buildType.name)) {
            def fileName = outputFile.name.replace("${variant.flavorName}", "V${defaultConfig.versionName}-${variant.flavorName}")
            fileName = fileName.replace('.apk', "-${buildTime()}.apk")
            output.outputFile = new File(outputFile.parent, fileName)
        }
    }
}
```
下面是经过修改之后3.0里面批量修改APK名字的配置：
```
applicationVariants.all { variant ->    //批量修改Apk名字
    variant.outputs.all { output ->
        if (!variant.buildType.isDebuggable()) {
            //获取签名的名字 variant.signingConfig.name
            //要被替换的源字符串
            def sourceFile = "-${variant.flavorName}-${variant.buildType.name}"
            //替换的字符串
            def replaceFile = "_V${variant.versionName}_${variant.flavorName}_${variant.buildType.name}_${buildTime()}"
            outputFileName = output.outputFile.name.replace(sourceFile, replaceFile);
           
        }
    }
}
```
参考文章：http://blog.csdn.net/uu00soldier/article/details/78440953

参考文章：https://developer.android.com/studio/build/gradle-plugin-3-0-0-migration.html?utm_source=android-studio#variant_aware
