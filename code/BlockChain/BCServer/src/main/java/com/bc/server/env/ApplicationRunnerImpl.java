package com.bc.server.env;


import cn.hutool.core.util.StrUtil;
import com.bc.server.backend.service.api.BlockService;
import com.bc.server.backend.service.block.Block;
import com.bc.server.backend.service.block.BlockChain;
import com.bc.server.utils.Constant;
import com.bc.server.utils.ObjectToByteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yangzl 2020.12.22
 * @version 1.00.00
 * @Description:启动后运行
 * @history:
 */
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private BlockService _blockService;

    @Autowired
    private BlockChain _blockChain;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(redisTemplate.hasKey(Constant.Key.LAST)) {
            //需要从redis中还原区块链
            restore();
        }else {
            //创建创世区块
            _blockService.createGenesisBlock("this is yzl");
        }
    }

    private void restore() {

        Block lastBlock = (Block) redisTemplate.opsForValue().get(Constant.Key.LAST);

        while(lastBlock != null) {

            _blockChain.addBlock(lastBlock);
            System.out.println(ObjectToByteUtils.byteToObject(lastBlock.getData()));
            Block block = (Block) redisTemplate.opsForValue().get(lastBlock.getPreBlockHash());
            lastBlock = block;
        }

        Collections.reverse(_blockChain.getBlockChain());
        System.out.println("ok");
    }
}
