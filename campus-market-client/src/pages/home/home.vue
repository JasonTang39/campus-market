<template>
	<van-search v-model="searchKeyWord" placeholder="请输入搜索关键词" />
	<van-notice-bar left-icon="volume-o" text="本小程序仍处于测试阶段，若遇到问题，请及时反馈。谢谢！" />

	<van-card v-for="(item, index) in productList" :key="index" :price="item.productPrice"
		:desc="item.productDescription" :title="item.productName" :thumb="item.productImage" />

	<tabbarVue :page-name="pageName" />
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue'
	import axios from 'axios';
	import tabbarVue from '../../components/tabbar.vue';

	const searchKeyWord = ref(''); // 搜索关键字
	const pageName = ref('home'); // 页面名称

	const productList = ref([]) // 商品数组

	onMounted(() => {
		axios.get('http://localhost:8080/api/product/getAll')
			.then(response => {
				console.log(response.data);
				const data = response.data
				productList.value = response.data
			}).catch(error => {
				console.error(error)
			})
	})
</script>

<style>
</style>