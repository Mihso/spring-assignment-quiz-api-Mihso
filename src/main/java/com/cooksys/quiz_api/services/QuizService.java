package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuizResponseDto;

import javassist.NotFoundException;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;

public interface QuizService {

  List<QuizResponseDto> getAllQuizzes();
  
  QuestionResponseDto getRandomQuestion(Long a);
  
  QuizResponseDto deleteQuiz(Long a);
  
  QuizResponseDto createQuiz(QuizRequestDto a);
  
  QuizResponseDto updateQuizName(Long id, String s) throws NotFoundException;
  
  QuizResponseDto addQuestion(Long id, QuestionRequestDto question) throws NotFoundException;
  
  QuestionResponseDto deleteQuestionFromQuiz(Long id, Long questionId) throws NotFoundException;

}
