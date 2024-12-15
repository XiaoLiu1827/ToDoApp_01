document.addEventListener('DOMContentLoaded', () => {

	/*
	マイ貯金ルール編集
	 */
	//編集ボタン押下時、入力フォームを表示
	document.querySelectorAll('.open-edit-modal').forEach((element) => {
		element.addEventListener("click", function() {
			const ruleId = this.dataset.id;
			const ruleElement = document.querySelector(`.savings-rule[data-id="${ruleId}"]`);

			// モーダルの値をセット
			document.getElementById("modal-id").value = ruleId;
			document.getElementById("modal-description").value = ruleElement.querySelector("#description")?.value || "";
			document.getElementById("modal-amount").value = ruleElement.querySelector(".card-text").textContent.trim().replace("円", "");

			// モーダルを表示
			document.getElementById("edit-modal").style.display = "block";
		})
	});


	// モーダルを閉じる
	document.getElementById("close-modal").addEventListener("click", function() {
		document.getElementById("edit-modal").style.display = "none";
	});

	//編集内容を保存
	document.getElementById('save-modal-button').addEventListener(`click`, async function() {
		console.log('Button clicked!');
		const id = document.getElementById("modal-id").value;
		const description = document.getElementById("modal-description").value;
		const amount = document.getElementById("modal-amount").value;

		//入力チェック
		const amountPattern = /^[0-9]+(\.[0-9]+)?$/;

		if (!amountPattern.test(amount)) {
			alert('貯金額は半角数字で入力してください。');
		}

		//null以外の項目をセットする　null項目は更新しない 
		const myRule = JSON.stringify({
			"description": description,
			"amount": amount
		}, function(prop, value) {
			if (value === null || value === "") {
				return;
			}
			return value;
		});
		try {
			console.log('Sending fetch request...');
			const response = await fetch(`/savings/api/mySavingRule/update/${id}`, {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json' // JSON形式で送信
				},
				body: myRule // オブジェクトをJSON形式に変換して送信
			});
			if (response.ok) {
				const updatedRule = await response.json();
				const ruleElement = document.querySelector(`.savings-rule[data-id="${id}"]`);
				ruleElement.querySelector('.card-title').textContent = updatedRule.description;
				ruleElement.querySelector('.card-text').textContent = `${updatedRule.amount}円`;
				alert(`更新が完了しました。`);
			} else {
				alert(`更新に失敗しました。`);
			}
		} catch (error) {
			console.error(`error:`, error);
		}
	});
});