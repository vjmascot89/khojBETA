package com.khojbeta.engine.model;

import java.util.Date;
import java.util.List;

public class MovieModel {
	Integer id;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public Date getYear() {
	return year;
}
public void setYear(Date year) {
	this.year = year;
}
public List<String> getGenre() {
	return genre;
}
public void setGenre(List<String> genre) {
	this.genre = genre;
}
String title;
Date year;
List<String> genre;
}
