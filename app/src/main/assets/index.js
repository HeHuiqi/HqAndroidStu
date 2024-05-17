function callAndroid() {

    let callbackId = "userInfoId"
    let params = { "name": "王小二" }
    let passNativeData = {"callbackId":callbackId,"params":params}
      //无回调
//    HqJsBridge.callNative(passNativeData)

      //有回调
      HqJsBridge.callNative(passNativeData,function (response) {
            alert("JsAlert-native-callback:" + JSON.stringify(response));
      });
}

function jsHello() {
    alert("hell js");
}