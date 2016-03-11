package me.nithanim.urlplaceholder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import me.nithanim.urlplaceholder.exceptions.ParameterAlreadyBoundException;
import me.nithanim.urlplaceholder.exceptions.ParameterNotBoundException;
import me.nithanim.urlplaceholder.exceptions.UnknownParameterException;

/**
 * This class takes an {@link UrlDescriptor} and is able to bind its
 * placeholders with actual values. When all required placeholders of the
 * {@link UrlDescriptor} have been bound you can retrieve the final url with
 * {@link #getUrl()}.
 *
 * After a successful call to {@link #getUrl()} the binder is completely reset
 * and can be used to bind another set of variables to the same url.
 */
public class UrlParameterBinder {
    private final UrlDescriptor descriptor;
    private final Map<String, BoundParameter> boundParameters = new HashMap<>();

    public UrlParameterBinder(UrlDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Either binds a path placeholder to an actual value or appends the value as
     * a query string at the end of the url. A parameter can only be bound once
     * and every required one need to be bound before the call to
     * {@link #getUrl()}.
     *
     * @param param the placeholder that should be replaced with value
     * @param value the value that the placeholder should be replaced with
     * @return the binder itself for chaining
     * @throws ParameterAlreadyBoundException
     * @throws UnknownParameterException
     */
    public UrlParameterBinder bindParameter(String param, Object value)
        throws ParameterAlreadyBoundException, UnknownParameterException {
        if (boundParameters.containsKey(param)) {
            throw new ParameterAlreadyBoundException(param);
        }

        UrlParameter parameter = descriptor.getParameter(param);
        if (parameter == null) {
            throw new UnknownParameterException("There is no parameter called \"" + param + "\"!");
        }

        BoundParameter bp = new BoundParameter(parameter.getType(), param, value);
        boundParameters.put(param, bp);
        return this;
    }

    /**
     * Returns the url with all path placeholders replaced and with all query
     * string parameters appended. It is expected that all required parameters
     * have been bound, otherwise a {@link ParameterNotBoundException} is
     * thrown.
     *
     * @return the url with all placeholders replaced and query string params
     * appended
     * @throws ParameterNotBoundException
     */
    public String getUrl() throws ParameterNotBoundException {
        checkAllParamsBound();

        String result = descriptor.getUrl();
        result = replacePathParams(result);
        result = replaceQueryStringParams(result);
        boundParameters.clear();
        return result;
    }

    private String replacePathParams(String url) {
        Iterator<BoundParameter> it = boundParameters.values().iterator();
        while (it.hasNext()) {
            BoundParameter bp = it.next();
            if (bp.getType() == ParameterType.PATH) {
                url = url.replace("{" + bp.getName() + "}", String.valueOf(bp.getValue()));
                it.remove();
            }
        }
        return url;
    }

    private String replaceQueryStringParams(String result) {
        StringBuilder sb = new StringBuilder(result);
        Iterator<BoundParameter> it = boundParameters.values().iterator();
        boolean firstParam = true;
        while (it.hasNext()) {
            BoundParameter bp = it.next();
            if (bp.getType() == ParameterType.QUERY_STRING) {
                sb.append(firstParam ? '?' : '&');
                sb.append(bp.getName()).append('=').append(bp.getValue());

                it.remove();
                firstParam = false;
            }
        }
        return sb.toString();
    }

    private void checkAllParamsBound() {
        if (descriptor.getParameterCount() > boundParameters.size()) {
            throw new ParameterNotBoundException("Not all parameters have been bound yet!");
        }
    }
}
