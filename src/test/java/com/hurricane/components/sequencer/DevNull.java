package com.hurricane.components.sequencer;


import com.hurricane.components.sequencer.configure.annotations.Name;
import com.hurricane.components.sequencer.configure.annotations.Process;
import com.hurricane.components.sequencer.runtime.Step;

@Name("devNull")
public class DevNull implements Step {
    @Process
    public void devNull() {
    }
}
