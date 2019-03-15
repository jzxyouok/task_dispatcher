//index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    selectIndex: 0,
    tasks: [
      {
        id: 'fsdfs',
        name: '任务1',
        projName: '项目1',
        taskDesc: '描述1',
        projPeriod: '1年',
        publisher: '辉神',
        workload: 3,
        outputValue: 500000000
      },
      {
        id: 'grgeg',
        name: '任务2',
        projName: '项目2',
        taskDesc: '描述2',
        projPeriod: '3年',
        publisher: '刘神',
        workload: 2,
        outputValue: 50000000
      },
      {
        id: 'gasgsag',
        name: '任务3',
        projName: '项目3',
        taskDesc: '描述3',
        projPeriod: '1个月',
        publisher: '彭博',
        workload: 1,
        outputValue: 1000000000
      },
      {
        id: 'grgegere',
        name: '任务4',
        projName: '项目4',
        taskDesc: '描述4',
        projPeriod: '100年',
        publisher: '威弟',
        workload: 100,
        outputValue: 0.5
      }
    ]
  },
  expandTask(event) {
    this.setData({
      selectIndex: event.target.dataset.index
    });
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