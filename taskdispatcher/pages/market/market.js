// pages/market/market.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/18 10:14
 \* Description: 首页-任务市场
 \*/
const { $Toast } = require('../../dist/base/index');
const { bidirectionalBind } = require('../../utils/bidirectionalBind.js');
import WxValidate from '../../utils/WxValidate.js'
Page({

  /**
   * 页面的初始数据
   */
  data: {
    requestIp: "",
    selectIndex: 0,
    modal: {
      isModalShow: false,
      isActivateClick: false
    },
    isMarketShow: false,
    msgCodeButton: {
      disabled: false,
      text: "发送验证码",
      type: "success",
      isClick: false
    },
    taskClaimButtons: [],
    userVo: {
      name: "",
      telephone: "",
      weChat: "",
      msgCode: ""
    },
    modalActions: [{
      name: "激活",
      color: '#2d8cf0',
      loading: false
    }],
    tasks: [
      {
        id: 'fsdfs',
        taskName: '任务1',
        projName: '项目1',
        taskDescription: '描述1',
        projPeriod: '1年',
        publisher: '辉神',
        workload: 3,
        outputValue: 500000000
      }
    ]
  },
  /**
   * 任务认领按钮
   */
  handleTaskClaimClick(event) {
    if (this.data.taskClaimButtons[event.target.dataset.index].isClick) {
      return;
    }
    this.data.taskClaimButtons[event.target.dataset.index].loading = true;
    this.data.taskClaimButtons[event.target.dataset.index].isClick = true;
    this.setData({
      taskClaimButtons: this.data.taskClaimButtons
    });
    let app = getApp();
    wx.request({
      url: this.data.requestIp + '/base_task/binding/task?userId=' + app.globalData.localUserInfo.id + "&taskId=" + event.target.dataset.taskid,
      method: "GET",
      success: res => {
        console.log(res);
        if (res.data && res.data.errCode != 0) {
          this.showToast("认领失败", "success");
          this.data.taskClaimButtons[event.target.dataset.index].loading = false;
          this.data.taskClaimButtons[event.target.dataset.index].isClick = false;
          this.setData({
            taskClaimButtons: this.data.taskClaimButtons
          });
          return;
        }
        this.showToast("认领成功", "success");
        this.getUndirecTasks();
      },
      fail: e => {
        this.showToast("认领失败", "success");
        this.data.taskClaimButtons[event.target.dataset.index].loading = false;
        this.data.taskClaimButtons[event.target.dataset.index].isClick = false;
        this.setData({
          taskClaimButtons: this.data.taskClaimButtons
        });
      }
    });
  },
  /**
   * 发送验证码按钮
   */
  handleMsgCodeBtnClick() {
    if (this.data.msgCodeButton.isClick) {
      return;
    }
    let params = {
      telephone: this.data.userVo.telephone
    }
    //值验证
    if (!this.WxValidate.checkForm(params)) {
      const error = this.WxValidate.errorList[0];
      this.showToast(error.msg, 'warning');
      return;
    }
    this.data.msgCodeButton.isClick = true;
    //发送验证码动画
    let time = 61;
    let interval = setInterval(() => {
      if (time-- > 1) {
        this.setData({
          msgCodeButton: {
            disabled: true,
            text: "等待（" + time + "s）",
            type: "ghost",
            isClick: true
          }
        });
      } else {
        clearInterval(interval);
        this.setData({
          msgCodeButton: {
            disabled: false,
            text: "发送验证码",
            type: "success",
            isClick: false
          }
        });
      }
    }, 1000);
    //请求后台发送短信验证码
    wx.request({
      url: this.data.requestIp + '/base_task/msgCode?phone=' + this.data.userVo.telephone,
      method: "GET",
      success: res => {
        if (res.data) {

        }
      },
      fail: e => {

      }
    })
  },
  /**
   * 输入框事件
   */
  handleInput(e) {
    bidirectionalBind(e, this);
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
    if (this.data.modal.isActivateClick) {
      return;
    }
    let params = {
      telephone: this.data.userVo.telephone
    }
    //值验证
    if (!this.WxValidate.checkForm(params)) {
      const error = this.WxValidate.errorList[0];
      this.showToast(error.msg, 'warning');
      return;
    }
    if (!this.data.userVo.msgCode) {
      this.showToast("请输入验证码", 'warning');
      return;
    }
    this.data.modal.isActivateClick = true;
    this.data.modalActions[0].loading = true;

    this.setData({
      modalActions: this.data.modalActions
    });

    //请求后台进行激活
    let app = getApp();
    this.data.userVo.weChat = app.globalData.openid;
    wx.request({
      url: app.globalData.requestIp + '/base_task/activate',
      method: "POST",
      data: this.data.userVo,
      success: res => {
        this.data.modal.isActivateClick = false;
        this.data.msgCodeButton.isClick = false;
        this.data.modalActions[0].loading = false;
        if (!res.data) {
          return;
        }
        if (res.data.errCode == 0) {
          wx.showTabBar();
          this.showToast('激活成功', 'success');
          this.setData({
            'modal.isModalShow': false,
            isMarketShow: true,
            modalActions: this.data.modalActions
          });
        } else {
          this.showToast(res.data.messages, 'warning');
          this.setData({
            'modal.isModalShow': true,
            isMarketShow: false,
            modalActions: this.data.modalActions
          });
        }
      },
      fail: e => {
        this.data.modal.isActivateClick = false;
        this.data.msgCodeButton.isClick = false;
        this.data.modalActions[0].loading = false;
        this.showToast("激活服务异常", 'error');
        this.setData({
          'modal.isModalShow': true,
          isMarketShow: false,
          modalActions: this.data.modalActions
        });
      }
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.initValidate()//验证规则函数
    let app = getApp();
    this.data.requestIp = app.globalData.requestIp;
    //初始化验证激活
    this.initActivate();
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
    //访问后台获取非定向任务列表
    this.getUndirecTasks();
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
   * 初始化验证器
   */
  initValidate() {
    const rules = {
      telephone: {
        required: true,
        tel: true
      }
    }
    const messages = {
      telephone: {
        required: '请输入手机号',
        tel: '请输入正确的手机号'
      }
    }
    this.WxValidate = new WxValidate(rules, messages)
  },

  /**
   * 访问后台获取非定向任务
   */
  getUndirecTasks() {
    wx.request({
      url: this.data.requestIp + '/base_task/unOrient/tasks',
      method: "GET",
      success: res => {
        res.data.forEach((v, k) => {
          this.data.taskClaimButtons.push({
            isClick: false,
            loading: false
          });
        });
        this.setData({
          tasks: res.data,
          taskClaimButtons: this.data.taskClaimButtons
        });
      },
      fail: e => {

      }
    })
  },

  /**
   * 初始化验证激活
   */
  initActivate() {
    let app = getApp();
    //获取到openid与用户信息后，才能开始进行激活流程
    let interval = setInterval(() => {
      if (app.globalData.openid && app.globalData.userInfo) {
        clearInterval(interval);
        wx.request({
          url: app.globalData.requestIp + '/base_task/login?openId=' + app.globalData.openid,
          method: "GET",
          success: res => {
            if (!res.data || !res.data.data) {
              this.setData({
                'modal.isModalShow': true,
                isMarketShow: false
              });
              wx.hideTabBar();
            } else {
              app.globalData.localUserInfo = JSON.parse(res.data.data);
              this.setData({
                'modal.isModalShow': false,
                isMarketShow: true
              });
              wx.showTabBar();
            }
          },
          fail: e => {

          }
        });
      }
    }, 1000);
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