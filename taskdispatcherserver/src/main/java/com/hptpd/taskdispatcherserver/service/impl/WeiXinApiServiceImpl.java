package com.hptpd.taskdispatcherserver.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hptpd.taskdispatcherserver.common.util.HttpUtil;
import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.Staff;
import com.hptpd.taskdispatcherserver.domain.User;
import com.hptpd.taskdispatcherserver.domain.vo.StaffVo;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;
import com.hptpd.taskdispatcherserver.domain.vo.UserVo;
import com.hptpd.taskdispatcherserver.repository.TaskRep;
import com.hptpd.taskdispatcherserver.repository.UserRep;
import com.hptpd.taskdispatcherserver.service.IWeiXinApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-04-23 17:04
 * \*
 * \* Description:
 * \
 *
 * @author longwei
 */
@Service("iWeiXinApiService")
public class WeiXinApiServiceImpl implements IWeiXinApiService {
    private Logger logger = LoggerFactory.getLogger(BaseTaskServiceImpl.class);

    @Resource(name = "taskRep")
    private TaskRep taskRep;

    @Resource(name = "userRep")
    private UserRep userRep;

    @Override
    public Result getOpenid(String code, String secretCode, String appid) {
        if (null == code || code.isEmpty()) {
            return Result.setResult(Result.ERROR,"code值无效");
        }
        if (null == secretCode || secretCode.isEmpty()) {
            return Result.setResult(Result.ERROR,"secretCode值无效");
        }
        if (null == appid || appid.isEmpty()) {
            return Result.setResult(Result.ERROR,"appid值无效");
        }
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("secret", secretCode);
        paramMap.put("appid", appid);
        JSONObject jsonObj = HttpUtil.doGet("https://api.weixin.qq.com/sns/jscode2session", paramMap);
        return Result.setResult(Result.SUCCESS,"openid获取成功", jsonObj.toJSONString());
    }

    @Override
    public Result sendTemplateMessage(TaskVo taskVo) {
        if (null == taskVo) {
            return Result.setResult(Result.ERROR, "传入参数异常");
        }
        String templateId = "lPatSrtysrA6YEobWWwlPnNrHIcUWFktgoxw_i9gypI";
        Map<String, Object> paramMap = Maps.newHashMap();
        List<StaffVo> staffVos =taskVo.getStaffVos();
        for (StaffVo staffVo:staffVos){
            UserVo userVo =staffVo.getUserVo();
            Optional<User> optionalUser = userRep.findById(userVo.getId());
            if (!optionalUser.isPresent()) {
                continue;
            }
            paramMap.put("touser", userVo.getWeChat());
            HttpUtil.doPost("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN", paramMap);
        }
        return Result.setResult(Result.SUCCESS, "模板消息发送成功");
    }
}
