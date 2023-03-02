package com.bc.server.env;


import com.bc.server.backend.service.api.BlockService;
import com.bc.server.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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

    @Override
    public void run(ApplicationArguments args) throws Exception {

        _blockService.createGenesisBlock("this is yzl");
//        if(redisTemplate.hasKey(Constant.Key.LAST)) {
//            //需要从redis中还原区块链
//        }else {
//            //创建创世区块
//            _blockService.createGenesisBlock("this is yzl");
//        }
    }
}
