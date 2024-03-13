adb 的使用

常用命令:

查看可用的设备和模拟器
 > adb devices
 List of devices attached
 cb030c81        device //真机
 emulator-5554   device //模拟器

查看当前页面的Activity
-s 表示要查看的设备
> adb -s emulator-5554  shell "dumpsys window | grep mCurrentFocus"
mCurrentFocus=Window{20a4c93 u0 com.hhq.hqdokitstu/com.hhq.hqdokitstu.MainActivity}


Log Cat
ActivityTaskManager