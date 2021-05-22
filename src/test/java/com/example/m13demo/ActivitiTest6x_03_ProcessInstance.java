package com.example.m13demo;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author GGstudy
 */
@SpringBootTest
public class ActivitiTest6x_03_ProcessInstance {

    private Logger logger = LoggerFactory.getLogger(ActivitiTest6x_03_ProcessInstance.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;

    @Test
    @Tag("开始一个流程实例")
    public void startProcessInstance() {
        String processDefinitionKey = "askleave_0521";
        //myProcess_Part1 Process_1 Process_2 Process_3
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(processDefinitionKey, processDefinitionKey + " bzcode");
        logger.info("getId: " + processInstance.getId());
        logger.info("getName: " + processInstance.getName());
        logger.info("getBusinessKey: " + processInstance.getBusinessKey());
        logger.info("getProcessDefinitionId: " + processInstance.getProcessDefinitionId());
    }

    @Test
    @Tag("查询流程实例")
    public void testQueryProcessInstance() {
        String processDefinitionKey = "askleave_0521";
        List<ProcessInstance> list = runtimeService
                .createProcessInstanceQuery()
                .processDefinitionKey(processDefinitionKey)
                .list();

        for (ProcessInstance processInstance : list) {
            logger.info("==================================================");
            logger.info("实例id: : " + processInstance.getId());
            logger.info("实例name: : " + processInstance.getName());
            logger.info("实例version: : " + processInstance.getAppVersion());
            logger.info("实例id: : " + processInstance.getDeploymentId());
            logger.info("实例BusinessKey: : " + processInstance.getBusinessKey());
        }
    }


}
