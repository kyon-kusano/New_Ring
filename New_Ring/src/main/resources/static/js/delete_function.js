function cancelsubmit() {
	return false;
}
document.getElementById("deleting").addEventListener('click', function(e) {
	var result = window.confirm('本当に削除しますか？');

	if (!result) {
		e.preventDefault();
		alert("キャンセルしました。");
	}

})