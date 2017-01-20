package com.example.advertisement;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Advertisement {
	@Parent Key<Board> board;
	@Id private Long id;

	public String author_email;
	public String author_id;
	private String title;

	@Index private double price;
	@Index private Date date;
	public Advertisement( String author_email, String author_id, String title, double price, Date date) {

		super();
		
		this.author_email = author_email;
		this.author_id = author_id;
		this.title = title;
		this.price = price;
		this.date = date;
	}

	public Advertisement(String title, double price, Date date) {
		super();
		this.title = title;
		this.price = price;
		this.date = date;
	}
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
