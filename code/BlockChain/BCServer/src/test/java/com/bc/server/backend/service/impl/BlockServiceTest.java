package com.bc.server.backend.service.impl;

import com.bc.server.Main;
import com.bc.server.backend.service.api.BlockService;
import com.bc.server.backend.service.block.Block;
import com.bc.server.backend.service.block.BlockChain;
import com.bc.server.utils.ObjectToByteUtils;
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

    @Autowired
    private BlockChain _blockChain;

    @Test
    public void writeToDiskTest() {
        Block block1 = _blockService.createGenesisBlock("hello world");
        byte[] bytes = ObjectToByteUtils.toByteArray(block1);

    }

    @Test
    public void blockTest() {

        Block block1 = _blockService.createGenesisBlock("hello world");
        Block block2 = _blockService.createAndAddBlock("block2", block1.getHash());
        Block block3 = _blockService.createAndAddBlock("block2", block2.getHash());
        System.out.println(_blockChain.toString());
    }
}
