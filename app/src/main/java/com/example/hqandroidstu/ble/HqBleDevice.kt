package com.example.hqandroidstu.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice

class HqBleDevice(val device:BluetoothDevice? = null) {

    @SuppressLint("MissingPermission")
    val deviceName = device?.name ?: "unknown"

}