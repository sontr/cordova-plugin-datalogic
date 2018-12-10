/*global cordova, module*/
var exec = require('cordova/exec');

var autoScanTrigger = function() {};

autoScanTrigger.isAvailable = function (successCallback, errorCallback) {
	exec(successCallback, errorCallback, "AutoScanTrigger", "isAvailable", []);
    
};

autoScanTrigger.getSupportedRanges = function (successCallback, errorCallback) {
	exec(successCallback, errorCallback, "AutoScanTrigger", "getSupportedRanges", []);
    
};

autoScanTrigger.getCurrentRange = function (successCallback, errorCallback) {
	exec(successCallback, errorCallback, "AutoScanTrigger", "getCurrentRange", []);
    
};

autoScanTrigger.setCurrentRange = function (id, successCallback, errorCallback) {
	exec(successCallback, errorCallback, "AutoScanTrigger", "setCurrentRange", [id]);
    
};

module.exports = autoScanTrigger;