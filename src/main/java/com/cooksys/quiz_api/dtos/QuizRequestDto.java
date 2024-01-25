package com.cooksys.quiz_api.dtos;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class QuizRequestDto {

  private Long id;
  
  private String name;

  public List<QuestionRequestDto> questions;
}
