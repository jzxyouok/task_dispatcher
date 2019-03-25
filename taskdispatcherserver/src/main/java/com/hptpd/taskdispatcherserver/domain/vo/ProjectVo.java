package com.hptpd.taskdispatcherserver.domain.vo;

import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.common.util.PinyinUtil;
import com.hptpd.taskdispatcherserver.domain.Project;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-14 17:11
 * \*
 * \* Description:
 * \
 *
 * @author longwei
 */
@Data
public class ProjectVo {
    private String id;

    private String name;

    private String department;

    private String firstLetter;

    public static List<ProjectVo> projectToVo(List<Project> projs){
        List<ProjectVo> projVos = Lists.newArrayList();
        for (Project proj:projs){
            ProjectVo projVo = new ProjectVo();
            AbstractMyBeanUtils.copyProperties(proj,projVo);
            projVo.setFirstLetter(PinyinUtil.getFirstLetter(projVo.name));
            projVos.add(projVo);
        }
        return projVos;
    }
}
