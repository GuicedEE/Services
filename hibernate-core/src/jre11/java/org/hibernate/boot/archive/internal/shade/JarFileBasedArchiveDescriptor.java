package org.hibernate.boot.archive.internal.shade;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

import org.hibernate.boot.archive.spi.AbstractArchiveDescriptor;
import org.hibernate.boot.archive.spi.ArchiveContext;
import org.hibernate.boot.archive.spi.ArchiveDescriptorFactory;
import org.hibernate.boot.archive.spi.ArchiveEntry;
import org.hibernate.boot.archive.spi.ArchiveEntryHandler;
import org.hibernate.boot.archive.spi.ArchiveException;
import org.hibernate.boot.archive.spi.InputStreamAccess;

import static org.hibernate.internal.log.UrlMessageBundle.URL_LOGGER;

/**
 * An ArchiveDescriptor implementation leveraging the {@link java.util.jar.JarFile} API for processing.
 *
 * @author Steve Ebersole
 */
public class JarFileBasedArchiveDescriptor extends AbstractArchiveDescriptor {
	/**
	 * Constructs a JarFileBasedArchiveDescriptor
	 *
	 * @param archiveDescriptorFactory The factory creating this
	 * @param archiveUrl The url to the JAR file
	 * @param entry The prefix for entries within the JAR url
	 */
	public JarFileBasedArchiveDescriptor(
			ArchiveDescriptorFactory archiveDescriptorFactory,
			URL archiveUrl,
			String entry) {
		super( archiveDescriptorFactory, archiveUrl, entry );
	}

	@Override
	public void visitArchive(ArchiveContext context) {
		if(getArchiveUrl().toString().startsWith("jrt:/"))
		{
			visitRuntime(context);
			return;
		}

		final JarFile jarFile = resolveJarFileReference();
		if ( jarFile == null ) {
			return;
		}

		try {
			final Enumeration<? extends ZipEntry> zipEntries = jarFile.entries();
			while ( zipEntries.hasMoreElements() ) {
				final ZipEntry zipEntry = zipEntries.nextElement();
				final String entryName = extractName( zipEntry );

				if ( getEntryBasePrefix() != null && ! entryName.startsWith( getEntryBasePrefix() ) ) {
					continue;
				}
				if ( zipEntry.isDirectory() ) {
					continue;
				}

				if ( entryName.equals( getEntryBasePrefix() ) ) {
					// exact match, might be a nested jar entry (ie from jar:file:..../foo.ear!/bar.jar)
					//
					// This algorithm assumes that the zipped file is only the URL root (including entry), not
					// just any random entry
					try (	final InputStream is = new BufferedInputStream( jarFile.getInputStream( zipEntry ) );
					         final JarInputStream jarInputStream = new JarInputStream( is )) {
						ZipEntry subZipEntry = jarInputStream.getNextEntry();
						while ( subZipEntry != null ) {
							if ( ! subZipEntry.isDirectory() ) {

								final String name = extractName( subZipEntry );
								final String relativeName = extractRelativeName( subZipEntry );
								final InputStreamAccess inputStreamAccess = buildByteBasedInputStreamAccess( name, jarInputStream );

								final ArchiveEntry entry = new ArchiveEntry() {
									@Override
									public String getName() {
										return name;
									}

									@Override
									public String getNameWithinArchive() {
										return relativeName;
									}

									@Override
									public InputStreamAccess getStreamAccess() {
										return inputStreamAccess;
									}
								};

								final ArchiveEntryHandler entryHandler = context.obtainArchiveEntryHandler( entry );
								entryHandler.handleEntry( entry, context );
							}

							subZipEntry = jarInputStream.getNextEntry();
						}
					}
					catch (Exception e) {
						throw new RuntimeException( "Error accessing JarFile entry [" + zipEntry.getName() + "]", e );
					}
				}
				else {
					final String name = extractName( zipEntry );
					final String relativeName = extractRelativeName( zipEntry );
					final InputStreamAccess inputStreamAccess;
					try (InputStream is = jarFile.getInputStream( zipEntry )) {
						inputStreamAccess = buildByteBasedInputStreamAccess( name, is );
					}
					catch (IOException e) {
						throw new RuntimeException(
								String.format(
										"Unable to access stream from jar file [%s] for entry [%s]",
										jarFile.getName(),
										zipEntry.getName()
								             )
						);
					}

					final ArchiveEntry entry = new ArchiveEntry() {
						@Override
						public String getName() {
							return name;
						}

						@Override
						public String getNameWithinArchive() {
							return relativeName;
						}

						@Override
						public InputStreamAccess getStreamAccess() {
							return inputStreamAccess;
						}
					};

					final ArchiveEntryHandler entryHandler = context.obtainArchiveEntryHandler( entry );
					entryHandler.handleEntry( entry, context );
				}
			}
		}
		finally {
			try {
				jarFile.close();
			}
			catch ( Exception ignore ) {
			}
		}
	}

	private JarFile resolveJarFileReference() {
		try {
			final String filePart = getArchiveUrl().getFile();
			if ( filePart != null && filePart.indexOf( ' ' ) != -1 ) {
				// unescaped (from the container), keep as is
				return new JarFile( getArchiveUrl().getFile() );
			}
			else {
				return new JarFile( getArchiveUrl().toURI().getSchemeSpecificPart() );
			}
		}
		catch (IOException e) {
			URL_LOGGER.logUnableToFindFileByUrl( getArchiveUrl(), e );
		}
		catch (URISyntaxException e) {
			URL_LOGGER.logMalformedUrl( getArchiveUrl(), e );
		}
		return null;
	}


	public void visitRuntime(ArchiveContext context)
	{
		final String jrtPart = getArchiveUrl().getPath();
		try
		{
			Path path = Paths.get(getArchiveUrl().toURI());
			String basePrefix = getEntryBasePrefix();
			String name = path.toString();
			name = name.startsWith("/modules/") ? name.substring(9) : name;
			Files.walkFileTree(path, new SimpleFileVisitor<>(){
				@Override
				public FileVisitResult visitFile(Path a, BasicFileAttributes attrs) throws IOException
				{
					String name = a.toString();
					if(name.startsWith("/modules/"))
					{
						name = name.substring(9);
						name = name.substring(name.indexOf('/') + 1);
					}

					final String entryName = name;
					final String relativeName = basePrefix != null && name.contains(basePrefix)
					                            ? name.substring(basePrefix.length())
					                            : name;
					final InputStreamAccess inputStreamAccess;
					try (InputStream is = Files.newInputStream(a))
					{
						inputStreamAccess = buildByteBasedInputStreamAccess(name, is);
					}
					catch (Exception e)
					{
						System.out.println("exception in walk - ");
						e.printStackTrace();
						throw new RuntimeException(
								String.format(
										"Unable to access stream from jrt ref [%s] for entry [%s]",
										path,
										a.toString()
								             )
						);
					}
					final ArchiveEntry entry = new ArchiveEntry()
					{
						@Override
						public String getName()
						{
							return entryName;
						}

						@Override
						public String getNameWithinArchive()
						{
							return relativeName;
						}

						@Override
						public InputStreamAccess getStreamAccess()
						{
							return inputStreamAccess;
						}
					};

					final ArchiveEntryHandler entryHandler = context.obtainArchiveEntryHandler(entry);
					entryHandler.handleEntry(entry, context);
					return super.visitFile(a, attrs);
				}
			});
		}
		catch (URISyntaxException | IOException e)
		{
			throw new RuntimeException(
					String.format(
							"Unable to access stream from jrt location [%s]",
							jrtPart)
			);
		}

	}
}
