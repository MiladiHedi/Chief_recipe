package com.example.demo.converters;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.example.demo.converter.CommentToCommentCommand;
import com.example.demo.entities.Comment;
import com.example.demo.form.CommentCommand;


public class CommentToCommentCommandTest {

	public static final String AUTHOR = "AUTHOR";
	public static final String CONTENT = "CONTENT";
	public static final Long ID_VALUE = 1L;


	CommentToCommentCommand converter;

	@Before
	public void setUp() throws Exception {
		converter = new CommentToCommentCommand();
	}

	@Test
	public void testNullConvert() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new Comment()));
	}

	@Test
	public void testConvert() throws Exception {
		//given
		Comment comment = new Comment();
		comment.setId(ID_VALUE);
		comment.setAuthor(AUTHOR);
		comment.setComContent(CONTENT);
		String dateStr = "2019-01-01 12:25:45";
	    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(dateStr);
	    comment.setDate(date);


		//when
		CommentCommand commentCommand = converter.convert(comment);
		//then
		assertNotNull(commentCommand);
        assertEquals(ID_VALUE, commentCommand.getId());
        assertEquals(AUTHOR, commentCommand.getAuthor());
        assertEquals(CONTENT, commentCommand.getComContent());
        assertEquals(dateStr, commentCommand.getDate());
        
	}
}