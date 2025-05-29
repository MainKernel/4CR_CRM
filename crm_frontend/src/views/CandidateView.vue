<template>
  <div class="search-container bg-gray-600">
    <div class="btn-container">
      <button class="btn-filter" @click="openFilterModal">
        Фільтр відображення
      </button>
      <FilterModal :is-visible="isFilterModalOpen"
                   :initial-page-size="pageSize"
                   @close="closeFilterModal"
                   @apply="applyPageSizeChange"
      />

      <router-link class="add-btn-link" to="/candidate/add">
        <button class="btn-add">
          Додати кандидата
        </button>
      </router-link>
    </div>
    <div class="search-box">
      <button type="button" @click="removeSearchQuery" class="remove-search-button">x</button>
      <input type="search"
             placeholder="Пошук кандидата"
             class="search-input"
             v-model="searchQuery"
             @keyup.enter="handleSearch"/>
    </div>
  </div>

  <div class="candidates-grid-container bg-gray-600">
    <div class="grid-header">
      <div class="grid-header-cell" title="Прізвище, Ім'я, По-батькові">Прізвище, Ім'я, По-батькові</div>
      <div class="grid-header-cell" title="Номер телефону">Номер телефону</div>
      <div class="grid-header-cell" title="Статус">Статус</div>
      <div class="grid-header-cell" title="Військова частина">Військова частина</div>
      <div class="grid-header-cell" title="Тип служби">Тип служби</div>
      <div class="grid-header-cell" title="Дата рекомендаційного листа">Дата рекомендаційного листа</div>
      <div class="grid-header-cell" title="Рекрутер">Рекрутер</div>
      <div class="grid-header-cell" title="Куратор">Куратор</div>
      <div class="grid-header-cell" title="Супровід">Супровід</div>
      <div class="grid-header-cell" title="Психологічне тестування">Психологічне тестування</div>
      <div class="grid-header-cell" title="Дата зарахування до НЦ">Дата зарахування до НЦ</div>
      <div class="grid-header-cell" title="Номер наказа на зарахування в НЦ">Номер наказа на зарахування в НЦ</div>
      <div class="grid-header-cell" title="Дата зарахування у В/Ч">Дата зарахування у В/Ч</div>
      <div class="grid-header-cell" title="Номер наказу на зарахування">Номер наказу на зарахування</div>
      <div class="grid-header-cell" title="До якого РТЦК приписаний">До якого РТЦК приписаний</div>
    </div>

    <div class="grid-body">
      <div
          v-for="(candidate, index) in candidates"
          :key="candidate.id"
          class="grid-row"
          :class="{'row-grey': index % 2 === 0}"
          @dblclick="openEdit(candidate)"
      >
        <div class="grid-data-cell">{{ candidate.surname + " " + candidate.name + " " + candidate.middleName }}</div>
        <div class="grid-data-cell">{{ candidate.phoneNumber }}</div>
        <div class="grid-data-cell" :style="{backgroundColor: '#' + candidate.status.color}">
          {{ candidate.status.status }}
        </div>
        <div class="grid-data-cell" :title="candidate.militaryUnit.strictUnitName">
          {{ candidate.militaryUnit.unitName }}
        </div>
        <div class="grid-data-cell" :style="{ backgroundColor: '#' + candidate.dutyType.color }">
          {{ candidate.dutyType.status }}
        </div>
        <div class="grid-data-cell">{{ candidate.recommendationLetter }}</div>
        <div class="grid-data-cell">{{ candidate.recruiter }}</div>
        <div class="grid-data-cell">{{ candidate.curator }}</div>
        <div class="grid-data-cell">{{ candidate.escortedBy }}</div>
        <div class="grid-data-cell" :style="{backgroundColor: '#'+candidate.psychologicalTest.color}">
          {{ candidate.psychologicalTest.status }}
        </div>
        <div class="grid-data-cell">{{ candidate.trainingCenterOrder }}</div>
        <div class="grid-data-cell">{{ candidate.trainingCenterOrderNumber }}</div>
        <div class="grid-data-cell">{{ candidate.enrolmentOrderDate }}</div>
        <div class="grid-data-cell">{{ candidate.orderNumber }}</div>
        <div class="grid-data-cell">{{ candidate.territoryCenterRecord }}</div>
      </div>
    </div>

    <CandidateModal
        :is-visible="isEditModalVisible"
        :candidate="selectedCandidate"
        @close="closeModal"
        @save="handleSaveCandidate"
    />
  </div>

  <div class="pagination-container bg-gray-600 m-0 p-0">
    <div class="display">
      <p class="text-gray-100">Відображається {{ pageSize }} елементів на сторінку</p>
    </div>
    <div class="pagination">
      <PaginationPageNumbers :current-page="currentPage"
                             :total-pages="totalPages"
                             @page-changed="onPageChanged"
      />
    </div>
  </div>
