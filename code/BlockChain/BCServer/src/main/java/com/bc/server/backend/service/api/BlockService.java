package com.bc.server.backend.service.api;

import com.bc.server.backend.service.block.Block;
import com.bc.server.backend.service.transaction.Transaction;

public interface BlockService {

    /**
     * 创建创世区块
     * 这个api不应该被action调用,应该是项目启动的时候检测是否有创建，若无则调用这个
     */
    public Block createGenesisBlock(Object data);

    /**
     * 添加区块
     * @param transactions
     * @param previousHash
     * @param height
     * @return
     */
    public Block createAndAddBlock(Transaction[] transactions, String previousHash, int height);
}
