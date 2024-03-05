package com.example.hqandroidstu

import kotlin.reflect.KProperty

/*
val(value的简写)用来声明一个不可变的变量，这种变量在初始赋值之后就再也不能重新赋 值，对应Java中的final变量。
var(variable的简写)用来声明一个可变的变量，这种变量在初始赋值之后仍然可以再被重新 赋值，对应Java中的非final变量。
 永远优先使用val来声明一个变量，而当val没有办法满足你的需求时再使用var
 */
//fun largerNumber(num1:Int,num2:Int):Int {
//    return max(num1,num2)
//}
//语法糖写法
//fun largerNumber(num1:Int,num2:Int):Int = max(num1,num2)
//fun largerNumber(num1:Int,num2:Int) = max(num1,num2)

//fun largerNumber(num1:Int,num2:Int):Int {
////    if语句使用每个条件的最后一行代码作为返回值，并将返回值赋值给了 value变量。
////    由于现在没有重新赋值的情况了，因此可以使用val关键字来声明value变量
//   val value = if (num1 > num2) {
//       num1
//   } else {
//       num2
//   }
//    return value
//}

//fun largerNumber(num1:Int,num2:Int):Int {
//    return if (num1 > num2) {
//        num1
//    } else {
//        num2
//    }
//}

fun largerNumber(num1:Int,num2:Int) = if (num1 > num2) num1 else num2

//fun getScore(name:String) = if (name == "Tom") {
//    86
//} else if (name == "Jim") {
//    77
//} else if (name == "Jack") {
//    95
//} else if (name == "Lily") {
//    100
//} else {
//    0
//}

//使用when改写
fun getScore(name:String) = when (name) {
    "Tom" -> {
        86
    }
    "Jim" -> {
        77
    }
    "Jack" -> {
        95
    }
    "Lily" -> {
        100
    }
    else -> {
        0
    }
}

fun checkNumber(num:Number) {
    when(num) {
        // is关键字就是类型匹配的核心
        is Int -> println("num is Int")
        is Double -> println("num is Double")
        else -> println("num not support")
    }
}

fun loop(){
    //range 是闭区间
    val range = 0..10

    for (i in range) {
        println("i=$i")
    }
    // range1 是左闭又开区间
    val range1 = 0 until 10
    for (j in range1) {
        println("j=$j")
    }

    //step 设置步长
    for (k in 0..10 step 2) {
        println("k=$k")
    }
    // 逆序输出

    for (q in 10 downTo 1 step 2) {
        println("q=$q")
    }

}

fun base(){
    println("Hello Kotlin!")
    var a = 10
    a *= 10
    println("a=$a")
    //显示声明变量
    val str:String = "apple"
    println("str=$str")

    val res = largerNumber(100,80)
    println("res=$res")
    val num = 10L
    checkNumber(num)
    loop()
}

fun oop(){
    val person = HqPerson()
    person.name = "Jack"
    person.age = 10
    person.eat()


    val teacher = HqTeacher("")
    println(teacher)

    val student = HqStudent()
    student.name = "LiLei"
    doStudy(student)

    val cellphone = HqCellphone("apple",19.9)
    val cellphone1 = HqCellphone("google",18.9)
    println(cellphone)
    println(cellphone1)
    println("cellphone equals cellphone1: ${cellphone == cellphone1}")

    println("cellphone-info{${cellphone.brand},${cellphone.price}}")

    /*
    这种写法虽然看上去像是静态方法的调用，
    但其实Kotlin在背后自动帮我们创建了一个 Singleton类的实例，并且保证全局只会存在一个Singleton实例
    * */
    HqSingleton.singletonTest()
}
// 可以传递 null 值，但不会报异常
fun doStudy(study: HqStudy?) {
    //?.操作符表示对象为空时什么都不做，对象不为空时就调用let 函数，
    // 而let函数会将study对象本身作为参数传递到Lambda表达式中，
    // 此时的study对象肯 定不为空了，我们就能放心地调用它的任意方法了
    study?.let { stu ->
        stu.readBooks()
        stu.doHomework()
    }
}


