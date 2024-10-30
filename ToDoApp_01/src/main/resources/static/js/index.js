const selectElement = document.getElementById(`myRule`);
selectElement.addEventListener(`change`, function() {
	let selectedOption = selectElement.options[selectElement.selectedIndex];
	let defaultSavingsAmount = selectedOption.getAttribute(`data-default-savings-amount`);
	console.log('Selected Rule ID:', selectElement.value);
	console.log('Default Savings Amount:', defaultSavingsAmount);

	const inputAmount = document.getElementById(`amount`);
	inputAmount.value = defaultSavingsAmount;
});

function setMyRuleData(button) {
	const myRuleId = button.getAttribute("data-id");

	// 隠しフィールドに設定
	document.getElementById("myRuleId").value = myRuleId;

	// フォームを送信
	button.closest("form").submit();
}