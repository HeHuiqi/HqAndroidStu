package com.example.hqandroidstu.network

import android.util.Log
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class HqSaxHandler:DefaultHandler() {

    private var nodeName = ""
    private lateinit var id:StringBuilder
    private lateinit var name:StringBuilder
    private lateinit var version:StringBuilder
    var result = StringBuilder()
    //开始解析文档
    override fun startDocument() {
        super.startDocument()
        id = StringBuilder()
        name = StringBuilder()
        version = StringBuilder()
    }

    //开始解析某个节点
    override fun startElement(
        uri: String?,
        localName: String?,
        qName: String?,
        attributes: Attributes?
    ) {
        super.startElement(uri, localName, qName, attributes)
        // 记录当前节点名
        localName?.let {
            nodeName = it
        }
        Log.d("HqSaxHandler", "uri is $uri")
        Log.d("HqSaxHandler", "localName is $localName")
        Log.d("HqSaxHandler", "qName is $qName")
        Log.d("HqSaxHandler", "attributes is $attributes")
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        // characters()方法可能会 被调用多次，一些换行符也被当作内容解析出来，我们需要针对这种情况在代码中做好控制
        super.characters(ch, start, length)
        // 根据当前节点名判断将内容添加到哪一个StringBuilder对象中
        when(nodeName) {
            "id" -> id.appendRange(ch!!,start, start + length)
            "name" -> name.appendRange(ch!!,start,start+length)
            "version" -> version.appendRange(ch!!,start,start+length)
        }
    }

    //结束某个节点解析
    override fun endElement(uri: String?, localName: String?, qName: String?) {
        super.endElement(uri, localName, qName)
        localName?.let {
            if ("app" == it) {
                //目前id、name和version中都可能是包括回车或换行符的，因此需要调用一下trim()方法
                val appInfo = "{id:${id.trim()},name:${name.trim()},version:${version.trim()}}\n"
                result.append(appInfo)
                //一个节点解析结束 最后要将StringBuilder清空
                id.setLength(0)
                name.setLength(0)
                version.setLength(0)
            }
        }
    }
    override fun endDocument() {
        super.endDocument()
    }

}