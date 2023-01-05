package BlockChain

import (
	"bytes"
	"crypto/sha256"
	"encoding/binary"
	"fmt"
	"log"
	"math/big"
)

// 0000 0000 0000 0000 1001 0001 0000 ....0001
//一个hash字符串有64长度
//001a 6c72 1bb2 a096 5058 1808 4803 8084 e417 71db 5975 6d2f a0ab aca0 4b3c ddd1
//hash有256位，所以1位字符串对应4位的长度, 64*4 = 256
//所以targetBit：4是找1个0、8是2个0、12是3个0.....

const targetBit = 20

type ProofOfWork struct {
	Block  *Block
	target *big.Int
}

func NewProofOfWork(block *Block) *ProofOfWork {

	target := big.NewInt(1)
	//比如说0010
	//向左移1位就是0100，所左移位就是*2
	//向右移位就是0001也就是/2
	//3<<4 可以表示为3乘以2的4次方, 左移
	//2>>3 可以表示为2除以2的3次方，右移
	target = target.Lsh(target, 256-targetBit)

	return &ProofOfWork{block, target}
}

func (proofOfWork *ProofOfWork) Run() ([]byte, int64) {

	nonce := 0
	var hashInt big.Int
	var hash [32]byte

	for {
		dataBytes := proofOfWork.prepareData(nonce)
		//生成hash
		hash = sha256.Sum256(dataBytes)
		fmt.Printf("\r%x\n", hash)
		//将hash存储到hashInt
		hashInt.SetBytes(hash[:])

		if proofOfWork.target.Cmp(&hashInt) == 1 {
			break
		}

		nonce = nonce + 1
	}

	return hash[:], int64(nonce)
}

func (pow *ProofOfWork) prepareData(nonce int) []byte {

	data := bytes.Join(
		[][]byte{
			pow.Block.PreBlockHash,
			pow.Block.Data,
			IntToHex(pow.Block.Timestamp),
			IntToHex(int64(targetBit)),
			IntToHex(int64(nonce)),
			IntToHex(int64(pow.Block.Height)),
		},
		[]byte{},
	)
	return data
}

func IntToHex(num int64) []byte {

	buff := new(bytes.Buffer)
	err := binary.Write(buff, binary.BigEndian, num)

	if err != nil {
		log.Panic(err)
	}

	return buff.Bytes()
}

func (ProofOfWork *ProofOfWork) isValid() bool {

	var hashInt big.Int
	hashInt.SetBytes(ProofOfWork.Block.Hash)

	if ProofOfWork.target.Cmp(&hashInt) == 1 {
		return true
	}

	return false
}
