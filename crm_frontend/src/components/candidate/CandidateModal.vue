<template>
  <div v-if="isVisible" class="fixed inset-0 z-50 flex items-center justify-center">
    <!-- Backdrop -->
    <div
        class="fixed inset-0 bg-black bg-opacity-50 transition-opacity"
        @click="handleCancel"
    ></div>

    <!-- Modal -->
    <div class="relative bg-white rounded-lg shadow-xl max-w-7xl w-full mx-1 max-h-[95vh] overflow-y-auto">
      <!-- Header -->
      <div class="flex items-center justify-between p-1 border-b border-gray-200">
        <h2 class="text-2xl font-bold text-gray-900">
          {{ isEditMode ? 'Редагування кандидата' : 'Інформація про кандидата' }}
        </h2>
        <div class="flex items-center gap-2">
          <button
              v-if="!isEditMode"
              @click="enableEditMode"
              class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
          >
            Редагувати
          </button>
          <button
              @click="handleCancel"
              class="text-gray-400 hover:text-gray-600 transition-colors"
          >
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        </div>
      </div>

      <!-- Error Message -->
      <div v-if="isError" class="bg-red-600 text-white p-3 m-4 rounded-lg text-center">
        Виникла помилка під час збереження змін
      </div>

      <!-- Content -->
      <div class="p-1 space-y-2" v-if="localCandidate">
        <form @submit.prevent="handleSave" class="space-y-6">
          <!-- Personal Information -->
          <div class="bg-gray-50 rounded-lg p-1">
            <h3 class="text-lg font-semibold text-gray-900 mb-2">Особисті дані кандидата</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Прізвище*</label>
                <input
                    v-if="isEditMode"
                    v-model="editData.surname"
                    type="text"
                    required
                    class="w-full px-2 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <p v-else class="text-sm text-gray-900 py-2">{{ localCandidate.surname }}</p>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Ім'я*</label>
                <input
                    v-if="isEditMode"
                    v-model="editData.name"
                    type="text"
                    required
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <p v-else class="text-sm text-gray-900 py-2">{{ localCandidate.name }}</p>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">По-батькові</label>
                <input
                    v-if="isEditMode"
                    v-model="editData.middleName"
                    type="text"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <p v-else class="text-sm text-gray-900 py-2">{{ localCandidate.middleName }}</p>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Номер телефону*</label>
                <input
                    v-if="isEditMode"
                    v-model="editData.phoneNumber"
                    type="tel"
                    required
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <p v-else class="text-sm text-gray-900 py-2">{{ localCandidate.phoneNumber }}</p>
              </div>
            </div>
          </div>

          <!-- Status and Service -->
          <div class="bg-gray-50 rounded-lg p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Статус та служба</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Статус</label>
                <select
                    v-if="isEditMode"
                    v-model="editData.statusId"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">Оберіть статус</option>
                  <option v-for="status in statusOptions" :key="status.id" :value="status.id">
                    {{ status.status }}
                  </option>
                </select>
                <div v-else class="flex justify-center py-2">
                  <span
                      class="inline-block w-3 h-3 rounded-full mr-2 items-center justify-center"
                      :style="{ backgroundColor: `#${localCandidate.status?.color}` }"
                  ></span>
                  <span class="text-sm text-gray-900">{{ localCandidate.status?.status }}</span>
                </div>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Тип служби</label>
                <select
                    v-if="isEditMode"
                    v-model="editData.dutyTypeId"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">Оберіть тип служби</option>
                  <option v-for="duty in dutyOptions" :key="duty.id" :value="duty.id">
                    {{ duty.status }}
                  </option>
                </select>
                <div v-else class="flex justify-center py-2">
                  <span
                      class="inline-block w-3 h-3 rounded-full mr-2"
                      :style="{ backgroundColor: `#${localCandidate.dutyType?.color}` }"
                  ></span>
                  <span class="text-sm text-gray-900">{{ localCandidate.dutyType?.status }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Military Unit and Position -->
          <div class="bg-gray-50 rounded-lg p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Військова частина та посада</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Військова частина</label>
                <select
                    v-if="isEditMode"
                    v-model="editData.militaryUnitId"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">Оберіть військову частину</option>
                  <option v-for="unit in unitOptions" :key="unit.id" :value="unit.id">
                    {{ unit.unitName }} {{ unit.strictUnitName }}
                  </option>
                </select>
                <div v-else class="py-2">
                  <p class="text-sm text-gray-900" :title="localCandidate.militaryUnit?.strictUnitName">
                    {{ localCandidate.militaryUnit?.unitName }}</p>
                </div>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Посада</label>
                <select
                    v-if="isEditMode"
                    v-model="editData.candidatePositionId"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">Оберіть посаду</option>
                  <option v-for="position in positionOptions" :key="position.id" :value="position.id">
                    {{ position.positionName }}
                  </option>
                </select>
                <div v-else class="py-2">
                  <p class="text-sm text-gray-900">{{ localCandidate.candidatePosition?.positionName }}</p>
                </div>
              </div>
            </div>
          </div>

          <!-- Personnel Information -->
          <div class="bg-gray-50 rounded-lg p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-2">Рекрутери та супровід</h3>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-2">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Рекрутер</label>
                <select
                    v-if="isEditMode"
                    v-model="editData.recruiter"
                    type="text"
                    class="w-full px-2 py-1 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500">
                  <option value="">Оберіть рекрутера</option>
                  <option v-for="(recruit, index) in recruitersOptions" :key="index" :value="recruit.name">
                    {{ recruit.name }}
                  </option>
                </select>
                <p v-else class="text-sm text-gray-900 py-2">{{ localCandidate.recruiter }}</p>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Куратор</label>
                <select
                    v-if="isEditMode"
                    v-model="editData.curator"
                    type="text"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">Оберіть куратора</option>
                  <option v-for="(recruit, index) in recruitersOptions" :key="index" :value="recruit.curator">
                    {{ recruit.name }}
                  </option>
                </select>
                <p v-else class="text-sm text-gray-900 py-2">{{ localCandidate.curator }}</p>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Супроводжує</label>
                <select
                    v-if="isEditMode"
                    v-model="editData.escortedBy"
                    type="text"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value=""> Оберіть супровід</option>
                  <option v-for="escort in workUnitOptions" :key="escort.unitName" :value="escort.unitName">
                    {{ escort.unitName }}
                  </option>
                </select>
                <p v-else class="text-sm text-gray-900 py-2">{{ localCandidate.escortedBy }}</p>
              </div>
            </div>
          </div>

          <!-- Tests and Documents -->
          <div class="bg-gray-50 rounded-lg p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Тести та документи</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Психологічний тест</label>
                <select
                    v-if="isEditMode"
                    v-model="editData.psychologicalTestId"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                  <option value="">Оберіть статус тестування</option>
                  <option v-for="test in psychologyOptions" :key="test.id" :value="test.id">
                    {{ test.status }}
                  </option>
                </select>
                <div v-else class="flex justify-center py-2">
                  <span
                      class="inline-block w-3 h-3 rounded-full mr-2"
                      :style="{ backgroundColor: `#${localCandidate.psychologicalTest?.color}` }"
                  ></span>
                  <span class="text-sm text-gray-900">{{ localCandidate.psychologicalTest?.status }}</span>
                </div>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Рекомендаційний лист</label>
                <input
                    v-if="isEditMode"
                    v-model="editData.recommendationLetter"
                    type="date"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <p v-else class="text-sm text-gray-900 py-2">{{ formatDate(localCandidate.recommendationLetter) }}</p>
              </div>

              <div class="md:col-span-2">
                <label class="block text-sm font-medium text-gray-700 mb-2">До якого ТЦК приписаний</label>
                <input
                    v-if="isEditMode"
                    v-model="editData.territoryCenterRecord"
                    type="text"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <p v-else class="text-sm text-gray-900 py-2">{{ localCandidate.territoryCenterRecord }}</p>
              </div>
            </div>
          </div>

          <!-- Orders -->
          <div class="bg-gray-50 rounded-lg p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Накази</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Дата наказу про зарахування</label>
                <input
                    v-if="isEditMode"
                    v-model="editData.enrolmentOrderDate"
                    type="date"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <p v-else class="text-sm text-gray-900 py-2">
                  {{ localCandidate.enrolmentOrderDate ? formatDate(localCandidate.enrolmentOrderDate) : 'Не вказано' }}
                </p>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Номер наказу</label>
                <input
                    v-if="isEditMode"
                    v-model="editData.orderNumber"
                    type="text"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <p v-else class="text-sm text-gray-900 py-2">{{ localCandidate.orderNumber || 'Не вказано' }}</p>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Наказ навчального центру</label>
                <input
                    v-if="isEditMode"
                    v-model="editData.trainingCenterOrder"
                    type="date"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <p v-else class="text-sm text-gray-900 py-2">{{
                    localCandidate.trainingCenterOrder || 'Не вказано'
                  }}</p>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">Номер наказу навчального центру</label>
                <input
                    v-if="isEditMode"
                    v-model="editData.trainingCenterOrderNumber"
                    type="text"
                    class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />
                <p v-else class="text-sm text-gray-900 py-2">{{
                    localCandidate.trainingCenterOrderNumber || 'Не вказано'
                  }}</p>
              </div>
            </div>
          </div>

          <!-- Documents and Comments Section -->
          <div class="bg-gray-50 rounded-lg p-6">
            <h3 class="text-lg font-semibold text-gray-900 mb-4">Коментарі та документи</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">

              <!-- Documents Section -->
              <div>
                <div class="flex justify-between items-center mb-2">
                  <label class="block text-sm font-medium text-gray-700">Документи</label>
                  <span v-if="isEditMode && pendingDocuments.length > 0"
                        class="text-xs text-blue-600 bg-blue-100 px-2 py-1 rounded">
                    {{ pendingDocuments.length }} нових документів
                  </span>
                </div>

                <!-- Edit Mode -->
                <div v-if="isEditMode" class="space-y-3">
                  <!-- Batch Add Documents -->
                  <div
                      class="border-2 border-dashed border-gray-300 rounded-lg p-4 hover:border-gray-400 transition-colors">
                    <div class="space-y-3">
                      <!-- Multiple File Upload -->
                      <div>
                        <input
                            type="file"
                            @change="handleBatchFileUpload"
                            class="w-full text-sm text-gray-500 file:mr-4 file:py-2 file:px-4 file:rounded-lg file:border-0 file:text-sm file:font-medium file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100"
                            multiple
                            accept=".pdf,.doc,.docx,.jpg,.jpeg,.png,.txt"
                        >
                      </div>

                      <!-- Batch Document Type -->
                      <div class="grid grid-cols-2 gap-2">
                        <select
                            v-model="batchDocumentType"
                            class="px-3 py-2 text-sm border border-gray-300 rounded-lg focus:outline-none focus:ring-1 focus:ring-blue-500 focus:border-blue-500"
                        >
                          <option value="">Тип для всіх документів</option>
                          <option value="passport">Паспорт</option>
                          <option value="military_id">Військовий квиток</option>
                          <option value="medical">Медична довідка</option>
                          <option value="education">Документ про освіту</option>
                          <option value="recommendation">Рекомендаційний лист</option>
                          <option value="other">Інше</option>
                        </select>

                        <input
                            v-model="batchDocumentDescription"
                            type="text"
                            placeholder="Опис для всіх документів"
                            class="px-3 py-2 text-sm border border-gray-300 rounded-lg focus:outline-none focus:ring-1 focus:ring-blue-500 focus:border-blue-500"
                        >
                      </div>

                      <!-- Apply Batch Settings Button -->
                      <button
                          v-if="pendingDocuments.length > 0"
                          @click="applyBatchDocumentSettings"
                          type="button"
                          class="w-full px-3 py-2 bg-green-500 text-white text-sm rounded-lg hover:bg-green-600 transition-colors"
                      >
                        Застосувати до всіх нових документів
                      </button>
                    </div>
                    <p class="text-xs text-gray-500 mt-2">
                      Виберіть декілька файлів для пакетного завантаження
                    </p>
                  </div>

                  <!-- Pending Documents (New) -->
                  <div v-if="pendingDocuments.length > 0" class="bg-blue-50 border border-blue-200 rounded-lg p-3">
                    <h4 class="text-sm font-medium text-blue-800 mb-2">Нові документи ({{
                        pendingDocuments.length
                      }})</h4>
                    <div class="max-h-32 overflow-auto space-y-2">
                      <div
                          v-for="(document, index) in pendingDocuments"
                          :key="'pending-' + index"
                          class="bg-white border border-blue-200 rounded p-2 text-sm"
                      >
                        <div class="flex justify-between items-start">
                          <div class="flex-1 grid grid-cols-2 gap-2">
                            <input
                                v-model="document.fileName"
                                type="text"
                                placeholder="Назва файлу"
                                class="px-2 py-1 text-xs border border-gray-300 rounded focus:outline-none focus:ring-1 focus:ring-blue-500"
                            >
                            <select
                                v-model="document.type"
                                class="px-2 py-1 text-xs border border-gray-300 rounded focus:outline-none focus:ring-1 focus:ring-blue-500"
                            >
                              <option value="">Тип</option>
                              <option value="passport">Паспорт</option>
                              <option value="military_id">Військовий квиток</option>
                              <option value="medical">Медична довідка</option>
                              <option value="education">Документ про освіту</option>
                              <option value="recommendation">Рекомендаційний лист</option>
                              <option value="other">Інше</option>
                            </select>
                            <input
                                v-model="document.description"
                                type="text"
                                placeholder="Опис"
                                class="col-span-2 px-2 py-1 text-xs border border-gray-300 rounded focus:outline-none focus:ring-1 focus:ring-blue-500"
                            >
                          </div>
                          <button
                              @click="removePendingDocument(index)"
                              class="ml-2 text-red-500 hover:text-red-700"
                              type="button"
                          >
                            <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M6 18L18 6M6 6l12 12"></path>
                            </svg>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Existing Documents -->
                  <div v-if="editableDocuments.length > 0" class="bg-gray-50 border border-gray-200 rounded-lg p-3">
                    <h4 class="text-sm font-medium text-gray-700 mb-2">Існуючі документи ({{
                        editableDocuments.length
                      }})</h4>
                    <div class="max-h-48 overflow-auto space-y-2">
                      <div
                          v-for="(document, index) in editableDocuments"
                          :key="document.id || index"
                          class="bg-white border border-gray-200 rounded-lg p-3 shadow-sm"
                      >
                        <div class="flex items-center justify-between mb-2">
                          <span class="text-xs font-medium text-gray-500">Документ #{{ index + 1 }}</span>
                          <button
                              @click="removeDocument(index)"
                              class="text-red-500 hover:text-red-700 transition-colors"
                              type="button"
                          >
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M6 18L18 6M6 6l12 12"></path>
                            </svg>
                          </button>
                        </div>

                        <div class="grid grid-cols-1 gap-2">
                          <input
                              v-model="document.fileName"
                              type="text"
                              placeholder="Назва файлу"
                              class="w-full px-2 py-1 text-sm border border-gray-300 rounded focus:outline-none focus:ring-1 focus:ring-blue-500"
                          >
                          <select
                              v-model="document.type"
                              class="w-full px-2 py-1 text-sm border border-gray-300 rounded focus:outline-none focus:ring-1 focus:ring-blue-500"
                          >
                            <option value="">Тип документу</option>
                            <option value="passport">Паспорт</option>
                            <option value="military_id">Військовий квиток</option>
                            <option value="medical">Медична довідка</option>
                            <option value="education">Документ про освіту</option>
                            <option value="recommendation">Рекомендаційний лист</option>
                            <option value="other">Інше</option>
                          </select>
                          <textarea
                              v-model="document.description"
                              placeholder="Опис документу"
                              class="w-full px-2 py-1 text-sm border border-gray-300 rounded focus:outline-none focus:ring-1 focus:ring-blue-500"
                              rows="2"
                          ></textarea>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- View Mode -->
                <div v-else>
                  <div class="h-48 overflow-auto">
                    <div class="grid grid-cols-4 gap-1 text-xs font-medium text-gray-600 mb-2 pb-1 border-b">
                      <span>Назва файлу</span>
                      <span>Тип</span>
                      <span>Завантажив</span>
                      <span>Дата</span>
                    </div>
                    <div
                        v-for="document in localCandidate.documents"
                        :key="document.id"
                        class="grid grid-cols-4 gap-1 py-2 border-b border-gray-100 hover:bg-gray-50 transition-colors"
                        @dblclick="downloadDocument(document.id)"
                    >
                      <p class="truncate text-sm text-gray-700" :title="document.fileName">{{ document.fileName }}</p>
                      <p class="truncate text-sm text-gray-700" :title="document.type">{{ document.type }}</p>
                      <p class="truncate text-sm text-gray-700" :title="document.uploadedBy">{{
                          document.uploadedBy
                        }}</p>
                      <p class="truncate text-sm text-gray-700" :title="document.uploadedDate">{{
                          document.uploadedDate
                        }}</p>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Comments Section -->
              <div>
                <div class="flex justify-between items-center mb-2">
                  <label class="block text-sm font-medium text-gray-700">Коментарі</label>
                  <span v-if="isEditMode && pendingComments.length > 0"
                        class="text-xs text-green-600 bg-green-100 px-2 py-1 rounded">
                    {{ pendingComments.length }} нових коментарів
                  </span>
                </div>

                <!-- Edit Mode -->
                <div v-if="isEditMode" class="space-y-3">
                  <!-- Batch Add Comments -->
                  <div class="bg-white border border-gray-200 rounded-lg p-3">
                    <label class="block text-xs font-medium text-gray-600 mb-1">Додати коментарі</label>
                    <textarea
                        v-model="batchCommentsText"
                        placeholder="Введіть коментарі, кожен з нового рядка..."
                        class="w-full px-3 py-2 text-sm border border-gray-300 rounded focus:outline-none focus:ring-1 focus:ring-blue-500 focus:border-blue-500"
                        rows="4"
                    ></textarea>
                    <div class="flex justify-between items-center mt-2">
                      <span class="text-xs text-gray-500">Кожен рядок стане окремим коментарем</span>
                      <button
                          @click="addBatchComments"
                          type="button"
                          class="px-3 py-1 bg-blue-500 text-white text-sm rounded hover:bg-blue-600 transition-colors"
                          :disabled="!batchCommentsText.trim()"
                      >
                        Додати коментарі
                      </button>
                    </div>
                  </div>

                  <!-- Pending Comments (New) -->
                  <div v-if="pendingComments.length > 0" class="bg-green-50 border border-green-200 rounded-lg p-3">
                    <h4 class="text-sm font-medium text-green-800 mb-2">Нові коментарі ({{
                        pendingComments.length
                      }})</h4>
                    <div class="max-h-32 overflow-auto space-y-2">
                      <div
                          v-for="(comment, index) in pendingComments"
                          :key="'pending-comment-' + index"
                          class="bg-white border border-green-200 rounded p-2"
                      >
                        <div class="flex justify-between items-start">
                          <textarea
                              v-model="comment.comment"
                              class="flex-1 px-2 py-1 text-sm border border-gray-300 rounded focus:outline-none focus:ring-1 focus:ring-green-500"
                              rows="2"
                          ></textarea>
                          <button
                              @click="removePendingComment(index)"
                              class="ml-2 text-red-500 hover:text-red-700"
                              type="button"
                          >
                            <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M6 18L18 6M6 6l12 12"></path>
                            </svg>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!-- Existing Comments -->
                  <div v-if="editableComments.length > 0" class="bg-gray-50 border border-gray-200 rounded-lg p-3">
                    <h4 class="text-sm font-medium text-gray-700 mb-2">Існуючі коментарі ({{
                        editableComments.length
                      }})</h4>
                    <div class="max-h-32 overflow-auto space-y-2">
                      <div
                          v-for="(comment, index) in editableComments"
                          :key="comment.id || index"
                          class="bg-white border border-gray-200 rounded-lg p-3"
                      >
                        <div class="flex justify-between items-start mb-2">
                          <div class="text-xs text-gray-500">
                            <span class="font-medium">{{ comment.commentedBy }}</span> • {{ comment.commentDate }}
                          </div>
                          <button
                              @click="removeComment(index)"
                              class="text-red-500 hover:text-red-700 transition-colors"
                              type="button"
                          >
                            <svg class="w-3 h-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M6 18L18 6M6 6l12 12"></path>
                            </svg>
                          </button>
                        </div>
                        <textarea
                            v-model="comment.comment"
                            class="w-full px-2 py-1 text-sm border border-gray-300 rounded focus:outline-none focus:ring-1 focus:ring-blue-500"
                            rows="2"
                        ></textarea>
                      </div>
                    </div>
                  </div>
                </div>

                <!-- View Mode -->
                <div v-else>
                  <div class="h-48 overflow-auto space-y-2">
                    <div
                        v-for="comment in localCandidate.comments"
                        :key="comment.id"
                        class="bg-white border border-gray-100 rounded p-2 hover:bg-gray-50 transition-colors"
                    >
                      <div class="flex justify-between text-xs text-gray-500 mb-1">
                        <span class="font-medium">{{ comment.commentedBy }}</span>
                        <span>{{ comment.commentDate }}</span>
                      </div>
                      <p class="text-sm text-gray-700">{{ comment.comment }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Save Progress Indicator -->
            <div v-if="isEditMode && (pendingDocuments.length > 0 || pendingComments.length > 0)"
                 class="mt-4 p-3 bg-yellow-50 border border-yellow-200 rounded-lg">
              <div class="flex items-center justify-between">
                <div class="text-sm text-yellow-800">
                  <strong>Незбережені зміни:</strong>
                  {{ pendingDocuments.length }} документів, {{ pendingComments.length }} коментарів
                </div>
                <button
                    @click="saveBatchChanges"
                    type="button"
                    class="px-4 py-2 bg-yellow-500 text-white text-sm rounded-lg hover:bg-yellow-600 transition-colors"
                    :disabled="isSavingBatch"
                >
                  {{ isSavingBatch ? 'Збереження...' : 'Зберегти зміни' }}
                </button>
              </div>
            </div>
          </div>
        </form>
      </div>

      <!-- Footer -->
      <div class="flex justify-end gap-3 p-6 border-t border-gray-200">
        <button
            v-if="isEditMode"
            @click="handleCancel"
            type="button"
            class="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition-colors"
        >
          Скасувати
        </button>
        <button
            v-if="isEditMode"
            @click="handleSave"
            type="button"
            class="px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
            :disabled="isSaving"
        >
          {{ isSaving ? 'Збереження...' : 'Зберегти' }}
        </button>
        <button
            v-if="!isEditMode"
            @click="$emit('close')"
            class="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-600 transition-colors"
        >
          Закрити
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EditableCandidateModal',
  props: {
    isVisible: {
      type: Boolean,
      required: true,
      default: false
    },
    candidate: {
      type: Object,
      required: true,
      default: () => ({})
    }
  },
  emits: ['close', 'save', 'success', 'error'],
  data() {
    return {
      isEditMode: false,
      isError: false,
      isSaving: false,
      editData: {},
      originalData: {},
      localCandidate: {}, // Local copy of candidate prop
      editableDocuments: [],
      editableComments: [],
      pendingDocuments: [],
      pendingComments: [],
      batchDocumentType: '',
      batchDocumentDescription: '',
      batchCommentsText: '',
      isSavingBatch: false,
      endpoints: [
        {endpoint: "/app-user/recruiters", matcher: "recruitersOptions"},
        {endpoint: "/app-user/work-units", matcher: "workUnitOptions"},
        {endpoint: "/psychological", matcher: "psychologyOptions"},
        {endpoint: "/military", matcher: "unitOptions"},
        {endpoint: "/duty", matcher: "dutyOptions"},
        {endpoint: "/status", matcher: "statusOptions"},
        {endpoint: "/position", matcher: "positionOptions"},
      ],
      recruitersOptions: [],
      workUnitOptions: [],
      statusOptions: [],
      dutyOptions: [],
      unitOptions: [],
      positionOptions: [],
      psychologyOptions: []
    }
  },
  watch: {
    candidate: {
      handler(newCandidate) {
        if (newCandidate) {
          // Create a deep copy of the candidate prop
          this.localCandidate = JSON.parse(JSON.stringify(newCandidate))
          this.initializeEditData()
        }
      },
      immediate: true,
      deep: true
    }
  },
  methods: {
    initializeEditData() {
      this.editData = {
        id: this.localCandidate.id,
        surname: this.localCandidate.surname || '',
        name: this.localCandidate.name || '',
        middleName: this.localCandidate.middleName || '',
        phoneNumber: this.localCandidate.phoneNumber || '',
        statusId: this.localCandidate.status?.id || '',
        dutyTypeId: this.localCandidate.dutyType?.id || '',
        militaryUnitId: this.localCandidate.militaryUnit?.id || '',
        candidatePositionId: this.localCandidate.candidatePosition?.id || '',
        recruiter: this.localCandidate.recruiter || '',
        curator: this.localCandidate.curator || '',
        escortedBy: this.localCandidate.escortedBy || '',
        psychologicalTestId: this.localCandidate.psychologicalTest?.id || '',
        recommendationLetter: this.localCandidate.recommendationLetter || '',
        territoryCenterRecord: this.localCandidate.territoryCenterRecord || '',
        enrolmentOrderDate: this.localCandidate.enrolmentOrderDate || '',
        orderNumber: this.localCandidate.orderNumber || '',
        trainingCenterOrder: this.localCandidate.trainingCenterOrder || '',
        trainingCenterOrderNumber: this.localCandidate.trainingCenterOrderNumber || ''
      }
      this.originalData = {...this.editData}
      this.editableDocuments = this.localCandidate.documents ? [...this.localCandidate.documents] : []
      this.editableComments = this.localCandidate.comments ? [...this.localCandidate.comments] : []

      // Clear pending arrays
      this.pendingDocuments = []
      this.pendingComments = []

      // Clear batch fields
      this.batchDocumentType = ''
      this.batchDocumentDescription = ''
      this.batchCommentsText = ''
    },

    enableEditMode() {
      this.isEditMode = true
      this.isError = false
    },

    handleCancel() {
      if (this.isEditMode) {
        // Restore original data
        this.editData = {...this.originalData}
        this.initializeEditData() // Reset all data including pending items
        this.isEditMode = false
        this.isError = false
      } else {
        this.$emit('close')
      }
    },

    async handleSave() {
      this.isSaving = true
      this.isError = false

      try {
        // Save batch changes first if there are any
        if (this.pendingDocuments.length > 0 || this.pendingComments.length > 0) {
          await this.saveBatchChanges()
        }

        // Save main candidate data
        const saveData = {
          ...this.editData,
          documents: this.editableDocuments,
          comments: this.editableComments
        }

        const response = await fetch(`${process.env.VUE_APP_BACKEND_URL}/candidates/${this.localCandidate.id}`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${this.getAuthToken()}`
          },
          body: JSON.stringify(saveData)
        })

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`)
        }

        const result = await response.json()

        // Update local candidate with server response
        this.localCandidate = result
        this.$emit('save', result)
        this.originalData = {...this.editData}
        this.isEditMode = false

      } catch (error) {
        console.error('Error saving candidate:', error)
        this.isError = true
      } finally {
        this.isSaving = false
      }
    },

    async fetchData() {
      this.endpoints.forEach(endpoint => {
        let url = `${process.env.VUE_APP_BACKEND_URL}${endpoint.endpoint}`
        this.processData(url, endpoint.matcher)
      })
    },

    async processData(url, target) {
      try {
        const response = await fetch(url, {
          method: "GET",
        });
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        this[target] = data.content;
      } catch (error) {
        console.error("Could not process data for " + target + ":", error);
      }
    },

    formatDate(dateString) {
      if (!dateString) return 'Не вказано'
      const date = new Date(dateString)
      return date.toLocaleDateString('uk-UA')
    },

    // Batch document upload
    handleBatchFileUpload(event) {
      const files = Array.from(event.target.files)
      const currentUser = this.getCurrentUser()
      const currentDate = new Date().toLocaleDateString('uk-UA')

      files.forEach(file => {
        const newDocument = {
          id: Date.now() + Math.random(),
          fileName: file.name,
          type: this.batchDocumentType || '',
          description: this.batchDocumentDescription || '',
          uploadedBy: currentUser,
          uploadedDate: currentDate,
          file: file,
          isNew: true
        }
        this.pendingDocuments.push(newDocument)
      })

      event.target.value = ''
    },

    applyBatchDocumentSettings() {
      this.pendingDocuments.forEach(doc => {
        if (this.batchDocumentType) {
          doc.type = this.batchDocumentType
        }
        if (this.batchDocumentDescription) {
          doc.description = this.batchDocumentDescription
        }
      })

      this.batchDocumentType = ''
      this.batchDocumentDescription = ''
    },

    removePendingDocument(index) {
      this.pendingDocuments.splice(index, 1)
    },

    addBatchComments() {
      if (this.batchCommentsText.trim()) {
        const currentUser = this.getCurrentUser()
        const currentDate = new Date().toLocaleDateString('uk-UA')

        const commentLines = this.batchCommentsText
            .split('\n')
            .map(line => line.trim())
            .filter(line => line.length > 0)

        commentLines.forEach(commentText => {
          const newComment = {
            id: Date.now() + Math.random(),
            comment: commentText,
            commentedBy: currentUser,
            commentDate: currentDate,
            isNew: true
          }
          this.pendingComments.push(newComment)
        })

        this.batchCommentsText = ''
      }
    },

    removePendingComment(index) {
      this.pendingComments.splice(index, 1)
    },

    removeDocument(index) {
      if (confirm('Ви впевнені, що хочете видалити цей документ?')) {
        this.editableDocuments.splice(index, 1)
      }
    },

    removeComment(index) {
      if (confirm('Ви впевнені, що хочете видалити цей коментар?')) {
        this.editableComments.splice(index, 1)
      }
    },

    getCurrentUser() {
      return localStorage.getItem('currentUser') || 'Поточний користувач'
    },

    async saveBatchChanges() {
      this.isSavingBatch = true

      try {
        const uploadedDocuments = await this.uploadPendingDocuments()

        const updateData = {
          candidateId: this.localCandidate.id,
          documents: {
            existing: this.editableDocuments,
            new: uploadedDocuments
          },
          comments: {
            existing: this.editableComments,
            new: this.pendingComments
          }
        }

        const response = await fetch(`${process.env.VUE_APP_BACKEND_URL}/candidates/${this.localCandidate.id}/documents-comments`, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${this.getAuthToken()}`
          },
          body: JSON.stringify(updateData)
        })

        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`)
        }

        const result = await response.json()
        this.updateLocalDataFromServer(result)

        this.pendingDocuments = []
        this.pendingComments = []

        this.$emit('success', 'Зміни успішно збережено')

      } catch (error) {
        console.error('Error saving batch changes:', error)
        this.isError = true
        this.$emit('error', 'Помилка при збереженні змін')
      } finally {
        this.isSavingBatch = false
      }
    },

    async uploadPendingDocuments() {
      const uploadedDocuments = []

      for (const document of this.pendingDocuments) {
        if (document.file) {
          const formData = new FormData()
          formData.append('file', document.file)
          formData.append('candidateId', this.localCandidate.id)
          formData.append('fileName', document.fileName)
          formData.append('type', document.type)
          formData.append('description', document.description)

          try {
            const response = await fetch(`${process.env.VUE_APP_BACKEND_URL}/documents/upload`, {
              method: 'POST',
              headers: {
                'Authorization': `Bearer ${this.getAuthToken()}`
              },
              body: formData
            })

            if (response.ok) {
              const result = await response.json()
              uploadedDocuments.push({
                ...document,
                id: result.id,
                url: result.url,
                filePath: result.filePath
              })
            }
          } catch (error) {
            console.error('Error uploading document:', error)
            throw error
          }
        }
      }

      return uploadedDocuments
    },

    updateLocalDataFromServer(serverData) {
      if (serverData.documents) {
        this.editableDocuments = serverData.documents
        this.localCandidate.documents = serverData.documents
      }
      if (serverData.comments) {
        this.editableComments = serverData.comments
        this.localCandidate.comments = serverData.comments
      }
    },

    getAuthToken() {
      return localStorage.getItem('authToken') || ''
    },
    async downloadDocument(id) {
      try {
        const response = await fetch(`${process.env.VUE_APP_BACKEND_URL}/documents/${id}`, {
          method: 'GET',
          // 'responseType: 'blob'' є опцією для Axios, а не для Fetch API.
          // Fetch API автоматично обробляє тип відповіді, але нам потрібно явно
          // вказати, що ми очікуємо BLOB або ArrayBuffer.
          headers: {
            'Authorization': `Bearer ${this.getAuthToken()}`
          }
        });

        if (!response.ok) {
          // Обробка помилок HTTP (наприклад, 404 Not Found, 401 Unauthorized)
          const errorText = await response.text(); // Читаємо тіло помилки, якщо воно є
          throw new Error(`HTTP error! status: ${response.status}, message: ${errorText}`);
        }

        // Отримуємо дані як BLOB
        const blob = await response.blob();

        // Отримуємо ім'я файлу з заголовка Content-Disposition, якщо бекенд його надає
        // Наприклад: Content-Disposition: attachment; filename="my_file.pdf"
        const contentDisposition = response.headers.get('Content-Disposition');
        let filename = `download-${id}`; // Ім'я за замовчуванням, якщо не знайдено в заголовку
        if (contentDisposition) {
          const filenameMatch = contentDisposition.match(/filename="?([^"]+)"?/);
          if (filenameMatch && filenameMatch[1]) {
            filename = filenameMatch[1];
          }
        }

        // Створюємо тимчасовий URL для BLOB
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', filename); // Встановлюємо ім'я файлу для завантаження
        document.body.appendChild(link);
        link.click(); // Ініціюємо завантаження
        document.body.removeChild(link); // Видаляємо тимчасове посилання

        window.URL.revokeObjectURL(url); // Звільняємо пам'ять, зайняту BLOB URL

        console.log(`Файл ${filename} успішно завантажено.`);

      } catch (error) {
        console.error('Помилка при завантаженні документа:', error);
        // Можна відобразити повідомлення про помилку користувачеві
        alert(`Не вдалося завантажити документ: ${error.message}`);
      }
    },
  },

  mounted() {
    this.fetchData()
  }
}
</script>

<style scoped>
.transition-colors {
  transition: background-color 0.2s ease-in-out, color 0.2s ease-in-out;
}

button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

* {
  color: black;
}
</style>