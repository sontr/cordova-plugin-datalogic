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

***or, if*** you are using ionic, this ionic command:

```bash
ionic cordova plugin add @datalogic/cordova-plugin-datalogic
```

## Sample apps

Several Ionic sample applications are provided to demonstrate using the plugin. You can find them here: https://github.com/datalogic/ionic-samples

## API Reference

### barcodeManager.addReadListener(`successCallback`, `errorCallback`)

Registers a callback function to be notified when a read event is triggered.

Example:

```js
barcodeManager.addReadListner(
   (data) => {
    this.events.publish('data:received', JSON.parse(data));
   },
   (err)=>{
     alert(err);
   }
);
```

and then `data:received` event can be handled like so:

```js
events.subscribe('data:received', (data) => {
   // use data.barcode and data.barcodeType
});
```
