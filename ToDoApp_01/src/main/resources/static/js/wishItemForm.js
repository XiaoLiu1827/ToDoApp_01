/**
 * 
 */

const form = document.getElementById('wishlistForm');
const imageUpload = document.getElementById('imageUpload');
const imageInput = document.getElementById('imageInput');
const imagePreview = document.getElementById('imagePreview');
const uploadPlaceholder = document.getElementById('uploadPlaceholder');
const amountError = document.getElementById('amountError');
const submitButton = document.getElementById('submit-button');
const deleteButton = document.getElementById('delete-button');
const formGroup = document.querySelectorAll('.form-group');
let isImageChanged = false;

document.addEventListener('DOMContentLoaded', () => {
	const storedData = localStorage.getItem("editWishItem");
	if (storedData) {
		const wishItem = JSON.parse(storedData);
		const encodedImagePath = encodeURIComponent(wishItem.imagePath);
		const deleteButton = document.getElementById('delete-button');

		//削除ボタンを表示する
		deleteButton.style.display = "block";
		//idをつける
		console.log(wishItem);
		document.getElementById("id").value = wishItem.id;
		document.getElementById("name").value = wishItem.name;
		document.getElementById("neededAmount").value = wishItem.neededAmount;

		// 画像の表示
		const imagePreview = document.getElementById('imagePreview');
		if (encodedImagePath && encodedImagePath !== 'no_image') {
			imagePreview.src = '/savings/api/wishItem/image?fileName=' + encodedImagePath; // サーバーから画像を表示
			imagePreview.classList.add('visible');  // 画像プレビューが表示されるようにクラスを追加
		} else {
			imagePreview.src = '';  // 画像がない場合
			imagePreview.classList.remove('visible');
		}

		// Cleanup storage after use
		localStorage.removeItem("editWishItem");
	}
});


// Image upload handling
imageUpload.addEventListener('click', () => imageInput.click());

imageUpload.addEventListener('dragover', (e) => {
	e.preventDefault();
	imageUpload.style.borderColor = '#2196F3';
});

imageUpload.addEventListener('dragleave', (e) => {
	e.preventDefault();
	imageUpload.style.borderColor = '#e0e0e0';
});

imageUpload.addEventListener('drop', (e) => {
	e.preventDefault();
	imageUpload.style.borderColor = '#e0e0e0';

	const file = e.dataTransfer.files[0];
	if (file && file.type.startsWith('image/')) {
		handleImage(file);
		isImageChanged = true;
	}
});

imageInput.addEventListener('change', (e) => {
	if (e.target.files.length > 0) {
		handleImage(e.target.files[0]);
		isImageChanged = true;

	}
});

deleteButton.addEventListener('click', async function(event) {
	const id = document.getElementById('id')?.value;

	try {
		console.log('Sending fetch request...');
		const response = await fetch(`/savings/api/wishItem/delete/${id}`, {
			method: 'POST',
		});
		if (response.ok) {
			const redirectUrl = await response.text();
			window.location.href = redirectUrl;
		} else {
			console.error("エラー発生：", await response.text());
		}
	} catch (error) {
		console.error(`error:`, error);
	}
});

submitButton.addEventListener('click', async function(event) {
	const form = document.getElementById("wishItemForm");
	const id = document.getElementById('id')?.value;
	const formData = new FormData(form);
	let hasError = false;

	//入力チェック
	formGroup.forEach((formElement) => {

		let inputField = formElement.querySelector('.form-input');
		if (!inputField) return; // null ならスキップ
		let errorMsg = formElement.querySelector('.error-message');
		let inputValue = inputField.value.trim();

		//未入力チェック
		if (!inputValue) {
			inputField.classList.add("input-error");
			errorMsg.style.display = "block";
			event.preventDefault();
			hasError = true;
			return;
		} else {
			inputField.classList.remove("input-error");
			errorMsg.style.display = "none";
		}

		//数値チェック
		if (inputField.id === "amount" && (!/^\d+(\.\d{1,2})?$/.test(inputValue)
			|| Number(inputValue) <= 0)) {
			inputField.classList.add("input-error");
			inputField.value = "金額には有効な値を入力してください";
			errorMsg.style.display = "block";
			hasError = true;
		} else {
			inputField.classList.remove("input-error");
			errorMsg.style.display = "none";
		};
	});

	if (hasError) return;

	//変更がなければ画像を削除してアップロードしない
	if (!isImageChanged) {
		//ここでsrcに画像があるかを確認する
		const imagePreview = document.getElementById('imagePreview');
		//画像があれば削除する
		if (imagePreview.src && imagePreview.src !== window.location.href) {
			formData.delete("image");
			formData.append("keepCurrentImage", true);
		}
	}

	try {
		//idが設定されている場合は更新、以外は登録
		const response = await fetch(id ? `/savings/api/wishItem/update/${id}` : "/savings/api/wishItem/add", {
			method: "POST",
			body: formData,
		});

		if (response.ok) {
			const redirectUrl = await response.text();
			window.location.href = redirectUrl;
		} else {
			console.error("エラー発生：", await response.text());
		}

	} catch (error) {
		console.log(error);
	}
})



function handleImage(file) {
	const reader = new FileReader();
	reader.onload = (e) => {
		imagePreview.src = e.target.result;
		imagePreview.classList.add('visible');
		uploadPlaceholder.style.display = 'none';
		imageUpload.classList.add('has-image');
	};
	reader.readAsDataURL(file);
}




