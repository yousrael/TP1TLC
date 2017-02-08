package com.example.advertisement;

import java.util.Date;

import com.example.guestbook.Guestbook;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Advertisement {
	@Parent Key<Board> board;
	@Id public Long id;

	public String author_email;
	public String author_id;
	public String title;

	@Index public double price;
	@Index public Date date;
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

	public Advertisement() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}