// 页面加载时更新购物车
window.onload = function() {
	var currentTime = new Date();
	var cartData = JSON.parse(getCookie('shoppingCart')) || [];
	shoppingCart = cartData;
	timestamp = currentTime
	updateCart();
};

// 更新购物车表格和总价
function updateCart() {
	var cartItems = document.getElementById('cartItems');
	var totalPrice = document.getElementById('totalPrice');

	cartItems.innerHTML = ''; // 清空购物车表格

	var total = 0;

	// 遍历购物车数组，生成表格行并累计价格
	for (var i = 0; i < shoppingCart.length; i++) {
		var item = shoppingCart[i];
		item.timestamp = new Date();

		var row = document.createElement('tr');

		var brandCell = document.createElement('td');
		brandCell.innerText = item.brand;
		row.appendChild(brandCell);

		var modelCell = document.createElement('td');
		modelCell.innerText = item.model;
		row.appendChild(modelCell);

		var priceCell = document.createElement('td');
		priceCell.innerText = item.price.toFixed(2); // 保留两位小数
		row.appendChild(priceCell);

		cartItems.appendChild(row);

		total += item.price;
	}

	totalPrice.innerText = '總價: ' + total.toFixed(2); // 显示总价，保留两位小数
}

$(function() {
	$('#clear').on('click', function() {
		Swal.fire({
			title: '確認清空購物車？',
			text: '您確定要清空購物車嗎？',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: '確認',
			cancelButtonText: '取消'
		}).then((result) => {
			if (result.isConfirmed) {
				// 执行删除购物车的操作
				shoppingCart = [];
				updateCart();

				// 删除cookie中的购物车数据
				setCookie('shoppingCart', '', -1); // 将过期天数设置为负数以立即删除cookie
			}
		});
	});
});



$(function() {
	$('#buy').on('click', function() {
		Swal.fire({
			title: '確認購買商品？',
			text: '您確定要購買這些商品嗎？',
			icon: 'info',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: '確認',
			cancelButtonText: '取消'
		}).then((result) => {
			if (result.isConfirmed) {
				$.ajax({
					type: "POST",
					url: "/phocos/api/store", // 修改为与后端控制器中的请求映射路径相对应
					contentType: "application/json",
					data: JSON.stringify(shoppingCart),
					success: function() {
						Swal.fire({
							title: '購買完成',
							text: '商品已成功購買',
							icon: 'success',
							confirmButtonColor: '#3085d6',
							confirmButtonText: '確認'
						});
						shoppingCart = [];
						updateCart();
						setCookie('shoppingCart', '', -1);
					},
					error: function() {
						alert("保存購物車數據時出错。");
					}
				});
			}
		});
	});
});


