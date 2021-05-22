package com.example.m13demo;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ActtivitiTest7x_01_processRuntime {

    private Logger logger = LoggerFactory.getLogger(ActtivitiTest7x_01_processRuntime.class);

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private ProcessRuntime processRuntime;

    @Test
    @Tag("启动流程实例")
    public void startProcessInstance() {
        securityUtil.logInAs("user1");
        String processDefiniedKey = "askleave_0521";
        ProcessInstance processInstance = processRuntime.start(
                ProcessPayloadBuilder.start()
                        .withProcessDefinitionKey(processDefiniedKey) //key多次部署可能重复，需要加限定条件
                        .withName(processDefiniedKey+"流程实例名称")
                        .withVariable("days", "2")
                        .withBusinessKey("自定义bKey")
                        .build()
        );
        logger.info("==================================================");
        logger.info("getId：" + processInstance.getId());
        logger.info("getName：" + processInstance.getName());
        logger.info("getStartDate：" + processInstance.getStartDate());
        logger.info("getStatus：" + processInstance.getStatus());
        logger.info("getProcessDefinitionId：" + processInstance.getProcessDefinitionId());
        logger.info("getProcessDefinitionKey：" + processInstance.getProcessDefinitionKey());
    }

    @Test
    @Tag("获取流程实例")
    public void getProcessInstance() {
        securityUtil.logInAs("user1");
        Page<ProcessInstance> processInstancePage = processRuntime
                .processInstances(Pageable.of(0, 100));
        List<ProcessInstance> list = processInstancePage.getContent();
        for (ProcessInstance pi : list) {
            logger.info("==================================================");
            logger.info("getId：" + pi.getId());
            logger.info("getName：" + pi.getName());
            logger.info("getStartDate：" + pi.getStartDate());
            logger.info("getStatus：" + pi.getStatus());
            logger.info("getProcessDefinitionId：" + pi.getProcessDefinitionId());
            logger.info("getProcessDefinitionKey：" + pi.getProcessDefinitionKey());
        }
    }


}
