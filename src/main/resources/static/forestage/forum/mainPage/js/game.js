var canvas = document.getElementById('game-canvas');
var context = canvas.getContext('2d');
var colorSelector = document.getElementById('colorSelector');

var painting = false;

function startDraw(e) {
	painting = true;
	draw(e);
}

function endDraw() {
	painting = false;
	context.beginPath();
}

function draw(e) {
	if (!painting) return;
	context.lineWidth = 10;
	context.lineCap = 'round';
	context.strokeStyle = colorSelector.value;

	var rect = canvas.getBoundingClientRect();
	var x = e.clientX - rect.left;
	var y = e.clientY - rect.top;

	context.lineTo(x, y);
	context.stroke();
	context.beginPath();
	context.moveTo(x, y);
}

document.getElementById('clearCanvas').addEventListener('click', function() {
	const canvas = document.getElementById('game-canvas');
	const ctx = canvas.getContext('2d');
	ctx.clearRect(0, 0, canvas.width, canvas.height);
});

document.getElementById('downloadButton').addEventListener('click', function() {
	var canvas = document.getElementById('game-canvas');
	var image = canvas.toDataURL('image/png');   // 這行將畫布的內容轉換成圖片
	var link = document.createElement('a');  // 創建一個新的鏈接元素
	link.href = image;  // 設定鏈接的目標為圖片的 URL
	link.download = '我的畫作.png';  // 為下載的檔案命名
	link.click();  // 觸發點擊事件，開始下載
});



canvas.addEventListener('mousedown', startDraw);
canvas.addEventListener('mouseup', endDraw);
canvas.addEventListener('mousemove', draw);