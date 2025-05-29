<template>
  <div class="container mx-auto p-4 sm:p-6 lg:p-8">
    <h2 class="text-2xl sm:text-3xl font-bold mb-6 sm:mb-8 text-gray-800 border-b pb-3">Додавання нового кандидата</h2>
    <div v-show="isError" class="bg-red-600 h-10 flex rounded-xl m-7 top-4 z-30 sticky justify-center items-center ">
      Виникла помилка під час додавання кандидата</div>

    <form @submit.prevent="submitForm" class="space-y-8">
      <div class="p-6 bg-white shadow-lg rounded-lg">
        <h3 class="text-xl font-semibold mb-6 text-gray-700">Основна інформація</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2" for="name">
              Ім'я*
            </label>
            <input class="appearance-none block w-full bg-gray-100 text-gray-700 border border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-indigo-500"
                   id="name" type="text" placeholder="Ім'я" v-model="formData.name" required />
          </div>
          <div>
            <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2" for="phone">
              Номер телефону*
            </label>
            <input class="appearance-none block w-full bg-gray-100 text-gray-700 border border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-indigo-500"
                   id="phone" type="text" placeholder="+380XXXXXXXXX" v-model="formData.phone" required />
          </div>
          <div>
            <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2" for="second-name">
              Прізвище
            </label>
            <input class="appearance-none block w-full bg-gray-100 text-gray-700 border
            border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-indigo-500"
                   id="second-name" type="text" placeholder="Прізвище" v-model="formData.surname">
          </div>
          <div>
            <label class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2" for="middle-name">
              По-батькові
            </label>
            <input class="appearance-none block w-full bg-gray-100 text-gray-700 border
            border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-indigo-500"
                   id="middle-name" type="text" placeholder="По-батькові" v-model="formData.middleName">
          </div>
        </div>
      </div>

      <div class="p-6 bg-white shadow-lg rounded-lg">
        <h3 class="text-xl font-semibold mb-6 text-gray-700">Військова служба | посада | статус</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label for="unit" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Військова частина
            </label>
            <div class="relative">
              <select
                  class="block appearance-none w-full bg-gray-100 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-indigo-500"
                  id="unit" v-model="formData.unit">
                <option disabled value="">Оберіть військову частину</option>
                <option v-for="unit in units" :key="unit.id" :value="unit.id">{{ unit.unitName + " " + unit.strictUnitName }}</option>
              </select>
              <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
              </div>
            </div>
          </div>

          <div>
            <label for="position" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Посада
            </label>
            <div class="relative">
              <select id="position" v-model="formData.position"
                      class="block appearance-none w-full bg-gray-100 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
                <option disabled value="">Оберіть посаду</option>
                <option v-for="position in positions" :key="position.id" :value="position.id">{{ position.positionName }}</option>
              </select>
              <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
              </div>
            </div>
          </div>

          <div>
            <label for="duty" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Тип служби
            </label>
            <div class="relative">
              <select id="duty" v-model="formData.dutyType"
                      class="block appearance-none w-full bg-gray-100 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
                <option disabled value="">Оберіть тип служби</option>
                <option v-for="du in duty" :key="du.id" :value="du.id">{{ du.status }}</option>
              </select>
              <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
              </div>
            </div>
          </div>

          <div>
            <label for="status" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Статус
            </label>
            <div class="relative">
              <select id="status" v-model="formData.status"
                      class="block appearance-none w-full bg-gray-100 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
                <option disabled value="">Оберіть статус</option>
                <option v-for="stat in status" :key="stat.id" :value="stat.id">{{ stat.status }}</option>
              </select>
              <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="p-6 bg-white shadow-lg rounded-lg">
        <h3 class="text-xl font-semibold mb-6 text-gray-700"> Рекомендаційний лист | Рекрутер | Куратор | Супровід</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label for="rl-date" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Дата рекомендаційного листа
            </label>
            <input type="date" id="rl-date" v-model="formData.rlDate"
                   class="appearance-none block w-full bg-gray-100 text-gray-700 border border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
          </div>
          <div>
            <label for="recruiter" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Рекрутер
            </label>
            <div class="relative">
              <select id="recruiter" v-model="formData.recruiter"
                      class="block appearance-none w-full bg-gray-100 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
                <option disabled value="">Оберіть рекрутера</option>
                <option v-for="recr in recruiters" :key="recr.id" :value="recr.name">{{ recr.name }}</option>
              </select>
              <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
              </div>
            </div>
          </div>
          <div>
            <label for="curator" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Куратор
            </label>
            <div class="relative">
              <select id="curator" v-model="formData.curator"
                      class="block appearance-none w-full bg-gray-100 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
                <option disabled value="">Оберіть куратора</option>
                <option v-for="recr in recruiters" :key="recr.id" :value="recr.name">{{ recr.name }}</option> </select>
              <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
              </div>
            </div>
          </div>
          <div>
            <label for="escort" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Супровід
            </label>
            <div class="relative">
              <select id="escort" v-model="formData.escort"
                      class="block appearance-none w-full bg-gray-100 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
                <option disabled value="">Оберіть супровід</option>
                <option v-for="unit in work" :key="unit.id" :value="unit.id">{{ unit.unitName }}</option>
              </select>
              <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="p-6 bg-white shadow-lg rounded-lg">
        <h3 class="text-xl font-semibold mb-6 text-gray-700">Накази та зарахування</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label for="order" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Дата наказу на зарахування у НЦ
            </label>
            <input type="date" id="order" v-model="formData.order"
                   class="appearance-none block w-full bg-gray-100 text-gray-700 border border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
          </div>
          <div>
            <label for="order-number" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Номер наказу на зарахування у НЦ
            </label>
            <input type="text" id="order-number" v-model="formData.orderNumber" placeholder="Номер наказу"
                   class="appearance-none block w-full bg-gray-100 text-gray-700 border
                   border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
          </div>
          <div>
            <label for="unit-order" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Дата наказу на зарахування у ВЧ
            </label>
            <input type="date" id="unit-order" v-model="formData.unitOrder"
                   class="appearance-none block w-full bg-gray-100 text-gray-700 border border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
          </div>
          <div>
            <label for="unit-number" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Номер наказу на зарахування у ВЧ
            </label>
            <input type="text" id="unit-number" v-model="formData.unitNumber" placeholder="Номер наказу"
                   class="appearance-none block w-full bg-gray-100 text-gray-700 border border-gray-300 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
          </div>
        </div>
      </div>

      <div class="p-6 bg-white shadow-lg rounded-lg">
        <h3 class="text-xl font-semibold mb-6 text-gray-700">Додаткова інформація</h3>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label for="psychology" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              Психологічне тестування
            </label>
            <div class="relative">
              <select id="psychology" v-model="formData.psychology"
                      class="block appearance-none w-full bg-gray-100 border border-gray-300 text-gray-700 py-3 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-indigo-500">
                <option disabled value="">Оберіть статус тестування</option>
                <option v-for="ps in psychology" :key="ps.id" :value="ps.id">{{ ps.status }}</option>
              </select>
              <div class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
                <svg class="fill-current h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/></svg>
              </div>
            </div>
          </div>
          <div>
            <label for="territory-record" class="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
              В якому ТЦК на обліку
            </label>
            <input type="text" id="territory-record" placeholder="Назва ТЦК"
                   class="appearance-none block w-full bg-gray-100
                   text-gray-700 border border-gray-300 rounded py-3
                   px-4 leading-tight focus:outline-none focus:bg-white focus:border-indigo-500" v-model="formData.territoryRecord">
          </div>
        </div>
      </div>

      <div class="flex justify-end pt-4">
        <button type="submit"
                class="bg-indigo-600 hover:bg-indigo-700 text-white font-bold py-3 px-6 rounded-lg
                 focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
          Додати кандидата
        </button>
      </div>

    </form>
  </div>
