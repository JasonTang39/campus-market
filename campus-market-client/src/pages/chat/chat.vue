<template>
	<van-cell-group>
		<van-cell v-for="(chat, index) in chatList" :key="index" :title="chat.chatPartnerName" :label="chat.productName"
			icon="contact" size="large" is-link
			@click="goToMessage(chat.id,chat.senderId,chat.receiverId,chat.chatPartnerName)" />
	</van-cell-group>
	<tabbarVue :page-name="pageName" />
</template>

<script setup>
	import {
		onMounted,
		ref
	} from 'vue'
	import tabbarVue from '../../components/tabbar.vue';
	import axios from 'axios';
	const pageName = ref('chat')

	const chatList = ref([])

	const goToMessage = (id, senderId, receiverId, chatPartnerName) => {
		uni.navigateTo({
			url: `/pages/message/message?chatId=${encodeURIComponent(id)}&senderId=${encodeURIComponent(senderId)}&receiverId=${encodeURIComponent(receiverId)}&receiverName=${encodeURIComponent(chatPartnerName)}`
		})
	}

	onMounted(() => {
		axios.get('http://localhost:8080/api/chat/getChat', {
			withCredentials: true
		}).then(response => {
			console.log(response.data);
			chatList.value = response.data
		}).catch(error => {
			console.error(error)
		})
	})
</script>

<style scoped>

</style>