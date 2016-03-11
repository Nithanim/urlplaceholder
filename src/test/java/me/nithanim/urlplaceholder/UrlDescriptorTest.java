/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.nithanim.urlplaceholder;

import java.util.Collections;
import me.nithanim.urlplaceholder.exceptions.UnknownParameterException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class UrlDescriptorTest {
    @Test
    public void testConstructor() {
        new UrlDescriptor(
            "http://example.com/{testparam}/",
            new UrlParameter(ParameterType.PATH, "testparam", true)
        );
    }

    @Test
    public void testConstructorMultiple() {
        new UrlDescriptor(
            "http://example.com/{testparam}/{testparam}",
            new UrlParameter(ParameterType.PATH, "testparam", true)
        );
    }

    @Test
    public void testConstructorNotClosingParam() {
        UrlDescriptor ud = new UrlDescriptor(
            "http://example.com/{testparam1}/{testparam1",
            new UrlParameter(ParameterType.PATH, "testparam1", true)
        );

        UrlParameterBinder upb = new UrlParameterBinder(ud);
        upb.bindParameter("testparam1", "test");
        assertEquals("http://example.com/test/{testparam1", upb.getUrl());
    }

    @Test(expected = UnknownParameterException.class)
    public void testConstructorMissingParameterArgs() {
        new UrlDescriptor(
            "http://example.com/",
            new UrlParameter(ParameterType.PATH, "other", true)
        );
    }

    @Test(expected = UnknownParameterException.class)
    public void testConstructorMissingParameterUrlPath() {
        new UrlDescriptor(
            "http://example.com/{testparam}/",
            Collections.EMPTY_SET
        );
    }

    @Test
    public void testConstructorParameterUrlQueryString() {
        new UrlDescriptor(
            "http://example.com/",
            new UrlParameter(ParameterType.QUERY_STRING, "other", true)
        );
    }

    @Test(expected = UnknownParameterException.class)
    public void testConstructorMissingParameterBoth() {
        new UrlDescriptor(
            "http://example.com/{testparam}/",
            new UrlParameter(ParameterType.PATH, "other", true)
        );
    }

    @Test
    public void testConstructorMultipleParameter() {
        new UrlDescriptor(
            "http://example.com/{testparam1}/{testparam2}/",
            new UrlParameter(ParameterType.PATH, "testparam1", true),
            new UrlParameter(ParameterType.PATH, "testparam2", true)
        );
    }

    @Test(expected = UnknownParameterException.class)
    public void testConstructorMultipleMissingParameterArgs() {
        new UrlDescriptor(
            "http://example.com/{testparam}",
            new UrlParameter(ParameterType.PATH, "testparam", true),
            new UrlParameter(ParameterType.PATH, "other", true)
        );
    }

    @Test(expected = UnknownParameterException.class)
    public void testConstructorMultipleMissingParameterUrlPath() {
        new UrlDescriptor(
            "http://example.com/{testparam}/{other}/",
            new UrlParameter(ParameterType.PATH, "testparam", true)
        );
    }

    @Test
    public void testConstructorMultipleParameterUrlQueryString() {
        new UrlDescriptor(
            "http://example.com/{testparam}/",
            new UrlParameter(ParameterType.PATH, "testparam", true),
            new UrlParameter(ParameterType.QUERY_STRING, "other", true)
        );
    }

    @Test(expected = UnknownParameterException.class)
    public void testConstructorMultipleMissingParameterBoth() {
        new UrlDescriptor(
            "http://example.com/{testparam1}/",
            new UrlParameter(ParameterType.PATH, "testparam2", true),
            new UrlParameter(ParameterType.PATH, "other", true)
        );
    }
}
