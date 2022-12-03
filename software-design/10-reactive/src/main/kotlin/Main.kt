import server.RxNettyServer

fun main() {
    // Create user
    // http://localhost:4000/createUser?userId=1&name=Bob&currency=eur

    // Create product
    // http://localhost:4000/createProduct?name=Iphone13&price=200000

    // Get users
    // http://localhost:4000/getUsers

    // Get products
    // http://localhost:4000/getProducts?userId=1
    RxNettyServer().start(port = 4000)
}