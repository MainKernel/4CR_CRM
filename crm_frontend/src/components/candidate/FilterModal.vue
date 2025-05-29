<template>
  <div v-if="isVisible" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <h2>Параметри відображення</h2>

      <div class="filter-section">
        <label for="page-size">Кількість кандидатів на сторінку:</label>
        <select id="page-size" v-model.number="internalPageSize">
          <option value="10">10</option>
          <option value="15">15</option>
          <option value="20">20</option>
          <option value="50">50</option>
          <option value="100">100</option>
        </select>
      </div>

      <div class="modal-actions">
        <button @click="applyPageSize">Застосувати</button>
        <button @click="closeModal">Скасувати</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FilterModal',
  props: {
    isVisible: {
      type: Boolean,
      required: true
    },
    initialPageSize: { // Змінено для ясності, приймаємо конкретно pageSize
      type: Number,
      default: 15
    }
  },
  data() {
    return {
      internalPageSize: 15,
    };
  },
  watch: {
    isVisible: {
      handler(newVal) {
        if (newVal) {
          // При кожному відкритті модального вікна ініціалізуємо internalPageSize
          // значенням, переданим з батьківського компонента
          this.internalPageSize = this.initialPageSize;
        }
      },
    },
    initialPageSize: {
      handler(newPageSize) {
        // Якщо initialPageSize змінюється, коли модалка вже видима
        if (this.isVisible) {
          this.internalPageSize = newPageSize;
        }
      },
      immediate: true // Для першої ініціалізації при завантаженні
    }
  },
  methods: {
    closeModal() {
      this.$emit('close');
    },
    applyPageSize() {
      const settingsToApply = {
        pageSize: this.internalPageSize,
      };
      // Генеруємо подію 'apply', яку слухає батьківський компонент
      this.$emit('apply', settingsToApply);
      // Батьківський компонент відповідає за закриття модального вікна
    }
  }
};
</script>

<style scoped>
/* Стилі для оверлею (затемнення поза модалкою) */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5); /* Напівпрозорий чорний фон */
  display: flex;
  justify-content: center; /* Центрування по горизонталі */
  align-items: center; /* Центрування по вертикалі */
  z-index: 1000; /* Гарантуємо, що модалка буде поверх іншого контенту */
}

/* Стилі для вмісту модального вікна */
.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  min-width: 300px; /* Мінімальна ширина */
  max-width: 90%; /* Максимальна ширина */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* Тінь */
  position: relative;
  color: #333;
}

.modal-content h2 {
  margin-top: 0;
  margin-bottom: 20px;
  font-size: 1.5em;
  color: #333;
}

.filter-section {
  margin-bottom: 15px;
}

.filter-section label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
  color: #555;
}

.filter-section select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color: #333;
}

.modal-actions {
  margin-top: 20px;
  text-align: right;
}

.modal-actions button {
  margin-left: 10px;
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1em;
  transition: opacity 0.2s ease-in-out;
}

.modal-actions button:first-of-type {
  background-color: #197cdf;
  color: white;
}

.modal-actions button:last-of-type {
  background-color: #e0e0e0;
  color: #333;
}

.modal-actions button:hover {
  opacity: 0.85;
}
</style>