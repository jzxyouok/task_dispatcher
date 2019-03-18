package com.hptpd.taskdispatcherserver.domain.vo;

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

    /**
     *  发布者
     */
    private ProposerVo proposerVo;

    /**
     *  审核人
     */
    private AuditorVo auditorVo;

    private List<StaffVo> staffVos;
}
