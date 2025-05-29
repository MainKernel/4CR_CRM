<template>
  <div class="pagination text-black">
    <template v-if="totalPages > 1">
      <button
          @click="goToPage(currentPage - 1)"
          :disabled="currentPage <= 0"
          class="pagination-button pagination-button-previous"
      >
        Попередня
      </button>

      <button
          v-for="pageNumber in totalPages"
          :key="pageNumber"
          @click="goToPage(pageNumber - 1)"
          :class="['pagination-button', { 'active-page': pageNumber - 1 === currentPage }]"
      >
        {{ pageNumber }}
      </button>

      <button
          @click="goToPage(currentPage + 1)"
          :disabled="currentPage >= totalPages - 1"
          class="pagination-button pagination-button-previous"
      >
        Наступна
      </button>
    </template>
  </div>
</template>

<style scoped>
.pagination-button{
  background: #e9e9ed;
  border: 1px solid #303133;
  height: 36px;
  width: 36px;
}
.pagination-button-previous{
  width: auto;
}
.active-page{
  background: #6DB33F;
}


</style>
<script>
export default {
  name: 'PaginationControls',
  props: {
    totalPages: {
      type: Number,
      required: true,
      default: 0
    },
    currentPage: {
      type: Number,
      required: true,
      default: 0
    }
  },
  methods: {
    // Метод, який буде викликатися при кліку на кнопку сторінки
    goToPage(pageIndex) {
      if (pageIndex >= 0 && pageIndex < this.totalPages) {
        this.$emit('page-changed', pageIndex);
      }
    }
  },
}

</script>