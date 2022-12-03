package server

import database.Product
import database.ReactiveMongoDriver
import database.ReactiveMongoDriver.createProduct
import database.ReactiveMongoDriver.createUser
import database.User
import io.reactivex.netty.protocol.http.server.HttpServer
import rx.Observable

class RxNettyServer {
    fun start(port: Int = 2390) {
        HttpServer.newServer(port)
            .start { request, response ->
                val requestName = request.decodedPath
                val requestParams = request.queryParameters
                when (requestName) {
                    "/createUser" -> response.writeString(createUser(requestParams))
                    "/createProduct" -> response.writeString(createProduct(requestParams))
                    "/getUsers" -> response.writeString(getUsers())
                    "/getProducts" -> response.writeString(getProducts(requestParams))
                    else -> response.writeString(Observable.just("Error: Wrong request"))
                }
            }
            .awaitShutdown()
    }

    private fun createUser(requestParams: Map<String, List<String>>): Observable<String> {
        val id = requestParams[User.ID_FIELD]?.first()?.toInt()
            ?: return Observable.just("User id field not found")
        val name = requestParams[User.NAME_FIELD]?.first()
            ?: return Observable.just("User name field not found")
        val currency = requestParams[User.CURRENCY_FIELD]?.first()
            ?: return Observable.just("User current field not found")

        createUser(User(id, name, currency))
        return Observable.just("Success: User with id=$id name=$name and currency=$currency created")
    }

    private fun createProduct(requestParams: Map<String, List<String>>): Observable<String> {
        val name = requestParams[Product.NAME_FIELD]?.first()
            ?: return Observable.just("Product name field not found")
        val price = requestParams[Product.PRICE_FIELD]?.first()?.toDouble()
            ?: return Observable.just("Product price field not found")

        createProduct(Product(name, price))

        return Observable.just("Success: Product with name$name and price=$price(RUB) created")
    }

    private fun getUsers(): Observable<String> = ReactiveMongoDriver.users().map(User::toString)

    private fun getProducts(requestParams: Map<String, List<String>>): Observable<String> {
        val userId = requestParams[User.ID_FIELD]?.first()?.toInt()
            ?: return Observable.just("User id field not found")

        val products: Observable<Product> = ReactiveMongoDriver.products()

        return ReactiveMongoDriver.getUser(userId)
            .map { u -> u.currency }
            .flatMap { currency ->
                products.map { p -> p.toString(currency) }
            }
    }
}