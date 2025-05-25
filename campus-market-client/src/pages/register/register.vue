<template>
	<div style="display: flex;justify-content: center; align-items: center; margin-top: 30%;">
		<van-image
		  width="40"
		  height="40"
		  src="/static/icon.png"
		/>
		<span style="font-size: 35px; margin-left: 10px; ">校园集市</span>
	</div>
	
	<van-cell-group inset style="margin-top: 15%;">
		<van-field v-model="userName" placeholder="请输入用户名" label="用户名" />
		<van-field v-model="schoolName" placeholder="请输入学校名" label="学校" />
		<van-field v-model="password" type="password" placeholder="请输入密码" label="密码" />
		<van-field v-model="confirmPassword" type="password" placeholder="请再次输入密码" label="确认密码" />
	</van-cell-group>

	<div style="display: flex;justify-content: center; margin-top: 15%;">
		<van-button type="primary" style="width: 50%;" @click="register">注册</van-button>
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
	const confirmPassword = ref('')
	const schoolName = ref('')

	const register = () => {
		if (password.value != confirmPassword.value) {
			showNotify({
				type: 'warning',
				message: '两次密码输入不一致'
			});
		}

		axios.post('http://localhost:8080/api/user/register', {
				userName: userName.value,
				password: password.value,
				schoolName: schoolName.value
			})
			.then(function(response) {
				uni.redirectTo({
					url: '/pages/index/index',
				})
				showNotify({
					type: 'primary',
					message: '注册成功'
				});
				console.log(response);
			})
			.catch(function(error) {
				showNotify({
					type: 'warning',
					message: '用户已存在'
				});
				console.log(error);
			});
	}
</script>

<style>

</style>