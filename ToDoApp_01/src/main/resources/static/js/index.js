//const selectElement = document.getElementById(`myRule`);
//selectElement.addEventListener(`change`, function() {
//	let selectedOption = selectElement.options[selectElement.selectedIndex];
//	let defaultSavingsAmount = selectedOption.getAttribute(`data-default-savings-amount`);
//	console.log('Selected Rule ID:', selectElement.value);
//	console.log('Default Savings Amount:', defaultSavingsAmount);
//
//	const inputAmount = document.getElementById(`amount`);
//	inputAmount.value = defaultSavingsAmount;
//});

//プルダウンに設定したマイ貯金ルールをサーバ送信用に格納する
function setMyRuleData(button) {
	const myRuleId = button.getAttribute("data-id");

	// 隠しフィールドに設定
	document.getElementById("myRuleId").value = myRuleId;

	// フォームを送信
	button.closest("form").submit();
}

//目標額到達時にサーバに通知する
document.addEventListener("DOMContentLoaded", function() {
	checkProgress();
});

function checkProgress() {
	const wishItems = document.querySelectorAll(".wanted-list .card-body");

	wishItems.forEach((item) => {
		const currentAmount = parseInt(item.querySelector(".card-text span").textContent);
		const neededAmount = parseInt(item.querySelector(".card-text span:nth-of-type(2)").textContent);

		if (currentAmount >= neededAmount) {
			// 目標到達時にサーバーへ通知
			console.log(item.getAttribute("data-id"));
			notifyGoalAchieved(item.getAttribute("data-id"));
		}
	});
}

function notifyGoalAchieved(wishItemId) {
	fetch(`/savings/user/goalAchieved?wishItemId=${wishItemId}`, {
		method: "POST",
	})
		.catch((error) => console.error("通知エラー:", error));
}
