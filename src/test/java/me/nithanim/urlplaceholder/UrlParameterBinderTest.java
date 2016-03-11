package me.nithanim.urlplaceholder;

import java.util.Collections;
import me.nithanim.urlplaceholder.exceptions.ParameterNotBoundException;
import me.nithanim.urlplaceholder.exceptions.UnknownParameterException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UrlParameterBinderTest {
    @Test
    public void testBindParameter() {
        UrlDescriptor ud = new UrlDescriptor(
            "http://example.com/{testparam}/",
            new UrlParameter(ParameterType.PATH, "testparam", true)
        );
        UrlParameterBinder ub = new UrlParameterBinder(ud);
        ub.bindParameter("testparam", "world");
        assertEquals("http://example.com/world/", ub.getUrl());
    }

    @Test
    public void testBindParameterMultibind() {
        UrlDescriptor ud = new UrlDescriptor(
            "http://example.com/{testparam}/{testparam}/",
            new UrlParameter(ParameterType.PATH, "testparam", true)
        );
        UrlParameterBinder ub = new UrlParameterBinder(ud);
        ub.bindParameter("testparam", "world");
        assertEquals("http://example.com/world/world/", ub.getUrl());
    }

    @Test
    public void testBindParameterMultiple() {
        UrlDescriptor ud = new UrlDescriptor(
            "http://example.com/{testparam1}/{testparam2}/",
            new UrlParameter(ParameterType.PATH, "testparam1", true),
            new UrlParameter(ParameterType.PATH, "testparam2", true)
        );
        UrlParameterBinder ub = new UrlParameterBinder(ud);
        ub.bindParameter("testparam1", "hello");
        ub.bindParameter("testparam2", "world");
        assertEquals("http://example.com/hello/world/", ub.getUrl());
    }
    
    @Test
    public void testBindParameterQueryString() {
        UrlDescriptor ud = new UrlDescriptor(
            "http://example.com/{testparam1}/",
            new UrlParameter(ParameterType.PATH, "testparam1", true),
            new UrlParameter(ParameterType.QUERY_STRING, "testparam2", true)
        );
        UrlParameterBinder ub = new UrlParameterBinder(ud);
        ub.bindParameter("testparam1", "hello");
        ub.bindParameter("testparam2", "world");
        assertEquals("http://example.com/hello/?testparam2=world", ub.getUrl());
    }
    
    @Test
    public void testBindParameterQueryStringDouble() {
        UrlDescriptor ud = new UrlDescriptor(
            "http://example.com/",
            new UrlParameter(ParameterType.QUERY_STRING, "testparam1", true),
            new UrlParameter(ParameterType.QUERY_STRING, "testparam2", true)
        );
        UrlParameterBinder ub = new UrlParameterBinder(ud);
        ub.bindParameter("testparam1", "hello");
        ub.bindParameter("testparam2", "world");
        assertEquals("http://example.com/?testparam1=hello&testparam2=world", ub.getUrl());
    }
    
    @Test(expected = ParameterNotBoundException.class)
    public void testBindParameterMissingBind() {
        UrlDescriptor ud = new UrlDescriptor(
            "http://example.com/{testparam}/",
            new UrlParameter(ParameterType.PATH, "testparam", true),
            new UrlParameter(ParameterType.QUERY_STRING, "other", true)
        );
        UrlParameterBinder ub = new UrlParameterBinder(ud);
        ub.bindParameter("testparam", "hello");
        ub.getUrl();
    }
    
    @Test(expected = UnknownParameterException.class)
    public void testBindParameterWrongBind() {
        UrlDescriptor ud = new UrlDescriptor(
            "http://example.com/{testparam}/",
            new UrlParameter(ParameterType.PATH, "testparam", true)
        );
        UrlParameterBinder ub = new UrlParameterBinder(ud);
        ub.bindParameter("other", "hello");
        ub.getUrl();
    }
}
