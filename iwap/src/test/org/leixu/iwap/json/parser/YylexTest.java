package org.leixu.iwap.json.parser;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;

public class YylexTest extends TestCase {

	public void testYylex() throws Exception{
		String s="\"\\/\"";
		System.out.println(s);
		StringReader in = new StringReader(s);
		Lexer lexer=new Lexer(in);
		Token token=lexer.lex();
		assertEquals(Token.TYPE_VALUE,token.type);
		assertEquals("/",token.value);
		
		s="\"abc\\/\\r\\b\\n\\t\\f\\\\\"";
		System.out.println(s);
		in = new StringReader(s);
		lexer=new Lexer(in);
		token=lexer.lex();
		assertEquals(Token.TYPE_VALUE,token.type);
		assertEquals("abc/\r\b\n\t\f\\",token.value);
		
		s="[\t \n\r\n{ \t \t\n\r}";
		System.out.println(s);
		in = new StringReader(s);
		lexer=new Lexer(in);
		token=lexer.lex();
		assertEquals(Token.TYPE_LEFT_SQUARE,token.type);
		token=lexer.lex();
		assertEquals(Token.TYPE_LEFT_BRACE,token.type);
		token=lexer.lex();
		assertEquals(Token.TYPE_RIGHT_BRACE,token.type);
		
		s="\b\f{";
		System.out.println(s);
		in = new StringReader(s);
		lexer=new Lexer(in);
		ParseException err=null;
		try{
			token=lexer.lex();
		}
		catch(ParseException e){
			err=e;
			System.out.println("error:"+err);
			assertEquals(ParseException.ERROR_UNEXPECTED_CHAR, e.getErrorType());
			assertEquals(0,e.getPosition());
			assertEquals(new Character('\b'),e.getUnexpectedObject());
		}
		catch(IOException ie){
			throw ie;
		}
		assertTrue(err!=null);
		
		s="{a : b}";
		System.out.println(s);
		in = new StringReader(s);
		lexer=new Lexer(in);
		err=null;
		try{
			lexer.lex();
			token=lexer.lex();
		}
		catch(ParseException e){
			err=e;
			System.out.println("error:"+err);
			assertEquals(ParseException.ERROR_UNEXPECTED_CHAR, e.getErrorType());
			assertEquals(new Character('a'),e.getUnexpectedObject());
			assertEquals(1,e.getPosition());
		}
		catch(IOException ie){
			throw ie;
		}
		assertTrue(err!=null);
	}

}
