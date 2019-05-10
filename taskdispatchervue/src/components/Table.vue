<template >
   <div class="block">
    <el-row :gutter="20">
     <el-col :span="12" :offset="6">
    <div><span class="demonstration">月份：</span>
    <el-date-picker
      v-model="month"
      type="month"
      placeholder="选择月" value-format="yyyy-MM" @change="getMonth()" >
    </el-date-picker>
    </div>
    </el-col>
    </el-row>

  <el-table
    :data="tableData"  style="width:100%;font-size:10px" align="center"  > 
    <el-table-column
      prop="id"
      label="序号"
      width="50">
    </el-table-column>
    <el-table-column
      prop="projectName"
      label="项目名称"
      width="100">
    </el-table-column>

   <el-table-column
      prop="day"
      label="有效人天数"
      width="50">
    </el-table-column>
     <el-table-column
      prop="proportion"
      label="项目权重"
      width="50">
    </el-table-column>
     <el-table-column label="参与者权重" :data="tableData" align="center">
        <el-table-column
        prop="name0"
        label="李思涛"
        width="50">
      </el-table-column>
        <el-table-column
        prop="name1"
        label="彭思杰"
        width="50">
      </el-table-column>        <el-table-column
        prop="name2"
        label="闵祎"
        width="50">
      </el-table-column>        <el-table-column
        prop="name3"
        label="蒋立豪"
        width="50">
      </el-table-column>        <el-table-column
        prop="name4"
        label="陈蕾"
        width="50">
      </el-table-column>        <el-table-column
        prop="name5"
        label="龙威"
        width="50">
      </el-table-column>        <el-table-column
        prop="name6"
        label="刘成"
        width="50">
      </el-table-column>        <el-table-column
        prop="name7"
        label="姚梦辉"
        width="50">
      </el-table-column>        <el-table-column
        prop="name8"
        label="曹政"
        width="50">
      </el-table-column>        <el-table-column
        prop="name9"
        label="庞奥"
        width="50">
      </el-table-column>        <el-table-column
        prop="name10"
        label="徐洁"
        width="50">
      </el-table-column>        <el-table-column
        prop="name11"
        label="李想"
        width="50">
      </el-table-column>        <el-table-column
        prop="name12"
        label="柏玉锋"
        width="50">
      </el-table-column>        <el-table-column
        prop="name13"
        label="郑卉"
        width="50">
      </el-table-column>        <el-table-column
        prop="name14"
        label="雷蕾"
        width="50">
      </el-table-column>        <el-table-column
        prop="name15"
        label="王凯"
        width="50">
      </el-table-column>  
 

     </el-table-column>
  </el-table>
   </div>  
</template>

<script>
  import axios from 'axios';
  export default {
    name: 'Table',
    data() {
      return {
        month:'',
        tableData: []
      }
    },

    methods: {

     getMonth() { 
       this.tableData = [];
       let url="https://192.168.0.100/month_report/getMonthReport?time=";
       url += this.month;
       axios.get(url).
       then((response) => {

         if(response.data.errCode === 0){
            var obj1 = JSON.parse(response.data.data);
            console.log(obj1);
            for(var i = 0 ;i < obj1.length; i++){
                this.tableData.push ({id:i,projectName :obj1[i]['projectName'],
                day:obj1[i]['validityDays'],
                proportion :obj1[i]['projectWeight'],
                name0 : obj1[i]['monthReportStaffWorkloadVos'][0]['workload'],
                name1 : obj1[i]['monthReportStaffWorkloadVos'][1]['workload'],
                name2 : obj1[i]['monthReportStaffWorkloadVos'][2]['workload'],
                name3 : obj1[i]['monthReportStaffWorkloadVos'][3]['workload'],
                name4 : obj1[i]['monthReportStaffWorkloadVos'][4]['workload'],
                name5 : obj1[i]['monthReportStaffWorkloadVos'][5]['workload'],
                name6 : obj1[i]['monthReportStaffWorkloadVos'][6]['workload'],
                name7 : obj1[i]['monthReportStaffWorkloadVos'][7]['workload'],
                name8 : obj1[i]['monthReportStaffWorkloadVos'][8]['workload'],
                name9 : obj1[i]['monthReportStaffWorkloadVos'][9]['workload'],
                name10 : obj1[i]['monthReportStaffWorkloadVos'][10]['workload'],
                name11 : obj1[i]['monthReportStaffWorkloadVos'][11]['workload'],
                name12 : obj1[i]['monthReportStaffWorkloadVos'][12]['workload'],
                name13 : obj1[i]['monthReportStaffWorkloadVos'][13]['workload'],
                name14 : obj1[i]['monthReportStaffWorkloadVos'][14]['workload'],
                name15 : obj1[i]['monthReportStaffWorkloadVos'][15]['workload']            
                });
            }
 
         } else {
           alert('获取失败');
         }
                         })
      .catch((error) => {
        console.log(error);
                        });

            }
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