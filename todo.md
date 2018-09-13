# Thinks that should be done
* SequencerBuilder<T> - add assertions for every method. Should requires non null parameters  
* SequencerFactory<T> - create a validator that will check if expected inputs match created artifacts  
* Write tests
* Write logs messages
* For now the only way to define Sequence is to provide it's class. There should be a way to provide just an instance (then there is no need to create a new one from class definition).
  Create a SequenceDefinition class (with method "fromClass" and "fromInstance") that will encapsulate a sequence class or instance.
  

# Maybe
* change name of a Sequence to Step  
* listeners - before and after Sequence execution  
* guards - before executing sequence check if all required artifacts are available  

# Next Futures  
* Container - support for container. That give you an option to encapsulate artifacts in one class and put their names inside that class.  
* Sequencer - there can be a situation when sequence wants to stop processing. Not necessary because of an error, but normal use case: when there is already enough data.  
* Retries - give an option to retry a particular sequence do to an error or something  
* implement Spring Factory of a SequenceFactory's interface  
