// pages/issue/issue.js

const { $Toast } = require('../../dist/base/index');
const watch = require('../../utils/watcher.js');
const { bidirectionalBind } = require('../../utils/bidirectionalBind.js');
import dateUtil from '../../utils/date_util';
import WxValidate from '../../utils/WxValidate.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    requestIp: '',
    staffNames: '',
    labelNames: '',
    taskVo: {
      taskName: '',
      taskDescription: '',
      startTime: dateUtil.dateFormat(new Date(), "yyyy-MM-dd"),
      endTime: dateUtil.dateFormat(new Date(new Date().getTime() + 7*24*60*60*1000), "yyyy-MM-dd"),
      orient: true,
      taskState: '待审核',
      workload: '',
      labelVos: [],
      projectVo: {},
      proposerVo: {},
      auditorVo: {},
      staffVos: []
    },
    issueButton: {
      isClick: false
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
    });
  },
  chooseLabel() {
    if (this.data.taskVo.labelVos && this.data.taskVo.labelVos.length > 0) {
      wx.setStorageSync("hasSelectLabels", this.data.taskVo.labelVos);
    }
    wx.navigateTo({
      url: '../label/label'
    })
  },
  handleSave(){
    this.showToast("已保存到草稿", 'success');
    this.setData({
      taskVo: this.data.taskVo
    })
  },

  handleIssue() {
    if (this.data.issueButton.isClick) {
      return;
    }
    let params = {
      taskName: this.data.taskVo.taskName,
      taskDescription: this.data.taskVo.taskDescription,
      projectName: this.data.taskVo.projectVo.name,
      auditoName: this.data.taskVo.auditorVo.userVo ? this.data.taskVo.auditorVo.userVo.name : "",
      workload: this.data.taskVo.workload
    }
    //表单验证
    if (!this.WxValidate.checkForm(params)) {
      const error = this.WxValidate.errorList[0];
      this.showToast(error.msg, 'warning');
      return;
    }
    if (!this.data.labelNames) {
      this.showToast("请选择标签", 'warning');
      return;
    }
    if (this.data.taskVo.orient && !this.data.staffNames) {
      this.showToast("请选择承接人", 'warning');
      return;
    }
    this.data.taskVo.proposerVo = {
      userVo: {
        id: getApp().globalData.localUserInfo.id,
        name: getApp().globalData.localUserInfo.name,
        openid: getApp().globalData.openid
      }
    };
    this.data.issueButton.isClick = true;
    wx.request({
      url: this.data.requestIp + '/base_task/dispatchTask',
      method: "POST",
      data: this.data.taskVo,
      success: res => {
        console.log(res);
        if (res.data.errCode != 0) {
          this.showToast("发布失败", 'success');
          this.data.issueButton.isClick = false;
          return;
        }
        this.removeAllStorage();
        this.showToast("发布成功", 'success');
        this.data.issueButton.isClick = false;
        this.resetTaskVo();
      },
      fail: e => {
        this.data.issueButton.isClick = false;
        console.log(e);
      }
    });
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
        } else {
          staffNames = "";
        }
        this.setData({
          staffNames
        });
      },
      deep: true
    },
    'taskVo.labelVos': {
      handler(newValue) {
        let labelNames = "";
        newValue.forEach((v, k) => {
          labelNames += v.labelName + ",";
        });
        if (labelNames.indexOf(",") > -1) {
          labelNames = labelNames.substring(0, labelNames.length - 1);
        } else {
          labelNames = "";
        }
        this.setData({
          labelNames
        });
      },
      deep: true
    }
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.initValidate()//验证规则函数
    let app = getApp();
    this.data.requestIp = app.globalData.requestIp;
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
    let selectLabel = wx.getStorageSync('selectLabel');
    if (selectLabel && selectLabel.length > 0) {
      let labelVos = [];
      selectLabel.forEach((v, k) => {
        labelVos.push({
          id: v.id,
          labelName: v.labelName
        });
      });
      this.data.taskVo.labelVos = labelVos;
      let taskVo = this.data.taskVo;
      this.setData({
        'taskVo.labelVos': labelVos
      });
    } else {
      this.setData({
        "taskVo.labelVos": []
      });
    }

    let selectProject = wx.getStorageSync('selectProject');
    if (selectProject) {
      this.setData({
        'taskVo.projectVo': {
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
    this.removeAllStorage();
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
     * 初始化验证器
     */
  initValidate() {
    const rules = {
      taskName: {
        required: true
      },
      taskDescription: {
        required: true
      },
      projectName: {
        required: true
      },
      auditoName: {
        required: true
      },
      workload: {
        required: true
      }
    }
    const messages = {
      taskName: {
        required: '请输入任务名称'
      },
      taskDescription: {
        required: '请输入任务描述'
      },
      projectName: {
        required: '请选择项目'
      },
      auditoName: {
        required: '请选择审核人'
      },
      workload: {
        required: '请输入工作量'
      }
    }
    this.WxValidate = new WxValidate(rules, messages)
  },

  /**
   * 重置taskVo
   */
  resetTaskVo() {
    let orient = this.data.taskVo.orient;
    let taskState = this.data.taskVo.taskState;
    this.setData({
      taskVo: {
        taskName: '',
        taskDescription: '',
        startTime: dateUtil.dateFormat(new Date(), "yyyy-MM-dd"),
        endTime: dateUtil.dateFormat(new Date(new Date().getTime() + 7 * 24 * 60 * 60 * 1000), "yyyy-MM-dd"),
        orient,
        taskState,
        workload: '',
        projectVo: {},
        proposerVo: {},
        auditorVo: {},
        staffVos: [],
        labelVos: []
      }
    });
    watch.setWatcher(this); // 原监视变量引用地址更改，需重监视
    this.setData({
      'taskVo.staffVos': [],
      'taskVo.labelVos': [],
    });
  },
  /**
   * 移除所有storage
   */
  removeAllStorage() {
    wx.removeStorage({
      key: 'selectLabel'
    });
    wx.removeStorage({
      key: 'selectProject'
    });
    wx.removeStorage({
      key: 'auditor-selectPeople'
    });
    wx.removeStorage({
      key: 'staff-selectPeople'
    });
  },
  /**
   * 显示气泡
   */
  showToast(content, type) {
    $Toast({
      content,
      type
    });
  }
})