package com.example.m13demo;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
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
public class AskLeaveTest_6x_full {
    private Logger logger = LoggerFactory.getLogger(AskLeaveTest_6x_full.class);

    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    ManagementService managementService;
    @Autowired
    SecurityUtil securityUtil;

    @Test
    @Tag("test")
    public void askLeaveTest() {
        // 外系统的关联单号
        String businessKey = "ASK00001";
        //流程定义的key
        String processDefinitionKey = "askleave_0521";

        String days = "4";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("days", days);

        //启动一个流程实例
        ProcessInstance processInstance = runtimeService
                .startProcessInstanceByKey(processDefinitionKey, businessKey,variables);
        logger.info(processInstance + " 流程启动成功");

        //user1 查找任务
        days = "2";
        String assignee = "user1";
        securityUtil.logInAs(assignee);
        Task task = taskService
                .createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskAssignee(assignee)
                .list().get(0);
        //user1 处理任务，即填写请假单，把天数变量赋值 >3 天总监要加签
        variables.put("days", days);
        taskService.complete(task.getId(), variables); //set variables有效 ！！
        logger.info(task.getId() + " 请假单填写完成，请教天数：" + days);

        //主管 查找并处理任务
        assignee = "user2";
        securityUtil.logInAs(assignee);
        List<Task> taskList = taskService
                .createTaskQuery()
                .processInstanceId(processInstance.getId()) //指定实例ID
                .taskAssignee(assignee) //指定用户
                .list();
        if (taskList.size() > 0) {
            task = taskList.get(0);
            taskService.complete(task.getId(), variables);
            logger.info(task.getId() + " 主管审批完成");
        } else {
            logger.info("主管无任务可审核");
        }

        //总监 查找并处理任务 判断是否需要加签
        assignee = "user4";
        securityUtil.logInAs(assignee);
        taskList = taskService
                .createTaskQuery()
                .processInstanceId(processInstance.getId()) //指定实例ID
                .taskAssignee(assignee) //指定用户
                .list();
        if (taskList.size() > 0) {
            task = taskList.get(0);
            taskService.complete(task.getId(), variables);
            logger.info(task.getId() + " 总监加签完成");
        } else {
            logger.info("总监无任务可加签");
        }

        //人事 查找并处理任务 归档
        assignee = "user3";
        securityUtil.logInAs(assignee);
        taskList = taskService
                .createTaskQuery()
                .processInstanceId(processInstance.getId()) //指定实例ID
                .taskAssignee(assignee) //指定用户
                .list();
        if (taskList.size() > 0) {
            task = taskList.get(0);
            taskService.complete(task.getId(), variables);
            logger.info(task.getId() + " 人事归档完成");
        } else {
            logger.info("人事无任务可归档");
        }

        //查看历史记录
        String processInstanceId = processInstance.getId();
        List<HistoricTaskInstance> HisList = historyService
                .createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime().desc()
                .processInstanceId(processInstanceId) //流程实例ID条件
                .list();
        logger.info("");
        for (HistoricTaskInstance hi : HisList) {
            logger.info("=============================================================");
            logger.info("getId                 ：" + hi.getId());
            logger.info("getProcessDefinitionId：" + hi.getProcessDefinitionId());
            logger.info("getProcessInstanceId  ：" + hi.getProcessInstanceId());
            logger.info("getName               ：" + hi.getName());
            logger.info("getStartTime          ：" + hi.getStartTime());
            logger.info("getEndTime            ：" + hi.getEndTime());
        }
    }


}

