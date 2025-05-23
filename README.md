# catch-exception 2

Catch and verify exceptions in a single line of code

[![Java CI](https://github.com/hazendaz/catch-exception/workflows/Java%20CI/badge.svg)](https://github.com/hazendaz/catch-exception/actions?query=workflow%3A%22Java+CI%22)
[![Coverage Status](https://coveralls.io/repos/github/hazendaz/catch-exception/badge.svg?branch=master-fork)](https://coveralls.io/github/hazendaz/catch-exception?branch=master-fork)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/com.github.hazendaz.catch-exception/catch-exception/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.hazendaz.catch-exception/catch-exception)
[![Apache 2](http://img.shields.io/badge/license-Apache%202-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

![hazendaz](src/site/resources/images/hazendaz-banner.jpg)

See site page [here](https://hazendaz.github.io/catch-exception/)

*This is continuation of famous [catch-exception](https://code.google.com/p/catch-exception/) library created
by Rod Woo*.

This library catches exceptions in a single line of code and makes them available for further analysis.

# Usage
The most basic usage is:

```java
import static com.googlecode.catchexception.CatchException.*;

// given: an empty list
List myList = new ArrayList();

// when: we try to get the first element of the list
// then: catch the exception if any is thrown
catchException(() -> myList.get(1));

// then: we expect an IndexOutOfBoundsException
assert caughtException() instanceof IndexOutOfBoundsException;
```

The last both lines of code can be combined in a single line of code if you like:

```java
verifyException(myList, IndexOutOfBoundsException.class).get(1);
```
More information about the usage you find in the [Javadoc documentation](http://codearte.github.io/catch-exception/apidocs/overview-summary.html).

# BDD-like
If you prefer a BDD-like approach, you can use [BDDCatchException](http://codearte.github.io/catch-exception/apidocs/com/googlecode/catchexception/apis/CatchExceptionBdd.html) for another code style:

```java
import static com.googlecode.catchexception.apis.BDDCatchException.*;

// given: an empty list
List myList = new ArrayList();

// when: we try to get the first element of the list
when(() -> myList.get(1));

// then: we expect an IndexOutOfBoundsException
then(caughtException())
        .isInstanceOf(IndexOutOfBoundsException.class)
        .hasMessage("Index: 1, Size: 0")
        .hasNoCause();
```
The assertions used here are part of [AssertJ](http://joel-costigliola.github.io/assertj/).

# Hamcrest
If you prefer [Hamcrest](http://hamcrest.org/JavaHamcrest/) matchers to express assertions, you can use [CatchExceptionHamcrestMatchers](http://codearte.github.io/catch-exception/apidocs/com/googlecode/catchexception/apis/CatchExceptionHamcrestMatchers.html) with the following code style:

```java
import static com.googlecode.catchexception.CatchException.*;
import static com.googlecode.catchexception.apis.CatchExceptionHamcrestMatchers.*;

// given: an empty list
List myList = new ArrayList();

// when: we try to get the first element of the list
catchException(myList).get(1);

// then: we expect an IndexOutOfBoundsException with message "Index: 1, Size: 0"
assertThat(caughtException(),
    allOf(
        instanceOf(equalTo(IndexOutOfBoundsException.class)),
        CatchExceptionHamcrestMatchers.hasMessage("Index: 1, Size: 0"),
        CatchExceptionHamcrestMatchers.hasNoCause()
    )
);
```

# Catch constructor exceptions
Catch-exception supports exceptions that are thrown by constructors. 

```java
verifyException(() -> new Thing("baddata"));
```

Thanks to the community for the example.

# Catch throwables
If you want to catch both throwables and exceptions have a look at the `catch-throwable` packages in [javadoc](http://codearte.github.io/catch-exception/apidocs/index.html?overview-summary.html). They provide the same API as the `catch-exception` packages but they belong to a different maven module.

# JUnit4
If you want to handle expected exceptions, the [documentation](http://codearte.github.io/catch-exception/apidocs/com/googlecode/catchexception/CatchException.html) of catch-exception names some general reasons to prefer catch-exception in comparison to mechanisms that are provided by testing frameworks.

But some reasons that are specific to JUnit4 are outlined only here.

## Collecting errors
If you want to combine the JUnit4's rules [ExpectedException](http://kentbeck.github.com/junit/javadoc/latest/org/junit/rules/ExpectedException.html) and [ErrorCollector](http://kentbeck.github.com/junit/javadoc/latest/org/junit/rules/ErrorCollector.html) you will find out: this won't work.

Catch-exception instead can be easily combined with the error collecting rule:

```java
@Rule
public ErrorCollector collector = new ErrorCollector();

@Test
public void testErrorCollectorWithExpectedException() {

    // collect first error
    collector.checkThat("a", equalTo("b"));

    // collect second error
    catchException(() -> new ArrayList().get(1));
    collector.checkThat(caughtException(), instanceOf(IllegalArgumentException.class));

    // collect third error
    collector.checkThat(1, equalTo(2));
}
```
## Theories respectively parameterized tests
Sometimes you want to test for an [optional exception in a parameterized test](http://stackoverflow.com/questions/7275859/testing-for-optional-exception-in-parameterized-junit-4-test?rq=1). JUnit4's [ExpecteException](http://kentbeck.github.com/junit/javadoc/latest/org/junit/rules/ExpectedException.html) rule does not help in this case. This is another use case where catch-exception comes in quite handy.

# Download
Go to the [Installation page](https://github.com/Codearte/catch-exception/wiki/Installation) to get the latest release. This page provides also the Maven coordinates, prerequisites, and information about dependencies to other libraries.

# Credits
Thanks to Rod Woo, the former author of catch-exception for creating this awesome library.

Thanks to Szczepan Faber who made some suggestions about a BDD-like syntax for catch-exception. Finally, his ideas triggered the enhancements that came with the 1.0.4 release.

# Questions, Suggestions, Issues
Questions, suggestions and Issues are welcome and can be reported via [Issues page](https://github.com/Codearte/catch-exception/issues) of this project.

Please give me feedback of any kind. It is highly appreciated.
