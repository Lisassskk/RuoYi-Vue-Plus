package org.dromara.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.dromara.workflow.annotation.FlowListenerAnnotation;
import org.dromara.workflow.flowable.strategy.FlowTaskEventHandler;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Component;

/**
 * 自定义监听测试
 *
 * @author may
 * @date 2023-12-27
 */
@Slf4j
@Component
@FlowListenerAnnotation(processDefinitionKey = "leave1", taskDefId = "sid-31C52262-04C3-43D8-A3FB-8434017A6572")
public class TestCustomTaskHandler implements FlowTaskEventHandler {

    @Override
    public void handleTask(Task task) {
        log.info("任务名称:" + task.getName());
    }
}
