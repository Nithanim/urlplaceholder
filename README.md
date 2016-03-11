# UrlPlaceholder
## What it is
UrlPlaceholder is a very simple replacer for variables in urls. It does not escape anything.

## How to use
At first you create a descriptor that specifies the layout of the url. This is descriptor is intended to be re-used (just like Pattern.compile()).
You have to give every parameter a type and a name. If it is a path parameter, the name has to match the name of the placeholder in the url. If it is a query parameter, the name will be used as the key of the key-value-pair and automatically appended.

```java
UrlDescriptor ud = new UrlDescriptor(
	"http://127.0.0.1/{folder}/{file}",
	new UrlParameter(ParameterType.PATH, "folder"),
	new UrlParameter(ParameterType.PATH, "file"),
	new UrlParameter(ParameterType.QUERY_STRING, "width"),
	new UrlParameter(ParameterType.QUERY_STRING, "height")
);
```

Every time you want to fill in the variables you create a new UrlParameterBinder and provide it with all the parameter specified. 

```java
UrlParameterBinder upd = new UrlParameterBinder(ud)
	.bindParameter("folder", "images")
	.bindParameter("file", "art.jpg")
	.bindParameter("width", "800")
	.bindParameter("height", "600");
```

At the end you call
```java
String url = upd.getUrl();
```
which yields ```http://127.0.0.1/images/art.jpg?width=800&height=600``` in this case.