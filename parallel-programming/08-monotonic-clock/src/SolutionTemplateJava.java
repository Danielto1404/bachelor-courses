import org.jetbrains.annotations.NotNull;

/**
 * В теле класса решения разрешено использовать только финальные переменные типа RegularInt.
 * Нельзя volatile, нельзя другие типы, нельзя блокировки, нельзя лазить в глобальные переменные.
 */
public class SolutionTemplateJava implements MonotonicClock {
    private final RegularInt c1 = new RegularInt(0);
    private final RegularInt c2 = new RegularInt(0);
    private final RegularInt c3 = new RegularInt(0);

    @Override
    public void write(@NotNull Time time) {
        // write right-to-left
        c3.setValue(time.getSeconds());
        c2.setValue(time.getMinutes());
        c1.setValue(time.getHours());
    }

    @NotNull
    @Override
    public Time read() {
        // read left-to-right
        return new Time(c1.getValue(), c2.getValue(), c3.getValue());
    }
}