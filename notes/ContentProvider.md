ContentProvider 提供者
清单配置
```xml
<manifest>
    <!-- 自定义权限，为自定义的 provider 配置的 permission 保持一致 -->
    <permission android:name="com.example.hqandroidstu.provider.READ_WRITE_PROVIDER"
        android:protectionLevel="normal"/>

    <application>
        <provider
            android:name="com.example.hqandroidstu.data.HqDatabaseContentProvider"
            android:authorities="com.example.hqandroidstu.provider"
            android:enabled="true"
            android:exported="true"
            android:directBootAware="true"
            android:permission="com.example.hqandroidstu.provider.READ_WRITE_PROVIDER"/>
    </application>
</manifest>

```

使用者
清单配置
```xml
<manifest>
<!-- 申请provider的权限   -->
    <uses-permission android:name="com.example.hqandroidstu.provider.READ_WRITE_PROVIDER" />
<!-- 配置查询的 provider authorities和提过者保持一致 -->
    <queries>
        <provider android:authorities="com.example.hqandroidstu.provider">
        </provider>
    </queries>
    
    <application>
        
    </application>
</manifest>
```
参考连接 https://blog.csdn.net/anddlecn/article/details/51733690

