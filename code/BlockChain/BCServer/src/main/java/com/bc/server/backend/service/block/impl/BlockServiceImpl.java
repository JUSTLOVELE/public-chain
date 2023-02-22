package com.bc.server.backend.service.block.impl;

import com.bc.server.backend.service.block.BlockService;
import com.bc.server.backend.service.proofofwork.ProofOfWork;
import com.bc.server.utils.ObjectToByteUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BlockServiceImpl implements BlockService {

    private final static Log _logger = LogFactory.getLog(BlockServiceImpl.class);

    @Override
    public void createGenesisBlock(Object data) {

        byte[] preBlockHash = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        Block block = new Block(1, preBlockHash, ObjectToByteUtils.toByteArray(data), new Date(), null, 0);
        ProofOfWork proofOfWork = ProofOfWork.newProofOfWork(block);
        proofOfWork.run();
    }


}
