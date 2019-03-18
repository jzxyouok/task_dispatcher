package com.hptpd.taskdispatcherserver.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-15 09:47
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */
@Data
public class RoleVo {

    private String id;

    private String roleInfo;


    private List<UserVo> userVos;

}
