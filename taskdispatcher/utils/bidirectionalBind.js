/**
 \* Created with 微信开发者工具.
 \* @author: 龙威
 \* @time: 2019/3/13 10:14
 \* Description: 模仿Vue v-model双向绑定属性
 \* Usage:
 \* 1、const { inputgetName } = require('../../utils/bidirectionalBind.js');
 \* 2、在input的bindchange的调用方法中使用 inputgetName(e, this);
 ---------------------
  作者：lizhen_software
来源：CSDN
原文：https://blog.csdn.net/lizhen_software/article/details/81632229
版权声明：本文为博主原创文章，转载请附上博文链接！
 \*/
module.exports = {

  bidirectionalBind(e, page) {

    let name = e.currentTarget.dataset.name;

    let nameMap = {}

    if (name.indexOf('.')) {

      let nameList = name.split('.')

      if (page.data[nameList[0]]) {

        nameMap[nameList[0]] = page.data[nameList[0]]

      } else {

        nameMap[nameList[0]] = {}

      }

      nameMap[nameList[0]][nameList[1]] = e.detail.value || (e.detail.detail ? e.detail.detail.value : "")

    } else {

      nameMap[name] = e.detail.value || (e.detail.detail ? e.detail.detail.value : "")

    }

    page.setData(nameMap)

  }
}