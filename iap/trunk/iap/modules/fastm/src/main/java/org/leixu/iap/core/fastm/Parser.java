package org.leixu.iap.core.fastm;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Stack;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.logging.Logger;

/**
 * <p>Title: Fast Template</p>
 * <p>Description: Fast Template For XML File (Using XML Comment as Tag)</p>
 * @author Wang Hailong
 * @version 1.0
 */

public class Parser {
	static final Logger log = Logger.getLogger(Parser.class.getName()); 

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static ITemplate parse(String fileName) throws IOException{
		return parse(fileName, "utf-8");
	}

    /**
     * parse a file to template
     *
     * @param fileName String
     * @param charsetName String
     * @throws IOException
     * @return ITemplate
     */
    public static ITemplate parse(String fileName, String charsetName) throws IOException{
		log.info("start parsing " + fileName);

		FileInputStream fileStream = new FileInputStream(fileName);
        ITemplate template = parse(fileStream, charsetName);
		fileStream.close();

        return template;
    }

	/**
	 * 
	 * @param stream
	 * @param charsetName
	 * @return
	 * @throws IOException
	 */    
    public static ITemplate parse(InputStream stream, String charsetName) throws IOException{
		if(charsetName == null) charsetName = "utf-8";
		log.info("parsing with charset " + charsetName );
		InputStreamReader streamReader = new InputStreamReader(stream, charsetName);
		BufferedReader reader = new BufferedReader(streamReader);

		ITemplate template = parse(reader);
		streamReader.close();
		reader.close();
		return template;
    }

    /**
     * parse a BufferedReader to template
     *
     * @param reader BufferedReader
     * @throws IOException
     * @return ITemplate
     */
    public static ITemplate parse(BufferedReader reader) throws IOException{
        StringBuffer staticLines = new StringBuffer(); // hold the static lines in the file
        Stack stack = new Stack(); // store DynamicPart by level
        DynamicPart top = new DynamicPart("top"); // the DynamicPart representing the whole file

        StaticPart staticPart = null; // just a commonly used variable
        String ignoredName = null; // record the IgnoredPart name
        int lineNo = 0; // record line number
        List lineQueue = new LinkedList();
        boolean foundComment = false;
        String commentPart = null; 

        // parse line by line, to create the Template Tree
        while(true){
        	// to support flexible format, 
			String line = null;
			if(commentPart != null){
				line = commentPart;
			}else {
				if(lineQueue.isEmpty()){
					line = reader.readLine();
					lineNo++;
				}else{
					line = (String)lineQueue.remove(0);
				}
			}

			if(line == null) break;

			if(commentPart != null){
				commentPart = null;
			}else{
				int commentBegin = line.indexOf("<!--");
				if(commentBegin >= 0){
					String lineBefore = null; 
					if(commentBegin > 0) {
						lineBefore = line.substring(0, commentBegin);
					}
	
					StringBuffer commentBuf = new StringBuffer();
	
					String commentLine = line;
					int commentEnd = commentLine.indexOf("-->");
					if(commentEnd < 0){
						commentBuf.append(line.substring(commentBegin));
						commentBegin = 0; // set to begin
					}

					while(commentEnd < 0){
						commentLine = reader.readLine();
						lineNo++;
						if(commentLine == null) break; // this means no end
						commentEnd = commentLine.indexOf("-->");
						if(commentEnd >= 0) break;
						commentBuf.append(commentLine + "\n");
					}
	
					if(commentLine == null || commentEnd < 0){ // no end
						commentPart = commentBuf.toString();
					}else{
						int pos = commentEnd + "-->".length();
						String postfix = commentLine.substring(commentBegin, pos);
						commentBuf.append(postfix);

						if(commentLine.length() > pos){
							String lineAfter = commentLine.substring(pos);
							lineQueue.add(lineAfter);
						}

						commentPart = commentBuf.toString();
					}

					if(lineBefore != null){
						line = lineBefore;
					}else{
						continue;
					}
				}
			}

            // parse a line
            Token token = parseLine(line);

            // ignore block between <!-- BEGIN IGNORED: name --> <!-- END IGNORED: name -->
            if(ignoredName != null){
                // if End of the IgnoredPart, then back to normal procedure
                if(token.type == Token.EndIgnored && ignoredName.equals(token.name)){
					ignoredName = null;
                }else{
                    continue; // ignore this line
                }
            }

            // normal line, put it to static lines buffer
            if(token.type == Token.Ordinary){
                staticLines.append(line + "\n");
                continue; // fetch next line
            }

            // code goes here, means this line contains DynamicPart definition or Variable
            if(staticLines.length() > 0){
                // create a StaticPart to hold the curret static lines in buf.
                staticPart = new StaticPart(staticLines.toString());
                top.addStep(staticPart);
                staticLines.setLength(0); // clean the static lines buf.
            }

            switch(token.type){
                case Token.BeginIgnored: // <!-- BEGIN INGNORED : name -->
					ignoredName = token.name;
                    break;

                case Token.BeginDynamic: // <!-- BEGIN DYNAMIC : name -->
                    DynamicPart dynamicPart = new DynamicPart(token.name);
                    top.addStep(dynamicPart);

                    // now we enter in a new level DynamicPart, store the old
                    // use the new one as top
                    stack.push(top);
                    top = dynamicPart;

                    break;

                case Token.EndDynamic: // <!-- END DYNAMIC : name -->
                    if(!top.getName().equals(token.name)){
                        throw new IOException("line " + lineNo + ": End Dynamic: " +
                            top.getName() + " instead of " + token.name + " is expected." );
                    }

                    // back to previous level DynamicPart
                    top = (DynamicPart)stack.pop();
                    if(top == null){
                        throw new IOException("line " + lineNo + ": End Dynamic: top = null, why?");
                    }

                case Token.HasVariable: // this line contains {..}
                    List posPairs = token.posPairs;

                    // separate this line to
                    // ........{....{.........}....}.....{..........}....... etc
                    //              ^         ^         ^          ^
                    // StaticPart   VariablePart  Static   Variable   Static etc
                    if(posPairs != null){
                        int nPairs = posPairs.size();

                        int begin = 0;
                        int end = line.length() - 1;

                        for(int k = 0; k < nPairs; k++){
                            PosPair posPair = (PosPair)posPairs.get(k);

                            if(begin < posPair.begin){
                                staticPart =
                                    new StaticPart(line.substring(begin, posPair.begin));
                                top.addStep(staticPart);
                            }

							// not include { }
							int nameBegin = posPair.begin + 1;
							int nameEnd = posPair.end;
                            VariablePart varPart = 
                                new VariablePart(line.substring(nameBegin, nameEnd));
                            top.addStep(varPart);

                            begin = posPair.end + 1;
                        }// end for(int k = 0; k < nPairs; k++)

                        String tail = "\n";

                        if(begin <= end){
                            tail = line.substring(begin, end + 1) + "\n";
                        }
                        staticPart = new StaticPart(tail);
                        top.addStep(staticPart);
                    } // end if(posPairs != null)
            }; // end switch(token.type)
        } // end for(String line = reader.readLine(); line != null; line = reader.readLine())

		if(stack.size() > 0){
			DynamicPart left = (DynamicPart)stack.pop();
			throw new IOException("line " + lineNo + ": END DYNAMIC:" + left.getName() + " is expected but not found." );
		}

        if(staticLines.length() > 0){
            staticPart = new StaticPart(staticLines.toString());
            top.addStep(staticPart);
        }

        return top;
    }

