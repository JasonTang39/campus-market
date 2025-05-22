<template>
	<van-cell-group>
		<van-cell title="用户名" :value="userName" icon="user-circle-o" size="large" />
		<van-cell title="钱包" :value="money" icon="balance-o" size="large" />
		<van-cell title="我的交易" is-link icon="bill-o" size="large" @click="goToMyTransactions" />
	</van-cell-group>
	<tabbarVue :page-name="pageName" />
</template>

<script setup>
	import {
		ref
	} from 'vue'
	import tabbarVue from '../../components/tabbar.vue';

	const pageName = ref('user')
	const userName = ref('')
	const money = ref()

	const goToMyTransactions = () => {
		uni.navigateTo({
			url: '/pages/myTransactions/myTransactions'
		})
	}
</script>

<style>

</style>