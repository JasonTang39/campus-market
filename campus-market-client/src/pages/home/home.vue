<template>
	<van-search v-model="searchKeyWord" placeholder="请输入搜索关键词" />
	<van-notice-bar left-icon="volume-o" text="本小程序仍处于测试阶段，若遇到问题，请及时反馈。谢谢！" />
	<van-card
	  price="2.00"
	  desc="描述信息"
	  title="商品标题"
	  thumb="https://fastly.jsdelivr.net/npm/@vant/assets/ipad.jpeg"
	/>
	<tabbarVue :page-name="pageName" />
</template>

<script setup>
	import {
		ref
	} from 'vue'
	import tabbarVue from '../../components/tabbar.vue';

	
	const searchKeyWord = ref('');	
	const pageName = ref('home');
</script>

<style>
</style>