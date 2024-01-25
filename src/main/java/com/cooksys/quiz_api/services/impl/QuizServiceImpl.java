package com.cooksys.quiz_api.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cooksys.quiz_api.dtos.AnswerRequestDto;
import com.cooksys.quiz_api.dtos.AnswerResponseDto;
import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.AnswerMapper;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import javassist.NotFoundException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

  private final QuizRepository quizRepository;
  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;
  private final QuizMapper quizMapper;
  private final QuestionMapper questionMapper; 
  private final AnswerMapper answerMapper;

  @Override
  public List<QuizResponseDto> getAllQuizzes() {
    return quizMapper.entitiesToDtos(quizRepository.findAll());
  }
  
  @Override
  public QuestionResponseDto getRandomQuestion(Long a){
	  QuizResponseDto current = quizMapper.entityToDto(quizRepository.getById(a));
	  List<QuestionResponseDto> questions = current.questions;
	  return questions.get((int)(Math.random()*(questions.size())));
  }
  
  @Override
  public QuizResponseDto updateQuizName(Long id, String name) throws NotFoundException {
	  
	  Optional<Quiz> current = quizRepository.findById(id);
	  
	  if(current.isEmpty()) {
		  throw new NotFoundException("no Quiz with this id.");
	  }
	  
	  Quiz finall = current.get();
	  finall.setName(name);
	  
	  return quizMapper.entityToDto(quizRepository.saveAndFlush(finall));
  }
  
  @Override
  public QuizResponseDto addQuestion(Long id, QuestionRequestDto question) throws NotFoundException{
	  Optional<Quiz> current = quizRepository.findById(id);
	  
	  if(current.isEmpty()) {
		  throw new NotFoundException("no Quiz with this id.");
	  }
	  
	  Quiz finall = current.get();
	  
	  Question newQuestion = createQuestion(question, finall);
	  
	  finall.getQuestions().add(newQuestion);
	  
	  return quizMapper.entityToDto(quizRepository.saveAndFlush(finall));
	  
  }
  
  @Override
  public QuizResponseDto deleteQuiz(Long a) {
	  Quiz current = quizRepository.getById(a);
	  for(Question q: current.getQuestions()) {
		  deleteQuestion(q.getId());
	  }
	  
	  quizRepository.deleteById(a);
	  return quizMapper.entityToDto(current);
  }
  
  @Override
  public QuestionResponseDto deleteQuestionFromQuiz(Long id, Long questionId) throws NotFoundException {
	  Quiz current = quizRepository.getById(id);
	  for(Question q: current.getQuestions()) {
		  if(q == (questionRepository.getById(questionId) )) {
			  return questionMapper.entityToDto(deleteQuestion(questionId));
		  }
	  }
	  
	  throw new NotFoundException("Question not found in this Quiz.");
  }
  
  @Override
  public QuizResponseDto createQuiz(QuizRequestDto a) {
	  Quiz result = new Quiz();
	  result.setName(a.getName());
	  List<Question> quest = new ArrayList<>();
	  QuizResponseDto finall = quizMapper.entityToDto(quizRepository.saveAndFlush(result));
	  for(QuestionRequestDto q: a.getQuestions()) {
		  quest.add(createQuestion(q, result));
	  }
	  result.setQuestions(quest);
	  return quizMapper.entityToDto(quizRepository.getById(finall.getId()));
  }
  
  private Answer createAnswer(AnswerRequestDto a, Question question ) {
	  Answer result = new Answer();
	  result.setText(a.getText());
	  result.setCorrect(a.getCorrect());
	  result.setQuestion(question);
	  answerMapper.entityToDto(answerRepository.saveAndFlush(result));
	  return result;
  }
  
  private Question createQuestion(QuestionRequestDto a, Quiz quiz) {
	  Question result = new Question();
	  result.setText(a.getText());
	  result.setQuiz(quiz);
	  List<Answer> answers = new ArrayList<>();
	  QuestionResponseDto finall = questionMapper.entityToDto(questionRepository.saveAndFlush(result));
	  for(AnswerRequestDto answ: a.getAnswers()) {
		  answers.add(createAnswer(answ, result));
	  }
	  result.setAnswers(answers);
	  return result;

	  
  }
  
  private void deleteAnswer(Answer a) {
	  answerRepository.delete(a);
  }
  
  private Question deleteQuestion(Long id) {
	  Question finall = questionRepository.getById(id);
	  for(Answer a: finall.getAnswers()) {
		  deleteAnswer(a);
	  }
	  questionRepository.deleteById(id);
	  return finall;
	  
	  
  }

}
