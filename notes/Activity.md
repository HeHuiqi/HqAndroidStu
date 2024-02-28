Activity的生存期
Activity类中定义了7个回调方法，覆盖了Activity生命周期的每一个环节，下面就来一一介绍 这7个方法。
onCreate()。这个方法你已经看到过很多次了，我们在每个Activity中都重写了这个方 法，它会在Activity第一次被创建的时候调用。你应该在这个方法中完成Activity的初始化 操作，比如加载布局、绑定事件等。 
onStart()。这个方法在Activity由不可见变为可见的时候调用。 
onResume()。这个方法在Activity准备好和用户进行交互的时候调用。此时的Activity一定位于返回栈的栈顶，并且处于运行状态。 
onPause()。这个方法在系统准备去启动或者恢复另一个Activity的时候调用。我们通常会在这个方法中将一些消耗CPU的资源释放掉，以及保存一些关键数据，但这个方法的执行速度一定要快，不然会影响到新的栈顶Activity的使用。 
onStop()。这个方法在Activity完全不可见的时候调用。它和onPause()方法的主要区别在于，如果启动的新Activity是一个对话框式的Activity，那么onPause()方法会得到执 行，而onStop()方法并不会执行。 
onDestroy()。这个方法在Activity被销毁之前调用，之后Activity的状态将变为销毁状态。 
onRestart()。这个方法在Activity由停止状态变为运行状态之前调用，也就是Activity被重新启动了