<template>
	<van-cell-group>
		<van-cell title="用户名" :value="userName" icon="user-circle-o" size="large" />
		<van-cell title="学校" :value="schoolName" icon="info-o" size="large" />
		<van-cell title="钱包" :value="money" icon="balance-o" size="large" />
		<van-cell title="我的交易" is-link icon="bill-o" size="large" @click="goToMyTransactions" />
	</van-cell-group>
	<div style="display: flex; justify-content: center;">
		<van-button type="success" round block style="position: fixed; bottom: 120px; width: 90%;">钱包充值</van-button>
		<van-button type="danger" round block style="position: fixed; bottom: 70px; width: 90%;" @click="logOut">退出登录</van-button>
	</div>
	
	<tabbarVue :page-name="pageName" />
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue'
	import axios from 'axios';
	import tabbarVue from '../../components/tabbar.vue';

	const pageName = ref('user')
	const userName = ref('')
	const schoolName = ref('')
	const money = ref('')

	const goToMyTransactions = () => {
		uni.navigateTo({
			url: '/pages/myTransactions/myTransactions'
		})
	}

	onMounted(() => {
		axios.get('http://localhost:8080/api/user/info', {
			withCredentials: true
		}).then(response => {
			console.log(response);
			const data = response.data
			userName.value = data.userName 
			schoolName.value = data.schoolName
			money.value = data.money + ' 元'
		}).catch(error => {
			console.error(error)
		})
	})
	
	const logOut = () =>{
		uni.redirectTo({
			url:"/pages/index/index"
		})
	}
</script>

<style>

</style>