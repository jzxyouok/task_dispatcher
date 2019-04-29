package com.hptpd.taskdispatcherserver.controller;

import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;
import com.hptpd.taskdispatcherserver.service.IWeiXinApiService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-04-23 17:22
 * \*
 * \* Description:
 * \
 *
 * @author longwei
 */
@RestController
@RequestMapping("/weixin_api")
public class WeiXinApiController {
    @Resource(name = "iWeiXinApiService")
    private IWeiXinApiService iWeiXinApiService;

    /**
     * 获取openid
     * @param code String
     * @param secretCode String
     * @param appid String
     * @return Result
     */
    @RequestMapping(value = "/getOpenid", method = RequestMethod.GET)
    public Result getOpenid(@RequestParam String code, @RequestParam String secretCode, @RequestParam String appid) {
        return iWeiXinApiService.getOpenid(code, secretCode, appid);
    }

    /**
     * 获取openid
     * @param taskVo TaskVo
     * @return Result
     */
    @RequestMapping(value = "/sendTemplateMessage", method = RequestMethod.POST)
    public Result sendTemplateMessage(@RequestBody TaskVo taskVo) {
        return iWeiXinApiService.sendTemplateMessage(taskVo);
    }
}
