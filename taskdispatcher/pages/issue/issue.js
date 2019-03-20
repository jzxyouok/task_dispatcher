// pages/issue/issue.js

const { $Toast } = require('../../dist/base/index');
const watch = require('../../utils/watcher.js');
const { inputgetName } = require('../../utils/bidirectionalBind.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    staffNames: '',
    taskVo: {
      taskName: '',
      taskDescription: '',
      startTime: '',
      endTime: '',
      orient: true,
      workload: '',
      project: {
        id: '123456',
        Name: '项目1',
        department: '测试1'
      },
      proposerVo: {
        userVo: {
          id: '402880e76990a8b4016990a8c4c60000',
          weChat: 'lc',
          name: '刘成',
        }
      },
      auditorVo: {},
      staffVos: []
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
  handleTabChange({ detail }) {
    console.log(detail);
    this.setData({
      'taskVo.orient': detail.key == "true"
    });
  },
  handleInput(e) {
    inputgetName(e, this);
  },
  choosePeople(e){
    wx.navigateTo({
      url: '../peoplelist/peoplelist?roleType=' + e.target.dataset.roletype,
    })
  },
  handleSave(){
    this.showToast("已保存到草稿", 'success');
    this.setData({
      taskVo: this.data.taskVo
    })
  },

  handleIssue() {
    console.log(this.data.taskVo);
    let requestIp = getApp().globalData.requestIp;
    // wx.request({
    //   url: requestIp + '/base_task/dispatchTask',
    //   method: "POST",
    //   data: this.data.taskVo,
    //   success: res => {
    //     console.log(res);
    //     this.showToast("发布成功", 'success');
    //   },
    //   fail: e => {
    //     console.log(e);
    //   }
    // });
  },
  watch: {
    'taskVo.staffVos': {
      handler(newValue) {
        console.log(newValue);
        let staffNames = "";
        newValue.forEach((v, k) => {
          staffNames += v.userVo.name + ",";
        });
        if (staffNames.indexOf(",") > -1) {
          staffNames = staffNames.substring(0, staffNames.length - 1);
        }
        this.setData({
          staffNames
        });
      },
      deep: true
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    watch.setWatcher(this); // 设置监听器，建议在onLoad下调用
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
    console.log("issue.js onShow");
    let selectPeople = wx.getStorageSync('selectPeople');
    if (!selectPeople || !selectPeople.peopleArr || selectPeople.peopleArr.length < 1) {
      return;
    }
    switch (selectPeople.roleType) {
      case "auditor":
        this.setData({
          "taskVo.auditorVo" : {
            userVo: {
              id: selectPeople.peopleArr[0].id,
              name: selectPeople.peopleArr[0].name,
              openid: selectPeople.peopleArr[0].openid
            }
          }
        });
        break;
      case "staff":
        let staffVos = [];
        selectPeople.peopleArr.forEach((v, k) => {
          staffVos.push({
            userVo:{
              id: v.id,
              name: v.name,
              openid: v.openid
            }
          });
        });
        this.data.taskVo.staffVos = staffVos;
        let taskVo = this.data.taskVo;
        this.setData({
          'taskVo.staffVos': staffVos
        });
        console.log(staffVos);
        console.log(this.data.taskVo);
        break;
    }
    //清楚缓存
    wx.removeStorageSync('selectPeople');
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
   * 显示成功的气泡
   */
  showToast(content, type) {
    $Toast({
      content,
      type
    });
  }
})