<template>
  <form @submit.prevent="handleClick">
    <div class="form-group">
      <label for="select-x">Выберите X:</label>
    <select v-model="x" id="select-x">
        <option id="x(-2)" name="X-option" value="-2">
          -2
        </option>
        <option id="x(-1.5)" name="X-option" value="-1.5">
          -1.5
        </option>
        <option id="x(-1)" name="X-option" value="-1">
          -1
        </option>
        <option id="x(-0.5)" name="X-option" value="-0.5">
          -0.5
        </option>
        <option id="x(0)" name="X-option" value="0">
          0
        </option>
        <option id="x(0.5)" name="X-option" value="0.5">
          0.5
        </option>
        <option id="x(1)" name="X-option" value="1">
          1
        </option>
        <option id="x(1.5)" name="X-option" value="1.5">
          1.5
        </option>
        <option id="x(2)" name="X-option" value="2">
          2
        </option>
      </select>
    </div>
    <div class="form-group">
        <label for="y">Введите значение Y:</label>
        <input
            id="y"
            v-model="y"
            class="y-input"
            placeholder="не менее -3 и не более 5"
            required
            type="text"
        />
    </div>
    <div class="form-group">
    <label for="select-r">Выберите R:</label>
      <select v-model="r" id="select-r" @change="updatePlot">
        <option id="r(0)" name="R-option" value="0">
          0
        </option>
        <option id="r(0.5)" name="R-option" value="0.5">
          0.5
        </option>
        <option id="r(1)" name="R-option" value="1">
          1
        </option>
        <option id="r(1.5)" name="R-option" value="1.5">
          1.5
        </option>
        <option id="x(2)" name="R-option" value="2">
          2
        </option>
      </select>
    </div>
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
    <div class="button-container">
      <button class="submit-button" type="submit">Проверить данные</button>
      <LogoutButton/>
    </div>
  </form>
</template>

<script>
import LogoutButton from "@/components/main_page_components/LogoutButton.vue";
export default {
  name: "FormComponent",
  components: {LogoutButton},
  data() {
    return {
      x: null,
      y: '',
      r: null,
      errorMessage: ''
    };
  },
  methods: {
    handleClick() {
      if (!this.validateAll()) return;
      this.$emit('submit-data', {
        x: parseFloat(this.x),
        y: parseFloat(this.y),
        r: parseFloat(this.r),
      });

      this.updatePlot();
      this.x = null;
      this.y = '';
      this.r = null;
      this.errorMessage = '';
    },
    validateAll() {
      this.errorMessage = '';
      const xParam = parseFloat(this.x);
      const yParam = parseFloat(this.y);
      const rParam = parseFloat(this.r);

      if (isNaN(xParam) || xParam < -2 || xParam > 2) {
        this.errorMessage = 'X должен быть числом от -2 до 2';
        return false;
      }

      if (isNaN(yParam) || yParam < -3 || yParam > 5) {
        this.errorMessage = 'Y должен быть числом -3 до 5';
        return false;
      }

      if (isNaN(rParam) || rParam < 0 || rParam > 2) {
        this.errorMessage = 'R должен быть неотрицательным числом, не большим 2'
        return false;
      }

      return true;
    },

    updatePlot() {
      if (!(this.r >= 0 && this.r <= 2)) {
        this.errorMessage = 'R должен быть числом от 0 до 2.';
        return;
      }

      this.errorMessage = '';
      this.$emit('update-plot', this.r);
    }
  }
};
</script>

<style scoped>
@font-face {
  src: url("../../../fonts/microsoftsansserif.ttf");
  font-family: "Courier New", sans-serif;
}

* {
  font-family: Courier New, sans-serif;
  text-align: center;
  box-sizing: border-box;
}

select {
  background-color: cornflowerblue;
  color: white;
  border-radius: 3px;
  width: 100%;
  border: 3px solid #178473;
}

select:hover{
  box-shadow: 0 2px 10px 5px cornflowerblue;
  color: white;
}

input {
  background-color: cornflowerblue;
  color: white;
  width: 200px;
  border-radius: 3px;
  border: 3px solid #178473;
}

input:hover {
  box-shadow: 0 2px 10px 5px cornflowerblue;
}

input:focus {
  border-radius: 4px;
}

.form-group {
  display: grid;
  grid-template-columns: repeat(2, 1.5fr);
  place-items: start;
  width: 30%;
  height: 20%;
  margin: 2px;
  padding: 10px;
}

.submit-button {
  margin: 10px;
  text-align: center;
  text-transform: uppercase;
  cursor: pointer;
  letter-spacing: 2px;
  position: relative;
  align-items: center;
  background-color: #F92C85;
  border: none;
  color: white;
  padding: 15px;
  width: 45%;
  height: 45%;
  transition-duration: 0.4s;
  overflow: hidden;
  box-shadow: 0 5px 15px #178473;
  border-radius: 5px;
}

.submit-button:hover {
  background: white;
  box-shadow: 0 2px 10px 5px #F92C85;
  color: black;
}

.submit-button::after {
  content: "";
  background: #F92C85;
  display: block;
  position: absolute;
  padding-top: 300%;
  padding-left: 350%;
  margin-left: -30px !important;
  margin-top: -150%;
  opacity: 0;
  transition: all 0.8s
}

.submit-button:active::after {
  padding: 0;
  margin: 0;
  opacity: 1;
  transition: 0s
}
</style>