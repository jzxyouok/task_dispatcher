// pages/myinfo/myinfo.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/13 10:14
 \* Description: 我的信息
 \*/
import constants from "../../static/constants.js";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    requestIp: "",
    ROLE: {},
    TASK_STATUS: {},
    count: {
      proposer: 0,
      auditor: 0,
      staff: 0
    },
    badgeShow: {
      auditor: false,
      staff: false,
      proposer: false
    },
    userVo:{
      weChat: '',
      name: '',
      telephone: '',
      position: ''
    },
    localUserInfo:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let app = getApp();
    this.data.requestIp = app.globalData.requestIp;
    this.setData({
      ROLE: constants.ROLE,
      TASK_STATUS: constants.TASK_STATUS,
      userVo: app.globalData.userInfo,
      localUserInfo: app.globalData.localUserInfo
    });
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
    this.getTasksCount(this.data.ROLE.AUDITOR);
    this.getTasksCount(this.data.ROLE.PROPOSER);
    this.getTasksCount(this.data.ROLE.STAFF);
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
   * 获取各种任务的总数
   */
  getTasksCount(role) {
    let app = getApp();
    wx.request({
      url: this.data.requestIp + '/base_task/state/tasks?userId=' + app.globalData.localUserInfo.id + '&role=' + role,
      method: "GET",
      success: res => {
        this.data.count[role] = res.data.length;
        this.dealResData(res.data, role);
        this.setData({
          count: this.data.count 
        });
      },
      fail: e => {

      }
    })
  },

  /**
   * 处理请求返回的数据
   */
  dealResData(arr, role) {
    if (!arr || arr.length == 0) {
      return;
    }
    switch(role) {
      case this.data.ROLE.STAFF:
        arr.forEach(v => {
          if (v.taskState == this.data.TASK_STATUS.TASK_DOING || v.taskState == this.data.TASK_STATUS.COMMIT_REJECTED) {
            this.setData({
              'badgeShow.staff': true
            });
            return;
          }
        });
        break;
      case this.data.ROLE.AUDITOR:
        arr.forEach(v => {
          if (v.taskState == this.data.TASK_STATUS.AUDITING || v.taskState == this.data.TASK_STATUS.EVALUATING) {
            this.setData({
              'badgeShow.auditor': true
            });
            return;
          }
        });
        break;
      case this.data.ROLE.EXPERT:
        arr.forEach(v => {
          if (v.taskState == this.data.TASK_STATUS.EXPERT_EVALUATING) {
            this.setData({
              'badgeShow.auditor': true
            });
            return;
          }
        });
        break;
      case this.data.ROLE.PROPOSER:
        arr.forEach(v => {
          if (v.taskState == this.data.TASK_STATUS.ISSUE_REJECTED) {
            this.setData({
              'badgeShow.proposer': true
            });
            return;
          }
        });
        break;
    }
  }
})