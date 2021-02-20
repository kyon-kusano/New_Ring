var initPostcodeJp = function() {
	 // APIキーを指定して住所補完サービスを作成
	var postcodeJp = new postcodejp.address.AutoComplementService('OGr8zzCsIjIfr6XZlp7yFZ3Bfw9hRHbgO8x5rF7');
	// 郵便番号テキストボックスを指定
	postcodeJp.setZipTextbox('post_Number')
	// 住所補完フィールドを追加
	postcodeJp.add(new postcodejp.address.StateTownTextbox('address1'));
	postcodeJp.add(new postcodejp.address.StreetTextbox('address2'));
	// 住所が存在しない場合に住所フィールドをクリアする
	postcodeJp.setClearAddressIfNotFound(true);
	 // 郵便番号テキストボックスの監視を開始
	postcodeJp.observe();
}
if (window.addEventListener) {
	window.addEventListener('load', initPostcodeJp)
} else {
	window.attachEvent('onload', initPostcodeJp)
}