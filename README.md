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

All functions are asynchronous. All functions will, at a minimum, include `successCallback` and `errorCallback` parameters, both of which are callback functions.

* `successCallback` will be called in normal cases, and will return an appropriate JSON `object`.

* `errorCallback` will be called when there was an error, and will return a single error `string`.

### barcodeManager

---

#### .addReadListener(`successCallback`, `errorCallback`): Object

`successCallback` will be called ***every time*** a barcode is successfully scanned. Therefore, you will typically only need to call `barcodeManager.addReadListener()` *once* in your application.

##### Response

* `barcodeData` : `string` - the barcode data scanned.
* `barcodeType` : `string` - will be one of the `BarcodeID` values defined in the [BarcodeID class](https://github.com/datalogic/datalogic-android-sdk/blob/master/sdk/src/main/java/com/datalogic/decode/BarcodeID.java) in the [Datalogic Android SDK](https://github.com/datalogic/datalogic-android-sdk).

```json
{
   "barcodeData": "EUG2997",
   "barcodeType": "CODE128"
}
```

##### Example

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

### autoScanTrigger

---

#### .isAvailable(`successCallback`, `errorCallback`): Object

Function to determine if the auto scan feature is available on this device.

##### Response

* `available` : `boolean` - indicates if autoscan is supported or not on this device.

```json
{
   "available": true
}
```

##### Example

```js
declare let autoScanTrigger : any;
isAvailable : boolean = false;
...
autoScanTrigger.isAvailable(
  (data) => {
    this.isAvailable = JSON.parse(data).available;
    alert(this.isAvailable);
  },
  (err) => {
    alert(err);
  }
);

```

#### .getSupportedRanges(`successCallback`, `errorCallback`): Object

Function to get the supported ranges of the autoscan feature.

##### Response

`supportedRanges` : `array` - provides array of ranges device supports. The array will be empty if device deos not support autoscan. Each object in the array contains:

* `id` : `integer` - unique value for a step in the supported ranges
* `name` : `string` - descriptive text related to the `id`

If AutoScan is not supported by device:

```json
{
  "supportedRanges":[]
}
```

If AutoScan is supported:

```json
{
  "supportedRanges":[
  {
    "id":0,
    "name":"Near"
  },
  {
    "id":1,
    "name":"Intermediate"
  },
  {
    "id":2,
    "name":"Far"
  }
  ]
}
```

##### Example

```js
declare let autoScanTrigger : any;
autoScanTrigger.getSupportedRanges(
  (data) => {
    alert(JSON.parse(data).supportedRanges);
    if(this.supportedRanges.length == 0)
      alert("Device does not support Auto Scan");
  },
  (err) => {
    alert(err);
  });
```

#### .getCurrentRange(`successCallback`, `errorCallback`): Object

Function to get the current range of the autoscan feature.

##### Response

`currentRange` : `object` - contains 2 fields:

* `id` : `integer`
* `name` : `string`

If AutoScan is not supported by device:

```json
{
  "currentRange":null
}
```

If AutoScan is supported:

```json
{
  "currentRange":
  {
      "id":1,
      "name":"Intermediate"
  }
}
```

##### Example

```js
declare let autoScanTrigger : any;
autoScanTrigger.getCurrentRange(
  (data) => {
    alert(JSON.parse(data).currentRange);
  },
  (err) => {
    alert(err);
  });
```

#### .setCurrentRange(`id`, `successCallback`, `errorCallback`): Object

Function to set the current range of the autoscan feature.

`id` : `integer` - should match one of the `id` values retrevied by the getSupportedRanges function.

##### Response

`string` with success message

##### Example

Set current range to "Intermediate"

```js
autoScanTrigger.setCurrentRange(
  0,
  (data) => {
    alert(data);
  },
  (err) => {
    alert(err);
  });
```

### keyboardManager

---

#### .getAllAvailableTriggers (`successCallback`, `errorCallback`): Object

Function to get all the available triggers of the device.

##### Response

`triggers` : `array` - each object in the array contains:

* `enabled` : `boolean`
* `id` : `integer`
* `name` : `string`

Typical resopsnse:

```json
{
    "triggers":[
    {
        "enabled":true,
        "id":3,
        "name":"Front Trigger"
    },
    {
        "enabled":false,
        "id":4,
        "name":"Auto Scan Trigger"
    },
    {
        "enabled":false,
        "id":5,
        "name":"Motion Trigger"
    }
    ]
}
```

##### Example

```js
keyboardManager.getAllAvailableTriggers(
  (data) => {
    alert(JSON.parse(data).triggers);
  },
  (err) => {
    alert(err);
  });
```

#### .setAllAvailableTriggers(`enable`, `successCallback`, `errorCallback`): Object

Function to set all the devices triggers on or off.

##### Response

`string` with success message

##### Example

Turn all triggers on.

```js
keyboardManager.setAllAvailableTriggers(
  true,
  (data) => {
    alert(data);
  },
  (err) => {
    alert(err);
  });
```

#### .setTriggers(`config`, `successCallback`, `errorCallback`): Object

Function to set one or more triggers on or off. You will likely call `getAllAvailableTriggers`, edit the `enabled` flags of each returned object as desired, and then resubmit by calling `setTriggers`.

`config` : `array` - each ojbect in the array represents an individual trigger. Each object in the array contains:

* `id` : `integer`
* `enabled` : `boolean`

##### Response

`string` with success message

### ledManager

---

#### .setLed(`ledConfig`, `successCallback`, `errorCallback`): Object

### scannerProperties

---

#### .edit(`successCallback`, `errorCallback`): Object

#### .store(`properties`, `successCallback`, `errorCallback`): Object