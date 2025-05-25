<template>
	<van-tabs v-model:active="active">
		<van-tab title="我上架的">
			<van-card v-for="item in publishedproducts" :key="item.id" :price="item.productPrice"
				:desc="item.productDescription" :title="item.productName" :thumb="item.productImageUrl">
				<template #footer>
					<van-button size="mini" type="danger" @click="removeProduct(item.id)">下架</van-button>
				</template>
			</van-card>
		</van-tab>
		<van-tab title="我卖出的">
			<van-card v-for="item in soldProductOrders" :key="item.id" :price="item.productPrice"
				:desc="item.productDescription" :title="item.productName" :thumb="item.productImageUrl">

			</van-card>
		</van-tab>
		<van-tab title="我买到的">
			<van-card v-for="item in boughtProductOrders" :key="item.id" :price="item.productPrice"
				:desc="item.productDescription" :title="item.productName" :thumb="item.productImageUrl">
				<template #footer>
					<van-button
					  size="mini"
					  :type="item.orderStatus === 1 ? 'default' : 'primary'"
					  :disabled="item.orderStatus === 1"
					  @click="confirmReceipt(item.id)"
					>
					  {{ item.orderStatus === 1 ? '已收货' : '确认收货' }}
					</van-button>
				</template>
			</van-card>
		</van-tab>

	</van-tabs>
</template>

<script setup>
	import {
		ref,
		watch,
		onMounted
	} from 'vue'
	import axios from 'axios'
	import {
		showConfirmDialog
	} from 'vant';
	const active = ref(0);
	const publishedproducts = ref([])
	const soldProductOrders = ref([])
	const boughtProductOrders = ref([])
	const orderStatus = ref(0);

	const fetchData = (index) => {
		const config = {
			withCredentials: true // 允许携带 cookie
		};

		if (index === 0) {
			axios.get('http://localhost:8080/api/product/getPublishedProducts', config)
				.then(res => {
					publishedproducts.value = res.data
					console.log(res.data)
				})
				.catch(console.error)
		} else if (index === 1) {
			axios.get('http://localhost:8080/api/order/getSoldProductOrders', config)
				.then(res => {
					soldProductOrders.value = res.data
					console.log(res.data)
				})
				.catch(console.error)
		} else if (index === 2) {
			axios.get('http://localhost:8080/api/order/getBoughtProductOrders', config)
				.then(res => {
					boughtProductOrders.value = res.data
					console.log(res.data)
				})
				.catch(console.error)
		}
	}

	watch(active, (newVal) => {
		fetchData(newVal)
	})

	onMounted(() => {
		fetchData(active.value)
	})

	// 下架已经上架的商品
	const removeProduct = (productId) => {
		axios.delete('http://localhost:8080/api/product/removeProduct', {
				params: {
					productId: productId
				},
			})
			.then(response => {
				console.log(response.data);
				fetchData(0);
			})
			.catch(error => {
				console.error(error);
			});
	}

	const confirmReceipt = (orderId) => {
		showConfirmDialog({
				title: '确认收货',
				message: '请确认商品符合描述并无质量问题后，再点击确认。',
			})
			.then(() => {
				axios.put('http://localhost:8080/api/order/confirmReceipt', {
						orderId: orderId
					})
					.then(response => {
						console.log(response)
						fetchData(2);
					})
					.catch(error => {
						console.error(error)
					})
			})
			.catch(() => {

			});
	}
</script>

<style>

</style>