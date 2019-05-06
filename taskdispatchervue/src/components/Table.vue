<template>
  <el-table
    :data="tableData"
    style="width: 100%">

    <el-table-column
      prop="index"
      label="序号"
      width="50">
    </el-table-column>
    <el-table-column
      prop="project"
      label="项目名称"
      width="150">
    </el-table-column>
    <el-table-column
      prop="manager"
      label="项目负责人"
      width="150">
    </el-table-column>
   <el-table-column
      prop="day"
      label="有效人天数"
      width="150">
    </el-table-column>
     <el-table-column
      prop="proportion"
      label="项目权重"
      width="150">
    </el-table-column>
     <el-table-column label="参与者权重">
        <el-table-column
        prop="name1"
        label="李想"
        width="120">
      </el-table-column>

     </el-table-column>
  </el-table>
</template>

<script>
  export default {
    name: 'Table',
    data() {
      return {
        tableData: [{
          index:1,
          project:'高速公路智能环保监控系统开发',
          manager:'柏玉锋',
          day:'12',
          proportion:'0.12',
          name1:'12'
        },
        {
          index:2,
          project:'高速公路智能环保监控系统开发',
          manager:'柏玉锋',
          day:'12',
          proportion:'0.12',
          name1:'12'
        }
        
        
        
        ]
      }
    }
  }
</script>