package main

import "fmt"
import "time"
import "encoding/binary"


// x^4 - 331 * x ^ 3 - 502852 * x ^ 2 + 32232192 * x + 46093059072 = 0
// Wolfram helps
// x = 337

func test1 (truba1 chan uint64,truba3 chan uint64){
    n1 := <- truba1
    if n1*n1*n1*n1 - 333*n1*n1*n1 - 502852*n1*n1 + 32232192*n1 + 46093059072 == 0 {
        truba3 <- n1
    }else{
        truba3 <- 0
    }
}

// x^4 - 259 * x^3 - 455274 * x^2 - 50590424 * x + 11117720960 = 0
// Wolfram helps

// Roots:
// x = -388
// x = 109
// x = 848
// x = -310

func test2 (truba2 chan uint64,truba3 chan uint64){
    n2 := <- truba2
    if n2*n2*n2*n2 - 259*n2*n2*n2 - 455274*n2*n2 - 50590424*n2 + 11117720960 == 0 {
        truba3 <- n2
    }else{
        truba3 <- 0
    }
}

// We should pick at least one odd root.
func test3 (truba3 chan uint64){
    var wow uint64 = 0
    for n:=0; n<2;n++{
        lol := <- truba3
        if lol % 2 !=0 {
            wow += lol
        }
    }
    truba3 <- wow
}

func main (){
    var tvoe_chislo1 uint64
    var tvoe_chislo2 uint64
    fmt.Print("Enter serial: ")
    fmt.Scanf("%d-%d",&tvoe_chislo1,&tvoe_chislo2)
    truba1 := make(chan uint64,2)
    truba2 := make(chan uint64,2)
    truba3 := make(chan uint64,2)
    go test1(truba1,truba3)
    go test2(truba2,truba3)
    go test3(truba3)
    truba1 <- tvoe_chislo1
    truba2 <- tvoe_chislo2
    time.Sleep(time.Second * 1)
    res := <-truba3
    if res > 0 {
        bs := make([]byte, 8)
        binary.BigEndian.PutUint64(bs,res*31337+54203286357058)
        fmt.Println("Flag{"+string(bs)+"}")
    }else{
        fmt.Println("Wrong serail")
    }
}

// tvoe_chislo1 = 337
// tvoe_chislo2 = 109
// Answer:= Flag{1L0vG0}
