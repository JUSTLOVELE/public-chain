package com.bc.server.backend.service.impl;

import com.bc.server.Main;
import com.bc.server.backend.service.block.BlockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes ={Main.class, BlockServiceTest.class})

public class BlockServiceTest {

    @Autowired
    private BlockService _blockService;

    @Test
    public void createGenesisBlockTest() {

        _blockService.createGenesisBlock("hello world");
    }
}
