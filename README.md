Microbench mark to measure difference between CGO and JNI

Both Java and Go versions will call a C function which will +1 to
a given input

## Go
1. `cd go`
2. `go clean -cache`
3. `go build -o bench main.go`
4. `./bench 10s native||whatever`

## Java
1. `cd java`
2. `javac -h . Bench.java`
3. `gcc -I/usr/lib/jvm/java/include -I/usr/lib/jvm/java/include/linux -shared Bench.c -o libbench.so`
4. `java -Djava.library.path=. Bench 10 native||whatever`
