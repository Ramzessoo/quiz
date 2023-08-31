package com.example.quiz.services;

import com.example.quiz.dto.CategoriesDto;
import com.example.quiz.dto.QuestionsDto;
import com.example.quiz.frontend.Difficulty;
import com.example.quiz.frontend.GameOptions;
import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Log
@Getter
public class OngoingGameService {
    private GameOptions gameOptions;
    private int currentQuestionIndex;
    private int points;

    private List<QuestionsDto.QuestionDto> questions;

    @Autowired
    private QuizDataService quizDataService;

    public void init(GameOptions gameOptions) {
        this.gameOptions = gameOptions;
        this.currentQuestionIndex = 0;
        this.points = 0;

        this.questions = quizDataService.getQuizQuestions(gameOptions);
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionIndex + 1;
    }

    public int getTotalQuestionNumber() {
        return questions.size();
    }

    public String getCurrentQuestion() {
        QuestionsDto.QuestionDto dto = questions.get(currentQuestionIndex);
        return dto.getQuestion();
    }

    public List<String> getCurrentQuestionAnswersInRandomOrder() {
        QuestionsDto.QuestionDto dto = questions.get(currentQuestionIndex);

        List<String> answers = new ArrayList<>();
        answers.add(dto.getCorrectAnswer());
        answers.addAll(dto.getIncorrectAnswers());

        Collections.shuffle(answers);
        return answers;
    }

    public boolean checkAnswerForCurrentQuestionAndUpdatePoints(String userAnswer) {
        QuestionsDto.QuestionDto dto = questions.get(currentQuestionIndex);
        boolean correct = dto.getCorrectAnswer().equals(userAnswer);
        if (correct) {
            points++;
        }
        return correct;
    }

    public boolean proceedToNextQuestion() {
        currentQuestionIndex++;
        return currentQuestionIndex < questions.size();
    }

    public Difficulty getDifficulty() {
        return gameOptions.getDifficulty();
    }

    public String getCategoryName() {
        Optional<String> category = quizDataService.getQuizCategories().stream()
                .filter(categoryDto -> categoryDto.getId() == gameOptions.getCategoryID())
                .map(CategoriesDto.CategoryDto::getName)
                .findAny();
        return category.orElse(null);
    }
}
