<template>
  <div class="output-container">
    <table class="output-table">
      <thead>
      <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Result</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(point, index) in points" :key="index">
        <td>{{ point.x }}</td>
        <td>{{ point.y }}</td>
        <td>{{ point.r }}</td>
        <td :class="{success: point.result, fail: !point.result}">
          {{ point.result ? 'True' : 'False' }}
        </td>
      </tr>
      </tbody>
    </table>
  </div>

</template>

<script>
export default {
  name: "TableComponent",
  props: {
    points: {
      type: Array,
      required: true,
    },
  },
};
</script>

<style scoped>

.output-container {
  grid-column-start: 2;
  grid-column-end: 5;
}

.output-table {
  width: 100%;
  border-collapse: collapse;
  margin: 10px auto;
  text-align: center;
}

.output-table th, .output-table td {
  border: 1px solid #178473;
  padding: 10px;
  word-wrap: break-word;
  white-space: normal;
}

thead th {
  background-color: #178473;
  color: white;
}

</style>