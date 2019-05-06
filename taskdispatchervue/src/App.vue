<template>
    <div id="app">
    <img alt="Vue logo" src="./assets/logo.png" width="100">
    <h1>任务发布后台系统</h1>
    <HelloWorld msg="Welcome to Your Vue.js App"/>
    <TimePicker/>
    <Table/>
  </div>
</template>

<script>

import TimePicker from './components/TimePicker.vue'
import Table from './components/Table.vue'

export default {
  name: 'app',
  components: {
    TimePicker,
    Table
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}



</style>
