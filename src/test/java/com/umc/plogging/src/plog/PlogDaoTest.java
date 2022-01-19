package com.umc.plogging.src.plog;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PlogDaoTest {

    @Autowired
    private PlogDao plogDao;

    @Test
    public void DAOTest(){
        log.info("{}", "test");
    }

}