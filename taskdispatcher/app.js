//app.js
App({
  data: {
    
  },
  onLaunch: function () {
    wx.hideTabBar();
    this.removeAllStorage();
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    //获取用户openid
    wx.login({
      success: (res) => {
        if (res.code) {
          console.log("获取到session_code，开始访问自己后台获取openid");
          this.getOpenidByWeixinApi(res.code);
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
              console.log(res);
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo;
              this.globalData.isAuth = true;
              if (!res.userInfo) {
                this.globalData.isAuth = false;
              }
              this.globalData.isFirstGetUserInfoDone = true;
            },
            fail: e => {
              this.globalData.isAuth = false;
              this.globalData.isFirstGetUserInfoDone = true;
            }
          });
        } else {
          this.globalData.isAuth = false;
          this.globalData.isFirstGetUserInfoDone = true;
        }
      }
    });
  },

  /**
   * 访问后台获取openid
   */
  getOpenidByWeixinApi(code) {
    wx.request({
      url: this.globalData.requestIp + '/base_task/getOpenidByWeixinApi?code=' + code,
      method: "GET",
      success: res => {
        if (res.data && res.data.errCode == 0) {
          console.log("获取到openid");
          this.globalData.openid = (JSON.parse(res.data.data)).openid;
        } else {
          console.log("未从自己后台获取到openid");
        }
      },
      fail: e => {
        console.log("访问自己后台获取openid服务异常");
      }
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
  globalData: {
    isFirstGetUserInfoDone: false,
    isAuth: true, //是否授权
    userInfo: null, //微信存储的用户信息
    localUserInfo: null, //本地数据库存储的用户信息
    // appid: 'wx07a87aa3fd0cc53a',
    appid: 'wx3f3f1f1c1fcdaae3',
    // secret: 'b96816b6e0d6e95d700443e88ba86396',
    secret: 'd9a18d334e4e94c7cd57a29936e55f23',
    openid: '',
    //开发人员需对此做修改，改为自己本地的开发服务IP
    requestIp: 'https://hptpd.haokuaidian.cn:443',

    serverIp: 'https://hptpd.haokuaidian.cn:443',
    localIp: 'http://10.39.40.52:8090'
  }
})