</template>

<script>
import PaginationPageNumbers from "../components/candidate/PaginationPageNumbers.vue";
import CandidateModal from "@/components/candidate/CandidateModal.vue";
import FilterModal from "../components/candidate/FilterModal.vue";
import router from "@/router";

export default {
  name: 'CandidateView',
  components: {
    PaginationPageNumbers,
    FilterModal,
    CandidateModal
  },
  data() {
    return {
      selectedCandidate: null,
      isEditModalVisible: false, // ✅ Ця змінна вже є
      candidates: [],
      totalPages: 0,
      currentPage: 0,
      pageSize: 15,
      searchQuery: "",
      isFilterModalOpen: false,
    }
  },
  methods: {
    async fetchCandidates(pageToFetch = this.currentPage) {
      const storedSize = Number(localStorage.getItem('candidateSize'));
      if (!isNaN(storedSize) && storedSize > 0) {
        this.pageSize = storedSize;
      } else {
        this.pageSize = 15;
        localStorage.setItem('candidateSize', '15');
      }

      this.currentPage = pageToFetch;

      let url = `${process.env.VUE_APP_BACKEND_URL}/candidates/candidate-page/search?page=${this.currentPage}&size=${this.pageSize}`;

      if (this.searchQuery.trim() !== "") {
        url += `&search=${encodeURIComponent(this.searchQuery.trim())}`;
      } else {
        url = `${process.env.VUE_APP_BACKEND_URL}/candidates/candidate-page?page=${this.currentPage}&size=${this.pageSize}`;
      }

      console.log("Fetching URL:", url);

      try {
        const response = await fetch(url, {method: "GET"});

        if (!response.ok) {
          console.error(`HTTP error! status: ${response.status}`);
          await router.push({pathname: "login"});
        }

        const data = await response.json();

        if (data && data.page && data.page.content) {
          this.candidates = data.page.content.content;
          this.totalPages = data.content.totalPages;
          this.currentPage = data.content.number;
        } else if (data && data.content) {
          this.candidates = data.content.content;
          this.totalPages = data.content.totalPages;
          this.currentPage = data.content.number;
        } else {
          console.warn("Отримана несподівана структура даних:", data);
          this.candidates = [];
          this.totalPages = 0;
          this.currentPage = 0;
        }

      } catch (error) {
        console.error("Помилка при отриманні кандидатів:", error);
        alert(`Помилка завантаження даних кандидатів: ${error.message}`);
        this.candidates = [];
        this.totalPages = 0;
        this.currentPage = 0;
      }
    },

    // ✅ ВИПРАВЛЕНО: Правильно встановлюємо isEditModalVisible
    openEdit(candidate) {
      this.selectedCandidate = candidate;
      this.isEditModalVisible = true; // ✅ Встановлюємо true
    },

    // ✅ ВИПРАВЛЕНО: Правильно закриваємо модальне вікно
    closeModal() {
      this.isEditModalVisible = false; // ✅ Встановлюємо false
      this.selectedCandidate = null;
    },

    openFilterModal() {
      this.isFilterModalOpen = true;
    },

    closeFilterModal() {
      this.isFilterModalOpen = false;
    },

    onPageChanged(newPage) {
      this.fetchCandidates(newPage);
    },

    applyPageSizeChange(settings) {
      if (settings && typeof settings.pageSize === 'number') {
        this.pageSize = settings.pageSize;
        localStorage.setItem('candidateSize', settings.pageSize);
        this.currentPage = 0;
        this.fetchCandidates();
      }
      this.closeFilterModal();
    },

    handleSearch() {
      this.currentPage = 0;
      this.fetchCandidates();
    },

    removeSearchQuery() {
      this.searchQuery = "";
      this.currentPage = 0;
      this.fetchCandidates();
    },

    handleSaveCandidate(updatedData) {
      console.log('Saving candidate:', updatedData);

      const index = this.candidates.findIndex(c => c.id === updatedData.id);
      if (index !== -1) {
        this.candidates[index] = {...this.candidates[index], ...updatedData};
      }

      this.closeModal();
    }
  },

  mounted() {
    this.fetchCandidates();
  }
}
</script>

