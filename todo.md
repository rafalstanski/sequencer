# Thinks that should be done
* better exception handling  
* clear all TODO  
* Write tests  
* Provide a way to give step a name (NamedStep, if not then from class name)  
* SequencerFactory<T> - create a validator that will check if expected inputs match created artifacts  
* Write logs messages  

# Maybe
* listeners - before and after Step execution  
* guards - before executing step check if all required artifacts are available

# Next Futures  
* Container - support for container. That give you an option to encapsulate artifacts in one class and put their names inside that class.  
* Sequencer - there can be a situation when step wants to stop processing. Not necessary because of an error, but normal use case: when there is already enough data.  
* Retries - give an option to retry a particular step do to an error or something  
* implement Spring Factory of a StepFactory's interface  
