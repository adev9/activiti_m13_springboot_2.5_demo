package com.example.m13demo;

import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ActtivitiTest7x_01_taskRuntime {

    private Logger logger = LoggerFactory.getLogger(ActtivitiTest7x_01_taskRuntime.class);

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private TaskRuntime taskRuntime;


    @Test
    @Tag("获取当前登录用户任务")
    public void getTasks() {
        String userName = "user4";
        //模拟登录，配合spring security框架
        securityUtil.logInAs(userName);

        Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 100));
        List<Task> list = tasks.getContent();
        for (Task tk : list) {
            logger.info("-------------------");
            logger.info("getId：" + tk.getId());
            logger.info("getName：" + tk.getName());
            logger.info("getStatus：" + tk.getStatus());
            logger.info("getCreatedDate：" + tk.getCreatedDate());
            if (tk.getAssignee() == null) {
                //候选人为当前登录用户，null的时候需要前端拾取,为null基本上是候选执行人才会出现
                logger.info("Assignee：待拾取任务");
            } else {
                logger.info("Assignee：" + tk.getAssignee());
            }
        }
    }

    @Test
    @Tag("完成一个任务")
    public void completeTask() {
        // 用户
        String userName = "user4";
        // 任务ID
        String taskId = "d63fb95d-bad6-11eb-8a5d-a2ca6933c5ae";
        securityUtil.logInAs(userName);

        Task task = taskRuntime.task(taskId);

        //null 则为 候选任务，需要现claim
        if (task.getAssignee() == null) {
            taskRuntime.claim(TaskPayloadBuilder.claim()
                    .withTaskId(task.getId())
                    .build());
        }
        taskRuntime.complete(TaskPayloadBuilder
                .complete()
                .withTaskId(task.getId())
                .build());
        logger.info(taskId + "任务执行完成");
    }

}
