// pages/taskdetail/taskdetail.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/13 10:14
 \* Description: 任务详情
 \*/
const { $Toast } = require('../../dist/base/index');
import dateUtil from '../../utils/date_util';
const { bidirectionalBind } = require('../../utils/bidirectionalBind.js');
import constants from "../../static/constants.js";
Page({

  /**
   * 页面的初始数据
   */
  data: {
    role: "",
    requestIp: "",
    userId: "",
    staffNames: "",
    labelNames: "",
    taskDetail: {},
    isAnyButtonClick: false,
    modal: {
      isModalShow: false,
      modalActions: [
        {
          name: "提交",
          color: '#2d8cf0',
          loading: false
        }, 
        {
          name: "取消",
          color: '#2d8cf0',
          loading: false
        }
      ]
    },
    ROLE: {},
    TASK_STATUS: {}
  },
  handleModalClick({ detail }) {
    if (detail.index === 1) {
      this.setData({
        'modal.isModalShow': false
      });
      return;
    }
    if (!this.data.taskDetail.reviewReason) {
      this.showToast("请填写提审原因", "warning");
      return;
    }
    if (this.data.isAnyButtonClick) {
      return;
    }
    this.data.isAnyButtonClick = true;
    this.data.modal.modalActions[0].loading = true;
    this.setData({
      'modal.modalActions': this.data.modal.modalActions
    });
    this.data.taskDetail.taskState = this.data.TASK_STATUS.EXPERT_EVALUATING;
    this.updateTaskByAjax("已提交专家组", "sucess", () => {
      this.data.modal.modalActions[0].loading = false;
      this.setData({
        'modal.modalActions': this.data.modal.modalActions
      });
    }, () => {
      this.data.modal.modalActions[0].loading = false;
      this.setData({
        'modal.modalActions': this.data.modal.modalActions
      });
    });
  },
  handleInput(e) {
    bidirectionalBind(e, this);
  },
  /**
   * 任务发布驳回
   */
  issueNoPass(){
    if (!this.data.taskDetail.comment) {
      this.showToast("请填写点评", "warning");
      return;
    }
    if (!this.data.taskDetail.expertComment && this.data.role == this.data.ROLE.EXPERT) {
      this.showToast("请填写评审意见", "warning");
      return;
    }
    if (this.data.isAnyButtonClick) {
      return;
    }
    this.data.taskDetail.taskState = this.data.TASK_STATUS.ISSUE_REJECTED;
    this.updateTaskByAjax("任务发布已驳回", "sucess");
  },
  /**
   * 任务完成提交未通过
   */
  commitNoPass() {
    if (!this.data.taskDetail.comment) {
      this.showToast("请填写点评", "warning");
      return;
    }
    if (!this.data.taskDetail.expertComment && this.data.role == this.data.ROLE.EXPERT) {
      this.showToast("请填写评审意见", "warning");
      return;
    }
    if (this.data.isAnyButtonClick) {
      return;
    }
    this.data.taskDetail.taskState = this.data.TASK_STATUS.COMMIT_REJECTED;
    this.updateTaskByAjax("任务提交完成已驳回", "sucess");
  },
  /**
   * 提交专家组
   */
  commit2Expert(){
    this.setData({
      'modal.isModalShow': true
    });
  },
  /**
   * 任务发布通过
   */
  issuePass(){
    if (!this.data.taskDetail.comment) {
      this.showToast("请填写点评", "warning");
      return;
    }
    if (!this.data.taskDetail.expertComment && this.data.role == this.data.ROLE.EXPERT) {
      this.showToast("请填写评审意见", "warning");
      return;
    }
    if (this.data.isAnyButtonClick) {
      return;
    }
    if (this.data.taskDetail.orient == 1) {
      this.data.taskDetail.taskState = this.data.TASK_STATUS.TASK_DOING;
    } else {
      this.data.taskDetail.taskState = this.data.TASK_STATUS.WAIT_CLAIM;
    }
    this.updateTaskByAjax("任务发布已通过", "sucess");
  },
  /**
   * 提交完成任务
   */
  commitDoneTask() {
    if (this.data.isAnyButtonClick) {
      return;
    }
    this.data.taskDetail.taskState = this.data.TASK_STATUS.EVALUATING;
    this.updateTaskByAjax("已提交任务完成申请", "sucess");
  },
  /**
   * 任务完成提交通过
   */
  commitPass() {
    if (!this.data.taskDetail.comment) {
      this.showToast("请填写点评", "warning");
      return;
    }
    if (!this.data.taskDetail.expertComment && this.data.role == this.data.ROLE.EXPERT) {
      this.showToast("请填写评审意见", "warning");
      return;
    }
    if (this.data.isAnyButtonClick) {
      return;
    }
    this.data.taskDetail.taskState = this.data.TASK_STATUS.DONE;
    this.updateTaskByAjax("任务完成已通过", "sucess");
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let app = getApp();
    this.data.requestIp = app.globalData.requestIp;
    this.data.userId = app.globalData.localUserInfo.id;
    this.setData({
      ROLE: constants.ROLE,
      TASK_STATUS: constants.TASK_STATUS,
      role: options.role
    });
    this.getTaskInfo(options.taskId);
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
   * 请求后台更新任务
   */
  updateTaskByAjax(toastMessg, toastType, successFunc, failFunc) {
    this.data.isAnyButtonClick = true;
    wx.request({
      url: this.data.requestIp + '/base_task/updateTaskState',
      method: "POST",
      data: this.data.taskDetail,
      success: res => {
        this.data.isAnyButtonClick = false;
        this.showToast(toastMessg, toastType);
        if (successFunc) {
          successFunc(res);
        }
        setTimeout(() => {
          wx.navigateBack();
        }, 500);
      },
      fail: e => {
        this.showToast("后台服务异常，请重新登陆", "error");
        if (failFunc) {
          failFunc(e);
        }
        this.data.isAnyButtonClick = false;
      }
    });
  },
  /**
   * 访问后台获取任务详情
   */
  getTaskInfo(taskId) {
    wx.request({
      url: this.data.requestIp + '/base_task/taskInfo?taskId=' + taskId,
      method: "GET",
      success: res => {
        console.log(res.data);
        res.data.startTime = dateUtil.dateFormat(new Date(res.data.startTime), "yyyy-MM-dd");
        res.data.endTime = dateUtil.dateFormat(new Date(res.data.endTime), "yyyy-MM-dd");
        this.setData({
          taskDetail: res.data
        });
        this.dealTaskData(res.data);
      },
      fail: e => {

      }
    })
  },

  /**
   * 改变当前页面的标题
   */
  changeNavigationBarTitle(text) {
    const barTitle = [
      '加载中...'
    ]
    wx.setNavigationBarTitle({
      title: text + "的任务"
    });
  },

  /**
   * 处理任务数据
   */
  dealTaskData(taskVo) {
    this.changeNavigationBarTitle(taskVo.taskState);
    let staffNames = "", labelNames = "";
    taskVo.staffVos.forEach((v, k) => {
      if (v.userVo.id == this.data.userId) {

      }
      staffNames += v.userVo.name + ",";
    });
    if (staffNames.indexOf(",") > -1) {
      staffNames = staffNames.substring(0, staffNames.length - 1);
    }
    taskVo.labelVos.forEach((v, k) => {
      labelNames += v.labelName + ",";
    });
    if (labelNames.indexOf(",") > -1) {
      labelNames = labelNames.substring(0, labelNames.length - 1);
    }
    this.setData({
      staffNames,
      labelNames
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