package com.example.m13demo;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
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
public class ActivitiTest6x_02_ProcessDefinition {

    private Logger logger = LoggerFactory.getLogger(ActtivitiTest7x_01_processRuntime.class);

    @Autowired
    private RepositoryService repositoryService;

    @Test
    @Tag("获得流程定义列表")
    public void testGetProcessDefinition() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .list();
        for (ProcessDefinition pd : list) {
            logger.info("==================================================");
            logger.info("getId: " + pd.getId());
            logger.info("getVersion: " + pd.getVersion());
            logger.info("getName: " + pd.getName());
            logger.info("getDeploymentTime: " + pd.getResourceName());
            logger.info("getKey: " + pd.getKey());
        }
    }

    @Test
    @Tag("删除流程定义")
    public void delDefinition() {
        String pdID = "44b15cfe-ce3e-11ea-92a3-dcfb4875e032"; //事先查询定位
        repositoryService.deleteDeployment(pdID, true);
        logger.info("删除流程定义成功");
    }


}
