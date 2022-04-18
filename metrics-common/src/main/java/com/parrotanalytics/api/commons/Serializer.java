package com.parrotanalytics.api.commons;

/**
 * @author Minh Vu
 */
public interface Serializer {

    byte[] serialize(final Object obj);

    <T> T deserialize(final byte[] objectData);
}
