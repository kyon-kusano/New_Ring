var initPostcodeJp = function() {
	var postcodeJp = new postcodejp.address.AutoComplementService('OGr8zzCsIjIfr6XZlp7yFZ3Bfw9hRHbgO8x5rF7');
	postcodeJp.setZipTextbox('input_zip')
	postcodeJp.add(new postcodejp.address.StateTownTextbox('input_state_town'));
	postcodeJp.add(new postcodejp.address.StreetTextbox('input_street'));
	postcodeJp.setClearAddressIfNotFound(true);
	postcodeJp.observe();
}
if (window.addEventListener) {
	window.addEventListener('load', initPostcodeJp)
} else {
	window.attachEvent('onload', initPostcodeJp)
}