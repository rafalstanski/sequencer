package com.hurricane.components.sequencer;


import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

@Name("throw")
public class Throw implements Step {
    public static final RuntimeException WILL_THROW = new RuntimeException();

    @Process
    public void throwException() {
        throw WILL_THROW;
    }
}
