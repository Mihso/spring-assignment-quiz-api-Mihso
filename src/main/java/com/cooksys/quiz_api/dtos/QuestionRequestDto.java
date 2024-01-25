package com.cooksys.quiz_api.dtos;

import java.util.List;

import com.cooksys.quiz_api.entities.Quiz;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class QuestionRequestDto {

  private Long id;
  
  private String text;
  
  private Quiz quiz;

  private List<AnswerRequestDto> answers;
}
