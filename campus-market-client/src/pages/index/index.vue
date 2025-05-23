<template>
	<van-cell-group inset style="margin-top: 40%;">
		<van-field v-model="userName" placeholder="请输入用户名" label="用户名" />
		<van-field v-model="password" type="password" placeholder="请输入密码" label="密码" />
	</van-cell-group>
	<div style="display: flex;justify-content: center;margin-top: 20%;">
		<van-button type="primary" style="width: 60%;" @click="login">登录</van-button>
	</div>

	<div style="display: flex;justify-content: center;font-size: 5%;margin-top: 5%;">
		<span>还没有账号？</span>
		<span style="color: #1989fa;" @click="goToRegisterPage">立即注册</span>
	</div>
</template>

<script setup>
	import {
		ref
	} from 'vue'

	import axios from 'axios';
	import {
		showNotify
	} from 'vant';


	const userName = ref('')
	const password = ref('')

	const goToRegisterPage = () => {
		uni.navigateTo({
			url: '/pages/register/register'
		})
	}

	const login = () => {
		axios.post('http://localhost:8080/api/user/login', {
				userName: userName.value,
				password: password.value
			}, {
				withCredentials: true 
			})
			.then(function(response) {
				showNotify({
					type: 'primary',
					message: '登录成功'
				});
				uni.redirectTo({
					url: '/pages/home/home'
				});

				console.log(response);
			})
			.catch(function(error) {
				showNotify({
					type: 'warning',
					message: '用户不存在或密码错误'
				});
				console.log(error);
			});
	};
</script>