// pages/issue/issue.js

const { $Toast } = require('../../dist/base/index');
const { watch, computed } = require('../../utils/vuefy.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
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
      auditorVo: {
        userVo: {
          id: '402880e76990a8b4016990a8c4f30003',
          weChat: 'doctor彭',
          name: 'doctor',
        }
      },
      staffVos: [{
        userVo: {
          id: '402880e76990a8b4016990a8c4f30002',
          weChat: 'ymh',
          name: '要梦回',
        }
      }, 
      {
        userVo: {
          id: '402880e76990a8b4016990a8c4f20001',
          weChat: 'lw',
          name: '龙威',
        }
      }]
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
  choosePeople(e){
    wx.navigateTo({
      url: '../peoplelist/peoplelist?roleType=' + e.target.dataset.roletype,
    })
  },
  handleSave(){
    wx.showToast({
      title: '已保存到草稿箱',
      icon: 'succes',
      duration: 1000,
      mask: true
    })
  },

  handleIssue() {
    console.log(this.data.taskVo);
    let requestIp = getApp().globalData.requestIp;
    wx.request({
      url: requestIp + '/base_task/dispatchTask',
      method: "POST",
      data: this.data.taskVo,
      success: res => {
        console.log(res);
        this.showToast("已经成功发布", 'success');
      },
      fail: e => {
        console.log(e);
      }
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    computed(this, {
      staffNames: function () {
        let destStr = "";
        this.data.taskVo.staffVos.forEach((v, k) => {
          destStr += v.userVo.name + ",";
        });
        if (destStr.indexOf(",") > -1) {
          destStr = destStr.substring(0, destStr.length - 1);
        }
        console.log("destStr:");
        console.log(destStr);
        return destStr;
      }
    })
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
    console.log(selectPeople);
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
        this.setData({
          taskVo: this.data.taskVo
        });
        console.log(staffVos);
        console.log(this.data.taskVo);
        break;
    }
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