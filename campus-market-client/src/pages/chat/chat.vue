<template>
	<van-cell-group>
	  <van-cell title="张三" label="商品名" icon="contact" size="large" is-link/>
	</van-cell-group>
	<tabbarVue :page-name="pageName" />
</template>

<script setup>
	import {
		ref
	} from 'vue'
	import tabbarVue from '../../components/tabbar.vue';
	
	const pageName = ref('chat')
</script>

<style scoped>
.no-tel .van-contact-card__value:nth-child(2) {
  display: none;
}
</style>