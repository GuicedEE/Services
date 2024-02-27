/*******************************************************************************
 * Copyright 2013 André Rouél
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.uadetector.internal.util;

import java.io.*;
import java.nio.charset.Charset;

/**
 * This class is intended to provide file utility functions.
 *
 * @author André Rouél
 */
public final class FileUtil
{

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private FileUtil()
	{
		// This class is not intended to create objects from it.
	}

	/**
	 * Checks if the given file is empty.
	 *
	 * @param file
	 * 		the file that could be empty
	 *
	 * @return {@code true} when the file is accessible and empty otherwise {@code false}
	 *
	 * @throws IOException
	 * 		if an I/O error occurs
	 */
	public static boolean isEmpty(File file, Charset charset) throws IOException
	{
		boolean empty;
		try (FileInputStream fis = new FileInputStream(file);
		     InputStreamReader isr = new InputStreamReader(fis, charset);
		     BufferedReader reader = new BufferedReader(isr))
		{
			String line = reader.readLine();
			empty = line == null;
		}
		return empty;
	}

}
