package me.nithanim.urlplaceholder;

public enum ParameterType {
    PATH(true), QUERY_STRING(false);

    private final boolean inUrl;

    private ParameterType(boolean inUrl) {
        this.inUrl = inUrl;
    }

    public boolean isInPath() {
        return inUrl;
    }
}
