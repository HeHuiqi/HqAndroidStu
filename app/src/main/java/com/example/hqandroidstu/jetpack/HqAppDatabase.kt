package com.example.hqandroidstu.jetpack

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


// 注解中声明了数 据库的版本号以及包含哪些实体类，多个实体类之间用逗号隔开即可。
// 必须继承自RoomDatabase类，并且一定要使用abstract关键字将它 声明成抽象类，然后提供相应的抽象方法，用于获取之前编写的Dao的实例，比如这里提供的 userDao()方法。
// 不过我们只需要进行方法声明就可以了，具体的方法实现是由Room在底层 自动完成的。
@Database(version = 2, entities = [HqUser::class,HqBook::class])
abstract class HqAppDatabase:RoomDatabase() {

    abstract fun userDao():HqUserDao
    abstract fun bookDao():HqBookDao

    companion object {
        /*实现了一个Migration的 匿名类，并传入了1和 2这两个参数，表示当数据库版本从1升级到2的时候就执行这个匿名类中 的升级逻辑。匿名类实例的变量命名也比较有讲究，这里命名成MIGRATION_1_2，可读性更 高。由于我们要新增一张Book表，所以需要在migrate()方法中编写相应的建表语句。另外必 须注意的是，Book表的建表语句必须和Book实体类中声明的结构完全一致，否则Room就会抛 出异常。*/
        val MIGRATION_1_2 = object :Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                //执行数据库升级的操作
                val sql = "create table if not exists HqBook(" +
                        "id integer primary key autoincrement not null," +
                        "name text not null, " +
                        "pages integer not null)"
                db.execSQL(sql)
            }
        }
        private var instance:HqAppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context):HqAppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext,HqAppDatabase::class.java,"hq_app_database.db")
                .addMigrations(MIGRATION_1_2)
                .build()
                .apply {
                instance = this
            }

        }
    }

}