	// below is the code to parse every line
	
	///{{ define the pattern for XML Comment and Java Script Pattern
	// Command pattern in XML Comment like BEGIN DYNAMIC: name etc
    static final Pattern bgeinDynPattern = Pattern.compile("\\s*BEGIN\\s*DYNAMIC\\s*:\\s*.*\\s*"); // BEGIN DYNAMIC: dyn_name
    static final Pattern endDynPattern = Pattern.compile("\\s*END\\s*DYNAMIC\\s*:\\s*.*\\s*"); // END DYNAMIC: dyn_name
    static final Pattern beginIgnPattern = Pattern.compile("\\s*BEGIN\\s*IGNORED\\s*:\\s*.*\\s*"); // BEGIN IGNORED: ign_name
    static final Pattern endIgnPattern = Pattern.compile("\\s*END\\s*IGNORED\\s*:\\s*.*\\s*"); // END IGNORED: ign_name

	// Command pattern in Java Script Comment // BEGIN-END DYNAMIC: code
	static final Pattern bgeinDynPatternScript = Pattern.compile("\\s*//\\s*BEGIN\\s*DYNAMIC\\s*:\\s*.*\\s*"); // BEGIN DYNAMIC: dyn_name
	static final Pattern endDynPatternScript = Pattern.compile("\\s*//\\s*END\\s*DYNAMIC\\s*:\\s*.*\\s*"); // END DYNAMIC: dyn_name
	///}}

	// XML Comment pattern group
    static final Pattern patternGroup[] = {
        bgeinDynPattern,
        endDynPattern,
        beginIgnPattern,
        endIgnPattern,
    };

