package com.bc.server.backend.service.api.impl;

import com.bc.server.backend.service.api.BlockService;
import com.bc.server.backend.service.block.Block;
import com.bc.server.backend.service.block.BlockChain;
import com.bc.server.backend.service.proofofwork.ProofOfWork;
import com.bc.server.model.PowResult;
import com.bc.server.backend.service.transaction.Transaction;
import com.bc.server.utils.Constant;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author yangzl 2023.03.02
 * @version 1.00.00
 * @Description:
 * @history:
 */
@Service
@Transactional
public class BlockServiceImpl implements BlockService {

    private final static Log _logger = LogFactory.getLog(BlockServiceImpl.class);

    @Autowired
    private BlockChain _blockChain;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Block createGenesisBlock(Object data) {

        byte[] preBlockHash = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Transaction transaction = new Transaction();
        transaction.setData(data);
        transaction.setPk(Constant.PK);
        Transaction[] transactions = new Transaction[1];
        transactions[0] = transaction;

        return createAndAddBlock(transactions, Hex.encodeHexString(preBlockHash), 1);
    }

    @Override
    public Block createAndAddBlock(Transaction[] transactions, String previousHash, int height){

        for(Transaction transaction: transactions) {
            transaction.sign(Constant.SK);
        }

        Block block = new Block(height, previousHash, transactions, new Date(), null, 0);
        ProofOfWork proofOfWork = ProofOfWork.newProofOfWork(block);
        PowResult result = proofOfWork.run();
        block.setHash(result.hash());
        block.setNonce(result.nonce());
        _blockChain.addBlock(block);
        redisTemplate.opsForValue().set(Constant.Key.LAST, block);
        redisTemplate.opsForValue().set(block.getHash(), block);

        return block;
    }
}
