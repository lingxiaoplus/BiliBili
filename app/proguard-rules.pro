# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# banner 的混淆代码
-keep class com.youth.banner.** {
    *;
 }

 -dontwarn com.tencent.bugly.**
 -keep public class com.tencent.bugly.**{*;}
 -keep class android.support.**{*;}


 #fresco混淆
 # Keep our interfaces so they can be used by other ProGuard rules.
 # See http://sourceforge.net/p/proguard/bugs/466/
 -keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
 -keep,allowobfuscation @interface com.facebook.soloader.DoNotOptimize

 # Do not strip any method/class that is annotated with @DoNotStrip
 -keep @com.facebook.common.internal.DoNotStrip class *
 -keepclassmembers class * {
     @com.facebook.common.internal.DoNotStrip *;
 }

 # Do not strip any method/class that is annotated with @DoNotOptimize
 -keep @com.facebook.soloader.DoNotOptimize class *
 -keepclassmembers class * {
     @com.facebook.soloader.DoNotOptimize *;
 }

 # Keep native methods
 -keepclassmembers class * {
     native <methods>;
 }

 -dontwarn okio.**
 -dontwarn com.squareup.okhttp.**
 -dontwarn okhttp3.**
 -dontwarn javax.annotation.**
 -dontwarn com.android.volley.toolbox.**
 -dontwarn com.facebook.infer.**