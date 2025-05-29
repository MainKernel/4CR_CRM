<template>
  <div class="file-uploader">
    <h3>Завантаження документів</h3>

    <div class="file-input">
      <label for="file-input">Вибрати файли</label>
      <input
          type="file"
          id="file-input"
          multiple
          @change="handleFileSelect"
      >
    </div>

    <div v-if="selectedFiles.length > 0" class="file-list">
      <div v-for="(file, index) in selectedFiles" :key="index" class="file-item">
        <div class="file-info">
          <span class="file-name">{{ file.file.name }}</span>
          <span class="file-size">({{ formatFileSize(file.file.size) }})</span>
        </div>
        <input
            type="text"
            v-model="file.fileName"
            placeholder="Назва файлу"
            class="file-name-input"
        >
        <button @click="removeFile(index)" class="remove-btn">✕</button>
      </div>
    </div>

    <div class="actions">
      <button
          @click="uploadFiles"
          :disabled="isUploading || selectedFiles.length === 0"
          class="upload-btn"
      >
        {{ isUploading ? 'Завантаження...' : 'Завантажити' }}
      </button>
    </div>

    <div v-if="message" :class="['message', messageType]">
      {{ message }}
    </div>
  </div>
</template>

<script>
import {ref} from 'vue';

export default {
  name: 'FileUploader',
  props: {
    candidateId: {
      type: Number,
      required: true
    }
  },
  setup(props) {
    const selectedFiles = ref([]);
    const isUploading = ref(false);
    const message = ref('');
    const messageType = ref('');

    const API_BASE_URL = 'http://localhost:6488';

    const handleFileSelect = (event) => {
      const files = Array.from(event.target.files);

      files.forEach(file => {
        selectedFiles.value.push({
          file,
          fileName: file.name
        });
      });

      event.target.value = null;
    };

    const removeFile = (index) => {
      selectedFiles.value.splice(index, 1);
    };

    const formatFileSize = (size) => {
      if (size < 1024) {
        return size + ' B';
      } else if (size < 1024 * 1024) {
        return (size / 1024).toFixed(1) + ' KB';
      } else {
        return (size / (1024 * 1024)).toFixed(1) + ' MB';
      }
    };

    const fileToBase64 = (file) => {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result.split(',')[1]);
        reader.onerror = error => reject(error);
      });
    };

    const getAuthToken = () => {
      return localStorage.getItem('token');
    };

    const uploadFiles = async () => {
      if (selectedFiles.value.length === 0) return;

      isUploading.value = true;
      message.value = '';

      try {
        const documentsData = await Promise.all(
            selectedFiles.value.map(async (fileObj) => {
              const base64Content = await fileToBase64(fileObj.file);
              return {
                fileName: fileObj.fileName,
                fileContent: base64Content,
                originalFileName: fileObj.file.name,
                contentType: fileObj.file.type
              };
            })
        );

        // Відправляємо масив безпосередньо, а не об'єкт з властивістю documents
        const response = await fetch(`${API_BASE_URL}/api/documents/${props.candidateId}/add`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${getAuthToken()}`
          },
          body: JSON.stringify(documentsData) // Відправляємо масив безпосередньо
        });

        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(`Помилка завантаження: ${response.status} - ${errorText}`);
        }

        message.value = `Успішно завантажено ${selectedFiles.value.length} файлів`;
        messageType.value = 'success';
        selectedFiles.value = [];

      } catch (error) {
        console.error('Помилка завантаження:', error);
        message.value = `Помилка: ${error.message}`;
        messageType.value = 'error';
      } finally {
        isUploading.value = false;
      }
    };

    return {
      selectedFiles,
      isUploading,
      message,
      messageType,
      handleFileSelect,
      removeFile,
      uploadFiles,
      formatFileSize
    };
  },
}
</script>

<style scoped>
.file-uploader {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #f9f9f9;
}

.file-input {
  margin-bottom: 20px;
}

.file-input label {
  display: inline-block;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.file-input label:hover {
  background-color: #0056b3;
}

.file-input input[type="file"] {
  display: none;
}

.file-list {
  margin-bottom: 20px;
}

.file-item {
  display: flex;
  align-items: center;
  padding: 10px;
  margin-bottom: 10px;
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.file-info {
  flex: 1;
  margin-right: 10px;
}

.file-name {
  font-weight: bold;
}

.file-size {
  color: #666;
  font-size: 0.9em;
}

.file-name-input {
  flex: 1;
  padding: 5px;
  margin-right: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.remove-btn {
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 50%;
  width: 25px;
  height: 25px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

.remove-btn:hover {
  background-color: #c82333;
}

.upload-btn {
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.upload-btn:hover:not(:disabled) {
  background-color: #218838;
}

.upload-btn:disabled {
  background-color: #6c757d;
  cursor: not-allowed;
}

.message {
  margin-top: 15px;
  padding: 10px;
  border-radius: 4px;
}

.message.success {
  background-color: #d4edda;
  color: #155724;
  border: 1px solid #c3e6cb;
}

.message.error {
  background-color: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}
</style>