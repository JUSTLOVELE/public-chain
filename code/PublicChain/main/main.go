package main

import (
	"PublicChain/BlockChain"
	"fmt"
)

func main() {

	BlockChain.CreateInitBlock("hello world")
	//target := big.NewInt(1)
	//newInt := big.NewInt(2)
	//fmt.Println(target.Cmp(newInt))
	fmt.Println(len("003caa541019ed862d21e239153cba6258366cedddafa77e83ef2a3b7ff77b15"))
}
