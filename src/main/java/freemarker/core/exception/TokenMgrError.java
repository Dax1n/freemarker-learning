/*
 * Copyright (c) 2003 The Visigoth Software Society. All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Visigoth Software Society (http://www.visigoths.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. Neither the name "FreeMarker", "Visigoth", nor any of the names of the
 *    project contributors may be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact visigoths@visigoths.org.
 *
 * 5. Products derived from this software may not be called "FreeMarker" or "Visigoth"
 *    nor may "FreeMarker" or "Visigoth" appear in their names
 *    without prior written permission of the Visigoth Software Society.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE VISIGOTH SOFTWARE SOCIETY OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Visigoth Software Society. For more
 * information on the Visigoth Software Society, please see
 * http://www.visigoths.org/
 */

package freemarker.core.exception;

import freemarker.template.Template;

/**
 * Exception thrown on lower (lexical) level parsing errors. Shouldn't reach normal FreeMarker users, as FreeMarker
 * usually catches this and wraps it into a {@link ParseException}.
 * 
 * This is a modified version of file generated by JavaCC from FTL.jj.
 * You can modify this class to customize the error reporting mechanisms so long as the public interface
 * remains compatible with the original.
 * 
 * @see ParseException
 */
public class TokenMgrError extends Error
{
   /*
    * Ordinals for various reasons why an Error of this type can be thrown.
    */

   /**
    * Lexical error occured.
    */
   static final int LEXICAL_ERROR = 0;

   /**
    * An attempt wass made to create a second instance of a static token manager.
    */
   static final int STATIC_LEXER_ERROR = 1;

   /**
    * Tried to change to an invalid lexical state.
    */
   static final int INVALID_LEXICAL_STATE = 2;

   /**
    * Detected (and bailed out of) an infinite loop in the token manager.
    */
   static final int LOOP_DETECTED = 3;

   /**
    * Indicates the reason why the exception is thrown. It will have
    * one of the above 4 values.
    */
   int errorCode;

   private String detail;
   private Integer lineNumber, columnNumber;

   /**
    * Replaces unprintable characters by their espaced (or unicode escaped)
    * equivalents in the given string
    */
   protected static final String addEscapes(String str) {
      StringBuffer retval = new StringBuffer();
      char ch;
      for (int i = 0; i < str.length(); i++) {
        switch (str.charAt(i))
        {
           case 0 :
              continue;
           case '\b':
              retval.append("\\b");
              continue;
           case '\t':
              retval.append("\\t");
              continue;
           case '\n':
              retval.append("\\n");
              continue;
           case '\f':
              retval.append("\\f");
              continue;
           case '\r':
              retval.append("\\r");
              continue;
           case '\"':
              retval.append("\\\"");
              continue;
           case '\'':
              retval.append("\\\'");
              continue;
           case '\\':
              retval.append("\\\\");
              continue;
           default:
              if ((ch = str.charAt(i)) < 0x20 || ch > 0x7e) {
                 String s = "0000" + Integer.toString(ch, 16);
                 retval.append("\\u" + s.substring(s.length() - 4, s.length()));
              } else {
                 retval.append(ch);
              }
              continue;
        }
      }
      return retval.toString();
   }

   /**
    * Returns a detailed message for the Error when it's thrown by the
    * token manager to indicate a lexical error.
    * Parameters : 
    *    EOFSeen     : indicates if EOF caused the lexicl error
    *    curLexState : lexical state in which this error occured
    *    errorLine   : line number when the error occured
    *    errorColumn : column number when the error occured
    *    errorAfter  : prefix that was seen before this error occured
    *    curchar     : the offending character
    * Note: You can customize the lexical error message by modifying this method.
    */
   protected static String LexicalError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar) {
      return("Lexical error: encountered " +
           (EOFSeen ? "<EOF> " : ("\"" + addEscapes(String.valueOf(curChar)) + "\"") + " (" + (int)curChar + "), ") +
           "after \"" + addEscapes(errorAfter) + "\".");
   }

   /**
    * You can also modify the body of this method to customize your error messages.
    * For example, cases like LOOP_DETECTED and INVALID_LEXICAL_STATE are not
    * of end-users concern, so you can return something like : 
    *
    *     "Internal Error : Please file a bug report .... "
    *
    * from this method for such cases in the release version of your parser.
    */
   public String getMessage() {
      return super.getMessage();
   }

   /*
    * Constructors of various flavors follow.
    */

   public TokenMgrError() {
   }

   public TokenMgrError(String detail, int reason) {
      super(detail);  // the "detail" must not contain location information, the "message" might does
      this.detail = detail;
      errorCode = reason;
   }

   /**
    * @since 2.3.20
    */
   public TokenMgrError(String detail, int reason, int errorLine, int errorColumn) {
       super(detail);  // the "detail" must not contain location information, the "message" might does
       this.detail = detail;
       errorCode = reason;
       
       this.lineNumber = new Integer(errorLine);  // In J2SE there was no Integer.valueOf(int)
       this.columnNumber = new Integer(errorColumn);
    }

   public TokenMgrError(boolean EOFSeen, int lexState, int errorLine, int errorColumn, String errorAfter, char curChar, int reason) {
      this(LexicalError(EOFSeen, lexState, errorLine, errorColumn, errorAfter, curChar), reason);
      
      this.lineNumber = new Integer(errorLine);  // In J2SE there was no Integer.valueOf(int)
      this.columnNumber = new Integer(errorColumn);
   }

   /**
    * @since 2.3.20
    */
   public Integer getLineNumber() {
      return lineNumber;
   }
    
   /**
    * @since 2.3.20
    */
   public Integer getColumnNumber() {
      return columnNumber;
   }
   
   public String getDetail() {
       return detail;
   }

   public ParseException toParseException(Template template) {
       return new ParseException(getDetail(),
               template,
               getLineNumber() != null ? getLineNumber().intValue() : 0,
               getColumnNumber() != null ? getColumnNumber().intValue() : 0);
   }
   
}
