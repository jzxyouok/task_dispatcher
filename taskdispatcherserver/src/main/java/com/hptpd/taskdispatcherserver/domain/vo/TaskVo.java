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
    public static String AUDITING = "待审核";
    public static String EXPERT_AUDITING = "待专家组审核";
    public static String PASSED = "已发布";
    public static String REJECTED = "已驳回";

    public static boolean UN_ORIENT=false;

    private String id;

    private String taskName;

    private String taskDescription;

    private Date startTime;

    private Date endTime;


    private boolean orient;

    private String workload;


    private String  taskState;

    private String comment;

    private String expertComment;

    private String reviewReason;

    private ProjectVo projectVo;

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
            ProjectVo projVo = new ProjectVo();
            AbstractMyBeanUtils.copyProperties(task.getProject(), projVo);
            taskVo.setProjectVo(projVo);
            ProposerVo proposerVo = new ProposerVo();
            AbstractMyBeanUtils.copyProperties(task.getProposer(), proposerVo);
            UserVo proposerUserVo = new UserVo();
            AbstractMyBeanUtils.copyProperties(task.getProposer().getUser(), proposerUserVo);
            proposerVo.setUserVo(proposerUserVo);
            taskVo.setProposerVo(proposerVo);
            taskVos.add(taskVo);
        }
        return taskVos;
    }
}
