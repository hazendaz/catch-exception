/*
 * Copyright 2011-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.catchexception.throwable;

/**
 * The Class CatchThrowable.
 */
public final class CatchThrowable {

    /**
     * Prevent Instantiation of a new catch exception.
     */
    private CatchThrowable() {
        // Preventing instantiation
    }

    /**
     * Returns the throwable caught during the last call on the proxied object in the current thread.
     *
     * @param <T>
     *            throwable caught during the last call on the proxied object
     *
     * @return Returns the throwable caught during the last call on the proxied object in the current thread - if the
     *         call was made through a proxy that has been created via {@link #verifyThrowable(ThrowingCallable, Class)}
     *         verifyThrowable()} or {@link #catchThrowable(ThrowingCallable)}. Returns null the proxy has not caught an
     *         throwable. Returns null if the caught throwable belongs to a class that is no longer {@link ClassLoader
     *         loaded}.
     */
    public static <T extends Throwable> T caughtThrowable() {
        return ThrowableHolder.get();
    }

    /**
     * Caught throwable.
     *
     * @param <T>
     *            the generic type
     * @param caughtThrowableType
     *            the caught throwable type
     *
     * @return the t
     *
     * @deprecated Use caghtThrowable() instead as the passed argument does nothing
     */
    @Deprecated(since = "2.3.0", forRemoval = true)
    public static <T extends Throwable> T caughtThrowable(Class<T> caughtThrowableType) {
        return ThrowableHolder.get();
    }

    /**
     * Use it to verify that an throwable is thrown and to get access to the thrown throwable (for further
     * verifications). The following example verifies that obj.doX() throws a Throwable:
     * <code>verifyThrowable(obj).doX(); // catch and verify
     * assert "foobar".equals(caughtThrowable().getMessage()); // further analysis
     * </code> If <code>doX()</code> does not throw a <code>Throwable</code>, then a
     * {@link ThrowableNotThrownAssertionError} is thrown. Otherwise the thrown throwable can be retrieved via
     * {@link #caughtThrowable()}.
     *
     * @param actor
     *            The instance that shall be proxied. Must not be <code>null</code>.
     */
    public static void verifyThrowable(ThrowingCallable actor) {
        verifyThrowable(actor, Throwable.class);
    }

    /**
     * Use it to verify that an throwable of specific type is thrown and to get access to the thrown throwable (for
     * further verifications). The following example verifies that obj.doX() throws a MyThrowable:
     * <code>verifyThrowable(obj, MyThrowable.class).doX(); // catch and verify
     * assert "foobar".equals(caughtThrowable().getMessage()); // further analysis
     * </code> If <code>doX()</code> does not throw a <code>MyThrowable</code>, then a
     * {@link ThrowableNotThrownAssertionError} is thrown. Otherwise the thrown throwable can be retrieved via
     * {@link #caughtThrowable()}.
     *
     * @param actor
     *            The instance that shall be proxied. Must not be <code>null</code>.
     * @param clazz
     *            The type of the throwable that shall be thrown by the underlying object. Must not be <code>null</code>
     */
    public static void verifyThrowable(ThrowingCallable actor, Class<? extends Throwable> clazz) {
        validateArguments(actor, clazz);
        catchThrowable(actor, clazz, true);
    }

    /**
     * Use it to catch an throwable and to get access to the thrown throwable (for further verifications). In the
     * following example you catch throwables that are thrown by obj.doX(): <code>catchThrowable(obj).doX(); // catch
     * if (caughtThrowable() != null) {
     * assert "foobar".equals(caughtThrowable().getMessage()); // further analysis
     * }</code> If <code>doX()</code> throws a throwable, then {@link #caughtThrowable()} will return the caught
     * throwable. If <code>doX()</code> does not throw a throwable, then {@link #caughtThrowable()} will return
     * <code>null</code>.
     *
     * @param actor
     *            The instance that shall be proxied. Must not be <code>null</code>.
     */
    public static void catchThrowable(ThrowingCallable actor) {
        validateArguments(actor, Throwable.class);
        catchThrowable(actor, Throwable.class, false);
    }

    /**
     * Use it to catch an throwable of a specific type and to get access to the thrown throwable (for further
     * verifications). In the following example you catch throwables of type MyThrowable that are thrown by obj.doX():
     * <code>catchThrowable(obj, MyThrowable.class).doX(); // catch
     * if (caughtThrowable() != null) {
     * assert "foobar".equals(caughtThrowable().getMessage()); // further analysis
     * }</code> If <code>doX()</code> throws a <code>MyThrowable</code>, then {@link #caughtThrowable()} will return the
     * caught throwable. If <code>doX()</code> does not throw a <code>MyThrowable</code>, then
     * {@link #caughtThrowable()} will return <code>null</code>. If <code>doX()</code> throws an throwable of another
     * type, i.e. not a subclass but another class, then this throwable is not thrown and {@link #caughtThrowable()}
     * will return <code>null</code>.
     *
     * @param actor
     *            The instance that shall be proxied. Must not be <code>null</code>.
     * @param clazz
     *            The type of the throwable that shall be caught. Must not be <code>null</code>.
     */
    public static void catchThrowable(ThrowingCallable actor, Class<? extends Throwable> clazz) {
        validateArguments(actor, clazz);
        catchThrowable(actor, clazz, false);
    }

    /**
     * Catch throwable.
     *
     * @param actor
     *            the actor
     * @param clazz
     *            the clazz
     * @param assertException
     *            the assert exception
     */
    private static void catchThrowable(ThrowingCallable actor, Class<? extends Throwable> clazz,
            boolean assertException) {
        resetCaughtThrowable();
        var throwable = ThrowableCaptor.captureThrowable(actor);
        if (throwable == null) {
            if (!assertException) {
                return;
            }
            throw new ThrowableNotThrownAssertionError(clazz);
        }
        // is the thrown exception of the expected type?
        if (clazz.isAssignableFrom(throwable.getClass())) {
            ThrowableHolder.set(throwable);
        } else if (assertException) {
            throw new ThrowableNotThrownAssertionError(clazz, throwable);
        } else {
            ExceptionUtil.sneakyThrow(throwable);
        }
    }

    /**
     * Validate arguments.
     *
     * @param actor
     *            the actor
     * @param clazz
     *            the clazz
     */
    private static void validateArguments(ThrowingCallable actor, Class<? extends Throwable> clazz) {
        if (actor == null) {
            throw new IllegalArgumentException("obj must not be null");
        }
        if (clazz == null) {
            throw new IllegalArgumentException("throwableClazz must not be null");
        }
    }

    /**
     * Sets the {@link #caughtThrowable() caught throwable} to null. This does not affect throwables saved at threads
     * other than the current one. Actually you probably never need to call this method because each method call on a
     * proxied object in the current thread resets the caught throwable. But if you want to improve test isolation or if
     * you want to 'clean up' after testing (to avoid memory leaks), call the method before or after testing.
     */
    public static void resetCaughtThrowable() {
        ThrowableHolder.set(null);
    }

}
