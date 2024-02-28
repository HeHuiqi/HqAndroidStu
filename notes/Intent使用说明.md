Intent是Android程序中各组件之间进行交互的一种重要方式，它不仅可以指明当前组件想要执 行的动作， 还可以在不同组件之间传递数据。
Intent一般可用于启动Activity、启动Service以 及发送广播等场景。

Intent大致可以分为两种:显式Intent和隐式Intent

显示启动Activity
```kotlin

    val intent = Intent(this,HqSecondActivity::class.java)
    startActivity(intent)

```
销毁activity
```kotlin
    //在要销毁的activity中调用如下方法，即可销毁
    finish()

```
隐式启动Activity
```kotlin

            /*
            首先要在 AndroidManifest.xml 配置Activity，android:exported 必须为true才能隐式启动
            <activity
            android:name=".HqSecondActivity"
            android:exported="true">
                <intent-filter>
                <!-- 
                action标签的 android:name 是自定义的名称，推荐格式为 包名.ACTION_自定义名称
                每个Intent中只能指定一个action，但能指定多个category
                 -->
                    <action android:name="com.example.hqandroidstu.HQ_ACTION_START"/>
                    <category android:name="android.intent.category.DEFAULT"/>
                    <!--添加自定义的category 可选，一般当需要精确匹配时 -->
                <category android:name="com.example.hqandroidstu.category.HQ_SENCOND"/>
                </intent-filter>
            </activity>
             */
            // 这里的action要和 AndroidManifest.xml 中Activity的配置名称保持一致，才能正确启动
            val intent = Intent("com.example.hqandroidstu.HQ_ACTION_START")
            //添加自定义的Category，这里自定义的Category必须在 AndroidManifest.xml 先配置
            //否则会崩溃或者回到主Activity
            //intent.addCategory("com.example.hqandroidstu.category.HQ_SENCOND")
            startActivity(intent)

```

使用隐式Intent，不仅可以启动自己程序内的Activity，还可以启动其他程序的Activity，这就 使多个应用程序之间的功能共享成为了可能

使用浏览器打开网址
```kotlin
     private fun openUrl(){
            //这个将会使用系统默认的浏览器打开url
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.baidu.com")
            startActivity(intent)
     }
```
我们还可以在<intent-filter>标签中再配置一个<data>标签，用于更精确地指 定当前Activity能够响应的数据。<data>标签中主要可以配置以下内容。
android:scheme。用于指定数据的协议部分，如上例中的https部分。 android:host。用于指定数据的主机名部分，如上例中的www.baidu.com部分。 android:port。用于指定数据的端口部分，一般紧随在主机名之后。 android:path。用于指定主机名和端口之后的部分，如一段网址中跟在域名之后的内 容。 android:mimeType。用于指定可以处理的数据类型，允许使用通配符的方式进行指定。
只有当<data>标签中指定的内容和Intent中携带的Data完全一致时，当前Activity才能够响应该Intent.