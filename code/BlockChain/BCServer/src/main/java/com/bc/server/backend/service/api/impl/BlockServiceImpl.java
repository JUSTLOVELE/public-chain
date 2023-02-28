package com.bc.server.backend.service.api.impl;

import com.bc.server.backend.service.api.BlockService;
import com.bc.server.backend.service.block.Block;
import com.bc.server.backend.service.block.BlockChain;
import com.bc.server.backend.service.proofofwork.ProofOfWork;
import com.bc.server.model.PowResult;
import com.bc.server.utils.ObjectToByteUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BlockServiceImpl implements BlockService {

    private final static Log _logger = LogFactory.getLog(BlockServiceImpl.class);

    @Autowired
    private BlockChain _blockChain;

    @Override
    public Block createGenesisBlock(Object data) {

        byte[] preBlockHash = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        return createAndAddBlock(data, Hex.encodeHexString(preBlockHash));
    }

    @Override
    public Block createAndAddBlock(Object data, String previousHash){

        Block block = new Block(1, previousHash, ObjectToByteUtils.toByteArray(data), new Date(), null, 0);
        ProofOfWork proofOfWork = ProofOfWork.newProofOfWork(block);
        PowResult result = proofOfWork.run();
        block.setHash(result.hash());
        block.setNonce(result.nonce());
        _blockChain.addBlock(block);

        return block;
    }
}
