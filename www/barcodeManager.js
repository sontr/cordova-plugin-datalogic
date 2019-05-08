
/*global cordova, module*/
var exec = require('cordova/exec');

var barcodeManager = function() {};

barcodeManager.addReadListner = function (successCallback, errorCallback) {
	exec(successCallback, errorCallback, "BarcodeManager", "addReadListner", []);
    
};

barcodeManager.startDecode = function (successCallback, errorCallback) {
    exec(successCallback, errorCallback, 'BarcodeManager', 'startDecode', []);
};

module.exports = barcodeManager;