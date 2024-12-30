const totalSavingsElement = document.getElementById('total-savings');
const totalSavingsAmount = parseInt(document.getElementById('total-savings').innerText, 10);
const withdrawButton = document.getElementById('withdraw-button');
const withdrawItemList = document.querySelectorAll('.withdraw-item');
const openEditModal = document.querySelectorAll('.open-edit-modal');
const closeEditModal = document.getElementById("close-edit-modal");
const closeWithdrawModal = document.getElementById("close-withdraw-modal");
const myRuleList = document.querySelectorAll('.savings-rule');
const achievedButtons = document.querySelectorAll('.achieved');

/**取り崩し処理 */
//モーダル表示
withdrawButton.addEventListener('click', () => {
	document.getElementById("withdraw-list-modal").style.display = "block";
});

// モーダルを閉じる
closeWithdrawModal.addEventListener("click", function() {
	document.getElementById("withdraw-list-modal").style.display = "none";
});

//編集モーダルを開く
openEditModal.forEach((element) => {
	element.addEventListener("click", function() {
		const ruleId = this.dataset.id;
		const ruleElement = document.querySelector(`.savings-rule[data-id="${ruleId}"]`);

		handleOpenEditModal(ruleId, ruleElement);
	})
});

// モーダルを閉じる
closeEditModal.addEventListener("click", function() {
	document.getElementById("edit-modal").style.display = "none";
});


//編集内容を保存
document.getElementById('save-modal-button').addEventListener(`click`, function() {
	const id = document.getElementById("modal-id").value;
	const description = document.getElementById("modal-description").value;
	const amount = document.getElementById("modal-amount").value;

	saveRuleEdit(id, description, amount);
});


document.addEventListener('DOMContentLoaded', () => {
	/**貯金達成ボタン関連処理 */
	myRuleList.forEach((rule) => {
		const achievedButton = rule.querySelector('button.achieved');
		const unachievedButton = rule.querySelector('button.unachieved');
		const ruleId = rule.dataset.id;
		const frequency = rule.dataset.frequency;

		//ボタン押下判定項目
		const todayString = new Date().toDateString();
		const dateKey = `buttonClicked_${achievedButton.dataset.id}`;

		//ボタン活性非活性制御
		controllButtonDisabled(achievedButton, unachievedButton, frequency, todayString, dateKey);

		// 達成ボタンのクリック処理
		achievedButton.addEventListener('click', function() {
			handleAchievedButtonClick(achievedButton, unachievedButton, ruleId, todayString, dateKey);
		});

		// 未達成ボタンのクリック処理
		unachievedButton.addEventListener('click', (event) => {
			handleUnachievedButtonClick(achievedButton, unachievedButton, event, todayString, dateKey);
		});
	});

	//取り崩し選択ボタン
	withdrawItemList.forEach((item) => {
		const neededAmount = parseInt(item.querySelector('.item-content').dataset.amount, 10);
		const selectButton = item.querySelector('button[type="submit"]');
		handleSelectButtonDisabled(neededAmount, selectButton);
	});
});


//貯金ルール編集内容を保存
async function saveRuleEdit(id, description, amount) {
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

	// サーバーに更新リクエストを送信し、レスポンスを処理する
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
}

//ボタンの活性制御
function controllButtonDisabled(achievedButton, unachievedButton, frequency, todayString, dateKey) {
	//曜日判定用項目
	const dayIndex = new Date().getDay();
	const dayNames = ["SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];
	const dayName = dayNames[dayIndex];

	//判定条件
	const isTodayIncluded = frequency.includes(dayName);
	const isAlreadyClicked = localStorage.getItem(dateKey) === todayString;

	//曜日判定=true,かつボタン押下判定=falseのとき活性表示
	achievedButton.disabled = !(isTodayIncluded && !isAlreadyClicked);
	unachievedButton.disabled = !(isTodayIncluded && !isAlreadyClicked);
}

//達成ボタンクリック処理
async function handleAchievedButtonClick(achievedButton, unachievedButton, ruleId, todayString, dateKey) {
	handleButtonState(achievedButton, unachievedButton, dateKey, todayString);

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
};

//未達成ボタンクリック処理
function handleUnachievedButtonClick(achievedButton, unachievedButton, event, todayString, dateKey) {
	event.preventDefault(); // サーバーへのリクエストを防止
	handleButtonState(achievedButton, unachievedButton, dateKey, todayString);
}

//クリック履歴の保存と非活性化を実行
function handleButtonState(achievedButton, unachievedButton, dateKey, todayString) {
	localStorage.setItem(dateKey, todayString);
	achievedButton.disabled = true;
	unachievedButton.disabled = true;
}

//取り崩し選択ボタン活性制御処理
function handleSelectButtonDisabled(neededAmount, selectButton) {
	if (neededAmount > totalSavingsAmount) {
		selectButton.disabled = true; // ボタンを無効化
	}
}

//編集モーダルを開き、現在値をセットする
function handleOpenEditModal(ruleId, ruleElement) {
	// モーダルの値をセット
	document.getElementById("modal-id").value = ruleId;
	document.getElementById("modal-description").value = ruleElement.querySelector(".card-title").textContent;
	document.getElementById("modal-amount").value = ruleElement.querySelector(".card-text").textContent.trim().replace("円", "");

	// モーダルを表示
	document.getElementById("edit-modal").style.display = "block";
}

//金額を円表示する
function formatToYen(amount) {
	return '¥' + Number(amount).toLocaleString('ja-JP');
}

