package com.bc.server.backend.service.block;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BlockChain {

    private final List<Block> blockList = new ArrayList<>();

    /**
     * 新增区块
     * @param block
     */
    public void addBlock(Block block) {
        blockList.add(block);
    }

    public List<Block> getBlockChain() {
        return blockList;
    }

    @Override
    public String toString() {

        StringBuffer sb = new StringBuffer();

        for(Block block: blockList) {
            sb.append(block.toString() + "\n");
        }

        return sb.toString();
    }
}
