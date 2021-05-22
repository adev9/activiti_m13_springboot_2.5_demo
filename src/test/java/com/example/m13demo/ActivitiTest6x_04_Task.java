package com.example.m13demo;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author GGstudy
 */
@SpringBootTest
public class ActivitiTest6x_04_Task {

    private Logger logger = LoggerFactory.getLogger(ActtivitiTest7x_01_processRuntime.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    SecurityUtil securityUtil;

    @Test
    @Tag("查找待处理的任务，可以带不同的条件")
    public void testFindPersonalTask() {
        String assignee = "user2";
        List<Task> list = taskService
                .createTaskQuery()
                //.processDefinitionKey(processDefinitionKey) //指定流程定义key
                //.processInstanceId("") //指定实例ID
                .taskAssignee(assignee) //指定用户
                .list();
        for (Task task : list) {
            logger.info("==================================================");
            logger.info("任务id: " + task.getId());
            logger.info("流程实例id: " + task.getProcessInstanceId());
            logger.info("任务名称: " + task.getName());
            logger.info("任务执行人: " + task.getAssignee());

        }
    }

    @Test
    @Tag("执行任务")
    public void completeTask() {
        //登录 不登陆会warn：java.lang.SecurityException: Invalid authenticated principal
        securityUtil.logInAs("user2");

        // taskID 从查找
        String taskId = "8e78f00b-bad6-11eb-b508-a2ca6933c5ae";
        // 处理参数
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("days", "4");
        taskService.complete(taskId, variables);
        logger.info(taskId + " 处理完成！");
    }
}
