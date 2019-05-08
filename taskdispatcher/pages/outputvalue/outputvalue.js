// pages/outputvalue/outputvalue.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/5/7 15:14
 \* Description: 我的产值
 \*/
import constants from "../../static/constants.js";
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
      workloadAwayFromLastOne: ""
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
        this.setData({
          outputValue: JSON.parse(res.data.data)
        });
      },
      fail: e => {
        console.log("getUserOutputValue服务异常");
      }
    });
  }
})