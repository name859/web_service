function inputCheck(input, msg, min, max, focus) {
	if (input.value.length >= min) return true;
	
	alert(msg +" "+ min +"자 이상 "+ max +"자 이하!");
	if (focus == "focus") {
		input.focus();
	} else if (focus == "select") {
		input.select();
	}
	return false;
}