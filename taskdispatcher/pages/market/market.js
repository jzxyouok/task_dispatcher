// pages/market/market.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/18 10:14
 \* Description: 首页-任务市场
 \*/
const { $Toast } = require('../../dist/base/index');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    selectIndex: 0,
    isModalShow: true,
    user: {
      realName: "",
      phoneNumber: ""
    },
    modalActions: [{
      name: "激活",
      color: '#2d8cf0',
      loading: false
    }],
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
  /**
   * 展开任务详情
   */
  expandTask(event) {
    this.setData({
      selectIndex: event.target.dataset.index
    });
  },
  /**
   * 模态框激活按钮
   */
  handleModalClick() {
    if (!this.data.user.realName) {
      $Toast({
        content: '请输入姓名',
        type: 'warning'
      });
      return;
    }
    if (!this.data.user.phoneNumber) {
      $Toast({
        content: '请输入电话',
        type: 'warning'
      });
      return;
    }
    this.data.modalActions[0].loading = true;

    this.setData({
      modalActions: this.data.modalActions
    });

    setTimeout(() => {
      this.data.modalActions[0].loading = false;
      wx.showTabBar();
      $Toast({
        content: '激活成功',
        type: 'success'
      });
      this.setData({
        isModalShow: false,
        modalActions: this.data.modalActions
      });
    }, 2000);
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
    this.setData({
      isModalShow: false
    });
    wx.showTabBar();
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