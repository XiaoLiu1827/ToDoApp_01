const totalSavingsElement = document.getElementById('total-savings');
let totalSavingsAmount = Number(document.getElementById('total-savings').dataset.amount);
const withdrawButton = document.getElementById('withdraw-button');
const withdrawItemList = document.querySelectorAll('.withdraw-item');
const openEditModalList = document.querySelectorAll('.open-edit-modal');
const closeEditModalEl = document.getElementById(`close-edit-modal`);
const closeWithdrawModal = document.getElementById("close-withdraw-modal");
const closeModalButtonList = document.querySelectorAll('.modal-close');
const myRuleList = document.querySelectorAll('.savings-rule');
const achievedButtons = document.querySelectorAll('.achieved');
const saveByManualButtons = document.getElementById('save-manual-button');
const wishItemList = document.querySelectorAll('.wishlist-item');
const withdrawlList = document.querySelectorAll('.withdraw-item');
const withdrawAmountElements = document.querySelectorAll('.withdraw-amount');
const withItemAmountElements = document.querySelectorAll('.wishItem-amount');
const editModalEl = document.getElementById("edit-modal");
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
let achievedItems = [];
let isPurchaseable = false;
const manualInputButton = document.querySelector('.manual-input');


//各モーダルを閉じる
closeModalButtonList.forEach((button) => {
	button.addEventListener('click', function() {
		const modalId = this.closest('.modal-overlay').id;
		closeModal(modalId);
	})
})

//手動入力ボタンを押下時モーダルを呼びだす
manualInputButton.addEventListener('click', () => {
	openModal(`manual-input-modal`);
	//貯金額フォームのみを表示


})

//モーダル表示
withdrawButton.addEventListener('click', () => {
	openModal(`withdraw-list-modal`);
});

wishItemList.forEach((element) => {
	console.log(element.dataset.id);
	const editButton = element.querySelector('.transition-edit-item');
	//欲しいもの編集画面に遷移
	editButton.addEventListener("click", function() {
		// Store selected wishItem data before transition
		const wishItem = {
			id: element.dataset.id,
			name: element.dataset.name,
			neededAmount: element.dataset.amount,
			imagePath: element.dataset.image
		};
		localStorage.setItem("editWishItem", JSON.stringify(wishItem));

		// Redirect to the form
		window.location.href = "/savings/wishItem";

	})
});
//編集モーダルを開く
openEditModalList.forEach((element) => {
	element.addEventListener("click", function() {
		const ruleId = this.dataset.id;
		const ruleElement = document.querySelector(`.savings-rule[data-id="${ruleId}"]`);

		handleOpenEditModal(ruleId, ruleElement);
	})
});

//手動入力貯金保存ボタンクリック処理
saveByManualButtons.addEventListener('click', () => {
	const inputAmount = document.getElementById('save-amount').value;

	if (inputAmount === null || inputAmount.trim() === "") {
		console.error("貯金額が入力されていません。");
		alert("貯金額を入力してください");
		return;  // 処理を中止
	}

	const amount = parseInt(inputAmount, 10);

	if (isNaN(amount)) {
		alert("有効な金額を入力してください");
		return;  // 処理を中止
	}

	handleManualInput(amount)
});

//編集モーダルボタンクリック処理
document.querySelectorAll(`.button-modal`).forEach(button => {
	button.addEventListener(`click`, (event) => {
		const ruleId = document.getElementById("modal-id").value;

		const title = document.getElementById("modal-title").value;
		const amount = document.getElementById("modal-amount").value;
		const buttonId = event.target.id;
		if (buttonId === `delete-modal-button`) {
			//モーダル画面を切替表示
			closeModal(`edit-modal`);
			setTimeout(() => {
				openModal(`deleteConfirmModal`);
			});
		} else if (buttonId === `save-modal-button`) {
			//編集内容を保存
			console.log(`保存処理を実行します。modal-id: ${ruleId}`);
			saveRuleEdit(ruleId, title, amount);
			closeModal(`edit-modal`);
		} else if (buttonId === `cancel-modal-button`) {
			closeModal(`deleteConfirmModal`);
		} else if (buttonId === `confirm-modal-button`) {
			deleteRule(ruleId);
			closeModal(`deleteConfirmModal`);
		}
	});
});

//削除確認モーダル処理
const deleteConfirmModalEl = document.getElementById(`deleteConfirmModal`);
deleteConfirmModalEl

