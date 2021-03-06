/*
MIT License

Copyright (c) 2019 Markus Heene <Markus.Heene@dwd.de>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

//@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast", "deprecation" })
public class CounterTest {

	class ParserErrorMessages {
		private String iterationErrorText;
		private String otherErrorText;
		private String fileName;

		public ParserErrorMessages(String iterationErrorText, String otherErrorText, String filename) {
			this.iterationErrorText = iterationErrorText;
			this.otherErrorText = otherErrorText;
		}

		public String getIterationErrorText() {
			return this.iterationErrorText;
		}

		public String getOtherErrorText() {
			return this.otherErrorText;
		}

		public String getFilename() {
			return this.fileName;
		}

	}

	private List<ParserErrorMessages> parseFile(File validTestFileFolder) throws Exception {
		// final InputStream original = System.in;
		// final FileInputStream fis = new FileInputStream(p_file);
		// System.setIn(fis);
		String iterationErrorString = null;
		ArrayList<ParserErrorMessages> p_list = new ArrayList<ParserErrorMessages>();

		for (final File fileEntry : validTestFileFolder.listFiles()) {
			if (fileEntry.isFile()) {
				CharStream input = CharStreams.fromPath(fileEntry.toPath());
				BUFRLexer lexer = new BUFRLexer(input);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				BUFRParser parser = new BUFRParser(tokens);
				parser.removeErrorListeners();
				VerboseListener verboseErrorListener = new VerboseListener();
				parser.addErrorListener(verboseErrorListener);
				ParseTree tree = parser.template();

				ParseTreeWalker walker = new ParseTreeWalker();
				CountReplicationDescriptor crd = new CountReplicationDescriptor();
				walker.walk(crd, tree);
				List<String> errors = crd.getErrors();

				Iterator<String> it = errors.listIterator();
				while (it.hasNext()) {
					String errorText = it.next();
					iterationErrorString = iterationErrorString + errorText;
				}

				ParserErrorMessages pem = new ParserErrorMessages(iterationErrorString,
						verboseErrorListener.getErrors(), fileEntry.getName());
				p_list.add(pem);
			}
		}
		return p_list;
	}

	@Test
	public void testValidSequences() throws Exception {

		final File validTestFileFolder = new File("build/classes/java/test/valid");
		int fileCounter = 0;
		for (final File fileEntry : validTestFileFolder.listFiles()) {
			if (fileEntry.isFile()) {
				String iterationErrorString = null;
				CharStream input = CharStreams.fromPath(fileEntry.toPath());
				BUFRLexer lexer = new BUFRLexer(input);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				BUFRParser parser = new BUFRParser(tokens);
				parser.removeErrorListeners();
				VerboseListener verboseErrorListener = new VerboseListener();
				parser.addErrorListener(verboseErrorListener);
				ParseTree tree = parser.template();

				ParseTreeWalker walker = new ParseTreeWalker();
				CountReplicationDescriptor crd = new CountReplicationDescriptor();
				walker.walk(crd, tree);
				List<String> errors = crd.getErrors();

				Iterator<String> it = errors.listIterator();
				while (it.hasNext()) {
					String errorText = it.next();
					iterationErrorString = iterationErrorString + errorText;
				}

				assertNull("Iteration Errors should be null for file: " + fileEntry.getName(), iterationErrorString);
				assertNull("Other Errors should be null for file: " + fileEntry.getName(),
						verboseErrorListener.getErrors());

				fileCounter++;
			}
		}
		System.out.println("Number of processed files: " + fileCounter);

	}

	@Test
	public void testInvalid() throws Exception {

		final File validTestFileFolder = new File("build/classes/java/test/invalid");
		int fileCounter = 0;
		for (final File fileEntry : validTestFileFolder.listFiles()) {
			if (fileEntry.isFile()) {
				String iterationErrorString = null;
				CharStream input = CharStreams.fromPath(fileEntry.toPath());
				BUFRLexer lexer = new BUFRLexer(input);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				BUFRParser parser = new BUFRParser(tokens);
				parser.removeErrorListeners();
				VerboseListener verboseErrorListener = new VerboseListener();
				parser.addErrorListener(verboseErrorListener);
				ParseTree tree = parser.template();

				ParseTreeWalker walker = new ParseTreeWalker();
				CountReplicationDescriptor crd = new CountReplicationDescriptor();
				walker.walk(crd, tree);
				List<String> errors = crd.getErrors();

				Iterator<String> it = errors.listIterator();
				while (it.hasNext()) {
					String errorText = it.next();
					iterationErrorString = iterationErrorString + errorText;
				}

				assertNull("Iteration Errors should be null for file: " + fileEntry.getName(), iterationErrorString);
				assertNotNull("Other Errors should not be null for file: " + fileEntry.getName(),
						verboseErrorListener.getErrors());

						fileCounter++;
					}
				}
		System.out.println("Number of processed files: " + fileCounter);
		

	}

	@Test
	public void testInvalidReplications() throws Exception {

		final File validTestFileFolder = new File("build/classes/java/test/invalid/replications");
		int fileCounter = 0;
		for (final File fileEntry : validTestFileFolder.listFiles()) {
			if (fileEntry.isFile()) {
				String iterationErrorString = null;
				CharStream input = CharStreams.fromPath(fileEntry.toPath());
				BUFRLexer lexer = new BUFRLexer(input);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				BUFRParser parser = new BUFRParser(tokens);
				parser.removeErrorListeners();
				VerboseListener verboseErrorListener = new VerboseListener();
				parser.addErrorListener(verboseErrorListener);
				ParseTree tree = parser.template();

				ParseTreeWalker walker = new ParseTreeWalker();
				CountReplicationDescriptor crd = new CountReplicationDescriptor();
				walker.walk(crd, tree);
				List<String> errors = crd.getErrors();

				Iterator<String> it = errors.listIterator();
				while (it.hasNext()) {
					String errorText = it.next();
					iterationErrorString = iterationErrorString + errorText;
				}

				assertNotNull("Iteration Errors should not be null for file: " + fileEntry.getName(),
						iterationErrorString);
				assertNull("Other Errors should be null for file: " + fileEntry.getName(),
						verboseErrorListener.getErrors());
						fileCounter++;
					}
				}
				System.out.println("Number of processed files: " + fileCounter);

	}

	@Test
	public void testInvalidReplications2() throws Exception {
		

		final File validTestFileFolder = new File("build/classes/java/test/invalid/replications/both");
		int fileCounter = 0;

			for (final File fileEntry : validTestFileFolder.listFiles()) {
			if (fileEntry.isFile()) {
				String iterationErrorString = null;
				CharStream input = CharStreams.fromPath(fileEntry.toPath());
				BUFRLexer lexer = new BUFRLexer(input);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				BUFRParser parser = new BUFRParser(tokens);
				parser.removeErrorListeners();
				VerboseListener verboseErrorListener = new VerboseListener();
				parser.addErrorListener(verboseErrorListener);
				ParseTree tree = parser.template();

				ParseTreeWalker walker = new ParseTreeWalker();
				CountReplicationDescriptor crd = new CountReplicationDescriptor();
				walker.walk(crd, tree);
				List<String> errors = crd.getErrors();

				Iterator<String> it = errors.listIterator();
				while (it.hasNext()) {
					String errorText = it.next();
					iterationErrorString = iterationErrorString + errorText;
				}

				assertNotNull("Iteration Errors should be null for file: " + fileEntry.getName(), iterationErrorString);
				assertNotNull("Other Errors should be null for file: " + fileEntry.getName(),
						verboseErrorListener.getErrors());

				fileCounter++;
			}
		}
		System.out.println("Number of processed files: " + fileCounter);

	}

/* 	@Test
	public void testInvalidReplications3() throws Exception {
		
		final File validTestFileFolder = new File("build/classes/java/test/invalid/replications/both");
		List<ParserErrorMessages> results = parseFile(validTestFileFolder);
		Iterator<ParserErrorMessages> it = results.iterator();
		while(it.hasNext()) {
			ParserErrorMessages pem = it.next();
			assertNotNull("Iteration Errors should not be null for file: " + pem.getFilename(),
			pem.iterationErrorText);
			assertNotNull("Other Errors should not be null for file: " + pem.getFilename(),
			pem.getOtherErrorText());
		}

		System.out.println("Number of processed files: " + results.size());
	} */
}
