//app.js
App({
  data: {
    
  },
  onLaunch: function () {
    wx.hideTabBar();
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    //获取用户openid
    wx.login({
      success: (res) => {
        if (res.code) {
          var url = 'https://api.weixin.qq.com/sns/jscode2session?appid=' + this.globalData.appid + '&secret=' + this.globalData.secret + '&js_code=' + res.code + '&grant_type=authorization_code';
          wx.request({
            url,
            success: res => {
              if (res.data) {
                this.globalData.openid = res.data.openid;
              } 
            }
          });
        } else {
          console.log('获取用户登录态失败！' + res.errMsg)
        }
      }
    });
    //获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo;
            }
          });
        }
      }
    });
  },
  globalData: {
    userInfo: null, //微信存储的用户信息
    localUserInfo: null, //本地数据库存储的用户信息
    appid: 'wx07a87aa3fd0cc53a',
    secret: 'b96816b6e0d6e95d700443e88ba86396',
    openid: '',
    // requestIp: 'http://10.39.100.19:8080'
    requestIp: 'http://192.168.0.10:8080'
  }
})