package com.example.hqandroidstu.network

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqDataParseBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import javax.xml.parsers.SAXParserFactory
import kotlin.text.StringBuilder

class HqDataParseActivity : AppCompatActivity() {
    companion object {
        fun actionStart(context: Context) {
               val intent = Intent(context, HqDataParseActivity::class.java)
               context.startActivity(intent)
        }
    }
    private val rootBinding:ActivityHqDataParseBinding by lazy {
        ActivityHqDataParseBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun setup() {
        rootBinding.hqXmlParseBtn.setOnClickListener {
            val xmlData = "<apps>\n" +
                    "    <app>\n" +
                    "        <id>1</id>\n" +
                    "        <name>name1</name>\n" +
                    "        <version>1.0</version>\n" +
                    "    </app>\n" +
                    "    <app>\n" +
                    "        <id>2</id>\n" +
                    "        <name>name2</name>\n" +
                    "        <version>2.0</version>\n" +
                    "    </app>\n" +
                    "    <app>\n" +
                    "        <id>3</id>\n" +
                    "        <name>name3</name>\n" +
                    "        <version>3.0</version>\n" +
                    "    </app>\n" +
                    "</apps>"
//            xmlDataParseWithPull(xmlData)
            xmlDataParseWithSax(xmlData)
        }
        rootBinding.hqJsonParseBtn.setOnClickListener {
            val jsonData = "[\n" +
                    "    {\n" +
                    "        \"id\": \"5\",\n" +
                    "        \"version\": \"5.5\",\n" +
                    "        \"name\": \"Clash of Clans\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": \"6\",\n" +
                    "        \"version\": \"7.0\",\n" +
                    "        \"name\": \"Boom Beach\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "        \"id\": \"7\",\n" +
                    "        \"version\": \"3.5\",\n" +
                    "        \"name\": \"Clash Royale\"\n" +
                    "    }\n" +
                    "]"
//            jsonDataParesWithJSONObject(jsonData)
            jsonDataParesWithGSON(jsonData)


        }
    }
    private fun xmlDataParseWithPull(xmlData:String){

        try {
            val factory = XmlPullParserFactory.newInstance()
            val xmlPullParser = factory.newPullParser()
            xmlPullParser.setInput(StringReader(xmlData))
            var eventType = xmlPullParser.eventType
            var id = ""
            var name = ""
            var version = ""
            val  result = StringBuilder()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                val nodeName = xmlPullParser.name

                when(eventType) {
                    XmlPullParser.START_TAG -> {
                        when(nodeName) {
                            "id" -> id = xmlPullParser.nextText()
                            "name" -> name = xmlPullParser.nextText()
                            "version" -> version = xmlPullParser.nextText()
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        if ("app" == nodeName) {
                            val appInfo = "{id:$id,name:$name,version:$version}\n"
                            result.append(appInfo)
                        }
                    }
                }
                eventType = xmlPullParser.next()

            }
            updateUI(result.toString())

        }catch (e:Exception) {
            e.printStackTrace()
        }

    }
    private fun xmlDataParseWithSax(xmlData: String) {
        try {
            val factory = SAXParserFactory.newInstance()
            val xmlReader = factory.newSAXParser().xmlReader
            val handler = HqSaxHandler()
            //设置解析处理器
            xmlReader.contentHandler = handler
            //开始解析文档
            xmlReader.parse(InputSource(StringReader(xmlData)))

            //将解析结果更新到UI
            updateUI(handler.result.toString())

        }catch (e:Exception) {
            e.printStackTrace()
        }
    }
    private fun jsonDataParesWithJSONObject(jsonData:String){
        try {
            val result = StringBuilder()
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val version = jsonObject.getString("version")
                val appInfo = "{id:$id,name:$name,version:$version}\n"
                result.append(appInfo)
            }
            updateUI(result.toString())
        }catch (e:Exception) {
            e.printStackTrace()
        }
    }
    private fun jsonDataParesWithGSON(jsonData:String){
        val gson = Gson()
        val typeOf = object :TypeToken<List<HqApp>>(){}.type
        val appList = gson.fromJson<List<HqApp>>(jsonData,typeOf)
        val result = StringBuilder()
        for (app in appList) {
            val appInfo = "{id:${app.id},name:${app.name},version:${app.version}}\n"
            result.append(appInfo)
        }
        updateUI(result.toString())
    }
    private fun updateUI(content:String) {
        rootBinding.hqParesContentTv.text = content
    }
}