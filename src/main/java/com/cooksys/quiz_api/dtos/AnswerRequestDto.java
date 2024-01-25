package com.cooksys.quiz_api.dtos;

import com.cooksys.quiz_api.entities.Question;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Data
public class AnswerRequestDto {

  private Long id;

  private String text;
  
  private Boolean correct;
  
  private Question question;

}