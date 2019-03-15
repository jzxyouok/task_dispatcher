// pages/completed/completed.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

    selectIndex: 0,
    tasks: [
      {
        id: 'asdda',
        name: '任务1',
        taskDesc: '历史上以少胜多、以弱胜强的著名战役之一',
        projName: '项目1',
        publisher: '诸葛亮',
        auditor: '刘备',
        label: '伏击',
        recipients: '关羽',
        starttime: 20190301 ,
        endtime: 20190320,
        workload: 30 ,
        reason: '当年',
        opinion:'通过',
        state:'通过'
      },
      {
        id: 'ada',
        name: '任务1',
        taskDesc: '历史上以少胜多、以弱胜强的著名战役之一',
        projName: '项目1',
        publisher: '诸葛亮',
        auditor: '刘备',
        label: '伏击',
        recipients: '关羽',
        starttime: 20190301,
        endtime: 20190320,
        workload: 30,
        reason: '当年',
        opinion: '通过',
        state: '通过'
      },
      {
        id: 'qdsa',
        name: '任务1',
        taskDesc: '历史上以少胜多、以弱胜强的著名战役之一',
        projName: '项目1',
        publisher: '诸葛亮',
        auditor: '刘备',
        label: '伏击',
        recipients: '关羽',
        starttime: 20190301,
        endtime: 20190320,
        workload: 30,
        reason: '当年',
        opinion: '通过',
        state: '通过'
      },
      {
        id: 'fsdfdass',
        name: '任务1',
        taskDesc: '历史上以少胜多、以弱胜强的著名战役之一',
        projName: '项目1',
        publisher: '诸葛亮',
        auditor: '刘备',
        label: '伏击',
        recipients: '关羽',
        starttime: 20190301,
        endtime: 20190320,
        workload: 30,
        reason: '当年',
        opinion: '通过',
        state: '通过'
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