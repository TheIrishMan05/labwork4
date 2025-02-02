<template>
  <div class="page">
    <div class="content">
      <FormComponent
          @submit-data="handleSubmit"
          @update-plot="updatePlot"
      />
      <CanvasComponent :points="points" :value-r="currentR" @submit-data="handleSubmit"/>
      <TableComponent :points="points"/>
    </div>
  </div>
</template>

<script>

import axios from "axios";
import FormComponent from "@/components/main_page_components/dataFields.vue";
import CanvasComponent from "@/components/main_page_components/canvas.vue";
import TableComponent from "@/components/main_page_components/table.vue";

export default {
  name: 'MainPage',
  components: {TableComponent, CanvasComponent, FormComponent},
  data() {
    return {
      currentR: 0,
      points: []
    };
  },
  methods: {
    async handleSubmit(data) {
      const response = await axios.post('http://localhost:8000/api/login', data);
      const newPoint = response.data;
      this.points.push(newPoint);
    },

    updatePlot(r) {
      this.currentR = r;
    }
  }
};
</script>

<style scoped>

</style>