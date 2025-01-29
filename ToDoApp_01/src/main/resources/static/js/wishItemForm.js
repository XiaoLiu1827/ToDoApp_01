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
	}
});

imageInput.addEventListener('change', (e) => {
	if (e.target.files.length > 0) {
		handleImage(e.target.files[0]);
	}
});

submitButton.addEventListener('click', async function(event) {
	const form = document.getElementById("wishItemForm");
	const formData = new FormData(form);
	try {
		const response = await fetch("/savings/api/wishItem/add", {
			method: "POST",
			body: formData,
		});
		
		if(response.ok){
			const redirectUrl = await response.text();
			window.location.href = redirectUrl;
		}else{
			console.error("エラー発生：",await response.text());
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




