package app.standard.regex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

/**
 * Characters
 *     x 	The character x
 *     \\ 	The backslash character
 *     \0n 	The character with octal value 0n (0 <= n <= 7)
 *     \0nn 	The character with octal value 0nn (0 <= n <= 7)
 *     \0mnn 	The character with octal value 0mnn (0 <= m <= 3, 0 <= n <= 7)
 *     \xhh 	The character with hexadecimal value 0xhh
 *     \x{h...h} 	The character with hexadecimal value 0xh...h (Character.MIN_CODE_POINT  <= 0xh...h <=  Character.MAX_CODE_POINT)
 *     \N{name} 	The character with Unicode character name 'name'
 *     \t 	The tab character ('\u0009')
 *     \n 	The newline (line feed) character ('\u000A')
 *     \r 	The carriage-return character ('\u000D')
 *     \f 	The form-feed character ('\u000C')
 *     \a 	The alert (bell) character ('\u0007')
 *     \e 	The escape character ('\u001B')
 *     \cx 	The control character corresponding to x
 * Character classes
 *     [abc] 	a, b, or c (simple class)
 *     [^abc] 	Any character except a, b, or c (negation)
 *     [a-zA-Z] 	a through z or A through Z, inclusive (range)
 *     [a-d[m-p]] 	a through d, or m through p: [a-dm-p] (union)
 *     [a-z&&[def]] 	d, e, or f (intersection)
 *     [a-z&&[^bc]] 	a through z, except for b and c: [ad-z] (subtraction)
 *     [a-z&&[^m-p]] 	a through z, and not m through p: [a-lq-z](subtraction)
 * Predefined character classes
 *     . 	Any character (may or may not match line terminators)
 *     \d 	A digit: [0-9]
 *     \D 	A non-digit: [^0-9]
 *     \h 	A horizontal whitespace character: [ \t\xA0\u1680\u180e\u2000-\u200a\u202f\u205f\u3000]
 *     \H 	A non-horizontal whitespace character: [^\h]
 *     \s 	A whitespace character: [ \t\n\x0B\f\r]
 *     \S 	A non-whitespace character: [^\s]
 *     \v 	A vertical whitespace character: [\n\x0B\f\r\x85\u2028\u2029]
 *     \V 	A non-vertical whitespace character: [^\v]
 *     \w 	A word character: [a-zA-Z_0-9]
 *     \W 	A non-word character: [^\w]
 * Boundary matchers
 *     ^ 	The beginning of a line
 *     $ 	The end of a line
 *     \b 	A word boundary
 *     \b{g} 	A Unicode extended grapheme cluster boundary
 *     \B 	A non-word boundary
 *     \A 	The beginning of the input
 *     \G 	The end of the previous match
 *     \Z 	The end of the input but for the final terminator, if any
 *     \z 	The end of the input
 * Linebreak matcher
 *     \R 	Any Unicode linebreak sequence, is equivalent to \u000D\u000A|[\u000A\u000B\u000C\u000D\u0085\u2028\u2029]
 * Unicode Extended Grapheme matcher
 *     \X 	Any Unicode extended grapheme cluster
 * Greedy quantifiers
 *     X? 	X, once or not at all
 *     X* 	X, zero or more times
 *     X+ 	X, one or more times
 *     X{n} 	X, exactly n times
 *     X{n,} 	X, at least n times
 *     X{n,m} 	X, at least n but not more than m times
 * Reluctant quantifiers
 *     X?? 	X, once or not at all
 *     X*? 	X, zero or more times
 *     X+? 	X, one or more times
 *     X{n}? 	X, exactly n times
 *     X{n,}? 	X, at least n times
 *     X{n,m}? 	X, at least n but not more than m times
 * Possessive quantifiers
 *     X?+ 	X, once or not at all
 *     X*+ 	X, zero or more times
 *     X++ 	X, one or more times
 *     X{n}+ 	X, exactly n times
 *     X{n,}+ 	X, at least n times
 *     X{n,m}+ 	X, at least n but not more than m times
 * Logical operators
 *     XY 	X followed by Y
 *     X|Y 	Either X or Y
 *     (X) 	X, as a capturing group
 * Back references
 *     \n 	Whatever the nth capturing group matched
 *     \k<name> 	Whatever the named-capturing group "name" matched
 * Quotation
 *     \ 	Nothing, but quotes the following character
 *     \Q 	Nothing, but quotes all characters until \E
 *     \E 	Nothing, but ends quoting started by \Q
 * Special constructs (named-capturing and non-capturing)
 *     (?<name>X) 	X, as a named-capturing group
 *     (?:X) 	X, as a non-capturing group
 *     (?idmsuxU-idmsuxU)  	Nothing, but turns match flags i d m s u x U on off
 *     (?idmsux-idmsux:X)   	X, as a non-capturing group with the given flags i d m s u x on off
 *     (?=X) 	X, via zero-width positive lookahead
 *     (?!X) 	X, via zero-width negative lookahead
 *     (?<=X) 	X, via zero-width positive lookbehind
 *     (?<!X) 	X, via zero-width negative lookbehind
 *     (?>X) 	X, as an independent, non-capturing group
 *
 * @author: pickjob@126.com
 * @date: 2024-09-08
 */
public class RegexShowcase {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        String source = "Hello World";
        Pattern pattern = Pattern.compile("\s");
        logger.info("{} test result {}", source, pattern.matcher(source).find());
    }
}