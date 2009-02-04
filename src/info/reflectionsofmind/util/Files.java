package info.reflectionsofmind.util;

import java.io.BufferedReader;
import java.io.FileReader;

public final class Files
{
	private Files()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	public static String readAsString(final String filePath) throws java.io.IOException
	{
		final StringBuffer fileData = new StringBuffer(1000);
		final BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1)
		{
			final String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}
}
