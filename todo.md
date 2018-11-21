# Thinks that should be done
* SequencerFactory<T> - create a validator that will check if expected inputs match created artifacts  
* Write tests
* Write logs messages
* ConsumerDefinition and ProducerDefinition - it's no longer a definition. It's a solid thing. Should be renamed to something like ContextConsumer    
* In BaseStepInvoker - change 'definition' variable to more suitable name  

# Maybe
* listeners - before and after Step execution  
* guards - before executing step check if all required artifacts are available  

# Next Futures  
* Container - support for container. That give you an option to encapsulate artifacts in one class and put their names inside that class.  
* Sequencer - there can be a situation when step wants to stop processing. Not necessary because of an error, but normal use case: when there is already enough data.  
* Retries - give an option to retry a particular step do to an error or something  
* implement Spring Factory of a StepFactory's interface  
