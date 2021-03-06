package com.mangofactory.bakehouse.typescript;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;

import lombok.SneakyThrows;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.mangofactory.bakehouse.core.AbstractFileManipulationTests;
import com.mangofactory.bakehouse.core.DefaultResource;
import com.mangofactory.bakehouse.core.compilers.CompilationResult;

public class TypescriptCompilerTests extends AbstractFileManipulationTests {

	TypescriptCompilerAdapter compiler;
	@Before
	public void setup()
	{
		compiler = new TypescriptCompilerAdapter();
	}
	@Test @SneakyThrows
	public void compilesTypescript()
	{
		
		DefaultResource resource = DefaultResource.fromFiles("text/javascript",testResource("typescript.ts"));
		File tempFile = getNewTempFile("js");
		CompilationResult compilationResult = compiler.compile(resource, tempFile);
		assertTrue("Compilation failed: " + compilationResult.getMessages(), compilationResult.isSuccessful());
		assertThat(compilationResult.getCompiledResource().getResourcePaths().size(), equalTo(1));
		
		assertTrue(tempFile.exists());
		
		String generatedTypescript = FileUtils.readFileToString(tempFile);
		String expectedTypescript = FileUtils.readFileToString(testResource("expectedTypescriptCompilationResult.js"));
		generatedTypescript = normalizeLineEndings(generatedTypescript);
		expectedTypescript = normalizeLineEndings(expectedTypescript);
		
		assertThat(generatedTypescript, equalTo(expectedTypescript));
	}
}
