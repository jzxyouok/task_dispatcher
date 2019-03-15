// pages/peoplelist/peoplelist.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/13 10:14
 \* Description: 人员列表
 \*/
Page({

  /**
   * 页面的初始数据
   */
  data: {
    current: [],
    people:[
      {
        id:"3325",
        name:"彭博"
      },
      {
        id: "3fds325",
        name: "辉少"
      },
      {
        id: "332gds5",
        name: "刘神"
      }
    ]
  },
  /**
   * 改变当前页面的标题
   */
  changeNavigationBarTitle(index) {
    let barTitle = [
      '加载中...',
      '选择审核人',
      '选择承接人'
    ]
    wx.setNavigationBarTitle({
      title: barTitle[index]
    })
  },
  /**
   * 处理checkbox事件
   */
  handleCheckBoxtChange({ detail = {} }) {
    const index = this.data.current.indexOf(detail.value);
    index === -1 ? this.data.current.push(detail.value) : this.data.current.splice(index, 1);
    this.setData({
      current: this.data.current
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
    this.setData({
      'taskDetail.taskStatus': 1
    });
    this.changeNavigationBarTitle(1);
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