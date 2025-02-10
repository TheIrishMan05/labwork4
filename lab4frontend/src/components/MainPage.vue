<template>
  <div class="page">
    <div class="header">
      <div class="menu">
        <input id="burger-checkbox" class="burger-checkbox" type="checkbox"/>
        <label class="burger" for="burger-checkbox">
          <span/>
        </label>
        <ul class="menu-list">
          <li><a class="menu-item" href="https://se.ifmo.ru/">Сайт ФПИиКТ ИТМО</a></li>
          <li><a class="menu-item" href="https://github.com/TheIrishMan05">Профиль на Github</a></li>
        </ul>
      </div>
      <div>
        <h2>Веб-программирование, Лабораторная работа №4, Вариант 52199055</h2>
        <h3>Главная страница</h3>
        <div id="credit">
          Машкин Григорий Андреевич, P3230
        </div>
      </div>
    </div>
      <div id="page-container">
        <FormComponent
            @submit-data="handleSubmit"
            @update-plot="updatePlot"
        />
        <CanvasComponent ref="canvasComponent" :points="points" :value-r="currentR" @submit-data="handleSubmit"/>
      </div>
    <TableComponent :points="points"/>
    <div class="footer">
      2024
    </div>
  </div>
</template>

<script>

import axios from "axios";
import FormComponent from "@/components/main_page_components/DataFields.vue";
import CanvasComponent from "@/components/main_page_components/Canvas.vue";
import TableComponent from "@/components/main_page_components/Table.vue";

export default {
  name: 'MainPage',
  components: {TableComponent, CanvasComponent, FormComponent},
  data() {
    return {
      currentR: null,
      points: []
    };
  },
  methods: {
    async handleSubmit(data) {
      this.$refs.canvasComponent.drawPoint(data.x, data.y, data.r);
      const response = await axios.post('http://localhost:8000/api/add', data);
      if(response.status === 200) {
        const newPoint = response.data;
        this.points.push(newPoint);
      }
    },

    updatePlot(r) {
      this.currentR = r;
    }
  }
};
</script>

<style scoped>
@font-face {
  src: url("../../fonts/microsoftsansserif.ttf");
  font-family: "Courier New", sans-serif;
}

* {
  font-family: Courier New, sans-serif;
  text-align: center;
  box-sizing: border-box;
}

body {
  background-color: peachpuff;
  background-repeat: no-repeat;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover !important;
}

#page-container {
  color: black;
  width: 100%;
  height: 50%;
  margin-top: 10%;
  display: grid;
  text-align: center;
  padding: 30px;
  border: 2px solid #178473;
  border-radius: 20px;
}


.burger-checkbox {
  position: absolute;
  visibility: hidden;
}

.burger {
  margin: 5px;
  position: absolute;
  z-index: 1;
  cursor: pointer;
  left: 5px;
  display: flex;
  border: none;
  background: transparent;
  width: 40px;
  height: 26px;
}

.burger::before,
.burger::after,
.burger span {
  content: '';
  position: absolute;
  display: block;
  width: 100%;
  height: 4px;
  border-radius: 10px;
  background: white;
}

.burger::before {
  top: 0;
  box-shadow: 0 11px 0 #000;
  transition: box-shadow .3s .15s, top .3s .15s, transform .3s;
}

.burger span {
  top: 11px;
  background: white;
  transition: background .3s;
}

.burger::after {
  bottom: 0;
  transition: bottom .3s .15s, transform .3s;
}

.burger-checkbox:checked + .burger::before {
  top: 11px;
  transform: rotate(45deg);
  box-shadow: 0 6px 0 rgba(0, 0, 0, 0);
  transition: box-shadow .15s, top .3s, transform .3s .15s;
}

.burger-checkbox:checked + .burger span {
  background: transparent;
}

.burger-checkbox:checked + .burger::after {
  bottom: 11px;
  transform: rotate(-45deg);
  transition: bottom .3s, transform .3s .15s;
}

.menu-list {
  top: 0;
  left: 0;
  position: absolute;
  display: grid;
  gap: 12px;
  padding: 42px 0;
  background: #ff496c;
  list-style-type: none;
  transform: translateX(-100%);
  transition: .3s;
  width: 200px;
}

.menu-item {
  display: block;
  padding: 8px;
  color: white;
  font-size: 18px;
  text-align: center;
  text-decoration: none;
}

.menu-item:hover {
  background: rgba(255, 255, 255, 0.2);
}

.burger-checkbox:checked ~ .menu-list {
  transform: translateX(0);
}

.header {
  font-size: 1em;
  top: 0;
}

.footer {
  bottom: 0;
}

.header,
.footer {
  background-color: #178473;
  color: white;
  width: 100%;
  position: fixed;
  z-index: 8;
  left: 0;
  right: 0;
}

.page {
  width: 100%;
}


</style>