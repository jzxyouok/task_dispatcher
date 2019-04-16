/**
 \* Created with IntelliJ IDEA.
 \* User: 彭诗杰
 \* Date: 2018/5/26
 \* Time: 11:55
 \* Description: 该文件用于记录常用的下拉框中等静态值
 \*/

export default {
  ROLE: {
    PROPOSER: "proposer",  //发布者
    AUDITOR: "auditor",  //审批者
    STAFF: "staff",  //承接者
    EXPERT: "expert" //专家组
  },
  TASK_STATUS: {
    AUDITING: "待审核",
    WAIT_CLAIM: "任务待认领",
    TASK_DOING: "任务执行中",
    ISSUE_REJECTED: "发布被驳回",
    EVALUATING: "完成待评估",
    EXPERT_EVALUATING: "待专家组评估",
    COMMIT_REJECTED: "完成被驳回",
    DONE: "已完成"
  }
}