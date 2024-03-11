package com.example.hqandroidstu.gaoji

import android.os.Parcel
import android.os.Parcelable

class HqStudent() :Parcelable {
    var name = ""
    var age = 0



    //序列化
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeInt(age)
    }
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HqStudent> {
        override fun createFromParcel(parcel: Parcel): HqStudent {
            val student =  HqStudent()
            //反序列化
            // 注意这里读取的顺序一定要和writeToParcel()方法的write顺序一致
            student.name = parcel.readString() ?: "" //读取name
            student.age = parcel.readInt() //读取age
            return student
        }

        override fun newArray(size: Int): Array<HqStudent?> {
            return arrayOfNulls(size)
        }
    }

}
/*
//使用注解自动写入和读取如下

@Parcelize
class HqStudent(var name: String, var age: Int) : Parcelable

* */