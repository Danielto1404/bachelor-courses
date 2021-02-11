import java.util.concurrent.locks.ReentrantLock

/**
 * Bank implementation.
 *
 * @author Daniil Korolev
 */
class BankImpl(n: Int) : Bank {
    /**
     * An array of accounts by index.
     */
    private val accounts: Array<Account> = Array(n) { Account() }
    override val numberOfAccounts: Int
        get() = accounts.size


    override fun getAmount(index: Int): Long {
        val account = accounts[index]

        // Lock
        account.lock()

        // Critical section
        val amount = account.amount

        // Unlock
        account.unlock()
        return amount
    }

    override val totalAmount: Long
        get() {
            var sum: Long = 0

            // Lock
            for (account in accounts) {
                account.lock()
            }

            // Critical section
            for (account in accounts) {
                sum += account.amount
            }

            // Unlock
            for (account in accounts) {
                account.unlock()
            }
            return sum
        }

    override fun deposit(index: Int, amount: Long): Long {
        require(amount > 0) { "Invalid amount: $amount" }
        val account = accounts[index]

        // Lock
        account.lock()

        // Critical section
        check(!(amount > Bank.MAX_AMOUNT || account.amount + amount > Bank.MAX_AMOUNT)) { "Overflow" }
        account.amount += amount
        val newAmount = account.amount

        // Unlock
        account.unlock()
        return newAmount
    }

    override fun withdraw(index: Int, amount: Long): Long {
        require(amount > 0) { "Invalid amount: $amount" }
        val account = accounts[index]

        // Lock
        account.lock()

        // Critical section
        if (account.amount - amount < 0) {
            account.unlock()
            throw IllegalStateException("Underflow")
        }
        account.amount -= amount
        val newAmount = account.amount

        // Unlock
        account.unlock()
        return newAmount
    }

    override fun transfer(fromIndex: Int, toIndex: Int, amount: Long) {
        require(amount > 0) { "Invalid amount: $amount" }
        require(fromIndex != toIndex) { "fromIndex == toIndex" }
        val from = accounts[fromIndex]
        val to = accounts[toIndex]

        // Lock
        while (true) {
            from.lock()
            if (to.lock.tryLock()) {
                break
            }
            from.unlock()
        }

        // Critical section
        if (amount > from.amount) {
            to.unlock()
            from.unlock()
            throw IllegalStateException("Underflow")
        } else if (amount > Bank.MAX_AMOUNT || to.amount + amount > Bank.MAX_AMOUNT) {
            to.unlock()
            from.unlock()
            throw IllegalStateException("Overflow")
        }
        from.amount -= amount
        to.amount += amount

        // Unlock
        to.unlock()
        from.unlock()
    }

    /**
     * Private account data structure.
     */
    internal class Account {
        /**
         * Amount of funds in this account.
         */
        var amount: Long = 0
        fun lock() {
            lock.lock()
        }

        fun unlock() {
            lock.unlock()
        }

        val lock = ReentrantLock(true)
    }
}