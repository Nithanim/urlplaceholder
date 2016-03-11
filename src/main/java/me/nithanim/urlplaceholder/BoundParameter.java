package me.nithanim.urlplaceholder;

import java.util.Objects;

public class BoundParameter {
    private final ParameterType type;
    private final String name;
    private final Object value;

    public BoundParameter(ParameterType type, String name, Object value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public ParameterType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BoundParameter other = (BoundParameter) obj;
        return Objects.equals(this.name, other.name);
    }

}
