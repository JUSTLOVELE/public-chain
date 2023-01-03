package BlockChain

import (
	"bytes"
	"encoding/gob"
	"log"
)

type Block struct {
	Height       int64
	PreBlockHash []byte
	Data         []byte
	Timestamp    int64
	Hash         []byte
	Nonce        int64
}

//序列化
func (block *Block) SerizalizeBlock() []byte {

	var result bytes.Buffer
	encoder := gob.NewEncoder(&result)
	err := encoder.Encode(block)

	if err != nil {
		log.Panic(err)
	}

	return result.Bytes()
}

//反序列化
func DeserializeBlock(blockBytes []byte) *Block {

	var block Block
	decoder := gob.NewDecoder(bytes.NewReader(blockBytes))
	err := decoder.Decode(&block)

	if err != nil {
		log.Panic(err)
	}

	return &block
}
