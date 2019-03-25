// pages/issue/issue.js

const { $Toast } = require('../../dist/base/index');
const watch = require('../../utils/watcher.js');
const { bidirectionalBind } = require('../../utils/bidirectionalBind.js');
import dateUtil from '../../utils/date_util';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    requestIp: '',
    staffNames: '',
    taskVo: {
      taskName: '',
      taskDescription: '',
      startTime: dateUtil.dateFormat(new Date(), "yyyy-MM-dd"),
      endTime: dateUtil.dateFormat(new Date(new Date().getTime() + 7*24*60*60*1000), "yyyy-MM-dd"),
      orient: true,
      workload: '',
      project: {},
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
    this.setData({
      'taskVo.orient': detail.key == "true"
    });
  },
  handleInput(e) {
    bidirectionalBind(e, this);
  },
  choosePeople(e){
    switch (e.target.dataset.roletype) {
      case "staff":
        if (this.data.taskVo.staffVos && this.data.taskVo.staffVos.length > 0) {
          let ids = "";
          this.data.taskVo.staffVos.forEach((v, k) => {
            ids += v.userVo.id + ",";
          });
          wx.setStorageSync("hasSelectPeopleIds", ids.substring(0, ids.length - 1));
        }
        break;
      case "auditor":
        if (this.data.taskVo.auditorVo && this.data.taskVo.auditorVo.userVo) {
          wx.setStorageSync("hasSelectPeopleIds", this.data.taskVo.auditorVo.userVo.id);
        }
        break;
    }
    wx.navigateTo({
      url: '../peoplelist/peoplelist?roleType=' + e.target.dataset.roletype,
    })
  },
  chooseProject() {
    wx.navigateTo({
      url: '../projlist/projlist'
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
    // wx.request({
    //   url: this.data.requestIp + '/base_task/dispatchTask',
    //   method: "POST",
    //   data: this.data.taskVo,
    //   success: res => {
    //     console.log(res);
    // wx.removeStorage({
    //   key: 'selectProject'
    // });
    // wx.removeStorage({
    //   key: 'auditor-selectPeople'
    // });
    // wx.removeStorage({
    //   key: 'staff-selectPeople'
    // });
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
    this.data.requestIp = getApp().globalData.requestIp;
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
    let selectProject = wx.getStorageSync('selectProject');
    console.log(selectProject);
    if (selectProject) {
      this.setData({
        'taskVo.project': {
          id: selectProject.id,
          name: selectProject.name
        }
      });
    }
    let auditorSelectPeople = wx.getStorageSync('auditor-selectPeople');
    let staffSelectPeople = wx.getStorageSync('staff-selectPeople');
    if (auditorSelectPeople && auditorSelectPeople.length > 0) {
      this.setData({
        "taskVo.auditorVo": {
          userVo: {
            id: auditorSelectPeople[0].id,
            name: auditorSelectPeople[0].name,
            openid: auditorSelectPeople[0].openid
          }
        }
      });
    } else {
      this.setData({
        "taskVo.auditorVo": {
          userVo: {
            id: "",
            name: "",
            openid: ""
          }
        }
      });
    }
    if (staffSelectPeople) {
      let staffVos = [];
      staffSelectPeople.forEach((v, k) => {
        staffVos.push({
          userVo: {
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
    }
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