</template>

<style scoped>
/* Scoped styles can be added here if needed, but Tailwind aims to reduce this. */
/* Example: Targeting select arrows if Tailwind's default isn't enough */
select {
  background-image: url('data:image/svg+xml;charset=US-ASCII,%3Csvg%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20width%3D%2220%22%20height%3D%2220%22%20fill%3D%22none%22%20stroke%3D%22%236b7280%22%20stroke-linecap%3D%22round%22%20stroke-linejoin%3D%22round%22%20stroke-width%3D%221.5%22%20viewBox%3D%220%200%2024%2024%22%3E%3Cpath%20d%3D%22M6%209l6%206%206-6%22%2F%3E%3C%2Fsvg%3E');
  background-position: right 0.5rem center;
  background-repeat: no-repeat;
  background-size: 1.5em 1.5em;
  -webkit-print-color-adjust: exact;
  print-color-adjust: exact;
}

/* Remove default arrow for Chrome/Safari */
select::-webkit-scrollbar {
  display: none; /* For Chrome, Safari, and Opera */
}
select {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
}
/* Add a disabled option style for selects */
select option[disabled] {
  color: #9ca3af; /* gray-400 */
}

</style>

<script>
// Your existing script remains the same
import router from "@/router";

export default {
  name: "AddCandidateView",
  data() {
    return {
      isSuccess: false,
      isError: false,
      status: [],
      recruiters: [],
      duty: [],
      units: [],
      work: [],
      positions: [],
      psychology: [],
      endpoints: [
        {endpoint: "/app-user/recruiters", matcher: "recruiters"},
        {endpoint: "/app-user/work-units", matcher: "work"},
        {endpoint: "/psychological", matcher: "psychology"},
        {endpoint: "/military", matcher: "units"},
        {endpoint: "/duty", matcher: "duty"},
        {endpoint: "/status", matcher: "status"},
        {endpoint: "/position", matcher: "positions"},
      ],
      formData: {
        name: "",
        phone: "",
        surname: "",
        middleName: "",
        unit: "",
        position: "",
        status: "",
        dutyType: "",
        rlDate: "",
        recruiter: "",
        curator: "",
        escort: "",
        order: "",
        orderNumber: "",
        unitOrder: "",
        unitNumber: "",
        psychology: "",
        territoryRecord: "",
      }
    }
  },
  methods: {
    fetchData() {
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

        // Initialize select fields with a default disabled option
        if (['unit', 'position', 'dutyType', 'status', 'recruiter', 'curator', 'escort', 'psychology'].includes(target)) {
          // this.formData[target] = ""; // This line ensures the placeholder is selected
        }

      } catch (error) {
        console.error("Could not process data for " + target + ":", error);
      }
    },
    async submitForm() {
      console.log("Form Data:", this.formData);
      let url = `${process.env.VUE_APP_BACKEND_URL}/candidates/add`;
        await fetch(url, {
          method: "POST",
          body: JSON.stringify(this.formData),
          headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "AcceptType": "application/json",
          }
        }).then((response) => {
          return response.json();
        }).then((data) => {
          if(data.httpStatus === "OK"){
            router.back();
          } else if(!data.httpStatus !== "OK"){
            this.isError = true;
          }
        });
    }
  },
  mounted() {
    this.fetchData();
    this.formData.unit = "";
    this.formData.position = "";
    this.formData.dutyType = "";
    this.formData.status = "";
    this.formData.recruiter = "";
    this.formData.curator = "";
    this.formData.escort = "";
    this.formData.psychology = "";
  }
}
</script>