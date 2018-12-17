module.exports = {
	pluginName: "NativeLogs",

	getLog:function(_cliArgs,successCB,failureCB){
		cordova.exec(successCB, failureCB, this.pluginName, "getLog", [_cliArgs]);
	},

	testException:function(successCB, failureCB){
		cordova.exec(successCB, failureCB, this.pluginName, "testException")
	},

	clearLog:function(successCB, failureCB){
		cordova.exec(successCB, failureCB, this.pluginName, "clearLog")
	}
};