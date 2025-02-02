<template>
  <form @submit.prevent="handleClick">
    <div class="input-container">
      <select v-model="x" class="form-group">
        <option disabled value="">Выберите X</option>
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
      <select v-model="r" class="form-group">
        <option disabled value="">Выберите R</option>
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
    <button class="submit-button" type="submit">Проверить данные</button>
  </form>
</template>

<script>
export default {
  name: "FormComponent",
  data() {
    return {
      x: 0,
      y: '',
      r: 0,
      errorMessage: ''
    };
  },
  methods: {
    handleClick() {
      if (!this.validateAll()) return;

      this.$emit('submit-data', {
        x: this.x,
        y: this.y,
        r: this.r
      });

      this.x = 0;
      this.y = '';
      this.r = 0;
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
      if (!(this.r >= 0 && this.r <= 3)) {
        this.errorMessage = 'R должен быть числом от 0 до 3.';
        return;
      }

      this.errorMessage = '';
      this.$emit('update-plot', this.r);
    }
  }
};
</script>

<style scoped>

</style>