package main

import (
	"PublicChain/BlockChain"
)

func main() {

	BlockChain.CreateInitBlock("hello world")
	//target := big.NewInt(1)
	//newInt := big.NewInt(2)
	//fmt.Println(target.Cmp(newInt))
	BlockChain.CreateBlockChainWithInitBlock()
}
