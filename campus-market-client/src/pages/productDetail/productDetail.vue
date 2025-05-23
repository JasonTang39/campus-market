<template>
	<van-cell-group>
		<van-cell title="卖家名" :value="product.sellerName" size="large" />
		<van-cell title="商品名" :value="product.productName" size="large" />
		<van-cell title="商品描述" :value="product.productDescription" size="large" />
		<van-cell title="价格" :value="product.productPrice" size="large" />
		<van-cell title="商品图片" size="large">
			<template #value>
				<van-image width="10rem" height="10rem" fit="cover" :src="product.productImageUrl" radius="10px" />
			</template>
		</van-cell>
	</van-cell-group>

	<div style="display: flex; justify-content: center;">
		<van-button type="success" round block style="position: fixed; bottom: 11%; width: 90%;"
			@click="goToChat">联系卖家</van-button>
		<van-button type="primary" round block style="position: fixed; bottom: 2%; width: 90%;">购买商品</van-button>
	</div>

</template>

<script setup>
	import {
		onLoad
	} from '@dcloudio/uni-app'
	import {
		ref
	} from 'vue'
	import axios from 'axios'

	const product = ref({})
	onLoad((options) => {
		const productId = options.productId
		axios.get('http://localhost:8080/api/product/getProductDetail', {
			params: {
				productId: productId
			}
		}).then(response => {
			product.value = response.data
			console.log(response)
		}).catch(error => {
			console.error(error)
		})
	})

	const goToChat = () => {
	  axios.post('http://localhost:8080/api/chat/save', {
	    receiverId: product.value.sellerId,
	    productId: product.value.id
	  }, {
	    withCredentials: true  // 这里才是配置项，允许携带 cookie
	  })
	  .then(response => {
	    console.log(response);
	    uni.navigateTo({
	      url: '/pages/chat/chat'
	    });
	  })
	  .catch(error => {
	    console.error(error);
	  });
	};

</script>

<style>

</style>