
/*global cordova, module*/

module.exports = {
    decode: function (successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "BarcodeDecoder", "receive-barcode-data", []);
    }
};