///**
// * 
// */
//
//let goalAmount = 0;
//let currentAmount = 0;
//
//function setGoal() {
//	goalAmount = parseInt(document.getElementById("goal-amount").value);
//	currentAmount = 0;
//	updateProgress();
//}
//
//function updateProgress() {
//	const progressBar = document.getElementById("progress");
//	const progressPercent = (currentAmount / goalAmount) * 100;
//	progressBar.style.width = progressPercent + "%";
//
//	if (currentAmount >= goalAmount) {
//		alert("Congratulations! You have reached your savings goal!");
//	}
//}
//
//function saveMoney() {
//	const amountSaved = parseInt(prompt("いくら貯金しましたか？"));
//	if (isNaN(amountSaved) || amountSaved <= 0) {
//		alert("Please enter a valid amount.");
//		return;
//	}
//
//	currentAmount += amountSaved;
//	updateProgress();
//
//	const remainingAmount = goalAmount - currentAmount;
//	if (remainingAmount > 0) {
//		alert("You still need to save " + remainingAmount + " to reach your goal!");
//	}
//}