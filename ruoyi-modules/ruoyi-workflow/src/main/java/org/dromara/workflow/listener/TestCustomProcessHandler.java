package org.dromara.workflow.listener;

import lombok.extern.slf4j.Slf4j;
import org.dromara.workflow.annotation.FlowListenerAnnotation;
import org.dromara.workflow.flowable.strategy.FlowProcessEventHandler;
import org.springframework.stereotype.Component;

/**
 * 自定义监听测试
 *
 * @author may
 * @date 2023-12-27
 */
@Slf4j
@Component
@FlowListenerAnnotation(processDefinitionKey = "leave1")
public class TestCustomProcessHandler implements FlowProcessEventHandler {


    @Override
    public void handleProcess(String processInstanceId, String status) {
        log.info("流程实例ID:" + processInstanceId + ",状态:" + status);
    }
}
