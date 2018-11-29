
/*global cordova, module*/
var exec = require('cordova/exec');

var barcodeManager = function() {};

barcodeManager.addReadListner = function (successCallback, errorCallback) {
	exec(successCallback, errorCallback, "BarcodeManager", "addReadListner", []);
    
};

module.exports = barcodeManager;