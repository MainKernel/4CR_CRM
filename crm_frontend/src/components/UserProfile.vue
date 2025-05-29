<template>
  <div class="user-menu-wrapper">
    <img src="../assets/user_icon.jpg" alt="avatar"
         @click="toggleDropdown"
         class="dropdown-menu profile-img"/>
    <div v-if="isDropDown" class="dropdown-menu">
      <router-link to="/">Profile</router-link>
      <router-link to="/logout">Logout</router-link>
      <router-view/>
    </div>
  </div>

</template>

<script>
export default {
  name: "UserProfile",
  data() {
    return {
      isDropDown: false,
    };
  },
  methods: {
    // Метод для перемикання стану видимості
    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    // Метод для закриття меню
    closeDropdown() {
      this.isDropdownOpen = false;
    },
    // Метод для закриття меню при кліку за його межами
    handleClickOutside(event) {
      // Перевіряємо, чи клік відбувся поза контейнером dropdown
      const dropdownContainer = this.$el; // $el посилається на кореневий елемент компонента
      if (dropdownContainer && !dropdownContainer.contains(event.target)) {
        this.closeDropdown();
      }
    }
  },
  watch:
      {
        // Відстежуємо зміни стану isDropdownOpen
        isDropdownOpen(isOpen) {
          if (isOpen) {
            // Якщо меню відкрилося, додаємо глобальний слухач кліків
            document.addEventListener('click', this.handleClickOutside);
          } else {
            // Якщо меню закрилося, видаляємо глобальний слухач кліків
            document.removeEventListener('click', this.handleClickOutside);
          }
        }
      },
  beforeUnmount() {
    // Важливо: видалити слухач подій перед знищенням компонента
    // Щоб уникнути витоків пам'яті та неправильної поведінки
    document.removeEventListener('click', this.handleClickOutside);
  }
}
</script>

<style scoped>
.profile-img {
  height: 1em;
  width: 1em;
  border-radius: 50%;
}

.user-menu-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}


.dropdown-menu {
  position: absolute;
  top: 100%; /* Розташовуємо меню під кнопкою */
  left: 0;
  background-color: white;
  border: 1px solid #ccc;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  z-index: 1000; /* Щоб меню було поверх іншого контенту */
  min-width: 120px;
}

</style>