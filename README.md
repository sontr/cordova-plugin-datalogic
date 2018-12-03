# Datalogic Cordova Plugin

Library that exposes the [Datalogic Android (Java) SDK](https://github.com/datalogic/datalogic-android-sdk) as a [Cordova plugin](https://cordova.apache.org/plugins/?q=cordova-plugin-datalogic). It lets you receive barcode data from the scanner, as well as configure various scanner and device settings. It is available as a npm package for easy consumption here: [@datalogic/cordova-plugin-datalogic](https://www.npmjs.com/package/@datalogic/cordova-plugin-datalogic).

## Installation

You can install the plugin from the `npm` registry as follows:

```bash
npm i @datalogic/cordova-plugin-datalogic
```

***...or*** use the following Cordova CLI command:

```bash
cordova plugin add @datalogic/cordova-plugin-datalogic
```

***or,*** if you are using ionic, this ionic command:

```bash
ionic cordova plugin add @datalogic/cordova-plugin-datalogic
```

## Sample apps

Several Ionic sample applications are provided to demonstrate using the plugin. You can find them here: https://github.com/datalogic/ionic-samples

## API Reference

### barcodeManager.addReadListener(`successCallback`, `errorCallback`): Object

#### Description

Registers a callback function (`successCallback`) to be notified when a read event is triggered. This function will be called ***every*** time a barcode is successfully scanned. Therefore, you will typically only need to call `barcodeManager.addReadListener()` *once* in your application.

#### Parameters

* ***successCallback*** - function be to called when barcode data is received.

* ***errorCallback*** - function to be called if an error occurs

#### Response

The object returned contains the barcode data and type. For example:

```json
{
   "barcodeData": "EUG2997",
   "barcodeType": "CODE128"
}
```

The `barcodeType` string will be one of the `BarcodeID` values defined in the [BarcodeID class](https://github.com/datalogic/datalogic-android-sdk/blob/master/sdk/src/main/java/com/datalogic/decode/BarcodeID.java) in the [Datalogic Android SDK](https://github.com/datalogic/datalogic-android-sdk).

#### Example Usage

```js
declare let barcodeManager : any;
...
barcodeManager.addReadListner(
   (data) => {
     parsedData = JSON.parse(data);
     alert(parsedData.barcodeData + ", " + parsedData.barcodeType);
   },
   (err)=>{
     alert(err);
   }
);
```

