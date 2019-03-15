// pages/taskdetail/taskdetail.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/13 10:14
 \* Description: 任务详情
 \*/
Page({

  /**
   * 页面的初始数据
   */
  data: {
    taskDetail: {
      projName: '项目1',
      taskType: 2,
      taskDesc: '任务描述1',
      publisher: '辉少',
      verifier: '彭博',
      taskTag: '标签1',
      contractor: '龙威',
      workload: 3,
      startTime: '2019-03-10 08:00:00',
      endTime: '2019-03-11 20:00:00',
      taskStatus: 0
    }
  },
  noPass(){

  },
  commitExpert(){

  },
  pass(){
    wx.request({
      url: 'http://localhost:8080/base_task/users',
      success: (res) => {
        console.log(res.data);
      }
    });
  },
  /**
   * 改变当前页面的标题
   */
  changeNavigationBarTitle(index) {
    let barTitle = [
      '加载中...',
      '已完成的任务',
      '我发布的任务',
      '未完成的任务--正在进行中',
      '未完成的任务--正在审核中',
      '未完成的任务--审核失败',
      '待审核的任务',
      '待审核的任务--提请专家组审核',
      '待审核的任务--请审核',
      '待审核的任务--专家组审核中',
    ]
    wx.setNavigationBarTitle({
      title: barTitle[index]
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    let taskStatus = 6;
    this.setData({
      'taskDetail.taskStatus': taskStatus
    });
    this.changeNavigationBarTitle(taskStatus);
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