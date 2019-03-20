package com.hptpd.taskdispatcherserver.domain.vo;

import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.domain.Task;
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
    public static boolean UN_ORIENT=false;

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


    /**
     * 转为VO
     * @param tasks
     * @return
     */
    public static List<TaskVo> convertTask(List<Task> tasks){
        List<TaskVo> taskVos = Lists.newArrayList();
        for (Task task:tasks){
            TaskVo taskVo =new TaskVo();
            AbstractMyBeanUtils.copyProperties(task,taskVo);
            taskVos.add(taskVo);
        }
        return taskVos;
    }
}
