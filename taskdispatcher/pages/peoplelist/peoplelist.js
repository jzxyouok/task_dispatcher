// pages/peoplelist/peoplelist.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/13 10:14
 \* Description: 人员列表
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
    isMultiSelect: false, //是否是多选
    roleType: '',
    indexList: [], //索引选择器
    people:[]
  },

  handleButtonTap() {
    //保存已选人到缓存
    this.saveSelectPeople2Storage();
    wx.navigateBack();
  },

  /**
   * 处理checkbox事件
   */
  handleCheckChange(e){
    if (this.data.isMultiSelect) {
      this.data.people.forEach((val, key) => {
        if (val.id == e.target.dataset.itemid) {
          val.isChecked = !(val.isChecked ? true : false);
          return;
        }
      });
      this.data.indexList.forEach((val, key) => {
        val.list.forEach((v, k) => {
          if (v.id == e.target.dataset.itemid) {
            v.isChecked = !(v.isChecked ? true : false);
            return;
          }
        });
      });
    } else {
      this.data.people.forEach((val, key) => {
        if (val.id == e.target.dataset.itemid) {
          val.isChecked = !(val.isChecked ? true : false);
        } else {
          val.isChecked = false;
        }
      });
      this.data.indexList.forEach((val, key) => {
        val.list.forEach((v, k) => {
          if (v.id == e.target.dataset.itemid) {
            v.isChecked = !(v.isChecked ? true : false);
          } else {
            v.isChecked = false;
          }
        });
      });
    }
    this.setData({
      indexList: this.data.indexList
    })
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
    this.data.people.forEach((item) => {
      if (item.name.indexOf(e.detail.detail.value) > -1) {
        let index = words.indexOf(item.firstLetter);
        if (index > -1) {
          indexStore[index].list.push({
            name: item.name,
            key: item.firstLetter,
            isChecked: item.isChecked,
            id: item.id
          });
        } else {
          indexStore[0].list.push({
            name: item.name,
            key: item.firstLetter,
            isChecked: item.isChecked,
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
  onIndexListChange(event) {
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let app = getApp();
    if (options) {
      if ("auditor" == options.roleType) {
        this.data.isMultiSelect = false;
      } else if ("staff" == options.roleType) {
        this.data.isMultiSelect = true;
      }
      this.changeNavigationBarTitle(options.roleType);
      this.setData({
        roleType: options.roleType
      });
    }
    this.data.requestIp = app.globalData.requestIp;
    let hasSelectPeopleIds = wx.getStorageSync("hasSelectPeopleIds");
    //访问后台获取选人列表
    this.getPeople(hasSelectPeopleIds);
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
    this.data.people.forEach((item) => {
      let index = words.indexOf(item.firstLetter);
      if (index > -1) {
        indexStore[index].list.push({
          name: item.name,
          key: item.firstLetter,
          isChecked: item.isChecked,
          id: item.id
        });
      } else {
        indexStore[0].list.push({
          name: item.name,
          key: item.firstLetter,
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
    barTitle["staff"] = '选择承接人';
    barTitle["auditor"] = '选择审核人';
    wx.setNavigationBarTitle({
      title: barTitle[index]
    })
  },

  /**
   * 访问后台获得people列表
   */
  getPeople(hasSelectPeopleIds) {
    wx.request({
      url: this.data.requestIp + '/base_task/users',
      success: res => {
        this.data.people = res.data;
        //如果有已经点击的人员，则给获取到的people中添加isChecked属性
        let hasSelectPeopleIdsArr = hasSelectPeopleIds.split(",");
        if (hasSelectPeopleIdsArr && hasSelectPeopleIdsArr.length > 0) {
          hasSelectPeopleIdsArr.forEach((val, key) => {
            this.data.people.forEach((v, k) => {
              if (val == v.id) {
                v.isChecked = true;
                return;
              }
            });
          });
          wx.removeStorage({
            key: 'hasSelectPeopleIds'
          });
        }
        //初始化索引列表
        this.initIndexList();
      },
      fail: e => {
        console.log(e);
      }
    })
  },

  /**
   * 保存已选人到缓存
   */
  saveSelectPeople2Storage() {
    let selectPeople = [];
    this.data.people.forEach((val, key) => {
      if (val.isChecked) {
        selectPeople.push(val);
      }
    });
    wx.setStorageSync(this.data.roleType + '-selectPeople', selectPeople);
  }
})