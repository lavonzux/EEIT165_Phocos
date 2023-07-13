// 创建一个空数组来存储购物车中的相机数据
var shoppingCart = [];


function addToCart(productId) {
	// 通过productId获取相机数据
	var model = document.getElementById('model_' + productId).innerText;
	var brand = document.getElementById('brand_' + productId).innerText;
	var price = document.getElementById('price_' + productId).innerText;
	var quantitySelect = document.getElementById('quantitySelect');
	var selectedQuantity = parseInt(quantitySelect.value);
	// 创建一个对象来存储相机数据
	var itemData = {
		productId: productId,
		model: model,
		brand: brand,
		price: parseFloat(price)
	};

	// 将相机数据添加到购物车数组中
	for (var i = 0; i < selectedQuantity; i++) {
		shoppingCart.push(itemData);
	}

	// 在控制台打印购物车数据（仅用于调试）
	console.log(shoppingCart);


	// 将数据存储到 cookie
	var cartData = JSON.parse(getCookie('shoppingCart')) || [];
	for (var i = 0; i < selectedQuantity; i++) {
		cartData.push(itemData);
	}
	setCookie('shoppingCart', JSON.stringify(cartData), 1); // 這裡的1代表cookie的過期天數
	
	Swal.fire({
		title: '新增成功',
		text: '商品已成功添加到购物车',
		icon: 'success',
		confirmButtonColor: '#3085d6',
		confirmButtonText: '確認'
		});
}

// 获取指定名称的 cookie 值
function getCookie(name) {
	var cookieName = name + "=";
	var cookieArray = document.cookie.split(';');
	for (var i = 0; i < cookieArray.length; i++) {
		var cookie = cookieArray[i].trim();
		if (cookie.indexOf(cookieName) === 0) {
			return cookie.substring(cookieName.length, cookie.length);
		}
	}
	return null;
}

// 设置 cookie
function setCookie(name, value, days) {
	var expires = "";
	if (days) {
		var date = new Date();
		date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
		expires = "; expires=" + date.toUTCString();
	}
	document.cookie = name + "=" + value + expires + "; path=/";
}


function clearCart() {

	var confirmClear = confirm("是否确定要清空购物车？");
	if (confirmClear) {
		shoppingCart = []; // Clear the shopping cart array
		updateCart();
		// Clear cart items display
		var cartItems = document.getElementById('cartItems');
		while (cartItems.firstChild) {
			cartItems.removeChild(cartItems.firstChild);
		}
		// Reset total price display
		var totalPrice = document.getElementById('totalPrice');
		totalPrice.innerText = '总价格: $0';
		// 删除cookie中的购物车数据
		setCookie('shoppingCart', '', -1);
		console.log('Shopping cart cleared.');
	}
}
