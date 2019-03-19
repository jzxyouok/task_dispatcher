// pages/issue/issue.js
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
        this.showSuccToast("已经成功发布");
      },
      fail: e => {
        console.log(e);
      }
    });
  },
  /**
   * 显示成功的气泡
   */
  showSuccToast(title){
    wx.showToast({
      title,
      icon: 'succes',
      duration: 1000,
      mask: true
    })
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
    console.log(wx.getStorageSync('selectPeople'));
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