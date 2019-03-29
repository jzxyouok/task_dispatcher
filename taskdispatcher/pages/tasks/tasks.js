// pages/completed/completed.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    requestIp: "",
    userid: "",
    selectIndex: 0,
    tasks: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let app = getApp();
    this.data.requestIp = app.globalData.requestIp;
    this.data.userid = app.globalData.localUserInfo.id;
    this.getTasks(options.role);
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
   * 访问后台获取任务列表
   */
  getTasks(role) {
    wx.request({
      url: this.data.requestIp + '/base_task/state/tasks?userId=' + this.data.userid + '&role=' + role,
      method: "GET",
      success: res => {
        console.log(res.data);
        this.setData({
          tasks: res.data
        });
      },
      fail: e => {

      }
    });
  }
})