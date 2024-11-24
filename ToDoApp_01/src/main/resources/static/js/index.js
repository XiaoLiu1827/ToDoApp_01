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

////プルダウンに設定したマイ貯金ルールをサーバ送信用に格納する
//function setMyRuleData(button) {
//	const myRuleId = button.getAttribute("data-id");
//
//	// 隠しフィールドに設定
//	document.getElementById("myRuleId").value = myRuleId;
//
//	// フォームを送信
//	button.closest("form").submit();
//}



	/*
	マイ貯金ルール編集
	 */
	//編集ボタン押下時、入力フォームを表示
	function showEditRuleForm() {
		const formContainer = document.getElementById(`form-container`);
		if (formContainer.style.display === `none`) {
			formContainer.style.display = `block`
		} else {
			formContainer.style.display = `none`;
		}
	}

	//編集内容を保存
	document.getElementById(`submit-myRule-button`).addEventListener(`click`, async function() {
		const id = this.dataset.id;
		const title = document.getElementById(`title`).value;
		const description = document.getElementById(`description`).value;
		const amount = document.getElementById(`amount`).value;
		//null以外の項目をセットする　null項目は更新しない 
		const myRule = JSON.stringify({
			"title": title,
			"description": description,
			"amount": amount
		}, function(prop, value) {
			if (value === null || value === "") {
				alert(`${prop} is required!`)
				return;
			}
			return value;
		});
		try {
			await fetch(`/savings/mySavingRule/update/${id}`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json' // JSON形式で送信
				},
				body: myRule // オブジェクトをJSON形式に変換して送信
			});
		} catch (error) {

		}
	});

//フォーム送信処理

////貯金報告
//document.addEventListener("DOMContentLoaded", () => {
//	// メインフォーム送信時の処理
//	document.getElementById("submit-savings").addEventListener("click", function(event) {
//		// デフォルト送信を有効にする
//		document.getElementById("savings").submit();
//	});
//目標額到達時にサーバに通知する
//document.addEventListener("DOMContentLoaded", function() {
//	checkProgress();
//});
//
//function checkProgress() {
//	const wishItems = document.querySelectorAll(".wanted-list .card-body");
//
//	wishItems.forEach((item) => {
//		const currentAmount = parseInt(item.querySelector(".card-text span").textContent);
//		const neededAmount = parseInt(item.querySelector(".card-text span:nth-of-type(2)").textContent);
//
//		if (currentAmount >= neededAmount) {
//			// 目標到達時にサーバーへ通知
//			const itemId = item.getAttribute("data-id");
//			console.log(itemId);
//
//			//削除するアイテムをプルダウンから無効化
//			const option = document.querySelector(`#wishItem option[value="${itemId}"]`);
//			console.log(option);
//			const option2 = document.querySelector('#wishItem option[value="1"]');
//			console.log(option2);
//			if (option) {
//				// 無効化または非表示にする
//				option.disabled = true; // 選択不可にする
//				option.style.display = 'none'; // 非表示にする場合
//			}
//			notifyGoalAchieved(itemId);
//
//		}
//	});
//}
//
//function notifyGoalAchieved(wishItemId) {
//	console.log("delet!!!!")
//	fetch(`/savings/user/goalAchieved?wishItemId=${wishItemId}`, {
//		method: "POST",
//	})
//		.catch((error) => console.error("通知エラー:", error));
//}
//
//
