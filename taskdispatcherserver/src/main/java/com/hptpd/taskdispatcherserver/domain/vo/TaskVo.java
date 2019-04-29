package com.hptpd.taskdispatcherserver.domain.vo;

import com.google.common.collect.Lists;
import com.hptpd.taskdispatcherserver.common.util.AbstractMyBeanUtils;
import com.hptpd.taskdispatcherserver.domain.Label;
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
    public final static String AUDITING = "待审核";
    public final static String WAIT_CLAIM = "任务待认领";
    public final static String TASK_DOING = "任务执行中";
    public final static String ISSUE_REJECTED = "发布被驳回";
    public final static String EVALUATING = "工作量二次评估";
    public final static String WAIT_ACCEPT = "任务验收中";
    public final static String EXPERT_EVALUATING = "待专家组评估";
    public final static String COMMIT_REJECTED = "完成被驳回";
    public final static String DONE = "已完成";

    public static boolean UN_ORIENT=false;

    private String id;

    private String taskName;

    private String taskDescription;

    private Date startTime;

    private Date endTime;


    private boolean orient;

    private String workload;

    private String realWorkload;


    private String  taskState;

    private String comment;

    private String expertComment;

    private String reviewReason;

    private ProjectVo projectVo;

    private List<LabelVo> labelVos;

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
            List<LabelVo> labelVos = Lists.newArrayList();
            for (Label label : task.getLabels()) {
                LabelVo labelVo = new LabelVo();
                AbstractMyBeanUtils.copyProperties(label,labelVo);
                labelVos.add(labelVo);
            }
            taskVo.setLabelVos(labelVos);
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
