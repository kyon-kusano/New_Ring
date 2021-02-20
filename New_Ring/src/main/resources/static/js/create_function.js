function cancelsubmit() {
	return false;
}
document.create.btn.addEventListener('click', function() {
	if (document.create.authority.value == "true") {
		var result = window.confirm('管理者権限で登録してよろしいですか？');

		if (result) {
			document.create.submit();

		} else {
			cancelsubmit();
			alert("登録をキャンセルしました。");

		}
	} else {
		document.create.submit();
	}
})