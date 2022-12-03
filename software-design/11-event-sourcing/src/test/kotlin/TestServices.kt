import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import services.AdminService
import services.ReportService
import services.TurnstileService
import storage.EventStorage
import java.time.LocalDateTime

internal class TestServices {
    private val minInHour = 60
    private var now: LocalDateTime? = null
    private var clock: TestClock? = null
    private var eventStorage: EventStorage? = null
    private var adminService: AdminService? = null

    @BeforeEach
    fun init() {
        now = LocalDateTime.now()
        clock = TestClock(now!!)
        eventStorage = EventStorage()
        adminService = AdminService(eventStorage!!, clock!!)
    }

    @Test
    fun adminServiceTest() {
        val adminService = adminService ?: throw RuntimeException("Admin service is not initialized")
        val clock = clock ?: throw RuntimeException("Clock is not initialized")
        val now = now ?: throw RuntimeException("Undefined time for property `now`")

        adminService.createAccount(1)
        adminService.extendExpiration(1, now.plusDays(3))
        clock.plusHours(24)

        val account = adminService.getAccountInfo(1)
            ?: throw RuntimeException("Account not found but should be")

        assertEquals(1, account.id)
        assertEquals(now, account.createdDate)
        assertEquals(now.plusDays(3), account.expirationDate)
    }

    @Test
    fun turnstileServiceTest() {
        val adminService = adminService ?: throw RuntimeException("Admin service is not initialized")
        val clock = clock ?: throw RuntimeException("Clock is not initialized")
        val eventStorage = eventStorage ?: throw RuntimeException("Event storage is not initialized")
        val now = now ?: throw RuntimeException("Undefined time for property `now`")

        val turnstileService = TurnstileService(eventStorage, clock)

        adminService.createAccount(1)
        adminService.extendExpiration(1, now.plusDays(3))
        turnstileService.visit(1)
        clock.plusHours(2)
        turnstileService.exit(1)
        clock.plusHours(24)
        turnstileService.visit(1)
        clock.plusHours(4)
        turnstileService.exit(1)

        val account = adminService.getAccountInfo(1)
            ?: throw RuntimeException("Account not found but should be")

        assertEquals(now.plusHours((2 + 24).toLong()), account.lastVisitDate)

        clock.plusHours(100)

        try {
            turnstileService.visit(1)
            fail("Account is no longer valid")
        } catch (ignored: RuntimeException) {
        }
    }

    @Test
    fun reportServiceTest() {

        val adminService = adminService ?: throw RuntimeException("Admin service is not initialized")
        val clock = clock ?: throw RuntimeException("Clock is not initialized")
        val eventStorage = eventStorage ?: throw RuntimeException("Event storage is not initialized")
        val now = now ?: throw RuntimeException("Undefined time for property `now`")

        val turnstileService = TurnstileService(eventStorage, clock)
        val reportService = ReportService()

        eventStorage.subscribe(reportService)

        adminService.createAccount(1)
        adminService.extendExpiration(1, now.plusDays(10))

        adminService.createAccount(2)
        adminService.extendExpiration(2, now.plusDays(10))

        val firstDay = now.toLocalDate()

        turnstileService.visit(1)
        turnstileService.visit(2)
        clock.plusHours(3)
        turnstileService.exit(1)
        turnstileService.exit(2)

        clock.plusHours(24)
        turnstileService.visit(1)

        clock.plusHours(2)
        turnstileService.exit(1)

        clock.plusHours(24)
        turnstileService.visit(2)

        clock.plusHours(4)
        turnstileService.exit(2)

        assertEquals(2, reportService.dailyStats[firstDay]?.size)

        assertEquals((3 * minInHour).toDouble(), reportService.averageDuration, 1.0E-02)

        assertEquals(1.33, reportService.averageDailyVisits, 1.0E-02)
    }
}