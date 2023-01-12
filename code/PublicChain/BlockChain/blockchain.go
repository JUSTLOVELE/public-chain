package BlockChain

import (
	"github.com/boltdb/bolt"
	"log"
)

const dbName = "blockchain.db"  //数据库名
const blockTableName = "blocks" //表名

type Blockchain struct {
	Tip []byte //最新的区块Hash
	DB  *bolt.DB
}

//创建创世块的区块链
func CreateBlockChainWithInitBlock() *Blockchain {

	db, err := bolt.Open(dbName, 0600, nil)

	if err != nil {
		log.Fatal()
	}

	var blockHash []byte

	err = db.Update(func(tx *bolt.Tx) error {

		bucket, err := tx.CreateBucketIfNotExists([]byte(blockTableName))

		if err != nil {
			log.Panic(err)
		}

		if bucket != nil {
			//创建创世区块
			initBlock := CreateInitBlock("Gensis Data...")
			//创世区块存储到表中
			err := bucket.Put(initBlock.Hash, initBlock.SerizalizeBlock())

			if err != nil {
				//创建失败
				log.Panic(err)
			}
			//存储最新的区块的hash
			err = bucket.Put([]byte("l"), initBlock.Hash)

			if err != nil {
				//创建失败
				log.Panic(err)
			}
			blockHash = initBlock.Hash
		}
		return nil
	})

	return &Blockchain{blockHash, db}
}
