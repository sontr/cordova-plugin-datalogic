/*global cordova, module*/

module.exports = {
    edit: function (successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ScannerProperties", "edit", []);
    },
    store: function (properties, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ScannerProperties", "store", [properties]);
    }
};