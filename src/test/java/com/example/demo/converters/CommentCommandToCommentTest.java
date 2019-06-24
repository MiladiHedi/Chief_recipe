package com.example.demo.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.example.demo.converter.CommentCommandToComment;
import com.example.demo.entities.Comment;
import com.example.demo.form.CommentCommand;

public class CommentCommandToCommentTest {

    public static final String AUTHOR = "AUTHOR";
    public static final String CONTENT = "CONTENT";
    public static final Long ID_VALUE = 1L;

    

    CommentCommandToComment converter;
    

    @Before
    public void setUp() throws Exception {
        converter = new CommentCommandToComment();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new CommentCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        CommentCommand commentCommand = new CommentCommand();
        commentCommand.setId(ID_VALUE);
        commentCommand.setAuthor(AUTHOR);
        commentCommand.setComContent(CONTENT);
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);  
        String strDate = dateFormat.format(date); 
        commentCommand.setDate(strDate);
       
        //when
        Comment comment = converter.convert(commentCommand);
        

        //then
        assertNotNull(comment);
        assertEquals(ID_VALUE, comment.getId());
        assertEquals(AUTHOR, comment.getAuthor());
        assertEquals(CONTENT, comment.getComContent());
        assertEquals(date.toString(), comment.getDate().toString()); // millisecond is different but its not important in this case
    }


}