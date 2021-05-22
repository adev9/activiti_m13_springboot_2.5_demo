package com.example.m13demo;

import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
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
public class ActivitiTest6x_05_HistoricProcessInstance {

    private Logger logger = LoggerFactory.getLogger(ActtivitiTest7x_01_processRuntime.class);

    @Autowired
    private HistoryService historyService;

    @Test
    @Tag("查询流程实例历史记录")
    public void HistoricTaskInstanceByUser() {
        String processInstanceId = "b8028d63-bad4-11eb-942c-a2ca6933c5ae";
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime().desc()
                //.processInstanceId(processInstanceId) //流程实例ID条件
                //.taskAssignee("user1")
                .list();
        for (HistoricTaskInstance hi : list) {
            logger.info("==================================================");
            logger.info("getId：" + hi.getId());
            logger.info("getProcessDefinitionId：" + hi.getProcessDefinitionId());
            logger.info("getProcessInstanceId：" + hi.getProcessInstanceId());
            logger.info("getName：" + hi.getName());
            logger.info("getStartTime：" + hi.getStartTime());
            logger.info("getEndTime：" + hi.getEndTime());
        }
    }

}
