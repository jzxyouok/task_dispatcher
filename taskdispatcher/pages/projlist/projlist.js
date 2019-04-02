// pages/projlist/projlist.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/22 16:14
 \* Description: 项目列表
 \*/
const { bidirectionalBind } = require('../../utils/bidirectionalBind.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    requestIp: '',
    searchInput: {
      searchName: ''
    },
    indexList: [], //索引选择器
    projList: []
  },

  /**
   * 处理项目点击事件
   */
  handleProjClick(e) {
    console.log(e);
    wx.setStorageSync('selectProject', { id: e.target.dataset.itemid, 
      name: e.target.dataset.itemname});
    wx.navigateBack();
  },
  onIndexListChange(event) {
    // console.log(event.detail, 'click right menu callback data')
  },
  handleInput(e) {
    bidirectionalBind(e, this);
    let indexStore = new Array(27);
    const words = ["#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"]
    words.forEach((item, index) => {
      indexStore[index] = {
        key: item,
        list: []
      }
    });
    this.data.projList.forEach((item) => {
      if (item.name.indexOf(e.detail.detail.value) > -1) {
        let index = words.indexOf(item.firstLetter);
        if (index > -1) {
          indexStore[index].list.push({
            name: item.name,
            key: item.firstLetter,
            id: item.id
          });
        } else {
          indexStore[0].list.push({
            name: item.name,
            key: item.firstLetter,
            id: item.id
          });
        }
      }
    });
    this.data.indexList = indexStore;
    this.setData({
      indexList: this.data.indexList
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let app = getApp();
    if (options) {

    }
    this.data.requestIp = app.globalData.requestIp;
    //访问后台获取项目列表
    this.getProjList();
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
   * 初始化索引列表
   */
  initIndexList() {
    let indexStore = new Array(27);
    const words = ["#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"]
    words.forEach((item, index) => {
      indexStore[index] = {
        key: item,
        list: []
      }
    })
    this.data.projList.forEach((item) => {
      let index = words.indexOf(item.firstLetter);
      if (index > -1) {
        indexStore[index].list.push({
          name: item.name,
          key: item.firstLetter,
          id: item.id
        });
      } else {
        indexStore[0].list.push({
          name: item.name,
          key: item.firstLetter,
          id: item.id
        });
      }
    })
    this.data.indexList = indexStore;
    this.setData({
      indexList: this.data.indexList
    })
  },

  /**
   * 改变当前页面的标题
   */
  changeNavigationBarTitle(index) {
    const barTitle = [
      '加载中...',
      '选择项目'
    ]
    wx.setNavigationBarTitle({
      title: barTitle[index]
    })
  },

  /**
   * 访问后台获得project列表
   */
  getProjList(hasSelectProj) {
    wx.request({
      url: this.data.requestIp + '/base_task/projects',
      success: res => {
        if (!res.data) {
          return;
        }
        this.data.projList = res.data;
        //初始化索引列表
        this.initIndexList();
      },
      fail: e => {
        console.log(e);
      }
    })
  }
})