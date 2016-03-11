/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.nithanim.urlplaceholder;

public class Test {
    public static void main(String[] args) {
        UrlDescriptor ud = new UrlDescriptor(
            "http://127.0.0.1/{folder}/{file}",
            new UrlParameter(ParameterType.PATH, "folder"),
            new UrlParameter(ParameterType.PATH, "file"),
            new UrlParameter(ParameterType.QUERY_STRING, "width"),
            new UrlParameter(ParameterType.QUERY_STRING, "height")
        );

        UrlParameterBinder upd = new UrlParameterBinder(ud)
            .bindParameter("folder", "images")
            .bindParameter("file", "art.jpg")
            .bindParameter("width", "800")
            .bindParameter("height", "600");

        String url = upd.getUrl();
        System.out.println(url);
    }
}
