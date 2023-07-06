// 页面加载时更新购物车
window.onload = function() {
	var cartData = JSON.parse(getCookie('shoppingCart')) || [];
	shoppingCart = cartData;

	updateCart();
};

// 更新购物车表格和总价
function updateCart() {
	var cartItems = document.getElementById('cartItems');
	var totalPrice = document.getElementById('totalPrice');

	cartItems.innerHTML = ''; // 清空购物车表格

	var total = 0; // 总价

	// 遍历购物车数组，生成表格行并累计价格
	for (var i = 0; i < shoppingCart.length; i++) {
		var item = shoppingCart[i];
		var row = document.createElement('tr');

		var modelCell = document.createElement('td');
		modelCell.innerText = item.model;
		row.appendChild(modelCell);

		var brandCell = document.createElement('td');
		brandCell.innerText = item.brand;
		row.appendChild(brandCell);

		var priceCell = document.createElement('td');
		priceCell.innerText = item.price.toFixed(2); // 保留两位小数
		row.appendChild(priceCell);

		cartItems.appendChild(row);

		total += item.price;
	}

	totalPrice.innerText = '总价: ' + total.toFixed(2); // 显示总价，保留两位小数
}

// 清除购物车数据
function clearCart() {
	// 弹出确认对话框
	var confirmClear = confirm("是否确定要清空购物车？");

	// 如果用户点击确认，则执行清空购物车的操作
	if (confirmClear) {
		shoppingCart = [];
		updateCart();

		// 删除cookie中的购物车数据
		setCookie('shoppingCart', '', -1); // 将过期天数设置为负数以立即删除cookie
	}
}
function saveCart() {   
    $.ajax({
        type: "POST",
        url: "/phocos/api/store", // 修改为与后端控制器中的请求映射路径相对应
        contentType: "application/json",
        data:JSON.stringify(shoppingCart),
        success: function() {
			
            alert("购物车数据已保存到数据库。");
        },
        error: function() {
            alert("保存购物车数据时出错。");
        }
    });
}


