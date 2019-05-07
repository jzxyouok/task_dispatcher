<template>
  <div class="block">
    

    <el-row :gutter="20">
     <el-col :span="6" :offset="6">
    <div><span class="demonstration">月份：</span>
    <el-date-picker
      v-model="value1"
      type="month"
      placeholder="选择月">
    </el-date-picker>
    </div>
    </el-col>
     <el-col :span="6" ><div class="grid-content">  <el-button type="info">查询</el-button></div></el-col>
    </el-row>
  </div>
</template>

<script>
import { Script } from 'vm';
export default {
  name: 'TimePicker',
    data() {
      return {
        value1: ''
      };
            }       
}
</script>




<style scoped>
  .el-row {
    margin-bottom: 20px;
    &:last-child {
      margin-bottom: 0;
    }
  }
  .el-col {
    border-radius: 4px;
  }
  
  .grid-content {
    border-radius: 4px;
     min-height: 36px;
  }
</style>