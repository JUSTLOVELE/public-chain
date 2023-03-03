package com.bc.server.backend.service.impl;

import com.bc.server.Main;
import com.bc.server.backend.service.api.BlockService;
import com.bc.server.backend.service.block.Block;
import com.bc.server.backend.service.block.BlockChain;
import com.bc.server.utils.Constant;
import com.bc.server.utils.ObjectToByteUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes ={Main.class, BlockServiceTest.class})
public class BlockServiceTest {

    @Autowired
    private BlockService _blockService;

    @Autowired
    private BlockChain _blockChain;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void runTest() throws IOException, ClassNotFoundException {

//        Block block1 = (Block) redisTemplate.opsForValue().get(Constant.Key.LAST);
//        Block block2 = _blockService.createAndAddBlock("block2", block1.getHash(), 2);
//        Block block3 = _blockService.createAndAddBlock("block3", block2.getHash(), 3);
//        Block block4 = _blockService.createAndAddBlock("block4", block3.getHash(), 4);
//        Block block5 = _blockService.createAndAddBlock("block5", block4.getHash(), 5);
    }

    @Test
    public void blockTest() {

        Block block1 = _blockService.createGenesisBlock("hello world");
        Block block2 = _blockService.createAndAddBlock("block2", block1.getHash(), 2);
        Block block3 = _blockService.createAndAddBlock("block2", block2.getHash(), 3);
        System.out.println(_blockChain.toString());
    }
}
