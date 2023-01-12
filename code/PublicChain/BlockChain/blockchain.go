package BlockChain

import (
	"fmt"
	"github.com/boltdb/bolt"
	"log"
	"math/big"
	"os"
	"time"
)

const dbName = "blockchain.db"  //数据库名
const blockTableName = "blocks" //表名

type Blockchain struct {
	Tip []byte //最新的区块Hash
	DB  *bolt.DB
}

//遍历输出所有区块的信息
func (blc *Blockchain) PrintChain() {

	iterator := blc.Iterator()

	for {

		block := iterator.Next()
		fmt.Printf("Height:%d\n", block.Height)
		fmt.Printf("PrevBlockHash:%x\n", block.PreBlockHash)
		fmt.Printf("Data:%s\n", block.Data)
		fmt.Printf("Timestamp:%s\n", time.Unix(block.Timestamp, 0).Format("2006-01-02 03:04:05 PM"))
		fmt.Printf("Hash:%x\n", block.Hash)
		fmt.Printf("Nonce:%d\n", block.Nonce)
		fmt.Println()

		var hashInt big.Int
		hashInt.SetBytes(block.PreBlockHash)

		if big.NewInt(0).Cmp(&hashInt) == 0 {
			break
		}
	}
}

func (blc *Blockchain) AddBlockToBlockchain(data string) {

	err := blc.DB.Update(func(tx *bolt.Tx) error {
		//1、获取表
		bucket := tx.Bucket([]byte(blockTableName))
		//2、创建区块
		if bucket != nil {
			//先获取最新区块
			blockBytes := bucket.Get(blc.Tip)
			//进行反序列化
			block := DeserializeBlock(blockBytes)
			//增加新区块,将区块序列化并且存储到数据库中
			newBlock := NewBlock(data, block.Height+1, block.Hash)
			err := bucket.Put(newBlock.Hash, newBlock.SerizalizeBlock())

			if err != nil {
				log.Panic(err)
			}
			//4.更新数据库里面"l"对应的hash
			err = bucket.Put([]byte("l"), newBlock.Hash)
			if err != nil {
				log.Panic(err)
			}
			//5.更新blockchain的tip
			blc.Tip = newBlock.Hash
		}
		return nil
	})

	//更新失败
	if err != nil {
		log.Panic(err)
	}
}

//判断创世区块是不是存在
func dbExists() bool {
	if _, err := os.Stat(dbName); os.IsNotExist(err) {
		return false
	}
	return true
}

//创建创世块的区块链
func CreateBlockChainWithInitBlock(initData string) *Blockchain {

	if dbExists() {

		fmt.Println("创世区块已经存在")
		db, err := bolt.Open(dbName, 0600, nil)
		if err != nil {
			log.Fatal(err)
		}

		var blockchain *Blockchain
		err = db.View(func(tx *bolt.Tx) error {
			b := tx.Bucket([]byte(blockTableName))
			hash := b.Get([]byte("l"))
			blockchain = &Blockchain{hash, db}
			return nil
		})
		return blockchain
	}

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
			initBlock := CreateInitBlock(initData)
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
				fmt.Println(err)
			}
			blockHash = initBlock.Hash
		}
		return nil
	})

	return &Blockchain{blockHash, db}
}
