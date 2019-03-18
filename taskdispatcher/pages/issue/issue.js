// pages/issue/issue.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    taskVo: {
      taskName: '',
      taskDescription: '',
      startTime: '',
      endTime: '',
      orient: 'direc',
      workload: '',
      taskState: '',
      projVo: {
        projName: ''
      },
      roleVos: [],
      labelVos: []
    }
  },
  bindStartTimeChange(e){
    this.setData({
      'taskVo.startTime': e.detail.value
    });
  },
  bindEndTimeChange(e) {
    this.setData({
      'taskVo.endTime': e.detail.value
    });
  },
  handleChange({ detail }) {
    this.setData({
      'taskVo.orient': detail.key
    });
  },
  choosePeople(e){
    wx.navigateTo({
      url: '../peoplelist/peoplelist?roleType=' + e.target.dataset.roletype,
    })
  },
  handleSave(){
    wx.showToast({
      title: '已保存到草稿箱',
      icon: 'succes',
      duration: 1000,
      mask: true
    })
  },

  handleIssue() {
    wx.request({
      url: 'https://localhost:8080',
      data: this.taskVo,
      success: (res) => {
        showSuccToast("已经成功发布");
      },
      fail: (e) => {
        console.log("fail");
      }
    });
  },
  /**
   * 显示成功的气泡
   */
  showSuccToast(title){
    wx.showToast({
      title,
      icon: 'succes',
      duration: 1000,
      mask: true
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
    console.log(wx.getStorageSync('selectPeople'));
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

  }
})