	// line Token type group 
    static final int typeGroup[] = {
        Token.BeginDynamic,
        Token.EndDynamic,
        Token.BeginIgnored,
        Token.EndIgnored,
    };

    /**
     * parse the line. 
     * the result Token indicate if this line is any special definition 
     * 
     * @param line String
     * @return Token
     */
    public static Token parseLine(String line){
        Token token = new Token();

        // if this line is a Comment Definition for DynamicPart etc
        if(line.startsWith("<!--") && line.endsWith("-->")){
            int commentBeginPos = line.indexOf("<!--") + "<!--".length();
            int commentEndPos = line.indexOf("-->", commentBeginPos);

            String tag = line.substring(commentBeginPos, commentEndPos).trim();

            int nPatterns = patternGroup.length;
            for(int i = 0; i < nPatterns; i++){
                Pattern pattern = patternGroup[i];
                if(pattern.matcher(tag).matches()){
                    token.type = typeGroup[i];
                    break;
                }
            }

            if(token.type > Token.Begin){ // valid
                int commaPos = tag.indexOf(":");
                token.name = tag.substring(commaPos + 1).trim();
            }

            return token;
        }

		// if this line is a JavaScript Comment Definition for DynamicPart etc
		// BEGIN DYNAMIC: js_code
		if(bgeinDynPatternScript.matcher(line).matches()){
			token.type = Token.BeginDynamic;
			int commaPos = line.indexOf(":");
			token.name = line.substring(commaPos + 1).trim();
			
			return token;
		}

		// END DYNAMIC: js_code
		if(endDynPatternScript.matcher(line).matches()){
			token.type = Token.EndDynamic;
			int commaPos = line.indexOf(":");
			token.name = line.substring(commaPos + 1).trim();

			return token;
		}

        // let's see if this line contains variables -- {...}
        int begin = 0;
        List posPairs = null;

        // separate this line to
        // ........{....{.........}....}...{..........}....... etc
        //              ^         ^         ^          ^
        //              begin     end       begin      end
        while(true){
            int lBracketPos = line.indexOf("{", begin);
            if(lBracketPos < 0) break;

            int rBracketPos = line.indexOf("}", lBracketPos);
            if(rBracketPos < 0) break;

			// pass the nested { { {, use the last one.
			int nestedLeftBracketPos = line.indexOf("{", lBracketPos + 1);
			while(nestedLeftBracketPos >= 0 && nestedLeftBracketPos < rBracketPos){
				lBracketPos = nestedLeftBracketPos;
				nestedLeftBracketPos = line.indexOf("{", lBracketPos + 1);
			}
            
            if(posPairs == null){
               posPairs = new ArrayList();
            }

            PosPair posPair = new PosPair();
            posPair.begin = lBracketPos;
            posPair.end = rBracketPos;

            posPairs.add(posPair);

			begin = rBracketPos + 1;
        }

        if(posPairs != null){
            token.type = Token.HasVariable;
            token.posPairs = posPairs;
        }

        return token;
    }

	// internal used Token Class 
	
	/**
	 * @author warrenw
	 * 
	 * the token to reprensent one line.
	 */
    public static class Token {
        static final int Begin = 10;
        static final int End = 20;
        static final int Dynamic = 1;
        static final int Ingnored = 2;

        /**
         * indicate the string is like
         * <!-- BEGIN DYNAMIC : name -->
         */
        static final int BeginDynamic = Begin + Dynamic;

        /**
         * indicate the string is like
         * <!-- END DYNAMIC : name -->
         */
        static final int EndDynamic = End + Dynamic;

        /**
         * indicate the string is like
         * <!-- BEGIN IGNORED : name -->
         */
        static final int BeginIgnored = Begin + Ingnored;

        /**
         * indicate the string is like
         * <!-- BEGIN IGNORED : name -->
         */
        static final int EndIgnored = End + Ingnored;

        /**
         * indicate the string is like
         * ....{...}....{....}....
         */
        static final int HasVariable = 5;

        /**
         * indicate this is a normal static string
         */
        static final int Ordinary = 0;

        /**
         * to indicate if this line is a comment definition,
         * or has variable, or just a static line
         */
        int type = 0;

        /**
         * DynamicPart or IngnoredPart name
         */
        String name = null;

        /**
         * variables {...} positions
         */
        List posPairs = null;
    };

   /**
    * to record a word position in a String.
    * for example,
    * .............{.........}.........{..........}....... etc
    *              ^         ^         ^          ^
    *              begin     end       begin      end
    */
    static class PosPair{
        int begin = 0;
        int end = 0;
    };
}