document.addEventListener('DOMContentLoaded', () => {
	//金額表示
	formatToYen([totalSavingsElement]);
	formatToYen(withdrawAmountElements);
	formatToYen(withItemAmountElements);

	updateProgress();


	myRuleList.forEach((rule) => {
		const achievedButton = rule.querySelector('button.achieved');
		const unachievedButton = rule.querySelector('button.unachieved');
		const ruleId = rule.dataset.id;
		const frequency = rule.dataset.frequency;
		const amountElement = rule.querySelector('.card-text');

		//ボタン押下判定項目
		const todayString = new Date().toDateString();
		const dateKey = `buttonClicked_${achievedButton.dataset.id}`;

		//金額表示
		formatToYen([amountElement]);

		//ボタン活性非活性制御
		controllButtonDisabled(achievedButton, unachievedButton, frequency, todayString, dateKey);

		// 達成ボタンのクリック処理
		achievedButton.addEventListener('click', function() {
			handleAchievedButtonClick(achievedButton, unachievedButton, ruleId, todayString, dateKey)
				.then(() => {
					updateProgress();
				});
		});

		// 未達成ボタンのクリック処理
		unachievedButton.addEventListener('click', (event) => {
			handleUnachievedButtonClick(achievedButton, unachievedButton, event, todayString, dateKey);
		});
	});

	//取り崩し選択ボタン
	let hasPurchasabaleItem = false;
	withdrawItemList.forEach((item) => {
		const neededAmount = parseInt(item.querySelector('.item-content').dataset.amount, 10);
		const selectButton = item.querySelector('button[type="submit"]');
		handleSelectButtonDisabled(neededAmount, selectButton);
		if (totalSavingsAmount >= neededAmount) {
			hasPurchasabaleItem = true;
		}
	});
	if (!hasPurchasabaleItem) {
		const piggyBankEl = document.getElementById('piggy-bank');
		const purchaseStatusEl = document.getElementById('purchaseStatus');
		piggyBankEl.classList.remove("purchasable");
		purchaseStatusEl.classList.remove("show");
		isPurchaseable = false;
	}
});


//貯金ルール編集内容を保存
async function saveRuleEdit(id, title, amount) {

	//入力チェック
	const amountPattern = /^[0-9]+(\.[0-9]+)?$/;

	if (!amountPattern.test(amount)) {
		alert('貯金額は半角数字で入力してください。');
	}

	//null以外の項目をセットする　null項目は更新しない 
	const myRule = JSON.stringify({
		"title": title,
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
				'Content-Type': 'application/json', // JSON形式で送信
				'X-CSRF-TOKEN': csrfToken // 必要なら追加
			},
			credentials: 'include',
			body: myRule // オブジェクトをJSON形式に変換して送信
		});
		if (response.ok) {

			const updatedRule = await response.json();
			const ruleElement = document.querySelector(`.savings-rule[data-id="${id}"]`);
			ruleElement.querySelector('.card-title').textContent = updatedRule.title;
			ruleElement.querySelector('.card-text').textContent = `${parseFloat(updatedRule.amount).toLocaleString("ja-JP", { style: "currency", currency: "JPY" })}`;
			alert(`更新が完了しました。`);
		} else {
			alert(`更新に失敗しました。`);
		}
	} catch (error) {
		console.error(`error:`, error);
	}
}

