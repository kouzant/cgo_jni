package main

//#cgo CFLAGS: -g -Wall
//#include "add.c"
import "C"
import (
	"context"
	"fmt"
	"os"
	"time"
)

func main() {
	fmt.Printf("Starting bench\n")

	d, _ := time.ParseDuration(os.Args[1])
	native := os.Args[2] == "native"
	ctx, cancel := context.WithTimeout(context.Background(), d)
	defer cancel()

	ni := C.int(0)
	i := 0
	go func() {
		for {
			if native {
				ni = C.add_one(ni)
			} else {
				i = AddOne(i)
			}
		}
	}()

	select {
	case <-ctx.Done():
		fmt.Printf("Finished\n")
		if native {
			fmt.Printf("Number of native calls: %d\n", ni)
		} else {
			fmt.Printf("Number of non-native calls: %d\n", i)
		}
		time.Sleep(100 * time.Millisecond)
	}
}

func AddOne(i int) int {
	return i + 1
}
