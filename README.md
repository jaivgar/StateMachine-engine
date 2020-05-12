# StateMachine-engine
The internal logic needed to create and execute State Machines.

### Theoretical background
Specifically, the project is used to create Finite State Machine of Mealy type, as the outputs of the State Machine are included in the transitions. The State Machine is basically composed of:

- States
- Transitions
  - Events
  - Guards
  - Actions

A State Machine then is composed of a list of States linked by Transitions. A State is represented by a unique name and has associated some Transitions. Unless its and ending State which is caracterized by having no Transitions. In that case, the State Machine has reached a never changing state, and it will not produce any more outputs. The Transitions include conditions, in the form of Events and Guards, and Actions, that provide the output of the State Machine. Transitions are attached to a State, its origin, and point to another State, its target.

Whenever the State Machine is updated, the Transitions that are part of the active State are tested against the active Events and the Environment. If the conditions of the Transition are met(true), then the Action is executed, and the State Machine will change its active State according to the target of the Transition.

### Project settings
This project can be run as a single standalone java project, it has no external dependecies.
