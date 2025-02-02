<template>
  <div class="table-container">
    <table class="result-container">
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

</style>