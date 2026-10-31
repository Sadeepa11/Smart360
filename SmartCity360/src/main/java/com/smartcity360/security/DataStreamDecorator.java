package com.smartcity360.security;

/**
 * Base Decorator maintaining a reference to a wrapped DataStream component.
 * Conforms to the DataStream interface while delegating calls to the wrappee.
 */
public abstract class DataStreamDecorator implements DataStream {
    protected final DataStream wrappee;

    public DataStreamDecorator(DataStream wrappee) {
        if (wrappee == null) {
            throw new IllegalArgumentException("Wrapped DataStream cannot be null.");
        }
        this.wrappee = wrappee;
    }

    @Override
    public void writeData(String data) {
        wrappee.writeData(data);
    }

    @Override
    public String readData() {
        return wrappee.readData();
    }
}
