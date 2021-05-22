package com.example.m13demo;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
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
public class ActivitiTest6x_01_Deploy {
    private Logger logger = LoggerFactory.getLogger(ActivitiTest6x_01_Deploy.class);

    @Autowired
    private RepositoryService repositoryService;

    @Test
    @Tag("获得部署列表")
    public void testGetDeploy() {
        List<Deployment> list = repositoryService.createDeploymentQuery().list();

        for (Deployment d : list) {
            logger.info("==================================================");
            logger.info("getId: " + d.getId());
            logger.info("getVersion: " + d.getVersion());
            logger.info("getName: " + d.getName());
            logger.info("getDeploymentTime: " + d.getDeploymentTime());
            logger.info("getKey: " + d.getKey());
        }
    }


}
