import {createRouter, createWebHistory} from 'vue-router';
import {useAuthStore} from "@/auth";
import StartPage from "@/components/startPage.vue";
import MainPage from "@/components/mainPage.vue";

const routes = [
    {path: "/", component: StartPage},
    {path: "/main", component: MainPage, meta: {obligatoryAuth: true}},
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
    if (to.meta.obligatoryAuth && !authStore.user) {
        return next("/")
    }
    next();
})
export default router;