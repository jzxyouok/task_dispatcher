// pages/taskdetail/taskdetail.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/13 10:14
 \* Description: 任务详情
 \*/
const { $Toast } = require('../../dist/base/index');
import dateUtil from '../../utils/date_util';
const { bidirectionalBind } = require('../../utils/bidirectionalBind.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    role: "",
    requestIp: "",
    userId: "",
    staffNames: "",
    taskDetail: {},
    ROLE: {
      PROPOSER: "proposer",  //发布者
      AUDITOR: "auditor",  //审批者
      STAFF: "staff",  //承接者
      EXPERT: "expert" //专家组
    },
    TASK_STATUS: {
      AUDITING: "待审核",
      EXPERT_AUDITING: "待专家组审核",
      PASSED: "已发布",
      REJECTED: "已驳回"
    }
  },
  handleInput(e) {
    bidirectionalBind(e, this);
  },
  noPass(){

  },
  commitExpert(){

  },
  pass(){
    // wx.request({
    //   url: 'http://localhost:8080/base_task/users',
    //   success: (res) => {
    //     console.log(res.data);
    //   }
    // });
  },
  /**
   * 改变当前页面的标题
   */
  changeNavigationBarTitle(text) {
    const barTitle = [
      '加载中...'
    ]
    wx.setNavigationBarTitle({
      title: text + "的任务"
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options);
    console.log(this.data.ROLE.AUDITOR == options.role);
    let app = getApp();
    this.data.requestIp = app.globalData.requestIp;
    this.data.userId = app.globalData.localUserInfo.id;
    this.setData({
      role: options.role
    })
    this.getTaskInfo(options.taskId);
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
   * 访问后台获取任务详情
   */
  getTaskInfo(taskId) {
    wx.request({
      url: this.data.requestIp + '/base_task/taskInfo?taskId=' + taskId,
      method: "GET",
      success: res => {
        console.log(res.data);
        res.data.startTime = dateUtil.dateFormat(new Date(res.data.startTime), "yyyy-MM-dd");
        res.data.endTime = dateUtil.dateFormat(new Date(res.data.endTime), "yyyy-MM-dd");
        this.setData({
          taskDetail: res.data
        });
        this.dealTaskData(res.data);
      },
      fail: e => {

      }
    })
  },

  /**
   * 处理任务数据
   */
  dealTaskData(taskVo) {
    this.changeNavigationBarTitle(taskVo.taskState);
    let staffNames = "";
    taskVo.staffVos.forEach((v, k) => {
      if (v.userVo.id == this.data.userId) {

      }
      staffNames += v.userVo.name + ",";
    });
    if (staffNames.indexOf(",") > -1) {
      staffNames = staffNames.substring(0, staffNames.length - 1);
    }
    this.setData({
      staffNames
    });
  }
})