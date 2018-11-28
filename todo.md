# Thinks that should be done
* SequencerFactory<T> - create a validator that will check if expected inputs match created artifacts
* Provide a way to give step a name (NamedStep, if not then from class name)  
* clear all TODO  
* Write tests  
* Write logs messages  

# Maybe
* Rearrange packages by its purpose - like construct (classes used only to extract/validate/set a Sequencer) / invoke / core 
* listeners - before and after Step execution  
* guards - before executing step check if all required artifacts are available

# Next Futures  
* Container - support for container. That give you an option to encapsulate artifacts in one class and put their names inside that class.  
* Sequencer - there can be a situation when step wants to stop processing. Not necessary because of an error, but normal use case: when there is already enough data.  
* Retries - give an option to retry a particular step do to an error or something  
* implement Spring Factory of a StepFactory's interface  