fun fixNullNumber(num:Number) : Number {
/*
* ?:操作符。这个操作符的左右两边都接收一个表达式， 如果左边表达式的结果不为空就返回左边表达式的结果，否则就返回右边表达式的结果
* */
    return num ?: 0
}



fun collectStu(){

    //创建一个list, listOf()函数创建的是一个不可变的集合
    val list = listOf<String>("Apple","Banana","Orange","Pear","Grape","Watermelon")
    println(list)
    val maxFruitName = list.maxBy { it.length }
    println("maxFruitName=$maxFruitName")


    for (fruit in list) {
        println(fruit)
    }

    //mutableListOf()函数创建的是一个可变的集合
    val list2 = mutableListOf<Int>(1,2,3,4,5)
    list2.add(0,9)
    list2.add(10)
    println(list2)

    val set = setOf<Double>(2.3,1.2,3.5,9.8)
    for (num in set) {
        println(num)
    }
    val set2 = mutableSetOf<Double>(1.0,2.0,3.0,4.0)
    println(set2)

    val mp = mutableMapOf<String,Any>()
    mp["name"] = "小李"
    mp["age"] = 18
    println(mp)
    println(mp["name"])

    for ((key,value ) in mp) {
        println("$key:$value")
    }



}
fun lambdaStu(){
    //创建一个list, listOf()函数创建的是一个不可变的集合
    val list = listOf<String>("Apple","Banana","Orange","Pear","Grape","Watermelon")
    println(list)


    /*
    * Lambda表达式的语法结构
    * {参数名1: 参数类型, 参数名2: 参数类型 -> 函数体}
    * 函数体最后一行代码会自动作为Lambda表达式的返回值
    * */

    val lambda = {fruit:String->fruit.length}
    var maxFruitName2 = list.maxBy(lambda)
    println("maxFruitName2=$maxFruitName2")

    maxFruitName2 = list.maxBy({fruit:String->fruit.length})
    println("maxFruitName3=$maxFruitName2")
    // Kotlin规定，当Lambda参数是函数的最后一个参数时，可以将Lambda表达式移到函数括 号的外面
    maxFruitName2 = list.maxBy(){fruit:String->fruit.length}
    println("maxFruitName4=$maxFruitName2")
    // 如果Lambda参数是函数的唯一一个参数的话，还可以将函数的括号省略
    maxFruitName2 = list.maxBy{fruit:String->fruit.length}
    println("maxFruitName5=$maxFruitName2")
    // Lambda表达式中的参数列表其实在大多数情况下不必声明参数类型，因此代码可 以进一步简化
    maxFruitName2 = list.maxBy{fruit ->fruit.length}
    println("maxFruitName6=$maxFruitName2")
    // 当Lambda表达式的参数列表中只有一个参数时，也不必声明参数名，而是可以使用it 关键字来代替

    val maxFruitName = list.maxBy { it.length }
    println("maxFruitName=$maxFruitName")

    var newList = list.map{it.uppercase()}
    println(newList)
    //链式调用
    newList = list.filter { it.length >=5 }.map { it.uppercase() }
    println(newList)

    // 其中any函数用于判断集 合中是否至少存在一个元素满足指定条件，all函数用于判断集合中是否所有元素都满足指定条 件

    val anyList = list.any { it.length <= 5 }
    println(anyList)

    val allList = list.all { it.length <= 5 }
    println(allList)


    //使用java的函数式api,Java单抽象方法接口参数，就可以使用函数式API,Java单抽象 方法接口指的是接口中只有一个待实现方法
//    Thread(object:Runnable{
//        override fun run() {
//            println("实现run")
//        }
//    }).start()

    Thread {
        println("实现run")
    }.start()

}

