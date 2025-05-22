<template>
	<van-cell-group>
		<van-field v-model="value" label="商品名" placeholder="请输入商品名" />
		<van-field v-model="message" rows="2" label="商品描述" type="textarea" maxlength="50" placeholder="请输入商品描述"
			show-word-limit />
		<van-field name="uploader" label="商品图片">
			<template #input>
				<van-uploader v-model="fileList" multiple :max-count="1" />
			</template>
		</van-field>
		<van-field v-model="number" type="number" label="价格" placeholder="请输入商品价格" />
	</van-cell-group>
	
	<van-button type="primary">发布商品</van-button>
	
	<tabbarVue :page-name="pageName" />
</template>

<script setup>
	import {
		ref
	} from 'vue'
	import tabbarVue from '../../components/tabbar.vue';

	const pageName = ref('publishProduct')
	const fileList = ref([]);
	
	
</script>

<style>

</style>