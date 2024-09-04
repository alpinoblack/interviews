Question 1
1. Design a system which stores the structure of a form.
The structure is then can be read and be displayed.
The form is a survey, it contains:
   1. A **title** which is the name of the survey
   2. 1 or more questions, each question can be:
      * Open Question
      * Close Question - each closed question have a list of 1 
      or more options to choose from
2. Represent it using Objects/Classes in a programming language
of your choice.
3. Represent it using RDBMS tables

```kotlin
data class Survey(val title: String, val questions: List<Question>)

sealed abstract class Question(protected val question: String)

data class ClosedQuestion(question: String,
                          val options: List<String>) : Question(question)

data class OpenQuestion(question: String,
                        placeHolder: String = "Entry your answer here")

```