fun standFunStu () {

    val list = listOf<String>("Apple","Banana","Orange","Pear","Grape","Watermelon")
    //with 函数对第一个参数，通过block回调处理后，返回最后的结果
    val result = with(StringBuilder()) {
        append("开始吃水果了\n")
        for (fruit in list) {
            append("${fruit}\n")
        }
        append("水果吃完了")
        //最后一行，作为返回值
        toString()
    }

    println("result=$result")

    //run函数只接收一个Lambda参数，并且会在Lambda表 达式中提供调用对象的上下文
    val result2 = StringBuilder().run {
        append("开始吃水果了\n")
        for (fruit in list) {
            append("${fruit}\n")
        }
        append("水果吃完了")
        //最后一行，作为返回值
        toString()
    }
    println("result2=$result2")

    // apply函数和run函数也是极其类似的，都要在某个对象上调用，并且只接收一个Lambda参数，
    // 也会在Lambda表达式中提供调用对象的上下 文，但是apply函数无法指定返回值，而是会自动返回调用对象本身

    // result3 和新建的StringBuilder()返回的是同一个实例
    val result3 = StringBuilder().apply {
        append("开始吃水果了\n")
        for (fruit in list) {
            append("${fruit}\n")
        }
        append("水果吃完了")
        //最后一行，作为返回值
        toString()
    }
    println("result3=$result3")


}
class Util {
    fun doAction1() {
        println("do action 1")
    }
    // 单例类或companion object中的方 法加上@JvmStatic注解，那么Kotlin编译器就会将这些方法编译成真正的静态方法
    // @JvmStatic注解只能加在单例类或companion object中的方法上
    companion object {
        // 添加上这个注解后，才能在java中通过类来调用这个方法
        @JvmStatic
        fun doAction2() {
            println("do action 2")
        }
    }
}

//--------------------
//密封类
sealed class Result
class Success(val msg:String):Result()
class Fail(val error:String):Result()
fun getResultMsg(result: Result):String {
    //使用密封类在使用when时可以不用处理 else 分支的情况
    // Kotlin编译器会自动检查该密封类有哪些子类，并强制要求你将每一个子类所对应的条件全部处理
    // 密封类及其所有子类只能定义在同
    //一个文件的顶层位置，不能嵌套在其他类中，这是被密封类底层的实现机制所限制的。
   return  when(result) {
        is Success -> {
             result.msg
        }
        is Fail -> {
            result.error
        }
    }
}
fun sealedClassStu() {
    val suc = Success("操作成功")
    var res = getResultMsg(suc)
    println("res=$res")
    val fail = Fail("操作失败")
    res = getResultMsg(fail)
    println("res=$res")
}
//-------------

//为已有类添加扩展函数
/*
* 语法
fun ClassName.methodName(param1: Int, param2: Int): Int {
    return 0
}
* */
//为String类添加一个扩展函数
fun String.lettersCount():Int {
    var count = 0
    for (char in this) {
        if (char.isLetter()) {
            count ++
        }
    }
    return count
}
fun extFunStu(){
    val str = "123124water"
    println("res=${str.lettersCount()}")
}

//运算符重载
class Money(val value:Int) {

    //重载 + 号运算符
    operator fun plus(money: Money):Money {
        val sum = value + money.value
        return Money(sum)
    }
    //可以多次重载运算符
    operator fun plus(newValue: Int):Money {
        return Money(value + newValue)
    }
}

// 重载String 类的 * 乘法运算符
operator fun String.times(n: Int): String {
    val builder = StringBuilder()
    repeat(n) {
        builder.append(this)
    }
    return builder.toString()
}

fun operatorStu() {
    val money = Money(5)
    val money2 = Money(10)
    var money3 = money + money2
    println("money=${money3.value}")
    money3 += 20
    println("money=${money3.value}")

    val res = "hello " * 3
    println("res=$res")

}

