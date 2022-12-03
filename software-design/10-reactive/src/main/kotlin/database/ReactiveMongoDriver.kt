package database

import com.mongodb.client.model.Filters
import com.mongodb.rx.client.MongoClient
import com.mongodb.rx.client.MongoClients
import rx.Observable
import rx.Subscription

object ReactiveMongoDriver {
    private val client = createMongoClient()

    private const val DATABASE_NAME = "software-design-rx"
    private const val USERS_COLLECTION = "users"
    private const val PRODUCTS_COLLECTION = "products"

    fun createUser(user: User): Subscription = client
        .getDatabase(DATABASE_NAME)
        .getCollection(USERS_COLLECTION)
        .insertOne(user.document)
        .subscribe()

    fun createProduct(product: Product): Subscription = client
        .getDatabase(DATABASE_NAME)
        .getCollection(PRODUCTS_COLLECTION)
        .insertOne(product.document)
        .subscribe()

    fun users(): Observable<User> = client
        .getDatabase(DATABASE_NAME)
        .getCollection(USERS_COLLECTION)
        .find()
        .toObservable()
        .map { doc -> User(doc) }

    fun products(): Observable<Product> = client
        .getDatabase(DATABASE_NAME)
        .getCollection(PRODUCTS_COLLECTION)
        .find()
        .toObservable()
        .map { doc -> Product(doc) }

    fun getUser(id: Int?): Observable<User> = client
        .getDatabase(DATABASE_NAME)
        .getCollection(USERS_COLLECTION)
        .find(Filters.eq(User.ID_FIELD, id))
        .toObservable()
        .map { doc -> User(doc) }

    private fun createMongoClient(): MongoClient = MongoClients.create("mongodb://localhost:27017")
}