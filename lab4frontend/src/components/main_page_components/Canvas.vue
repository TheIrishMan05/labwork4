<template>
  <div class="canvas-container">
    <canvas id="myCanvas" ref="canvas" height="350px" width="350px" @click="handleClick"></canvas>
  </div>
</template>

<script>
export default {
  name: 'CanvasComponent',
  props: {
    valueR: {
      type: Number,
      required: true,
    },
    points: {
      type: Array,
      required: true,
    }
  },
  data() {
    return {
      ctx: null,
      canvas: null,
    };
  },
  mounted() {
    this.canvas = this.$refs.canvas;
    this.ctx = this.canvas.getContext('2d');
    this.draw();
  },
  watch: {
    valueR: {
      immediate: true,
      handler() {
        if (this.ctx) {
          this.draw();
        }
      }
    }
  },
  methods: {
    draw() {
      this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
      this.ctx.scale(1, 1);
      this.ctx.beginPath();
      this.ctx.fillStyle = "blue";
      this.ctx.moveTo(this.canvas.width / 2, this.canvas.height / 2);
      this.ctx.arc(
          this.canvas.width / 2,
          this.canvas.height / 2,
          30 * this.valueR,
          Math.PI,
          1.5 * Math.PI
      );
      this.ctx.fill();
      this.ctx.closePath();

      this.ctx.fillRect(this.canvas.width / 2,
          this.canvas.height / 2,
          30 * this.valueR,
          30 * this.valueR);

      this.ctx.moveTo(this.canvas.width / 2, this.canvas.height / 2);
      this.ctx.lineTo(this.canvas.width / 2, this.canvas.height / 2 - 30 * this.valueR);
      this.ctx.lineTo(this.canvas.width / 2 + 15 * this.valueR, this.canvas.height / 2);
      this.ctx.fill();

      this.ctx.fillStyle = "black";
      this.ctx.beginPath();
      this.ctx.moveTo(0, this.canvas.height / 2);
      this.ctx.lineTo(this.canvas.width, this.canvas.height / 2);
      this.ctx.closePath();
      this.ctx.stroke();

      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width, this.canvas.height / 2);
      this.ctx.lineTo(this.canvas.width - 10, this.canvas.height / 2 - 5);
      this.ctx.lineTo(this.canvas.width - 10, this.canvas.height / 2 + 5);
      this.ctx.fillText('x', this.canvas.width - 15, this.canvas.height / 2 + 15);
      this.ctx.closePath();
      this.ctx.fill();

      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width / 2 - 15 * this.valueR, this.canvas.height / 2 + 5);
      this.ctx.lineTo(this.canvas.width / 2 - 15 * this.valueR, this.canvas.height / 2 - 5);
      this.ctx.closePath();
      this.ctx.stroke();
      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width / 2 - 30 * this.valueR, this.canvas.height / 2 + 5);
      this.ctx.lineTo(this.canvas.width / 2 - 30 * this.valueR, this.canvas.height / 2 - 5);
      this.ctx.closePath();
      this.ctx.stroke();
      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width / 2 + 15 * this.valueR, this.canvas.height / 2 + 5);
      this.ctx.lineTo(this.canvas.width / 2 + 15 * this.valueR, this.canvas.height / 2 - 5);
      this.ctx.closePath();
      this.ctx.stroke();
      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width / 2 + 30 * this.valueR, this.canvas.height / 2 + 5);
      this.ctx.lineTo(this.canvas.width / 2 + 30 * this.valueR, this.canvas.height / 2 - 5);
      this.ctx.closePath();
      this.ctx.stroke();

      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width / 2, 0);
      this.ctx.lineTo(this.canvas.width / 2, this.canvas.height);
      this.ctx.closePath();
      this.ctx.stroke();

      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width / 2, 0);
      this.ctx.lineTo(this.canvas.width / 2 - 5, 10);
      this.ctx.lineTo(this.canvas.width / 2 + 5, 10);
      this.ctx.fillText('y', this.canvas.width / 2 + 15, 10);
      this.ctx.closePath();
      this.ctx.fill();

      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width / 2 + 5, this.canvas.height / 2 - 30 * this.valueR);
      this.ctx.lineTo(this.canvas.width / 2 - 5, this.canvas.height / 2 - 30 * this.valueR);
      this.ctx.closePath();
      this.ctx.stroke();
      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width / 2 + 5, this.canvas.height / 2 - 15 * this.valueR);
      this.ctx.lineTo(this.canvas.width / 2 - 5, this.canvas.height / 2 - 15 * this.valueR);
      this.ctx.closePath();
      this.ctx.stroke();
      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width / 2 + 5, this.canvas.height / 2 + 15 * this.valueR);
      this.ctx.lineTo(this.canvas.width / 2 - 5, this.canvas.height / 2 + 15 * this.valueR);
      this.ctx.closePath();
      this.ctx.stroke();
      this.ctx.beginPath();
      this.ctx.moveTo(this.canvas.width / 2 + 5, this.canvas.height / 2 + 30 * this.valueR);
      this.ctx.lineTo(this.canvas.width / 2 - 5, this.canvas.height / 2 + 30 * this.valueR);
      this.ctx.closePath();
      this.ctx.stroke();
      if (this.valueR > 1) {
        this.ctx.fillText("-R/2", this.canvas.width / 2 - 15 * this.valueR, this.canvas.height / 2 + 10);
        this.ctx.fillText("-R", this.canvas.width / 2 - 30 * this.valueR, this.canvas.height / 2 + 10);
        this.ctx.fillText("R/2", this.canvas.width / 2 + 15 * this.valueR, this.canvas.height / 2 + 10);
        this.ctx.fillText("R", this.canvas.width / 2 + 30 * this.valueR, this.canvas.height / 2 + 10);
        this.ctx.fillText("-R", this.canvas.width / 2 - 25, this.canvas.height / 2 + 30 * this.valueR);
        this.ctx.fillText("-R/2", this.canvas.width / 2 - 25, this.canvas.height / 2 + 15 * this.valueR);
        this.ctx.fillText("R", this.canvas.width / 2 - 25, this.canvas.height / 2 - 30 * this.valueR);
        this.ctx.fillText("R/2", this.canvas.width / 2 - 25, this.canvas.height / 2 - 15 * this.valueR);
      }
    },
    handleClick(event) {
      let area = this.canvas.getBoundingClientRect();

      let rawX = event.clientX - area.left - this.canvas.width / 2;
      let rawY = this.canvas.height / 2 - (event.clientY - area.top);

      let valueX = (rawX / (this.canvas.width / 2) * this.valueR * 1.75);
      let valueY = (rawY / (this.canvas.height / 2) * this.valueR * 1.75);

      const dotX = this.canvas.width / 2 + rawX;
      const dotY = this.canvas.height / 2 - rawY;
      this.$emit('submit-data', {x: valueX,
        y: valueY,
        r: this.valueR});
      this.ctx.beginPath();
      this.ctx.arc(dotX, dotY, 3, 0, 2 * Math.PI);
      this.ctx.fill();
      this.ctx.closePath();
    },
    drawPoint(x, y, r) {
      const scale = 30 * r;
      const cx = this.canvas.width / 2;
      const cy = this.canvas.height / 2;
      const dotX = cx + (x / (r * 1.75)) * scale;
      const dotY = cy - (y / (r * 1.75)) * scale;
      this.ctx.beginPath();
      this.ctx.arc(dotX, dotY, 3, 0, 2 * Math.PI);
      this.ctx.fill();
      this.ctx.closePath();
    },

    changeColor(result) {
      if(result === true) {
        this.ctx.fillStyle = "green";
      } else {
        this.ctx.fillStyle = "red";
      }
    },
  }
};
</script>

<style scoped>
.canvas-container {
  width: 355px;
  height: 355px;
  border: 2px solid #178473;
  border-radius: 20px;
  background-color: white;
  grid-column-start: 3;
  grid-column-end: 4;
}

</style>
