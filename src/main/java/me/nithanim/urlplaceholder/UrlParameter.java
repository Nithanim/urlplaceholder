package me.nithanim.urlplaceholder;

public class UrlParameter {
    private final ParameterType type;
    private final String name;
    private final boolean required;

    public UrlParameter(ParameterType type, String name) {
        this(type, name, true);
    }
    
    public UrlParameter(ParameterType type, String name, boolean required) {
        this.type = type;
        this.name = name;
        this.required = required;
    }

    public String getName() {
        return name;
    }

    public ParameterType getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }
}
