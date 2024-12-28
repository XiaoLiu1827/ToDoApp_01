document.addEventListener('DOMContentLoaded', () => {
	const totalSavingsElement = document.getElementById('total-savings');
	const totalSavingsAmount = parseInt(document.getElementById('total-savings').innerText, 10);
	const withdrawButton = document.getElementById('withdraw-button');
	const withdrawItemList = document.querySelectorAll('.withdraw-item');
	const closeEditModal = document.getElementById("close-edit-modal");
	const closeWithdrawModal = document.getElementById("close-withdraw-modal");
	const myRuleList = document.querySelectorAll('.savings-rule');
	const achievedButtons = document.querySelectorAll('.achieved');

	/**貯金達成ボタン関連処理 */
	//①活性非活性制御,②達成処理
	myRuleList.forEach((rule) => {
		const achievedButton = rule.querySelector('button.achieved');
		const unachievedButton = rule.querySelector('button.unachieved');
		const ruleId = rule.dataset.id;

		//曜日判定用項目
		const dayIndex = new Date().getDay();
		const dayNames = ["SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];
		const dayName = dayNames[dayIndex];
		const frequency = rule.dataset.frequency;
		//ボタン押下判定項目
		const todayString = new Date().toDateString();
		const dateKey = `buttonClicked_${achievedButton.dataset.id}`;

		//判定条件
		const isTodayIncluded = frequency.includes(dayName);
		const isAlreadyClicked = localStorage.getItem(dateKey) === todayString;

		//曜日判定=true,かつボタン押下判定=falseのとき活性表示
		achievedButton.disabled = !(isTodayIncluded && !isAlreadyClicked);
		unachievedButton.disabled = !(isTodayIncluded && !isAlreadyClicked);

		// 達成ボタンのクリック処理
		achievedButton.addEventListener('click', async function() {
			//ボタン押下の履歴を残す
			localStorage.setItem(dateKey, todayString);
			achievedButton.disabled = true;
			unachievedButton.disabled = true;
			
			//サーバとの通信を実行する
			const response = await fetch(`/savings/api/deposit/${ruleId}`, {
				method: 'POST'
			});
			if (response.ok) {
				const updatedTotalAmount = await response.json();
				console.log(updatedTotalAmount);
				totalSavingsElement.textContent = updatedTotalAmount;
				alert('更新が完了しました。');
			} else {
				alert('更新に失敗しました。');
			}
		});

		// 未達成ボタンのクリック処理 (サーバー送信を防止)
		unachievedButton.addEventListener('click', (event) => {
			event.preventDefault(); // サーバーへのリクエストを防止
			localStorage.setItem(dateKey, todayString);
			achievedButton.disabled = true;
			unachievedButton.disabled = true;
		});
	});


	/**取り崩し処理 */
	//モーダル表示
	withdrawButton.addEventListener('click', () => {
		document.getElementById("withdraw-list-modal").style.display = "block";
	});

	//選択ボタン活性制御
	withdrawItemList.forEach((item) => {
		const neededAmount = parseInt(item.querySelector('.item-content').dataset.amount, 10);
		const selectButton = item.querySelector('button[type="submit"]');
		if (neededAmount > totalSavingsAmount) {
			selectButton.disabled = true; // ボタンを無効化
		}
	});

	// モーダルを閉じる
	closeWithdrawModal.addEventListener("click", function() {
		document.getElementById("withdraw-list-modal").style.display = "none";
	});


	//	/*
	//	貯金ルール達成処理
	//	 */
	//	achievedButtons.forEach(button => {
	//		button.addEventListener("click", function() {
	//			const ruleId = button.dataset.id
	//		});
	//	});
	//	

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
			document.getElementById("modal-description").value = ruleElement.querySelector(".card-title").textContent;
			document.getElementById("modal-amount").value = ruleElement.querySelector(".card-text").textContent.trim().replace("円", "");

			// モーダルを表示
			document.getElementById("edit-modal").style.display = "block";
		})
	});


	// モーダルを閉じる
	closeEditModal.addEventListener("click", function() {
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