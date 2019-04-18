// pages/label/label.js
/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/4/10 10:14
 \* Description: 标签选择
 \*/
const { $Toast } = require('../../dist/base/index');
const { bidirectionalBind } = require('../../utils/bidirectionalBind.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    NEW_LABEL_TAG: "@newLabel",
    requestIp: "",
    labels: [],
    choosedLabels: [],
    newLabel: {
      id: '@newLabel0',
      labelName: '',
      checked: true
    },
    modal: {
      isModalShow: false,
      modalActions: [{
        name: "添加",
        color: '#2d8cf0',
        loading: false
      },
      {
        name: "取消",
        color: '#2d8cf0',
        loading: false
      }]
    },
  },

  handleOkBtnClick() {
    //保存已选标签
    this.saveSelectLabel2Storage();
    wx.navigateBack();
  },

  handleInput(e) {
    bidirectionalBind(e, this);
  },

  handleModalClick({detail}) {
    if (detail.index === 1) {
      this.setData({
        'modal.isModalShow': false,
        'newLabel.labelName': ''
      });
      return;
    }
    //值验证，不允许重复标签
    if (!this.data.newLabel.labelName) {
      this.showToast("标签名不能为空", "warning");
      return;
    }
    let isNeedReturn = false;
    console.log(this.data.newLabel.labelName);
    this.data.labels.forEach((v, k) => {
      if (v.labelName === this.data.newLabel.labelName) {
        this.showToast("已存在该标签", "warning");
        isNeedReturn = true;
        return;
      }
    });
    if (isNeedReturn) {
      return;
    }
    this.data.choosedLabels.forEach((v, k) => {
      if (v.labelName === this.data.newLabel.labelName) {
        this.showToast("已存在该标签", "warning");
        isNeedReturn = true;
        return;
      }
    });
    if (isNeedReturn) {
      return;
    }

    let idIndex = this.data.newLabel.id.substring(this.data.NEW_LABEL_TAG.length, this.data.newLabel.id.length)*1;
    this.data.choosedLabels.forEach(v => {
      if ((v.id.substring(0, this.data.NEW_LABEL_TAG.length) === this.data.NEW_LABEL_TAG) && v.id.substring(this.data.NEW_LABEL_TAG.length, this.data.newLabel.id.length)*1 > idIndex) {
        idIndex = v.id.substring(this.data.NEW_LABEL_TAG.length, this.data.newLabel.id.length)*1;
      }
    });
    ++idIndex;
    this.data.newLabel.id = this.data.NEW_LABEL_TAG + idIndex;
    this.data.choosedLabels.push({
      id: this.data.newLabel.id,
      labelName: this.data.newLabel.labelName,
      checked: true
    });
    this.setData({
      choosedLabels: this.data.choosedLabels,
      'modal.isModalShow': false,
      'newLabel.labelName': ''
    });
  },

  handleAddBtnClick() {
    this.setData({
      'modal.isModalShow': true
    });
  },

  handleChooseLabelChange(e) {
    let index = -1, iindex = -1;
    this.data.labels.forEach((v, k) => {
      if (v.id === e.target.dataset.itemid) {
        index = k;
        return;
      }
    });
    this.data.choosedLabels.forEach((v, k) => {
      if (v.id === e.target.dataset.itemid) {
        iindex = k;
        return;
      }
    });
    this.data.choosedLabels.splice(iindex, 1);
    this.setData({
      choosedLabels: this.data.choosedLabels
    });
    if (index >= 0) {
      this.setData({
        ['labels[' + index + '].checked']: false
      });
    }
  },

  handleLabelChange(e) {
    let index = -1;
    this.data.labels.forEach((v, k) => {
      if (v.id === e.target.dataset.itemid) {
        index = k;
        if (v.checked ? v.checked : false) {
          let iindex = -1;
          this.data.choosedLabels.forEach((val, key) => {
            if (val.id === e.target.dataset.itemid) {
              iindex = key;
            }
          });
          if (iindex >= 0) {
            this.data.choosedLabels.splice(iindex, 1);
          }
        } else {
          this.data.choosedLabels.push(v);
        }
        return;
      }
    });
    if (index < 0) {
      return;
    }
    this.setData({
      ['labels[' + index + '].checked']: e.detail.checked,
      choosedLabels: this.data.choosedLabels
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let app = getApp();
    this.data.requestIp = app.globalData.requestIp;
    let hasSelectLabels = wx.getStorageSync("hasSelectLabels");
    //访问后台获取标签列表
    this.getLabels(hasSelectLabels);
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
   * 访问后台获取标签列表
   */
  getLabels(hasSelectLabels) {
    console.log(hasSelectLabels);
    wx.request({
      url: this.data.requestIp + '/base_task/labels',
      method: "GET",
      success: res => {
        if (!res.data) {
          return;
        }
        this.data.labels = res.data;
        //如果有已经点击的标签，则给获取到的label中添至已选
        if (hasSelectLabels && hasSelectLabels.length > 0) {
          hasSelectLabels.forEach((val, key) => {
            if (val.id.substring(0, this.data.NEW_LABEL_TAG.length) === this.data.NEW_LABEL_TAG) {
              this.data.choosedLabels.push({
                id: val.id,
                labelName: val.labelName,
                checked: true
              });
            } else {
              this.data.labels.forEach((v, k) => {
                if (val.id == v.id) {
                  this.data.choosedLabels.push({
                    id: val.id,
                    labelName: val.labelName,
                    checked: true
                  });
                  v.checked = true;
                  return;
                }
              });
            }
          });
          wx.removeStorage({
            key: 'hasSelectLabels'
          });
        }
        this.setData({
          labels: this.data.labels,
          choosedLabels: this.data.choosedLabels
        });
      },
      fail: e => {

      }
    })
  },

  /**
   * 保存已选标签
   */
  saveSelectLabel2Storage() {
    wx.setStorageSync('selectLabel', this.data.choosedLabels);
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