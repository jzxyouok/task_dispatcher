package com.hptpd.taskdispatcherserver.service;

import com.hptpd.taskdispatcherserver.component.Result;
import com.hptpd.taskdispatcherserver.domain.vo.TaskVo;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-04-23 17:04
 * \*
 * \* Description:
 * \
 *
 * @author longwei
 */
public interface IWeiXinApiService {
    /**
     * 获取openid
     * @param code String
     * @param secretCode String
     * @param appid String
     * @return Result
     */
    Result getOpenid(String code, String secretCode, String appid);

    /**
     * 获取openid
     * @param taskVo TaskVo
     * @return Result
     */
    Result sendTemplateMessage(TaskVo taskVo);
}
