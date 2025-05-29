<template>
  <div class="app">
    <h1>Система управління документами</h1>
    <FileUploader :candidateId="candidateId" />
  </div>
</template>

<script>
import FileUploader from '@/components/FileUploader.vue';

export default {
  name: 'AboutView',
  components: {
    FileUploader
  },
  data() {
    return {
      candidateId: 1 // ID кандидата, для якого завантажуються документи
    };
  }
};
</script>

<style>
.app {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  font-family: Arial, sans-serif;
}

h1 {
  text-align: center;
  margin-bottom: 30px;
  color: #2d3748;
}
</style>