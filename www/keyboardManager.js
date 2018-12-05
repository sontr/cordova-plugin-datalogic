/*global cordova, module*/
var exec = require('cordova/exec');

var keyboardManager = function() {};

keyboardManager.getAllAvailableTriggers = function (successCallback, errorCallback) {
	exec(successCallback, errorCallback, "KeyboardManager", "getAllAvailableTriggers", []);
    
};

keyboardManager.setAllAvailableTriggers = function (enable, successCallback, errorCallback) {
	exec(successCallback, errorCallback, "KeyboardManager", "setAllAvailableTriggers", [enable]);
    
};

keyboardManager.setTriggers = function (config, successCallback, errorCallback) {
	exec(successCallback, errorCallback, "KeyboardManager", "setTriggers", [config]);
    
};

module.exports = keyboardManager;