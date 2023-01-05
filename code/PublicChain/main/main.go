package main

import (
	"fmt"
	"math/big"
)

func main() {

	//BlockChain.CreateInitBlock("hello world")
	target := big.NewInt(1)
	newInt := big.NewInt(2)
	fmt.Println(target.Cmp(newInt))
}
