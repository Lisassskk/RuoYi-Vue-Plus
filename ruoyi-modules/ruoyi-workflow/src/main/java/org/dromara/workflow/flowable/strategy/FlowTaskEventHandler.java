package org.dromara.workflow.flowable.strategy;

import org.flowable.task.service.impl.persistence.entity.TaskEntity;

/**
 * 流程任务监听
 *
 * @author may
 * @date 2023-12-27
 */
public interface FlowTaskEventHandler {

    /**
     * 执行办理任务监听
     *
     * @param taskEntity 任务
     */
    void handleTask(TaskEntity taskEntity);
}
