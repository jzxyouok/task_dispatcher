package com.hptpd.taskdispatcherserver.domain.vo;

import com.hptpd.taskdispatcherserver.domain.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-03-15 08:51
 * \*
 * \* Description:
 * \
 *
 * @author liucheng
 */

@Data
public class TaskVo {

    private String id;

    private String taskName;

    private String taskDescription;

    private Date startTime;

    private Date endTime;


    private boolean orient;

    private String workload;


    private String  taskState;


    private LabelVo labelVo;

    private List<RoleVo> roleVos;
}
