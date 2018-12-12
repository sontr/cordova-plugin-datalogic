/*global cordova, module*/
var exec = require('cordova/exec');

var ledManager = function() {};

ledManager.setLed = function (ledConfig, successCallback, errorCallback) {
	exec(successCallback, errorCallback, "LedManager", "setLed", [ledConfig]);
    
};



module.exports = ledManager;