package me.nithanim.urlplaceholder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import me.nithanim.urlplaceholder.exceptions.UnknownParameterException;
import me.nithanim.urlplaceholder.util.MatcherHelper;

/**
 * This class describes an url with palceholders in it that can later be binded
 * with actual values easily. A parameter can be in the path of the url and is
 * specified like <code>{placeholder}</code>. Additionally it is possible to
 * support more parameters that are not directly in the path but in the query
 * string stat starts with a <code>?</code> at the end of the path. These are
 * appended automatically. For more information on possible parameters see
 * {@link ParameterType}, {@link UrlParameter} and {@link UrlParameterBinder}.
 *
 * This class is immutable and supposed to be reused for describing the same
 * url.
 *
 * @see ParameterType
 * @see UrlParameter
 * @see UrlParameterBinder
 */
public class UrlDescriptor {
    private final String url;
    private final Set<UrlParameter> parameters;

    public UrlDescriptor(String url, UrlParameter parameter) {
        this(url, Collections.singleton(parameter));
    }

    public UrlDescriptor(String url, UrlParameter... parameters) {
        this(url, new HashSet<>(Arrays.asList(parameters)));
    }

    public UrlDescriptor(String url, Set<UrlParameter> parameters) {
        this.url = url;
        this.parameters = Collections.unmodifiableSet(parameters);
        checkParameters();
    }

    public String getUrl() {
        return url;
    }

    public Set<UrlParameter> getParameters() {
        return parameters;
    }

    public boolean hasParameter(String param) {
        return getParameter(param) != null;
    }

    public int getParameterCount() {
        return parameters.size();
    }

    public UrlParameter getParameter(String param) {
        for (UrlParameter up : parameters) {
            if (up.getName().equals(param)) {
                return up;
            }
        }
        return null;
    }

    private void checkParameters() {
        checkParametersEqual();
    }

    private void checkParametersEqual() {
        ArrayList<UrlParameter> params = new ArrayList<>(parameters);
        {
            //only scream for unspecified params in url that are in fact path params
            Iterator<UrlParameter> it = params.iterator();
            while (it.hasNext()) {
                if (!it.next().getType().isInPath()) {
                    it.remove();
                }
            }
        }

        Matcher matcher = MatcherHelper.PATH_PARAMETER_PATTERN.matcher(url);

        //examine all params in url and check if has arg counterpart
        while (matcher.find()) {
            String match = matcher.group(1);
            if (!hasParameter(match)) {
                throw new UnknownParameterException("Parameter \"" + match + "\" was found in url but not in arguments!");
            }

            Iterator<UrlParameter> it = params.iterator();
            while (it.hasNext()) {
                if (it.next().getName().equals(match)) {
                    it.remove();
                }
            }
        }

        //all survivors are not in url but in args
        if (!params.isEmpty()) {
            throw new UnknownParameterException("Parameter \"" + params.get(0).getName() + "\" was found in args but not in url!");
        }
    }
}
