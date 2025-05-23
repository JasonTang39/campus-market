<template>
	<van-search v-model="searchKeyWord" placeholder="请输入搜索关键词" />
	<van-notice-bar left-icon="volume-o" text="本小程序仍处于测试阶段，若遇到问题，请及时反馈。谢谢！" />

	<!-- 无限滚动列表 -->
	<van-list v-model:loading="loading" :finished="finished" finished-text="没有更多商品了" @load="onLoad">
		<van-card v-for="(item, index) in productList" :key="index" :price="item.productPrice"
			:desc="item.productDescription" :title="item.productName" :thumb="item.productImageUrl">
			<template #footer>
				<van-button size="mini" type="primary" @click="goToProductDetail(item.id)">详细信息</van-button>
			</template>
		</van-card>
	</van-list>

	<tabbarVue :page-name="pageName" />
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue'
	import axios from 'axios'
	import tabbarVue from '../../components/tabbar.vue'

	const searchKeyWord = ref('')
	const pageName = ref('home')
	const productList = ref([])

	const loading = ref(false)
	const finished = ref(false)

	const page = ref(1)
	const pageSize = 10

	const onLoad = async () => {
		loading.value = true
		try {
			const response = await axios.get(`http://localhost:8080/api/product/getAll`, {
				params: {
					page: page.value,
					size: pageSize
				}
			})

			const data = response.data

			if (data.length < pageSize) {
				finished.value = true
			}

			productList.value.push(...data)
			page.value += 1
		} catch (err) {
			console.error(err)
		} finally {
			loading.value = false
		}
	}


	onMounted(() => {
		onLoad()
	})

	const goToProductDetail = (id) => {
		const productId = id
		uni.navigateTo({
			url: `/pages/productDetail/productDetail?productId=${encodeURIComponent(productId)}`
		});
	}
</script>