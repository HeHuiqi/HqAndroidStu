package com.example.hqandroidstu.ble

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.hqandroidstu.R
import com.example.hqandroidstu.databinding.ActivityHqBluetoothStuBinding
import com.example.hqandroidstu.utils.showToast


class HqBluetoothGattCallback(private val ctx:Context,private val mBluetoothDevice:BluetoothDevice?):BluetoothGattCallback(){
    //定义重连次数
    private var reConnectionNum = 0
    //最多重连次数
    private val maxConnectionNum = 3

    private var mBluetoothGatt:BluetoothGatt? = null
    //连接状态回调
    @SuppressLint("MissingPermission")
    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)
        // status 用于返回操作是否成功,会返回异常码。
        //操作成功的情况下
        if (status == BluetoothGatt.GATT_SUCCESS){

        }else{
            //重连次数不大于最大重连次数
            if(reConnectionNum < maxConnectionNum){
                //重连次数自增
                reConnectionNum += 1
                //连接设备
                mBluetoothGatt = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //连接外设，并设置回调
                    mBluetoothDevice?.connectGatt(ctx,false,this,BluetoothDevice.TRANSPORT_LE)
                } else {
                    //连接外设，并设置回调
                    mBluetoothDevice?.connectGatt(ctx,false,this)

                }
            }else{
                //断开连接，返回连接失败回调
            }
        }
    }
    //服务发现回调
    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        super.onServicesDiscovered(gatt, status)
    }
    //特征写入回调
    override fun onCharacteristicWrite(
        gatt: BluetoothGatt?,
        characteristic: BluetoothGattCharacteristic?,
        status: Int
    ) {
        super.onCharacteristicWrite(gatt, characteristic, status)
    }
    //外设特征值改变回调
    override fun onCharacteristicChanged(
        gatt: BluetoothGatt,
        characteristic: BluetoothGattCharacteristic,
        value: ByteArray
    ) {
        super.onCharacteristicChanged(gatt, characteristic, value)
    }
    //描述写入回调
    override fun onDescriptorWrite(
        gatt: BluetoothGatt?,
        descriptor: BluetoothGattDescriptor?,
        status: Int
    ) {
        super.onDescriptorWrite(gatt, descriptor, status)
    }
}
class HqConnectDevice(private val ctx: Context,private val mBluetoothDevice:BluetoothDevice?) {
    private val gattCallback = HqBluetoothGattCallback(ctx,mBluetoothDevice)
    @SuppressLint("MissingPermission")
    fun connectDevice() {
        //连接设备
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //连接外设，并设置回调
            mBluetoothDevice?.connectGatt(ctx,false,gattCallback,BluetoothDevice.TRANSPORT_LE)
        } else {
            //连接外设，并设置回调
            mBluetoothDevice?.connectGatt(ctx,false,gattCallback)
        }
    }
}
class HqScanCallback(private val  ctx:Context):ScanCallback() {

    private var mBluetoothDevice:BluetoothDevice? = null
    var scanResultCallback:((dv:BluetoothDevice?)->Unit)? = null
    @SuppressLint("MissingPermission")
    override fun onScanResult(callbackType: Int, result: ScanResult?) {
        super.onScanResult(callbackType, result)
        val deviceName = result?.device?.name ?: "unknown"
        mBluetoothDevice = result?.device
        Log.i("huiqi-ble", "onScanResult: $deviceName")

        //连接设备
        //HqConnectDevice(ctx,mBluetoothDevice)
        scanResultCallback?.invoke(result?.device)

    }
    override fun onScanFailed(errorCode: Int) {
        super.onScanFailed(errorCode)
    }

}

object HqBluetoothUtil {
     fun checkPermissions(ctx: Context,permissions:Array<String>):Boolean {
        var  allow = true
        for (pms in permissions) {
            allow  = ActivityCompat.checkSelfPermission(
                ctx,
                pms
            ) == PackageManager.PERMISSION_GRANTED
            Log.i("HqBluetoothUtil", "checkPermissions: $pms ,$allow")
            if (!allow) {
                break
            }
        }
        return allow
    }
     fun bluetoothPermissions() :Array<String> {
        val  ps = ArrayList<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ps.add(Manifest.permission.BLUETOOTH_SCAN)
            ps.add(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            ps.add(Manifest.permission.BLUETOOTH)
            ps.add(Manifest.permission.BLUETOOTH_ADMIN)
        }
        ps.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        ps.add(Manifest.permission.ACCESS_FINE_LOCATION)
        return ps.toTypedArray()
    }
    fun checkBluetoothPermissions(ctx: Context): Boolean {
        val pss = bluetoothPermissions()
        return checkPermissions(ctx, pss)
    }
    fun requestBluetoothPermissions(activity: Activity) {
        val pss = bluetoothPermissions()
        ActivityCompat.requestPermissions(activity,pss,1)
    }
}
class HqBluetoothStuActivity : AppCompatActivity() {

    val data = ArrayList<HqBleDevice>()

    private val bluetoothAdapter:BluetoothAdapter  by lazy {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
        } else {
            BluetoothAdapter.getDefaultAdapter()
        }
    }
    private var scanning = false
    private val handler = Handler(Looper.getMainLooper())

    // Stops scanning after 10 seconds.
    private val SCAN_PERIOD: Long = 10000
    private val bleScanCallback = HqScanCallback(this)

    @SuppressLint("MissingPermission")
    private fun startScanBLeDevice() {
        if (!HqBluetoothUtil.checkBluetoothPermissions(this)) {
            HqBluetoothUtil.requestBluetoothPermissions(this)
            return
        }
        val  bluetoothLeScanner =  bluetoothAdapter.bluetoothLeScanner
        if (!scanning) {

            bluetoothLeScanner.startScan(bleScanCallback)
            scanning = true
            handler.postDelayed({
                bluetoothLeScanner.stopScan(bleScanCallback)
                scanning = false
            }, SCAN_PERIOD)
        } else {
            bluetoothLeScanner.stopScan(bleScanCallback)
            scanning = false
        }
    }

    private val rootBinding:ActivityHqBluetoothStuBinding by lazy {
        ActivityHqBluetoothStuBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootBinding.root)
        setup()
    }
    private fun containsDevice(dv:HqBleDevice):Boolean {
        var  c = false
        for (device in data) {
            c = dv.deviceName == device.deviceName
            if (c) {
                break
            }
        }
        return c
    }
    private fun setup() {
        val listView: ListView = rootBinding.deviceListView
        val adapter = HqDeviceArrayAdapter(this, R.layout.ble_device_list_item,data)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->

        }
        rootBinding.scanBtn.setOnClickListener {
            data.clear()
            adapter.notifyDataSetChanged()
            startScanBLeDevice()
        }
        bleScanCallback.scanResultCallback = { dv ->
            val device = HqBleDevice(dv)

            val filter = rootBinding.nameEditText.text
            Log.i("hq-filter", "setup: $filter")
            if (filter.isNotEmpty()) {
                if (device.deviceName.contains(filter)) {
                    if (!containsDevice(device)) {
                        data.add(device)
                    }
                }
            }  else {
                if (!containsDevice(device)) {
                    data.add(device)
                }
            }
            adapter.notifyDataSetChanged()
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            1-> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startScanBLeDevice()
                } else {
                    "用户拒绝了蓝牙扫描权限".showToast()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
}