package falcon.interview.util;

import java.util.Objects;

public class InstrumentNameAndValue {
    private final String instrumentName;
    private final double value;

    public InstrumentNameAndValue(String instrumentName, double value) {
        this.instrumentName = instrumentName;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentNameAndValue that = (InstrumentNameAndValue) o;
        return Double.compare(that.value, value) == 0 &&
                Objects.equals(instrumentName, that.instrumentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrumentName, value);
    }

    @Override
    public String toString() {
        return "InstrumentNameAndValue{" +
                "instrumentName='" + instrumentName + '\'' +
                ", value=" + value +
                '}';
    }
}
