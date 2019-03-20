package com.hptpd.taskdispatcherserver.domain.vo;


import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.common.util.PinyinUtil;
import com.hptpd.taskdispatcherserver.domain.User;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 17:11
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Data
public class UserVo {

    private String id;

    private String weChat;



    private String name;



    private String telephone;



    private String position;

    private String firstLetter;


    public static List<UserVo> userToVo(List<User> users){
        List<UserVo> userVos = Lists.newArrayList();
        for (User user:users){
            UserVo userVo = new UserVo();
            AbstractMyBeanUtils.copyProperties(user,userVo);
            userVo.setFirstLetter(PinyinUtil.getFirstLetter(userVo.name));
            userVos.add(userVo);
        }
        return userVos;
    }


}
