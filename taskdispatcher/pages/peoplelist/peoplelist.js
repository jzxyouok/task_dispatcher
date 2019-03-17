// pages/peoplelist/peoplelist.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/13 10:14
 \* Description: 人员列表
 \*/

import pinyinUtil from '../../utils/pinyinUtil'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    current: [],
    roleType: '',
    indexList: [], //索引选择器
    people:[
      {
        id:"3325",
        name:"彭博"
      },
      {
        id: "3fds325",
        name: "辉少",
        isChecked: true
      },
      {
        id: "332gds5",
        name: "刘神"
      },
      {
        id: "332gdggs5",
        name: "雷神"
      },
      {
        id: "332gdgg",
        name: "gregre哈哈人"
      },
      {
        id: "3gdggs5",
        name: "__哈人"
      },
      {
        id: "3gdgg543s5",
        name: "__##gre人"
      },
      {
        id: "3gdgg32s5",
        name: "##gregre哈哈人"
      },
      {
        id: "3gdggfgs5",
        name: "regre哈哈人"
      },
      {
        id: "3gdgergs5",
        name: "regre哈"
      },
      {
        id: "3wergdggs5",
        name: "egre哈哈"
      },
      {
        id: "3gdgyrgs5",
        name: "##gregre哈哈人"
      },
      {
        id: "3gdgffggs5",
        name: "_##gregre哈人"
      }
    ]
  },

  /**
   * 处理checkbox事件
   */
  handleCheckChange(e){
    this.data.people.forEach((v, k) => {
      console.log(v);
      if (v.id == e.target.dataset.itemid) {
        v.isChecked = !(v.isChecked?true:false);
        return;
      }
    });
    this.setData({
      people: this.data.people
    });
    this.refreshIndexList();
  },
  onChange(event) {
    console.log(event.detail, 'click right menu callback data')
  },
  
  /**
   * 刷新索引列表
   */
  refreshIndexList() {
    this.data.people.forEach((v, k) => {
      console.log(pinyinUtil.chineseToPinYin(v.name));
    });
    let indexStore = new Array(27);
    const words = ["#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"]
    words.forEach((item, index) => {
      indexStore[index] = {
        key: item,
        list: []
      }
    })
    this.data.people.forEach((item) => {
      let pinyinName = pinyinUtil.chineseToPinYin(item.name);
      let firstName = pinyinName.substring(0, 1);
      let index = words.indexOf(firstName);
      if (index > -1) {
        indexStore[index].list.push({
          name: item.name,
          key: firstName,
          isChecked: item.isChecked,
          id: item.id
        });
      } else {
        indexStore[0].list.push({
          name: item.name,
          key: firstName,
          isChecked: item.isChecked,
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
      '选择审核人',
      '选择承接人'
    ]
    wx.setNavigationBarTitle({
      title: barTitle[index]
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options) {
      this.changeNavigationBarTitle(options.roleType);
      this.setData({
        roleType: options.roleType
      });
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.refreshIndexList();
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
    let selectPeople = {
      roleType: this.data.people.roleType,
      peopleArr: []
    };
    this.data.people.forEach((v, k) => {
      if (v.isChecked) {
        selectPeople.peopleArr.push(v);
      }
    });
    wx.setStorageSync('selectPeople', selectPeople);
    console.log(selectPeople);
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