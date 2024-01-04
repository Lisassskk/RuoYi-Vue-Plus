package org.dromara.workflow.flowable.strategy;


/**
 * 流程监听
 *
 * @author may
 * @date 2023-12-27
 */
public interface FlowProcessEventHandler {

    /**
     * 执行办理任务监听 当状态为草稿，驳回，撤销时说明流程为申请人节点提交办理
     *
     * @param processInstanceId 流程实例id
     * @param status            状态
     */
    void handleProcess(String processInstanceId, String status);
}
