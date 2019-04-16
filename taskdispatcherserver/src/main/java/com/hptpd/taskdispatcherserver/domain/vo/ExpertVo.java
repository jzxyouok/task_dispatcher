package com.hptpd.taskdispatcherserver.domain.vo;

import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.domain.Expert;
import lombok.Data;
import java.util.List;

@Data
public class ExpertVo {
    private String id;

    private UserVo userVo;

    private String name;

    public static List<ExpertVo> expertToVo(List<Expert> experts){
        List<ExpertVo> expertVos = Lists.newArrayList();
        for (Expert expert:experts){
            ExpertVo expertVo =new ExpertVo();
            UserVo userVo = new UserVo();
            AbstractMyBeanUtils.copyProperties(expert,expertVo);
            AbstractMyBeanUtils.copyProperties(expert.getUser(),userVo);
            expertVo.setUserVo(userVo);
            expertVos.add(expertVo);
        }
        return expertVos;
    }
}
