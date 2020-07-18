/*******************************************************************************
 * Copyright 2012 André Rouél
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

import net.sf.uadetector.internal.Check;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegularExpressionConverter
{

	/**
	 * Template to support the conversion into a PERL style regular expression
	 */
	private static final String PATTERN_TO_REGEX_TEMPLATE = "/%s/%s";
	/**
	 * Pattern for PERL style regular expression strings
	 */
	private static final Pattern PERL_STYLE = Pattern.compile("^/.*/((i|m|s|x)*)?$");
	/**
	 * Pattern for PERL style regular expression strings with more fault-tolerance to the modifiers
	 */
	private static final Pattern PERL_STYLE_TOLERANT = Pattern.compile("^/.*/(([A-z])*)?$");

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private RegularExpressionConverter()
	{
		// This class is not intended to create objects from it.
	}

	/**
	 * Converts a given {@code Pattern} into a PERL style regular expression.
	 *
	 * @param pattern
	 * 		regular expression pattern
	 *
	 * @return PERL style regular expression as string
	 */
	public static String convertPatternToPerlRegex(@javax.validation.constraints.NotNull Pattern pattern)
	{
		Check.notNull(pattern, "pattern");
		String modifiers = Flag.convertToModifiers(Flag.parse(pattern.flags()));
		return String.format(PATTERN_TO_REGEX_TEMPLATE, pattern.pattern(), modifiers);
	}

	/**
	 * Converts a PERL style regular expression into Java style.<br>
	 * <br>
	 * The leading and ending slash and the modifiers will be removed. The modifiers will be translated into equivalents
	 * flags of <code>java.util.Pattern</code>. If there are modifiers that are not valid an exception will be thrown.
	 *
	 * @param regex
	 * 		A PERL style regular expression
	 *
	 * @return Pattern
	 */
	public static Pattern convertPerlRegexToPattern(@javax.validation.constraints.NotNull String regex)
	{
		return convertPerlRegexToPattern(regex, false);
	}

	/**
	 * Converts a PERL style regular expression into Java style.<br>
	 * <br>
	 * The leading and ending slash and the modifiers will be removed.
	 *
	 * @param regex
	 * 		A PERL style regular expression
	 * @param faultTolerant
	 * 		Fault-tolerant translating the flags
	 *
	 * @return Pattern
	 */
	public static Pattern convertPerlRegexToPattern(@javax.validation.constraints.NotNull String regex, @javax.validation.constraints.NotNull boolean faultTolerant)
	{
		Check.notNull(regex, "regex");

		String pattern = regex.trim();
		Matcher matcher = faultTolerant ? PERL_STYLE_TOLERANT.matcher(pattern) : PERL_STYLE.matcher(pattern);
		if (!matcher.matches())
		{
			throw new IllegalArgumentException("The given regular expression '" + pattern
			                                   + "' seems to be not in PERL style or has unsupported modifiers.");
		}

		pattern = pattern.substring(1);
		int lastIndex = pattern.lastIndexOf('/');
		pattern = pattern.substring(0, lastIndex);

		int flags = Flag.convertToBitmask(Flag.parse(matcher.group(1)));
		return Pattern.compile(pattern, flags);
	}

	public enum Flag
	{

		/**
		 * Enables canonical equivalence.
		 */
		CANON_EQ(Pattern.CANON_EQ, 'c'),

		/**
		 * Enables case-insensitive matching.
		 */
		CASE_INSENSITIVE(Pattern.CASE_INSENSITIVE, 'i'),

		/**
		 * Permits whitespace and comments in pattern.
		 */
		COMMENTS(Pattern.COMMENTS, 'x'),

		/**
		 * Enables dotall mode.
		 */
		DOTALL(Pattern.DOTALL, 's'),

		/**
		 * Enables literal parsing of the pattern.
		 */
		LITERAL(Pattern.LITERAL, 'l'),

		/**
		 * Enables multiline mode.
		 */
		MULTILINE(Pattern.MULTILINE, 'm'),

		/**
		 * Enables Unicode-aware case folding.
		 */
		UNICODE_CASE(Pattern.UNICODE_CASE, 'u'),

		/**
		 * Enables Unix lines mode.
		 */
		UNIX_LINES(Pattern.UNIX_LINES, 'e');

		private static final FlagByCharacterComparator FLAG_COMPARATOR = new FlagByCharacterComparator();
		/**
		 * Representation of a flag as a character
		 */
		private final char character;
		/**
		 * Representation of a flag as a number
		 */
		private final int number;

		private Flag(int value, char character)
		{
			number = value;
			this.character = character;
		}

		/**
		 * Converts a set of flags as to a bitmask (sum of numerical values).
		 *
		 * @param flags
		 * 		a set of flags
		 *
		 * @return sum of numerical values of passed flags or 0
		 */
		public static int convertToBitmask(@javax.validation.constraints.NotNull Collection<Flag> flags)
		{
			Check.notNull(flags, "flags");

			int bitmask = 0;
			for (Flag flag : flags)
			{
				bitmask = bitmask | flag.getNumber();
			}
			return bitmask;
		}

		/**
		 * Returns this flag as numerical representation.
		 *
		 * @return representation as a number
		 */
		public int getNumber()
		{
			return number;
		}

		/**
		 * Converts a set of flags as to a string representation. The flags {@link Flag#CASE_INSENSITIVE},
		 * {@link Flag#DOTALL}, {@link Flag#MULTILINE} and {@link Flag#COMMENTS} are identical to the PERL regular
		 * expression modifiers.
		 *
		 * @param flags
		 * 		a set of flags
		 *
		 * @return sum of numerical values of passed flags or 0
		 */
		public static String convertToModifiers(@javax.validation.constraints.NotNull Collection<Flag> flags)
		{
			Check.notNull(flags, "flags");

			StringBuilder modifiers = new StringBuilder(8);
			Set<Flag> sortedFlags = new TreeSet<>(Collections.reverseOrder(FLAG_COMPARATOR));
			sortedFlags.addAll(flags);
			for (Flag flag : sortedFlags)
			{
				modifiers.append(flag.getCharacter());
			}
			return modifiers.toString();
		}

		/**
		 * Returns this flag as character representation.
		 *
		 * @return representation as a character
		 */
		public char getCharacter()
		{
			return character;
		}

		/**
		 * This method try to find a matching enum value by the given numerical representation. The number will be
		 * evaluated against the stored number of a flag.
		 *
		 * @param flag
		 * 		representation of a flag as a character
		 *
		 * @return the matching enum value or {@code null}
		 *
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the given number is smaller than zero
		 */
		public static Flag evaluateByNumber(int flag)
		{
			Check.notNegative(flag, "flag");
			Flag result = null;
			for (Flag value : values())
			{
				if (value.getNumber() == flag)
				{
					result = value;
					break;
				}
			}
			return result;
		}

		/**
		 * Parses a sum of flags as numerical values (bitmask) and translates it to set of enum values.
		 *
		 * @param bitmask
		 * 		Sum of numerical values of flags
		 *
		 * @return a set of flags
		 *
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the given number is smaller than zero
		 */
		@javax.validation.constraints.NotNull
		public static Set<Flag> parse(int bitmask)
		{
			Check.notNegative(bitmask, "bitmask");

			Set<Flag> flags = new HashSet<>();
			for (Flag flag : values())
			{
				if ((bitmask & flag.getNumber()) != 0)
				{
					flags.add(flag);
				}
			}
			return flags;
		}

		/**
		 * Translates PERL style modifiers to a set of {@code Pattern} compatible ones.
		 *
		 * @param modifiers
		 * 		modifiers as string of a PERL style regular expression
		 *
		 * @return a set of modifier flags that may include CASE_INSENSITIVE, MULTILINE, DOTALL and COMMENTS
		 */
		public static Set<Flag> parse(@javax.validation.constraints.NotNull String modifiers)
		{
			Check.notNull(modifiers, "modifiers");

			Set<Flag> flags = new HashSet<>();
			for (int i = 0; i < modifiers.length(); i++)
			{
				Flag flag = Flag.evaluateByCharacter(modifiers.charAt(i));
				if (flag != null)
				{
					flags.add(flag);
				}
			}
			return flags;
		}

		/**
		 * This method try to find a matching enum value by the given character representation. The character will be
		 * evaluated against the stored character of a flag.
		 *
		 * @param flag
		 * 		representation of a flag as a character
		 *
		 * @return the matching enum value or {@code null}
		 *
		 * @throws net.sf.uadetector.exception.IllegalNegativeArgumentException
		 * 		if the given number is smaller than zero
		 */
		public static Flag evaluateByCharacter(char flag)
		{
			Flag result = null;
			for (Flag value : values())
			{
				if (value.getCharacter() == flag)
				{
					result = value;
					break;
				}
			}
			return result;
		}

		private static class FlagByCharacterComparator
				extends CompareNullSafe<Flag>
		{
			private static final long serialVersionUID = 1L;

			@Override
			public int compareType(@javax.validation.constraints.NotNull Flag f1, @javax.validation.constraints.NotNull Flag f2)
			{
				Character c1 = f1.getCharacter();
				Character c2 = f2.getCharacter();
				return c1.compareTo(c2);
			}
		}

	}

}
