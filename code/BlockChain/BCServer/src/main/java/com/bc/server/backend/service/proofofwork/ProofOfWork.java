package com.bc.server.backend.service.proofofwork;

import com.bc.server.backend.service.block.impl.Block;
import com.bc.server.model.PowCompute;
import com.bc.server.utils.ByteUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;

/**
 * 工作量证明对象
 */
public class ProofOfWork {

    private static final int targetBit = 4;

    private BigInteger target;

    private Block block;

    public ProofOfWork(Block block, BigInteger target) {
        this.block = block;
        this.target = target;
    }

    private byte[] prepareData(long nonce) {

        return ByteUtils.merge(block.getPreBlockHash(),
                block.getData(),
                ByteUtils.toBytes(block.getTimestamp().getTime()),
                ByteUtils.toBytes(targetBit),
                ByteUtils.toBytes(nonce));
    }

    public static ProofOfWork newProofOfWork(Block block) {

        BigInteger target = BigInteger.valueOf(1).shiftLeft((256 - targetBit));
        ProofOfWork proofOfWork = new ProofOfWork(block, target);
        return proofOfWork;
    }

    public PowCompute run() {

        long nonce = 0;
        String shaHex = "";

        while (nonce < Long.MAX_VALUE) {

            byte[] data = this.prepareData(nonce);
            shaHex = DigestUtils.sha256Hex(data);

            if (new BigInteger(shaHex, 16).compareTo(this.target) == -1) {
                break;
            } else {
                nonce++;
            }
        }
        return new PowCompute(nonce, shaHex);
    }

    public BigInteger getTarget() {
        return target;
    }

    public void setTarget(BigInteger target) {
        this.target = target;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