//貯金ルール削除
async function deleteRule(id) {
	// サーバーに更新リクエストを送信し、レスポンスを処理する
	try {
		console.log('Sending fetch request...');
		const response = await fetch(`/savings/api/mySavingRule/delete/${id}`, {
			method: 'POST',

			headers: {
				'X-CSRF-TOKEN': csrfToken // 必要なら追加
			},
			credentials: 'include',
		});
		if (response.ok) {
			console.log(`削除成功`);
			const ruleElement = document.querySelector(`[data-id='${id}']`).closest('.savings-rule');
			if (ruleElement) {
				ruleElement.style.display = 'none';
			}
		} else {
			if (response.status === 404) {
				alert(`リソースが見つかりません`);
			} else {
				console.log(`削除失敗`);
			}
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

//手動入力貯金処理
async function handleManualInput(inputAmount) {

	const response = await fetch('/savings/api/save', {
		method: 'POST',
		headers: {
			'X-CSRF-TOKEN': csrfToken,// 必要なら追加
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		body: `amount=${inputAmount}`,
		credentials: 'include'
	});

	if (response.ok) {
		totalSavingsAmount = await response.json();
		setDataAfterSaving(totalSavingsAmount)
		alert('更新が完了しました。');
	} else {
		alert('更新に失敗しました。');
	}

	closeModal('manual-input-modal');
}

//達成ボタンクリック処理
async function handleAchievedButtonClick(achievedButton, unachievedButton, ruleId, todayString, dateKey) {
	handleButtonState(achievedButton, unachievedButton, dateKey, todayString);

	//サーバとの通信を実行する
	const response = await fetch(`/savings/api/deposit/${ruleId}`, {
		method: 'POST',
		headers: {
			'X-CSRF-TOKEN': csrfToken // 必要なら追加
		},
		credentials: 'include',
	});
	if (response.ok) {
		totalSavingsAmount = await response.json();
		setDataAfterSaving(totalSavingsAmount)
		alert('更新が完了しました。');

		//取り崩しボタン非活性判定
		withdrawItemList.forEach((item) => {
			console.log('button cliccked');
			const neededAmount = parseInt(item.querySelector('.item-content').dataset.amount, 10);
			const selectButton = item.querySelector('button[type="submit"]');
			handleSelectButtonDisabled(neededAmount, selectButton);
		});
	} else {
		alert('更新に失敗しました。');
	}
};

//貯金処理完了後の表示データの貼り付け
function setDataAfterSaving(totalSavingsAmount) {
	totalSavingsElement.setAttribute('data-amount', totalSavingsAmount);
	totalSavingsElement.textContent = totalSavingsAmount.toLocaleString("ja-JP", { style: "currency", currency: "JPY" });
}

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
	} else {
		selectButton.disabled = false;
	}
}

//編集モーダルを開き、現在値をセットする
function handleOpenEditModal(ruleId, ruleElement) {
	// モーダルの値をセット
	document.getElementById("modal-id").value = ruleId;
	document.getElementById("modal-title").value = ruleElement.querySelector(".card-title").textContent;
	console.log(ruleElement.querySelector(".card-text").textContent.trim().replace(/[￥,]/g, ""));
	document.getElementById("modal-amount").value = ruleElement.querySelector(".card-text").textContent.trim().replace(/[￥,]/g, "");

	// モーダルを表示
	openModal(`edit-modal`);
}

//金額を円表示する
function formatToYen(elements) {
	elements.forEach(amountElement => {
		const amountValue = amountElement.dataset.amount; // data-amount の値を取得
		amountElement.textContent = `${parseFloat(amountValue).toLocaleString("ja-JP", { style: "currency", currency: "JPY" })}`;
	});
}

function updateProgress() {
	wishItemList.forEach(item => {
		const targetAmount = Number(item.querySelector('.wishItem-amount').dataset.amount);
		const progressEl = item.querySelector('.progress');
		const progress = (totalSavingsAmount / targetAmount) * 100;
		const progressBarEl = item.querySelector('.progress-bar');

		//パーティクルの生成
		const particle = document.createElement("div");
		particle.classList.add("particle");
		progressBarEl.appendChild(particle);

		//達成状況判定
		checkeIsAchieved(item, targetAmount);

		//バーとパーティクルをスタート位置に設定
		progressEl.style.transition = 'none';
		progressEl.style.width = '0';
		particle.style.transition = 'none';
		particle.style.left = `-${particle.offsetWidth}px`;
		particle.style.animation = 'none';

		//バーとパーティクルを動かす
		setTimeout(() => {
			progressEl.style.transition = 'width 2s ease-in-out';
			progressEl.style.width = `${progress}%`;
			particle.style.transition = 'left 2s ease-in-out';
			particle.style.left = `calc(${progress}% - ${particle.offsetWidth}px)`;
			particle.style.animation = 'particleMove 3s ease-in-out forwards';
		}, 100);
	});
}

function checkeIsAchieved(item, targetAmount) {
	const statusEl = item.querySelector('.status-badge');
	if (totalSavingsAmount >= targetAmount) {
		statusEl.classList.add("status-complete");
		statusEl.classList.remove("status-incomplete");
		statusEl.textContent = "購入可能";
		if (!isPurchaseable) {
			const piggyBankEl = document.getElementById('piggy-bank');
			const purchaseStatusEl = document.getElementById('purchaseStatus');
			piggyBankEl.classList.add("purchasable");
			purchaseStatusEl.classList.add("show");
			isPurchaseable = true;
		}
	} else {
		statusEl.classList.remove("status-complete");
		if (!statusEl.classList.contains("status-incomplete")) {
			statusEl.classList.add("status-incomplete");
			statusEl.textContent = "未達成";
		}
	}
}

function openModal(modalId) {
	const modal = document.getElementById(modalId);
	modal.classList.add("active");
	modal.querySelector(`.modal-content`).classList.add(`active`);
}

function closeModal(modalId) {
	const modal = document.getElementById(modalId);
	modal.querySelector(`.modal-content`).classList.remove(`active`);
	setTimeout(() => {
		modal.classList.remove(`active`);
	}, 300);
}
