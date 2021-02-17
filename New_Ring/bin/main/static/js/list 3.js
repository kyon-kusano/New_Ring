var employee = document.getElementsByClassName("employee");
for (var i = 0; i < employee.length; i++) {

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



