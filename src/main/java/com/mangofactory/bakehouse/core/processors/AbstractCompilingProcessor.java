package com.mangofactory.bakehouse.core.processors;

import lombok.AccessLevel;
import lombok.Getter;

import com.mangofactory.bakehouse.config.BakehouseConfig;
import com.mangofactory.bakehouse.core.Resource;
import com.mangofactory.bakehouse.core.ResourceProcessor;
import com.mangofactory.bakehouse.core.compilers.CompilationResult;
import com.mangofactory.bakehouse.core.compilers.Compiler;
import com.mangofactory.bakehouse.core.io.FileManager;

public abstract class AbstractCompilingProcessor<T extends Compiler> implements ResourceProcessor{

	@Getter(value=AccessLevel.PROTECTED)
	private final T compiler;
	private final String targetFileName;
	
	private FileManager fileManager;

	public AbstractCompilingProcessor(String targetFileName, T compiler)
	{
		this.targetFileName = targetFileName;
		this.compiler = compiler;
	}
	
	public void configure(BakehouseConfig config) {
		this.fileManager = config.getFileManager();
	}
	public Resource process(Resource resource) {
		CompilationResult compilationResult = compiler.compile(resource, fileManager.getNewFile(targetFileName));
		return compilationResult.getCompiledResource();
	}
}
