<template>
	<div v-for="(message, index) in messageList" :key="index" :style="{
	    display: 'flex',
	    justifyContent: message.senderId === senderId ? 'flex-end' : 'flex-start',
	    margin: '5px'
	  }">
		<div
		  :class="['message-container', message.senderId === senderId ? 'self-message' : 'other-message']"
		>
		  {{ message.messageContent }}
		</div>
	</div>


	<div style="display: flex;justify-content: center;">
		<van-cell-group inset class="message-input-container">
			<van-field v-model="message" placeholder="想跟TA说点什么..." @keydown.enter="sendMessage" />
		</van-cell-group>
	</div>
</template>

<script setup>
	import {
		onLoad
	} from '@dcloudio/uni-app'

	import {
		ref
	} from 'vue'
	import axios from 'axios'
	let socket;

	const chatId = ref('')
	const senderId = ref('');
	const receiverId = ref('');
	const message = ref('')
	const messageList = ref([])

	onLoad((options) => {
		const title = options.receiverName;
		chatId.value = options.chatId;
		senderId.value = options.senderId;
		receiverId.value = options.receiverId;
		uni.setNavigationBarTitle({
			title
		});

		// 初始化 WebSocket
		socket = new WebSocket(`ws://localhost:8080/ws/message?userId=${senderId.value}`);
		socket.onopen = () => console.log('WebSocket 连接成功');
		socket.onmessage = (e) => {
			const msg = JSON.parse(e.data);
			messageList.value.push(msg)
			console.log('收到消息', msg);
		};


		// 获取历史消息记录
		axios.get('http://localhost:8080/api/message/getMessageByChatId', {
				params: {
					chatId: chatId.value
				}
			})
			.then(response => {
				messageList.value = response.data;
				console.log('历史消息加载成功', response.data);
			})
			.catch(error => {
				console.error('历史消息加载失败', error);
			})
	})

	const sendMessage = () => {
		if (!message.value.trim()) return;

		const msgObj = {
			senderId: senderId.value,
			receiverId: receiverId.value,
			messageContent: message.value.trim(),
			chatId: chatId.value
		};

		socket.send(JSON.stringify(msgObj));
		message.value = '';
	}

	const closeSocket = () => {
		if (socket && socket.readyState === WebSocket.OPEN) {
			socket.close();
			console.log('WebSocket 连接已关闭');
		}
	};

	import {
		onUnload
	} from '@dcloudio/uni-app'

	onUnload(() => {
		closeSocket();
	});
</script>


<style>
	.message-input-container {
		border-style: solid;
		border-color: #C0C0C0;
		position: fixed;
		bottom: 3%;
		width: 90%;
	}

	.message-container {
		max-width: 80%;
		border-radius: 10px;
		font-size: 12px;
		padding: 5px;
		line-height: 20px;
	}
	
	.self-message {
	  background-color: #95ec69; 
	  align-self: flex-end;
	}
	
	.other-message {
	  background-color: #e7e7e7;
	  align-self: flex-start;
	}
</style>