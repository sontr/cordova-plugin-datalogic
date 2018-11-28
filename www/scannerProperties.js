/*global cordova, module*/

module.exports = {
    edit: function (successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "ScannerProperties", "edit", []);
    }
};