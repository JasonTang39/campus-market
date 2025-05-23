<template>
	<van-cell-group>
		<van-field v-model="productName" label="商品名" placeholder="请输入商品名" />
		<van-field v-model="productDescription" rows="2" label="商品描述" type="textarea" maxlength="50"
			placeholder="请输入商品描述" show-word-limit />
		<van-field name="uploader" label="商品图片">
			<template #input>
				<van-uploader v-model="fileList" multiple :max-count="1" />
			</template>
		</van-field>
		<van-field v-model="productPrice" type="number" label="价格" placeholder="请输入商品价格" />
		<van-field name="radio" label="交易方式">
			<template #input>
				<van-radio-group v-model="transactionMethod" direction="horizontal">
					<van-radio name="1">邮寄</van-radio>
					<van-radio name="2">买家自提</van-radio>
				</van-radio-group>
			</template>
		</van-field>
	</van-cell-group>

	<van-button type="primary" round block style="margin-top: 10%; width: 80%;margin-right: auto;margin-left: auto;" @click="publishProduct">发布商品</van-button>

	<tabbarVue :page-name="pageName" />
</template>

<script setup>
	import {
		ref
	} from 'vue'
	import tabbarVue from '../../components/tabbar.vue';
	import axios from 'axios';
	const pageName = ref('publishProduct')

	const productName = ref('')
	const productDescription = ref('')
	const fileList = ref([]);
	const productPrice = ref()

	const transactionMethod = ref('1')

	const publishProduct = () => {
		const formData = new FormData()
		formData.append('productName', productName.value)
		formData.append('productDescription', productDescription.value)
		formData.append('productPrice', productPrice.value)
		formData.append('image', fileList.value[0].file)

		axios.post('http://localhost:8080/api/product/publish', formData, {
				headers: {
					'Content-Type': 'multipart/form-data'
				},
				withCredentials: true
			})
			.then(response => {
				console.log(response)
				uni.redirectTo({
					url:"/pages/home/home"
				})
			})
			.catch(error => {
				console.error(error)
			})
	}
</script>

<style>

</style>