package com.example.college.model;

import jakarta.persistence.*;

@Entity
@Table(name="quiz_questions")
public class QuizQuestion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="quiz_id")
    private Quiz quiz;

    @Column(length=1000)
    private String question;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    @Column(length=1)
    private String correctOption; // "A","B","C","D"

    public QuizQuestion(){}
    public QuizQuestion(Quiz quiz, String question, String optionA, String optionB, String optionC, String optionD, String correctOption){
        this.quiz = quiz; this.question = question; this.optionA = optionA; this.optionB = optionB; this.optionC = optionC; this.optionD = optionD; this.correctOption = correctOption;
    }

    public Long getId(){ return id; }
    public Quiz getQuiz(){ return quiz; }
    public void setQuiz(Quiz quiz){ this.quiz = quiz; }
    public String getQuestion(){ return question; }
    public void setQuestion(String question){ this.question = question; }
    public String getOptionA(){ return optionA; }
    public void setOptionA(String optionA){ this.optionA = optionA; }
    public String getOptionB(){ return optionB; }
    public void setOptionB(String optionB){ this.optionB = optionB; }
    public String getOptionC(){ return optionC; }
    public void setOptionC(String optionC){ this.optionC = optionC; }
    public String getOptionD(){ return optionD; }
    public void setOptionD(String optionD){ this.optionD = optionD; }
    public String getCorrectOption(){ return correctOption; }
    public void setCorrectOption(String correctOption){ this.correctOption = correctOption; }
}
