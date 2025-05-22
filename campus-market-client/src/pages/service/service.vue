<template>
  <!-- 输入框固定在底部 -->
  <view class="input-wrapper">
    <van-cell-group inset style="box-shadow: 0px 10px 10px #DCDCDC;">
      <van-field v-model="value" placeholder="给智能客服发送消息" />
    </van-cell-group>
  </view>

  <!-- 底部tabbar -->
  <tabbarVue :page-name="pageName" />
</template>

<script setup>
import { ref } from 'vue'
import tabbarVue from '../../components/tabbar.vue'

const value = ref('')
const pageName = ref('service')
</script>

<style>
.input-wrapper {
  position: fixed;
  bottom: 70px; /* tabbar高度 */
  left: 0;
  right: 0;
}
</style>
