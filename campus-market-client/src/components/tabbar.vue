<template>
	<van-tabbar v-model="active">
		<van-tabbar-item name="home" icon="wap-home-o" @click="goToHome">首页</van-tabbar-item>
		<van-tabbar-item name="service" icon="service-o" @click="goToService">智能客服</van-tabbar-item>
		<van-tabbar-item name="publishProduct" icon="add-o" @click="goToPublishProduct">发布商品</van-tabbar-item>
		<van-tabbar-item name="chat" icon="chat-o" @click="goToChat">消息</van-tabbar-item>
		<van-tabbar-item name="user" icon="user-o" @click="goToUser">我的</van-tabbar-item>
	</van-tabbar>
</template>

<script setup>
	import {
		ref
	} from 'vue'

	const props = defineProps(['pageName'])
	const active = ref(props.pageName)
	
	const goToHome = () => {
		uni.redirectTo({
			url: '/pages/home/home'
		})
	}
	
	const goToService = () => {
		uni.redirectTo({
			url: '/pages/service/service'
		})
	}
	
	const goToPublishProduct = () => {
		uni.redirectTo({
			url:'/pages/publishProduct/publishProduct'
		})
	}
	
	const goToChat = () => {
		uni.redirectTo({
			url: '/pages/chat/chat'
		})
	}
	const goToUser = () => {
		uni.redirectTo({
			url:'/pages/user/user'
		})
	}
</script>

<style>

</style>