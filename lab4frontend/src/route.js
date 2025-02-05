import {createRouter, createWebHistory} from 'vue-router';
import {useAuthStore} from "@/auth";
import StartPage from "@/components/StartPage.vue";
import MainPage from "@/components/MainPage.vue";
import RegisterPage from "@/components/RegisterPage.vue";

const routes = [
    {path: "/login", component: StartPage},
    {path: "/main", component: MainPage, meta: {obligatoryAuth: true}},
    {path: "/", component: RegisterPage},
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach(async (to, from, next) => {
    const authStore = useAuthStore();
    if (to.meta.obligatoryAuth && !authStore.user) {
        try {
            await authStore.fetchUser();
        } catch (err) {
            return next("/");
        }
    }
    next();
})
export default router;