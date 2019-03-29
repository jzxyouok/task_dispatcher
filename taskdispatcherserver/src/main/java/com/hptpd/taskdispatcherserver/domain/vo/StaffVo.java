package com.hptpd.taskdispatcherserver.domain.vo;

import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.domain.Staff;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-18 11:19
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Data
public class StaffVo {

    private String id;

    private UserVo userVo;

    private String name;

    public static List<StaffVo> staffToVo(List<Staff> staffs){
        List<StaffVo> staffVos = Lists.newArrayList();
        for (Staff staff:staffs){
            StaffVo staffVo =new StaffVo();
            UserVo userVo = new UserVo();
            AbstractMyBeanUtils.copyProperties(staff,staffVo);
            AbstractMyBeanUtils.copyProperties(staff.getUser(),userVo);
            staffVo.setUserVo(userVo);
            staffVos.add(staffVo);
        }
        return staffVos;
    }
}
