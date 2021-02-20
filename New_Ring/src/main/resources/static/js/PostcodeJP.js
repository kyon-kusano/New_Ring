var initPostcodeJp = function() {
	var postcodeJp = new postcodejp.address.AutoComplementService('OGr8zzCsIjIfr6XZlp7yFZ3Bfw9hRHbgO8x5rF7');
	postcodeJp.setZipTextbox('post_Number')
	postcodeJp.add(new postcodejp.address.StateTownTextbox('address1'));
	postcodeJp.add(new postcodejp.address.StreetTextbox('address2'));
	postcodeJp.setClearAddressIfNotFound(true);
	postcodeJp.observe();
}
if (window.addEventListener) {
	window.addEventListener('load', initPostcodeJp)
} else {
	window.attachEvent('onload', initPostcodeJp)
}