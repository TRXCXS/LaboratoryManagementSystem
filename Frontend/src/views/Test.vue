<template>
    <div class="bg_mask">
        hh
    </div>
</template>

<script>
export default {
    name: "test"
}
</script>

<style scoped>
.bg_mask {
    display: none;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: black;
    z-index: 1001;
    -moz-opacity: 0.95;
    opacity: 0.95;
    filter: alpha(opacity = 95);
}
</style>