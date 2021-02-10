var account = document.getElementsByTagName("tr");
for (var i = 0; i < account.length - 1; i++) {

	var getBirthday = document.getElementsByClassName("birthday")[i];
	var birthday = getBirthday.innerHTML;

	function calculate_age(birthday) {
		var birthdayDate = birthday.split('-');
		var today = new Date();
		var age = today.getFullYear() - parseFloat(birthdayDate[0]);
		var b = Number(birthdayDate[1] + adjust_digits(birthdayDate[2], 2));
		var t = Number((today.getMonth() + 1)
			+ adjust_digits(today.getDate(), 2));
		if (t < b) {
			age--;
		}
		return age;
	}
	function adjust_digits(num, digit) {
		var no = String(num);
		while (no.length < digit) {
			no = '0' + no;
		}
		return no;
	}
	function changeAge() {
		getBirthday.innerHTML = calculate_age(birthday) + "歳";
	}
	changeAge();

}

for (var i = 0; i < account.length - 2; i++) {
	var no = document.getElementsByClassName("no")[i];
	no.innerHTML = i + 1;
}

function departmentSubmit() {
	var departmentValue = document.getElementById("departments");
	var departmentIndex = departmentValue.selectIndex;
	var serchForm = document.getElementById("serch_form");
	serchForm.setAttribute("v", departmentValue[departmentIndex]);
	serchForm.submit();
}

var serchInput = document.getElementById("serch_input");
function serch() {
	var serchForm = document.getElementById("serch_form");
	var serchFormText = serchForm.innerHTML;
	serchForm.setAttribute("action", "/account/list");
	serchForm.setAttribute("value", serchFormText);
	if(serchInput.value != null && ""){
	serchForm.submit();
	window.alert("test");
	}

}
serchInput.addEventListener('change', serch());
