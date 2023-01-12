package BlockChain

import (
	"github.com/boltdb/bolt"
	"log"
)

//迭代器
func (blockchain *Blockchain) Iterator() *BlockchainIterator {
	return &BlockchainIterator{blockchain.Tip, blockchain.DB}
}

type BlockchainIterator struct {
	CurrentHash []byte
	DB          *bolt.DB
}

func (blockchainIterator *BlockchainIterator) Next() *Block {
	var block *Block
	err := blockchainIterator.DB.View(func(tx *bolt.Tx) error {
		b := tx.Bucket([]byte(blockTableName))
		if b != nil {
			currentBlockBytes := b.Get(blockchainIterator.CurrentHash)
			//获取到当前currentHash对应的区块
			block = DeserializeBlock(currentBlockBytes)
			//更新迭代器里面currentHash
			blockchainIterator.CurrentHash = block.PreBlockHash
		}
		return nil
	})

	if err != nil {
		log.Panic(err)
	}
	return block
}