//函数作为参数,
//inline 内联函数关键字，内联的函数类型参数只允许传递给另外一个内联函数，这也是它最大的局限性
inline fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {

    return operation(num1, num2)

}
fun customLambdaStu2(){
    val a = num1AndNum2(1,2) { n1,n2 ->
        n1 + n2
    }
    val b = num1AndNum2(1,2) { n1,n2 ->
        n1 - n2
    }
    println("a=$a")
    println("b=$b")

}
// 在函数类型的前面加上ClassName. 就表示这个函数类型是定义在哪个类当中的
// 这里block表示时定义在StringBuilder类中的
fun StringBuilder.build(block: StringBuilder.() -> Unit): StringBuilder {
    block()
    return this
}
fun customExtFun(){
    val list = listOf<String>("Apple","Banana","Orange","Pear","Grape","Watermelon")
    //调用自己的高级函数方法
    val result1 = StringBuilder().build {
        append("开始吃水果")
        for (fruit in list) {
            append(fruit).append("\n")
        }
        append("水果都吃完了")
    }
    println("res=$result1")
}
//泛型方法,这样任何类都可以调用这个build2方法了
fun <T> T.build2(block:T.() -> Unit):T {
    block()
    return this
}

fun fanxingStu(){
    val list = listOf<String>("Apple","Banana","Orange","Pear","Grape","Watermelon")

    val result1 = StringBuilder().build2 {
        append("开始吃水果")
        for (fruit in list) {
            append(fruit).append("\n")
        }
        append("水果都吃完了")
    }
    println("res=$result1")
}

// Kotlin中委托使用的关键字是by，我们只需要在接口声明的后面使用by关键字，再接上受委托 的辅助对象

class MySet<T>(private val helper:HashSet<T>):Set<T> by helper {

}

//属性委托
class HqClass {
    //p属性的set和get实现交给了HqDelegate类实现
    // 现在当我们给HqClass的p属性赋值时，就会调用 Delegate类的setValue()方法，当获取HqClass中p属性的值时，就会调用Delegate类的 getValue()方法
    val p by HqDelegate()
}
class HqDelegate {
    // 在Delegate类中我们必须实现getValue()和setValue()这 两个方法，并且都要使用operator关键字进行声明。
    var propValue:Any? = null

    //:第一个参数用于声明该Delegate类的委托功能可以在什么 类中使用，这里写成HqClass表示仅可在HqClass类中使用;第二个参数KProperty<*>是 Kotlin中的一个属性操作类，可用于获取各种属性相关的值
    operator fun getValue(hqClass: HqClass,prop:KProperty<*>):Any? {
        return propValue
    }
    operator fun setValue(hqClass: HqClass,prop: KProperty<*>,value: Any?) {
        propValue = value
    }
}

//infix 函数
//infix关键字之后，beginsWith()函数就变成了一个infix函数
//首先，infix函数是 不能定义成顶层函数的，它必须是某个类的成员函数，可以使用扩展函数的方式将它定义到某 个类当中;其次，infix函数必须接收且只能接收一个参数，至于参数类型是没有限制的
infix fun String.beginsWith(prefix:String) = startsWith(prefix)

infix fun <T> Collection<T>.has(element: T) = contains(element)
infix fun <A, B> A.with(that: B): Pair<A, B> = Pair(this, that)

fun infixStu(){
    // 一种特殊的语法糖格式调用beginsWith()函数,infix函数的调用
    val res = "Hello World!!" beginsWith "Hello"
    println(res)
    val list = listOf("Apple", "Banana", "Orange", "Pear", "Grape")
    if (list has "Apple") {
        println("有苹果")
    }
    val map = mapOf("Apple" with 1, "Banana" with 2, "Orange" with 3, "Pear" with 4,
        "Grape" with 5)
    println(map)
}

fun main(){

//    oop()
//    collectStu()
//    lambdaStu()
//    standFunStu()
//    Util.doAction2()
//    sealedClassStu()
//    extFunStu()
//    operatorStu()
//    customLambdaStu2()
//    customExtFun()
//    fanxingStu()
    infixStu()

}