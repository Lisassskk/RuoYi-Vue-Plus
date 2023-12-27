package org.dromara.workflow.flowable.strategy;


/**
 * 流程监听
 *
 * @author may
 * @date 2023-12-27
 */
public interface FlowProcessEventHandler {

    /**
     * 执行办理任务监听
     *
     * @param processInstanceId 流程实例id
     * @param status            状态
     */
    void handleProcess(String processInstanceId, String status);
}
