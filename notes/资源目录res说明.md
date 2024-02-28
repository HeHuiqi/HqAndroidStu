所有以“drawable”开头的目录都是用来放图片的，
所有以“mipmap”开头的目录都是用来放应用图 标的，
所有以“values”开头的目录都是用来放字符串、样式、颜色等配置的，
所有以“layout”开头的目录都是用来放布局文件的

有这么多“mipmap”开头的目录，其实主要是为了让程序能够更好地兼容各种设备。 drawable目录也是相同的道理，
我们应该自己创建drawable-hdpi、drawable-xhdpi、drawable-xxhdpi等目录

通常情况下把所有图片都放在drawable-xxhdpi目录下就好了，因为这是最主流的设备分辨率目录。