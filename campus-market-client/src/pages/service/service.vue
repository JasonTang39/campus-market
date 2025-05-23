<template>
  <view class="chat-list" ref="chatListRef">
    <ul>
      <li v-for="(message, index) in chatHistory" :key="index">
        <div class="time">{{ message.time }}</div>
        <span v-if="message.isUser" class="userMessage">{{ message.text }}</span>
        <!-- 使用 v-html 指令渲染转换后的 HTML -->
        <span v-else class="botMessage" v-html="message.html"></span>
      </li>
    </ul>
  </view>

  <!-- 输入框固定在底部 -->
  <view class="input-wrapper">
    <van-cell-group inset style="box-shadow: 0px 10px 10px #DCDCDC;">
      <van-field v-model="inputMessage" placeholder="给智能客服发送消息" @keydown.enter="sendMessage" />
    </van-cell-group>
  </view>

  <!-- 底部tabbar -->
  <tabbarVue :page-name="pageName" />
</template>

<script setup>
  import {
    ref,
    nextTick,
    onMounted
  } from 'vue';
  import tabbarVue from '../../components/tabbar.vue';
  import OpenAI from "openai";
  import axios from 'axios';
  import MarkdownIt from 'markdown-it'; // 引入 markdown-it 库
  import Cookies from 'js-cookie';

  const inputMessage = ref('');
  const pageName = ref('service');
  const chatHistory = ref([]);
  const chatListRef = ref(null);
  const cookieValue = ref('');

  const md = new MarkdownIt(); // 创建 MarkdownIt 实例

  function formatTime(date) {
    const month = String(date.getMonth() + 1);
    const day = String(date.getDate());
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    return `${month}/${day}   ${hours}:${minutes}:${seconds}`;
  }

  async function sendMessage() {
    if (inputMessage.value.trim() === '') return;

    // 将用户输入的消息添加到历史记录中
    chatHistory.value.push({
      text: inputMessage.value,
      html: "",
      isUser: true,
      time: formatTime(new Date())
    });
    nextTick(() => {
      scrollToBottom();
    });
    const input = inputMessage.value;
    inputMessage.value = '';

    try {
      // 发送 POST 请求到后端接口
      const response = await axios.post('http://localhost:8080/api/deepSeek/chat', input, {
        headers: {
          'Content-Type': 'text/plain' // 后端接收的是纯文本，设置请求头
        }
      });
      const html = md.render(response.data); // 将 Markdown 转换为 HTML
      // 将服务器响应的消息添加到历史记录中
      chatHistory.value.push({
        text: response.data, // 保留 Markdown 文本
        html: html, // 存储转换后的 HTML
        isUser: false,
        time: formatTime(new Date())
      });
    } catch (error) {
      console.error('请求出错:', error);
      chatHistory.value.push({
        text: '请求出错了XD',
        html: '<p>请求出错了XD</p>', // 错误信息的 HTML 形式
        isUser: false,
        time: formatTime(new Date())
      });
    }

    // 存储聊天历史到 localStorage
    saveChatHistoryToLocalStorage();

    nextTick(() => {
      scrollToBottom();
    });
  }

  function scrollToBottom() {
    if (!chatListRef.value) return; // 检查 ref 是否存在

    // 如果是组件，尝试访问 $el；否则直接使用 ref
    const element = chatListRef.value.$el || chatListRef.value;
    if (element) {
      element.scrollTop = element.scrollHeight;
    }
  }

  // 读cookie
  onMounted(() => {
    const targetCookieName = 'userId'; // 替换为你要读取的 cookie 名称
    const result = Cookies.get(targetCookieName);
    if (result) {
      cookieValue.value = result;
      console.log('读取到的 Cookie 值:', cookieValue.value);
      // 读取聊天历史从 localStorage
      loadChatHistoryFromLocalStorage();
    } else {
      console.log('未找到指定的 Cookie');
    }
    //翻页到末尾
    nextTick(() =>
      scrollToBottom());
  });

  // 构建 localStorage 键名
  function getLocalStorageKey() {
    return `chatHistory_${cookieValue.value}`;
  }

  // 存储聊天历史到 localStorage
  function saveChatHistoryToLocalStorage() {
    const key = getLocalStorageKey();
    localStorage.setItem(key, JSON.stringify(chatHistory.value));
  }

  // 从 localStorage 读取聊天历史
  function loadChatHistoryFromLocalStorage() {
    const key = getLocalStorageKey();
    const storedHistory = localStorage.getItem(key);
    if (storedHistory) {
      chatHistory.value = JSON.parse(storedHistory);
    }
  }
</script>

<style>
  .chat-list {
    height: calc(100vh - 185px);
    /* 减去输入框和 tabbar 的高度 */
    overflow-y: auto;
    padding: 10px;
    clear: both;
    box-sizing: border-box;
  }

  .chat-list span {
    flex-direction: column;
    padding: 10px;
    max-width: 70%;
    margin-inline: 20px;
    border-radius: 8px;
    word-wrap: break-word;
    word-break: break-all;
    display: inline-block;
    clear: both;
    margin-bottom: 20px;
  }

  .userMessage {
    background-color: #0084ff;
    color: #fff;
    float: right;
  }

  .botMessage {
    background-color: #f1f0f0;
    color: #000;
  }

  .time {
    text-align: center;
    clear: both;
    margin-bottom: 20px;
  }

  .input-wrapper {
    position: fixed;
    bottom: 70px;
    /* tabbar高度 */
    left: 0;
    right: 0;
  }
</style>