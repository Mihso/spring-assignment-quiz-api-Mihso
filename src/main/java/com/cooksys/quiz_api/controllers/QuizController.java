package com.cooksys.quiz_api.controllers;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.services.QuizService;

import javassist.NotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;

  @GetMapping
  public List<QuizResponseDto> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }
  
  @GetMapping("/{id}/random")
  public QuestionResponseDto getRandomQuestion(@PathVariable Long id){
	  return quizService.getRandomQuestion(id);
  }
  
  @DeleteMapping("/{id}")
  public QuizResponseDto deleteQuiz(@PathVariable Long id) {
	  return quizService.deleteQuiz(id);
  }
  
  @DeleteMapping("/{id}/delete/{questionId}")
  public QuestionResponseDto deleteQuestion(@PathVariable Long id, @PathVariable Long questionId) throws NotFoundException{
	  return quizService.deleteQuestionFromQuiz(id,questionId);
  }
  
  @PatchMapping("/{id}/rename/{name}")
  public QuizResponseDto updateQuizName(@PathVariable Long id, @PathVariable String name) throws NotFoundException {
	  return quizService.updateQuizName(id, name);
  }
  
  @PatchMapping("/{id}/add")
  public QuizResponseDto addQuestion(@PathVariable Long id, @RequestBody QuestionRequestDto questionRequest) throws NotFoundException {
	  return quizService.addQuestion(id, questionRequest);
  }
  
  @PostMapping
  public QuizResponseDto createQuiz(@RequestBody QuizRequestDto quizRequest){
	  return quizService.createQuiz(quizRequest);
  }
  
  // TODO: Implement the remaining 6 endpoints from the documentation.

}
