// pages/outputvalue/outputvalue.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/5/7 15:14
 \* Description: 我的产值
 \*/
import constants from "../../static/constants.js";
import * as echarts from '../../ec-canvas/echarts';

let chart = null;

function initChart(canvas, width, height) {
  chart = echarts.init(canvas, null, {
    width: width,
    height: height
  });
  canvas.setChart(chart);

  var option = {
    tooltip: {
      trigger: 'item'
    },
    xAxis: {
      type: 'value',
      show: false,
      max: 1
    },
    yAxis: {
      type: 'category',
      show: false
    },
    grid: {
      right: 40,
      containLabel: true
    },
    series: [
      {
        name: '已完成的工作量',
        type: 'bar',
        stack: 'xxx',
        label: {
          normal: {
            show: true,
            position: 'inside'
          }
        },
        itemStyle: {
          color: '#19be6b'
        },
        data: []
      },
      {
        name: '执行中的工作量',
        type: 'bar',
        stack: 'xxx',
        label: {
          normal: {
            show: true,
            position: 'inside'
          }
        },
        itemStyle: {
          color: '#2db7f5'
        },
        data: []
      },
      {
        name: '合计',
        type: 'bar',
        stack: 'xxx',
        label: {
          normal: {
            show: true,
            position: 'right',
            textStyle: {
              color: '#000'
            }
          }
        },
        //思路一：给series集合末尾多加一栏用于展示合计，但是值都是0；缺点：必须根据xAxis的data生成一组为空的数据，且tooltip不能加trigger: 'axis',这个条件，不然会展示合计：0
        data: []
      }
    ]
  };

  chart.setOption(option);
  return chart;
}

Page({

  /**
   * 页面的初始数据
   */
  data: {
    requestIp: "",
    userId: "",
    ROLE: {},
    TASK_STATUS: {},
    outputValue: {
      workloadSum: "",
      rank: "",
      workloadAwayFromLastOne: "",
      doneWorkload: ""
    },
    ec: {
      onInit: initChart
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let app = getApp();
    this.data.requestIp = app.globalData.requestIp;
    this.data.userId = app.globalData.localUserInfo.id;
    this.setData({
      ROLE: constants.ROLE,
      TASK_STATUS: constants.TASK_STATUS,
    });
    this.getUserOutputValue(this.data.userId);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  /**
   * 获取用户产值
   */
  getUserOutputValue(userId) {
    wx.request({
      url: this.data.requestIp + '/base_task/getUserOutputValue?userId=' + userId,
      method: "GET",
      success: res => {
        console.log(res);
        if (!res || !res.data|| res.data.errCode!=0) {
          return;
        }
        let resData = JSON.parse(res.data.data);
        resData.doneWorkload = resData.doneWorkload.toFixed(1);
        resData.doingWorkload = resData.doingWorkload.toFixed(1);
        resData.workloadAwayFromLastOne = resData.workloadAwayFromLastOne.toFixed(1);
        this.setData({
          outputValue: resData
        });
        if (resData.doneWorkload || resData.doingWorkload) {
          this.changeChartData(resData.doneWorkload, resData.doingWorkload, resData.doneWorkload*1 + resData.doingWorkload*1);
        } 
      },
      fail: e => {
        console.log("getUserOutputValue服务异常");
      }
    });
  },

  /**
   * 改变图表的数据
   */
  changeChartData(doneWorkload, doingWorkload, sum) {
    let option = {
      xAxis: {
        max: sum
      },
      series: [
        {
          data: [doneWorkload]
        },
        {
          data: [doingWorkload]
        },
        {
          data: [0],
          label: {
            normal: {
              formatter: () => {
                return sum;
              }
            }
          }
        }
      ]
    };
    let interval = setInterval(() => {
      console.log("等待echarts渲染");
      if (chart && chart.setOption) {
        console.log("echarts渲染成功");
        chart.setOption(option);
        clearInterval(interval);
      }
    }, 300);
  }
})