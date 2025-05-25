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

	<van-action-bar>
		<van-action-bar-icon icon="service-o" text="客服" @click="goToService" />
		<van-action-bar-icon icon="share-o" text="分享" @click="showShare = true" />
		<van-action-bar-icon icon="chat-o" text="联系卖家" @click="goToChat" />
		<van-action-bar-button type="primary" text="立即购买" @click="buyProduct" />
	</van-action-bar>
	<van-share-sheet v-model:show="showShare" title="立即分享给好友" :options="options" />

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
			console.log(response.data)
		}).catch(error => {
			console.error(error)
		})
	})

	const goToChat = () => {
		axios.post('http://localhost:8080/api/chat/save', {
				receiverId: product.value.sellerId,
				productId: product.value.id
			}, {
				withCredentials: true // 这里才是配置项，允许携带 cookie
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

	const goToService = () => {
		uni.reLaunch({
			url: "/pages/service/service"
		})
	}

	const showShare = ref(false);
	const options = [{
			name: '微信',
			icon: 'wechat'
		},
		{
			name: '微博',
			icon: 'weibo'
		},
		{
			name: '复制链接',
			icon: 'link'
		},
		{
			name: '分享海报',
			icon: 'poster'
		},
		{
			name: '二维码',
			icon: 'qrcode'
		},
	];

	const buyProduct = () => {
		axios.post('http://localhost:8080/api/product/buyProduct', {
			sellerId: product.value.sellerId,
			productId: product.value.id
		}, {
			withCredentials: true // 允许携带 cookie
		}).then(response => {
			uni.redirectTo({
				url: "/pages/home/home"
			});
			console.log(response);
		}).catch(error => {
			console.error(error);
		});
	}
</script>

<style>

</style>