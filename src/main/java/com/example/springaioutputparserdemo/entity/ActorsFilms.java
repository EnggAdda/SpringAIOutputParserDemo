package com.example.springaioutputparserdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ActorsFilms {

    private String actor;

    private List<String> movies;


}
