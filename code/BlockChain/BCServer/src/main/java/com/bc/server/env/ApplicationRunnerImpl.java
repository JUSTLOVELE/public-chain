package com.bc.server.env;


import cn.hutool.core.util.StrUtil;
import com.bc.server.backend.service.api.BlockService;
import com.bc.server.backend.service.block.Block;
import com.bc.server.backend.service.block.BlockChain;
import com.bc.server.utils.Constant;
import com.bc.server.utils.ObjectToByteUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private final static Log _logger = LogFactory.getLog(ApplicationRunnerImpl.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {

        initPkAndSk();

        if(redisTemplate.hasKey(Constant.Key.LAST)) {
            //restore blockchain from redis
            restore();
        }else {
            //create genesis block
            _blockService.createGenesisBlock("this is yzl");
        }
    }

    /**
     * set pk、sk
     */
    private void initPkAndSk() {

        try {

            File file = new File("../BCServer/src/main/resources");
            String pkPath = file.getCanonicalPath() + "\\pk.txt";
            Constant.PK = Files.readString(Paths.get(pkPath));
            String skPath = file.getCanonicalPath() + "\\sk.txt";
            Constant.SK = Files.readString(Paths.get(skPath));

        }catch (Exception e) {
            _logger.error("", e);
        }
    }

    private void restore() {

        Block lastBlock = (Block) redisTemplate.opsForValue().get(Constant.Key.LAST);

        while(lastBlock != null) {

            _blockChain.addBlock(lastBlock);
            Block block = (Block) redisTemplate.opsForValue().get(lastBlock.getPreBlockHash());
            lastBlock = block;
        }

        Collections.reverse(_blockChain.getBlockChain());
        System.out.println("ok");
    }
}