<style scoped>
.row-grey .grid-data-cell {
  background-color: #e8e8e8;
}

.remove-search-button {
  background: red;
  border: none;
  height: 2em;
  width: 2em;
  border-radius: 20%;
  margin-right: 0.1em;
}

.remove-search-button:hover {
  background: #970404;
}

.search-box {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5em;
  margin-right: 0.5em;
}

.grid-row {
  display: contents;
  cursor: pointer; /* ✅ Додано курсор для вказівки на можливість кліку */
}

.grid-row:hover .grid-data-cell {
  background-color: #f0f0f0; /* ✅ Додано ефект hover */
}

.pagination-container {
  display: grid;
  grid-template-columns: 1fr 10fr 1fr;
  grid-auto-rows: auto;
  margin: 0;
  padding: 0 0 0 10px;
}



.add-btn-link {
  text-decoration: none;
  color: black;
}

.btn-add {
  border-radius: 5px;
  border: 2px solid #262a2d;
  height: 3em;
}

.search-container {
  display: grid;
  grid-template-columns: auto 1fr;
  gap: 10px;
  align-items: center;
  padding: 5px;
}

.btn-filter {
  background-color: #197cdf;
  color: white;
  padding: 0.5em 1em;
  height: 3em;
}

.search-box {
  display: flex;
  justify-content: flex-end;
}

.search-input {
  margin: 0;
  background-color: #1B1F23;
  border: 2px solid #4a8523;
  height: 3em;
  width: 250px;
  color: white;
  padding: 0 10px;
  border-radius: 5px;
}

.search-input::placeholder {
  color: grey;
  text-align: left;
  padding-left: 5px;
}

.search-input:focus {
  background-color: #6DB33F;
  border-color: #4a8523;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
  color: black;
}

.btn-container {
  display: flex;
  gap: 10px;
  align-items: center;
  padding: 0 0 0 5px;
}

.btn-container button {
  border-radius: 5px;
  border: 2px solid #262a2d;
  padding: 0.5em 1em;
  height: 3em;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 5px;
  margin-top: 10px;
}

.candidates-grid-container {
  height: calc(100vh - 200px);
  overflow-y: auto;
  margin: 0 10px 0 10px;
  border: 1px solid black;
}

.grid-header,
.grid-body {
  display: grid;
  grid-template-columns:
        minmax(320px, 1fr)
        minmax(150px, 1fr)
        minmax(80px, 0.7fr)
        minmax(80px, 0.7fr)
        minmax(100px, 0.7fr)
        minmax(120px, 1fr)
        minmax(120px, 1fr)
        minmax(120px, 1fr)
        minmax(120px, 1fr)
        minmax(150px, 1fr)
        minmax(110px, 0.6fr)
        minmax(120px, 1fr)
        minmax(110px, 0.6fr)
        minmax(120px, 1fr)
        minmax(150px, 1fr);
  grid-auto-rows: auto;
}

.grid-header {
  position: sticky;
  top: 0;
  z-index: 1;
  background-color: #f8f9fa;
  border-bottom: 2px solid #dee2e6;
}

.grid-header-cell,
.grid-data-cell {
  border-left: 1px solid #e9ecef;
  border-right: 1px solid #e9ecef;
  border-bottom: 1px solid #e9ecef;
  padding: 8px 5px;
  text-align: center;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
  color: #212529;
  background-color: #ffffff;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.grid-header-cell:first-child,
.grid-data-cell:nth-of-type(15n + 1) {
  border-left: none;
}

.grid-header-cell:last-child,
.grid-data-cell:nth-of-type(15n) {
  border-right: none;
}

.grid-header-cell {
  display: flex;
  font-weight: 600;
  background-color: #e9ecef;
  white-space: normal;
  border-bottom: 2px solid #dee2e6;
  justify-content: center;
  align-items: center;
}

.grid-data-cell:nth-of-type(15n + 1) {
  text-align: left;
  padding-left: 10px;
}
</style>