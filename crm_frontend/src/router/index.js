import { createRouter, createWebHistory } from 'vue-router'
import CandidateView from "@/views/CandidateView.vue";

const routes = [
  {
    path: '/',
    name: 'home',
    component: CandidateView,
    meta: { requiresAuth: true },
  },
  {
    path: '/statistics',
    name: 'statistics',
    component: () => import( '../views/AboutView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/statuses',
    name: 'statuses',
    component: () => import( '../views/StatusView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/candidate',
    name: 'candidate',
    component: () => import( '../views/CandidateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/login',
    name: 'login',
    component: () => import( '../views/LoginView.vue')
  },
  {
    path: '/candidate/add',
    name: 'candidateAdd',
    component: () => import( '../views/AddCandidateView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/escort',
    name: 'escort',
    component: () => import('../views/EscortView.vue'),
    meta: { requiresAuth: true },
  }
]

const router = createRouter({
      history: createWebHistory(`${process.env.BASE_URL}`),
  routes
});
router.beforeEach((to, from, next) => {

  const token = localStorage.getItem("token"); // ← виправлено

  if (to.name === "login" && token) {
    next({ name: "candidate" });
  } else if (to.meta.requiresAuth && !token) {
    next({ name: "login" });
  } else {
    next();
  }
});

export default router
