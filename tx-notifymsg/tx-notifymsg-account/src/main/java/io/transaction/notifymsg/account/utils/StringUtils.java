package io.transaction.notifymsg.account.utils;

import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  
 */  
public abstract class StringUtils {  
  
    private static final String FOLDER_SEPARATOR = "/";  
  
    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";  
  
    private static final String TOP_PATH = "..";  
  
    private static final String CURRENT_PATH = ".";  
  
    private static final char EXTENSION_SEPARATOR = '.';  
  
	/**
	 * Check whether the given String is empty.
	 * <p>This method accepts any Object as an argument, comparing it to
	 * {@code null} and the empty String. As a consequence, this method
	 * will never return {@code true} for a non-null non-String object.
	 * <p>The Object signature is useful for general attribute handling code
	 * that commonly deals with Strings but generally has to iterate over
	 * Objects since attributes may e.g. be primitive value objects as well.
	 * @param str the candidate String
	 * @since 3.2.1
	 */
	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}
	
	public static boolean isEmptyWithTrim(String str) {
		return (str == null || "".equals(str.trim()));
	}

    /**
     * 传入的字符串任何一个为空
     * @param strs
     * @return
     */
	public static boolean isAnyEmpty(String ... strs){
	    if (strs == null) return true;
	    for (String str : strs){
            if(str == null || "".equals(str.trim())){
                return true;
            }
        }
        return false;
    }
  
	/**
	 * 检查CharSequence是否为{@code null}或长度为0
	 * Note: 字符串纯粹由空白符构成时返回 {@code true} .
	 * <p><pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param str the CharSequence to check (may be {@code null})
	 * @return {@code true} if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
    public static boolean hasLength(CharSequence str) {  
        return (str != null && str.length() > 0);  
    }  
  
	/**
	 * 检查字符串是否为 {@code null}或长度为0
	 * Note: 字符串纯粹由空白符构成时返回 {@code true} .
	 * @param str the String to check (may be {@code null})
	 * @return 字符串不为空且长度空0时返回{@code true}
	 * @see #hasLength(CharSequence)
	 */
    public static boolean hasLength(String str) {  
        return hasLength((CharSequence) str);  
    }  
  
	public static boolean lengthIn(String text,int min,int max) {
		return text != null && text.length() >= min && text.length() <= max;
	}

    /**  
     * 判断CharSequence是否有实际内容，空白符不算  
     * <p><pre>  
     * StringUtils.hasText(null) = false  
     * StringUtils.hasText("") = false  
     * StringUtils.hasText(" ") = false  
     * StringUtils.hasText("12345") = true  
     * StringUtils.hasText(" 12345 ") = true  
     */  
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {  
            return false;  
        }
        int strLen = str.length();  
        for (int i = 0; i < strLen; i++) {  
            if (!Character.isWhitespace(str.charAt(i))) {  
                return true;  
            }  
        }  
        return false;  
    }  
  
	/**
	 * 判断String是否有实际内容，空白符不算 
	 * More specifically, returns {@code true} if the string not {@code null},
	 * its length is greater than 0, and it contains at least one non-whitespace character.
	 * @param str the String to check (may be {@code null})
	 * @return {@code true} if the String is not {@code null}, its length is
	 * greater than 0, and it does not contain whitespace only
	 * @see #hasText(CharSequence)
	 */ 
    public static boolean hasText(String str) {  
        return hasText((CharSequence) str);  
    }  
  
    /**  
     *检查CharSequence是否有空白字符
     */  
    public static boolean containsWhitespace(CharSequence str) {  
        //如果长度为0，则返回false  
        if (!hasLength(str)) {  
            return false;  
        }  
        int strLen = str.length();  
        for (int i = 0; i < strLen; i++) {  
            if (Character.isWhitespace(str.charAt(i))) {  
                return true;  
            }  
        }  
        return false;  
    }  
  
    /**  
     *判断给定的字符串str是否含有空白字符 
     */  
    public static boolean containsWhitespace(String str) {  
        return containsWhitespace((CharSequence) str);  
    }  
  
    /**  
     * 去掉string开头和结尾的空白字符 
     */  
    public static String trimWhitespace(String str) {  
        if (!hasLength(str)) {  
            return str;  
        }
        StringBuilder sb = new StringBuilder(str);  
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {  
            sb.deleteCharAt(0);
        }
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {  
            sb.deleteCharAt(sb.length() - 1);  
        }  
        return sb.toString();  
    }  
  
    /**  
     *删除给定的字符串中所有的空白字符
     */  
    public static String trimAllWhitespace(String str) {  
        if (!hasLength(str)) {  
            return str;  
        }  
        StringBuilder sb = new StringBuilder(str);  
        int index = 0;  
        while (sb.length() > index) {  
            if (Character.isWhitespace(sb.charAt(index))) {  
                sb.deleteCharAt(index);  
            }  
            else {  
                index++;  
            }  
        }
        return sb.toString();  
    }  
  
    /**  
     *删除掉str的开头的空白字符
     */  
    public static String trimLeadingWhitespace(String str) {  
        if (!hasLength(str)) {  
            return str;  
        }  
        StringBuilder sb = new StringBuilder(str);  
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {  
            sb.deleteCharAt(0);  
        }
        return sb.toString();  
    }  
  
    /**  
     * 删除str结尾的空白字符
     */  
    public static String trimTrailingWhitespace(String str) {  
        if (!hasLength(str)) {  
            return str;  
        }  
        StringBuilder sb = new StringBuilder(str);  
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {  
            sb.deleteCharAt(sb.length() - 1);  
        }
        return sb.toString();  
    }  
  
    /**  
     *删除str中开头是字符是给定字符的那个字符  
     */  
    public static String trimLeadingCharacter(String str, char leadingCharacter) {  
        if (!hasLength(str)) {  
            return str;  
        }  
        StringBuilder sb = new StringBuilder(str); 
        while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {  
            sb.deleteCharAt(0);
        }
        return sb.toString();  
    }  
  
    /**  
     *删除结尾等于trailingCharacter的那个字串 
     */  
    public static String trimTrailingCharacter(String str, char trailingCharacter) {  
        if (!hasLength(str)) {  
            return str;  
        }  
        StringBuilder sb = new StringBuilder(str);  
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {  
            sb.deleteCharAt(sb.length() - 1);  
        }  
        return sb.toString();  
    }  
  
  
    /**  
     *串��str的前串��否是prefix，大小写不敏串 
     */  
    public static boolean startsWithIgnoreCase(String str, String prefix) {  
        if (str == null || prefix == null) {  
            return false;  
        }  
                //如果是则返回true  
        if (str.startsWith(prefix)) {  
            return true;  
        }  
                //如果str小于前缀，则返回false  
        if (str.length() < prefix.length()) {  
            return false;  
        }  
                //设定大小写不明感  
                //把str的前面长度等于prefix的字符变小写  
        String lcStr = str.substring(0, prefix.length()).toLowerCase();  
                //把prefix变小串 
        String lcPrefix = prefix.toLowerCase();  
                //判断  
        return lcStr.equals(lcPrefix);  
    }  
  
    /**  
     *串��str的后串��否是prefix，大小写不敏串 
     */  
    public static boolean endsWithIgnoreCase(String str, String suffix) {  
        if (str == null || suffix == null) {  
            return false;  
        }  
                //如果后缀是suffix，返回true  
        if (str.endsWith(suffix)) {  
            return true;  
        }  
        if (str.length() < suffix.length()) {  
            return false;  
        }  
               //设定大小写不敏感  
        String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();  
        String lcSuffix = suffix.toLowerCase();  
        return lcStr.equals(lcSuffix);  
    }  
  
    /**  
         * 判断给定的str中是否有在位置index处存在子序列subString  
     */  
    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {  
        for (int j = 0; j < substring.length(); j++) {  
            int i = index + j;  
                        //如果i>=str.length说明str字符串自index到最后的长度小于subString  
                        //str.charAt(i) != substring.charAt(j),如果当前j位置字符和str中i位置字符不相串 
            if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {  
                return false;  
            }  
        }  
        return true;  
    }  
  
    /**  
         *串��str中出现sub子字符串的个串  
     */  
    public static int countOccurrencesOf(String str, String sub) {  
               //边界处理  
        if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {  
            return 0;  
        }  
                //计数串 
        int count = 0;  
                //记录当前位置  
        int pos = 0;  
        int idx;  
                //indexOf(String str,int fromIndex)str - 要搜索的子字符串串 
                //fromIndex - 串��搜索的索引位串 
                //如果含有此sub，则计数串1  
        while ((idx = str.indexOf(sub, pos)) != -1) {  
            ++count;  
                        //下一个开始比较的位置  
            pos = idx + sub.length();  
        }  
                //返回sub出现的个串 
        return count;  
    }  
  
    /**  
         * 用newPattern来替换inString中的oldPattern  
     */  
    public static String replace(String inString, String oldPattern, String newPattern) {  
               //边界处理  
        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {  
            return inString;  
        }  
                  
        StringBuilder sb = new StringBuilder();  
        int pos = 0;  
                //返回oldPattern在inString的位置索串 
        int index = inString.indexOf(oldPattern);  
                //记录oldPattern的长串 
        int patLen = oldPattern.length();  
        while (index >= 0) {  
                        //保存index之前的inString子串  
            sb.append(inString.substring(pos, index));  
                        //拼接新的字符（串串 
            sb.append(newPattern);  
            pos = index + patLen;  
                        //串��pos之后是否还有oldPattern,如果有继续替串 
            index = inString.indexOf(oldPattern, pos);  
        }  
                //拼接pos之后的字符串  
        sb.append(inString.substring(pos));  
        // remember to append any characters to the right of a match  
        return sb.toString();  
    }  
  
    /**  
         *删除inString中符合pattern要求的字符（串）  
         * 实现方法是：把inString中符合pattern的字符（串）替换成串”从而实现删串 
     */  
    public static String delete(String inString, String pattern) {  
        return replace(inString, pattern, "");  
    }  
  
	/**
	 * 删除指定字符串中指定字符串
	 * @param inString the original String
	 * @param charsToDelete a set of characters to delete.
	 * E.g. "az\n" will delete 'a's, 'z's and new lines.
	 * @return the resulting String
	 */
    public static String deleteAny(String inString, String charsToDelete) {  
        //边界处理  
        if (!hasLength(inString) || !hasLength(charsToDelete)) {  
            return inString;  
        }  
                //字符构串串 
        StringBuilder sb = new StringBuilder();  
                //循环遍历inString,判断每个字符是否在charsToDelete串 
        for (int i = 0; i < inString.length(); i++) {  
                       //获取当前位置i的字符c  
            char c = inString.charAt(i);  
                        //如果charsToDelete中不包含c，则拼接到sb串 
            if (charsToDelete.indexOf(c) == -1) {  
                sb.append(c);  
            }  
        }  
                //返回处理过的字符串 
        return sb.toString();  
    }  
  
  
    //---------------------------------------------------------------------  
    // Convenience methods for working with formatted Strings  
    //---------------------------------------------------------------------  
  
    /**  
     * 用单引号把非空的str括起来，例如str == "hello" 那么返回的将是串hello串 
     */  
    public static String quote(String str) {  
        return (str != null ? "'" + str + "'" : null);  
    }  
  
    /**  
     * 如果给定的对象是String类型，则调用quote方法处理，否则什么都不做原样返回  
     */  
    public static Object quoteIfString(Object obj) {  
        return (obj instanceof String ? quote((String) obj) : obj);  
    }  
  
    /**  
     * Unqualify a string qualified by a '.' dot character. For example,  
     * "this.name.is.qualified", returns "qualified".  
     * @param qualifiedName the qualified name  
     */  
    public static String unqualify(String qualifiedName) {  
        return unqualify(qualifiedName, '.');  
    }  
  
    /**  
     * 获取给定的字符串中，串��串��满足分隔符separator之后字符串，  
         * 例如 qualifiedName = "this:name:is:qualified"  
         *     separator = ':'  
         * 那么处理过后的字符串就是 qualified  
     */  
    public static String unqualify(String qualifiedName, char separator) {  
        return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);  
    }  
  
    /**  
         *设置首字母为大写  
     */  
    public static String capitalize(String str) {  
        return changeFirstCharacterCase(str, true);  
    }  
  
    /**  
         *设置str首字母为小写  
     */  
    public static String uncapitalize(String str) {  
        return changeFirstCharacterCase(str, false);  
    }  
  
    private static String changeFirstCharacterCase(String str, boolean capitalize) {  
        if (str == null || str.length() == 0) {  
            return str;  
        }  
        StringBuilder sb = new StringBuilder(str.length());  
        if (capitalize) {//如果首字母要求大写的串 
            sb.append(Character.toUpperCase(str.charAt(0)));  
        }  
        else {   //否则首字母设置为小写  
            sb.append(Character.toLowerCase(str.charAt(0)));  
        }  
                //拼接首字母剩下的字符串 
        sb.append(str.substring(1));  
        return sb.toString();  
    }  
  
    /**  
     * 获得给用路径path中的文件串 
     * 例如 "mypath/myfile.txt" -> "myfile.txt".  
     */  
    public static String getFilename(String path) {  
                //边界处理  
        if (path == null) {  
            return null;  
        }  
                //获得path中最后一个文件分隔符串’的位置  
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);  
                //如果没有分隔符，说明给定的就是文件名，直接返回即可，否则返回分隔符剩下的字符  
        return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);  
    }  
  
    /**  
     *获得文件名的扩展名，也就是格串 
     * e.g. "mypath/myfile.txt" -> "txt".  
     */  
    public static String getFilenameExtension(String path) {  
                //边界处理  
        if (path == null) {  
            return null;  
        }  
                //获得串��串��串’的位置  
        int extIndex = path.lastIndexOf(EXTENSION_SEPARATOR);  
        if (extIndex == -1) {  
            return null;  
        }  
                //找到串��串��文件分隔符串/’的位置  
        int folderIndex = path.lastIndexOf(FOLDER_SEPARATOR);  
                //如果folderIndex在extIndex的右边，返回null  
        if (folderIndex > extIndex) {  
            return null;  
        }  
                //返回串’之后的子字符串  
        return path.substring(extIndex + 1);  
    }  
  
    /**  
     *过滤掉文件的扩展串 
     * 例如. "mypath/myfile.txt" -> "mypath/myfile".  
     */  
    public static String stripFilenameExtension(String path) {  
                //边界处理  
        if (path == null) {  
            return null;  
        }  
                //获得串��串��串’的位置  
        int extIndex = path.lastIndexOf(EXTENSION_SEPARATOR);  
        if (extIndex == -1) {  
            return path;  
        }  
                //找到串��串��文件分隔符串/’的位置  
        int folderIndex = path.lastIndexOf(FOLDER_SEPARATOR);  
                 //如果folderIndex在extIndex的右边，path是文件路径，没有扩展名可串��直接原样返回  
        if (folderIndex > extIndex) {  
            return path;  
        }  
                //返回滤掉扩展名之后的子字符串  
        return path.substring(0, extIndex);  
    }  
  
    /**  
         * 该方法的作用如下  
         * 如果path = "/hello/world/ relativePtah = "java"  
         * 经过处理后返串/hello/world/java  
         * 如果path = "helloworld" 那么处理后返回java  
         * 这个方法少了空串判断，个人觉得加上严谨些  
     */  
    public static String applyRelativePath(String path, String relativePath) {  
               //找到串��个文件分隔符的位串 
        int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);  
        if (separatorIndex != -1) {//如果有文件分隔符  
                       //获得串到最后一个分隔符之前的子字符串 
            String newPath = path.substring(0, separatorIndex);  
                        //如果relativePath不是以文件分隔符串��  
            if (!relativePath.startsWith(FOLDER_SEPARATOR)) {  
                               //把newPath后面追加串��/  
                newPath += FOLDER_SEPARATOR;  
            }  
                        //返回newPath+relativePath  
            return newPath + relativePath;  
        }  
        else {//如果没有，就返回relativePath  
            return relativePath;  
        }  
    }
  
    /**  
     * Normalize the path by suppressing sequences like "path/.." and  
     * inner simple dots.  
     * <p>The result is convenient for path comparison. For other uses,  
     * notice that Windows separators ("\") are replaced by simple slashes.  
     * @param path the original path  
     * @return the normalized path  
     */  
    public static String cleanPath(String path) {  
                //边界处理  
        if (path == null) {  
            return null;  
        }  
                //串地体pathToUse的\\  
        String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);  
  
        // Strip prefix from path to analyze, to not treat it as part of the  
        // first path element. This is necessary to correctly parse paths like  
        // "file:core/../core/io/Resource.class", where the ".." should just  
        // strip the first "core" directory while keeping the "file:" prefix.  
                //找到：的位置  
        int prefixIndex = pathToUse.indexOf(":");  
        String prefix = "";  
                //如果：不存在  
        if (prefixIndex != -1) {  
                        //前缀是pathToUse中从0到prefixIndex的字符，包括串 
            prefix = pathToUse.substring(0, prefixIndex + 1);  
                        //获得冒号之后的所有字符（串）  
            pathToUse = pathToUse.substring(prefixIndex + 1);  
        }  
        if (pathToUse.startsWith(FOLDER_SEPARATOR)) {//如果pathToUse是以/串��  
                        //把prefix +/  
            prefix = prefix + FOLDER_SEPARATOR;  
                        //过滤掉开头的/  
            pathToUse = pathToUse.substring(1);  
        }  
  
        String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);  
        List<String> pathElements = new LinkedList<String>();  
        int tops = 0;  
  
        for (int i = pathArray.length - 1; i >= 0; i--) {  
            String element = pathArray[i];  
            if (CURRENT_PATH.equals(element)) {  
                // Points to current directory - drop it.  
            }  
            else if (TOP_PATH.equals(element)) {  
                // Registering top path found.  
                tops++;  
            }  
            else {  
                if (tops > 0) {  
                    // Merging path element with element corresponding to top path.  
                    tops--;  
                }  
                else {  
                    // Normal path element found.  
                    pathElements.add(0, element);  
                }  
            }  
        }  
  
        // Remaining top paths need to be retained.  
        for (int i = 0; i < tops; i++) {  
            pathElements.add(0, TOP_PATH);  
        }  
  
        return prefix + collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);  
    }
  
	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>A single delimiter can consists of more than one character: It will still
	 * be considered as single delimiter string, rather than as bunch of potential
	 * delimiter characters - in contrast to {@code tokenizeToStringArray}.
	 * @param str the input String
	 * @param delimiter the delimiter between elements (this is a single delimiter,
	 * rather than a bunch individual delimiter characters)
	 * @return an array of the tokens in the list
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(String str, String delimiter) {
		return delimitedListToStringArray(str, delimiter, null);
	}
	
	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>A single delimiter can consists of more than one character: It will still
	 * be considered as single delimiter string, rather than as bunch of potential
	 * delimiter characters - in contrast to {@code tokenizeToStringArray}.
	 * @param str the input String
	 * @param delimiter the delimiter between elements (this is a single delimiter,
	 * rather than a bunch individual delimiter characters)
	 * @param charsToDelete a set of characters to delete. Useful for deleting unwanted
	 * line breaks: e.g. "\r\n\f" will delete all new lines and line feeds in a String.
	 * @return an array of the tokens in the list
	 * @see #tokenizeToStringArray
	 */
	public static String[] delimitedListToStringArray(String str, String delimiter, String charsToDelete) {
		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] {str};
		}
		List<String> result = new ArrayList<String>();
		if ("".equals(delimiter)) {
			for (int i = 0; i < str.length(); i++) {
				result.add(deleteAny(str.substring(i, i + 1), charsToDelete));
			}
		}
		else {
			int pos = 0;
			int delPos;
			while ((delPos = str.indexOf(delimiter, pos)) != -1) {
				result.add(deleteAny(str.substring(pos, delPos), charsToDelete));
				pos = delPos + delimiter.length();
			}
			if (str.length() > 0 && pos <= str.length()) {
				// Add rest of String, but not in case of empty input.
				result.add(deleteAny(str.substring(pos), charsToDelete));
			}
		}
		return toStringArray(result);
	}
    
	/**
	 * 将一个集合串联为字符串
	 * String. E.g. useful for {@code toString()} implementations.
	 * @param coll 被串联的集合
	 * @param delim t间隔符，比如","
	 * @param prefix 每个元素的前串
	 * @param suffix 每个元素的后串
	 * @return 串联后的字符串
	 */
	public static String collectionToDelimitedString(Collection<?> coll, String delim, String prefix, String suffix) {
		if (CollectionUtils.isEmpty(coll)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Iterator<?> it = coll.iterator();
		while (it.hasNext()) {
			sb.append(prefix).append(it.next()).append(suffix);
			if (it.hasNext()) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 将一个集合串联为字符串
	 * String. E.g. useful for {@code toString()} implementations.
	 * @param coll 被串联的集合
	 * @param delim t间隔符，比如","
	 * @param prefix 每个元素的前串
	 * @param suffix 每个元素的后串
	 * @return 串联后的字符串
	 */
	public static String collectionToDelimitedString(String delim, String prefix, String suffix,String... coll ) {
		if (ObjectUtils.isEmpty(coll)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0;;i++){
			sb.append(prefix).append(coll[i]).append(suffix);
			if (i == coll.length-1) break;
			sb.append(delim);
		}
		return sb.toString();
	}
	
	/**
	 * 将一个集合串联为字符串
	 * String. E.g. useful for {@code toString()} implementations.
	 * @param coll 被串联的集合
	 * @param delim t间隔符，比如","
	 * @param prefix 每个元素的前串
	 * @param suffix 每个元素的后串
	 * @return 串联后的字符串
	 */
	public static String collectionToDelimitedString(String delim, String prefix, String suffix,Object... coll ) {
		if (ObjectUtils.isEmpty(coll)) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0;;i++){
			sb.append(prefix).append(coll[i]).append(suffix);
			if (i == coll.length-1) break;
			sb.append(delim);
		}
		return sb.toString();
	}
	
	/**
	 * 将一个集合串联为字符串
	 * String. E.g. useful for {@code toString()} implementations.
	 * @param coll 被串联的集合
	 * @param delim t间隔符，比如","
	 * @param prefix 每个元素的前串
	 * @param suffix 每个元素的后串
	 * @return 串联后的字符串
	 */
	public static String collectionToDelimitedString(String delim, String prefix, String suffix,int... coll ) {
		if (coll == null || coll.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0;;i++){
			sb.append(prefix).append(coll[i]).append(suffix);
			if (i == coll.length-1) break;
			sb.append(delim);
		}
		return sb.toString();
	}
	
	/**
	 * 将一个集合串联为字符串
	 * String. E.g. useful for {@code toString()} implementations.
	 * @param coll 被串联的集合
	 * @param delim t间隔符，比如","
	 * @return 串联后的字符串
	 */
	public static String collectionToDelimitedString(Collection<?> coll, String delim) {
		return collectionToDelimitedString(coll, delim, "", "");
	}
	
	/**
	 * 将一个集合串联为字符串
	 * String. E.g. useful for {@code toString()} implementations.
	 * @param coll 被串联的集合
	 * @param delim t间隔符，比如","
	 * @return 串联后的字符串
	 */
	public static String collectionToDelimitedString( String delim,String... coll) {
		return collectionToDelimitedString( delim, "", "",coll);
	}
	
	/**
	 * 将一个集合串联为字符串
	 * String. E.g. useful for {@code toString()} implementations.
	 * @param coll 被串联的集合
	 * @param delim t间隔符，比如","
	 * @return 串联后的字符串
	 */
	public static String collectionToDelimitedString( String delim,Object... coll) {
		return collectionToDelimitedString( delim, "", "",coll);
	}
	
	/**
	 * 将一个集合串联为字符串
	 * String. E.g. useful for {@code toString()} implementations.
	 * @param coll 被串联的集合
	 * @param delim t间隔符，比如","
	 * @return 串联后的字符串
	 */
	public static String collectionToDelimitedString( String delim,int... coll) {
		return collectionToDelimitedString( delim, "", "",coll);
	}
	/**
	 * 将一个集合串联为字符串间隔符默认为","
	 * @param coll 被串联的集合
	 * @return 串联后的字符串
	 */
	public static String collectionToCommaDelimitedString(Collection<?> coll) {
		return collectionToDelimitedString(coll, ",");
	}
	
	/**
	 * 将一个集合串联为字符串间隔符默认为","
	 * @param coll 被串联的集合
	 * @return 串联后的字符串
	 */
	public static String collectionToCommaDelimitedString(String... coll) {
		return collectionToDelimitedString(",",coll );
	}
	
	/**
	 * 将一个集合串联为字符串间隔符默认为","
	 * @param coll 被串联的集合
	 * @return 串联后的字符串
	 */
	public static String collectionToCommaDelimitedString(Object... coll) {
		return collectionToDelimitedString(",",coll );
	}
	
	/**
	 * 将一个集合串联为字符串间隔符默认为","
	 * @param coll 被串联的集合
	 * @return 串联后的字符串
	 */
	public static String collectionToCommaDelimitedString(int... coll) {
		return collectionToDelimitedString(",",coll );
	}
	
    /**  
     * Compare two paths after normalization of them.  
     * @param path1 first path for comparison  
     * @param path2 second path for comparison  
     * @return whether the two paths are equivalent after normalization  
     */  
    public static boolean pathEquals(String path1, String path2) {  
        return cleanPath(path1).equals(cleanPath(path2));  
    }  
  
      
  
        //串��是否是有效路径locale的语法是locale -O 64 -a | -m | -c -k Name ...   
    private static void validateLocalePart(String localePart) {  
        for (int i = 0; i < localePart.length(); i++) {  
            char ch = localePart.charAt(i);  
                       //串��当前字符   
            if (ch != '_' && ch != ' ' && !Character.isLetterOrDigit(ch)) {  
                throw new IllegalArgumentException(  
                        "Locale part \"" + localePart + "\" contains invalid characters");  
            }  
        }  
    }  
  
  
    //---------------------------------------------------------------------  
    // Convenience methods for working with String arrays  
    //---------------------------------------------------------------------  
  
    /**  
     * Append the given String to the given String array, returning a new array  
         *   
     */  
    public static String[] addStringToArray(String[] array, String str) {  
                //如果arry==null或串里面没有元素  
        if (ObjectUtils.isEmpty(array)) {  
            return new String[] {str};  
        }  
                //扩展串��新数串 
        String[] newArr = new String[array.length + 1];  
                //把array内容复制到newArr里面  
        System.arraycopy(array, 0, newArr, 0, array.length);  
                //把str添加到数组末串 
        newArr[array.length] = str;  
                //返回新数串 
        return newArr;  
    }  
  
    /**  
         * 合并两个数组，直接无条件合并，即使两个数组有重复的元串 
         *  array1空则返回array2 ，array2空则返回array1  
     */  
    public static String[] concatenateStringArrays(String[] array1, String[] array2) {  
        if (ObjectUtils.isEmpty(array1)) {  
            return array2;  
        }  
        if (ObjectUtils.isEmpty(array2)) {  
            return array1;  
        }  
                //创建串��新数串 
        String[] newArr = new String[array1.length + array2.length];  
                //数据复制  
        System.arraycopy(array1, 0, newArr, 0, array1.length);  
        System.arraycopy(array2, 0, newArr, array1.length, array2.length);  
                //返回串��新数串 
        return newArr;  
    }  
  
    /**  
         *合并两个数组，如果两个数组有重复元素的话，只选择串��合并即可  
     */  
    public static String[] mergeStringArrays(String[] array1, String[] array2) {  
                //如果array1空的话，返回array2  
        if (ObjectUtils.isEmpty(array1)) {  
            return array2;  
        }  
                //如果array2空的话，返回array1  
        if (ObjectUtils.isEmpty(array2)) {  
            return array1;  
        }  
                //定义串��array链表  
        List<String> result = new ArrayList<String>();  
                //先装array1  
        result.addAll(Arrays.asList(array1));  
                //把array2跟array1不同的元素装入链串 
        for (String str : array2) {  
            if (!result.contains(str)) {  
                result.add(str);  
            }  
        }  
                  
        return toStringArray(result);  
    }  
  
    /**  
     * Turn given source String array into sorted array.  
     * @param array the source array  
     * @return the sorted array (never <code>null</code>)  
     */  
    public static String[] sortStringArray(String[] array) {  
        if (ObjectUtils.isEmpty(array)) {  
            return new String[0];  
        }  
        Arrays.sort(array);  
        return array;  
    }  
  
    /**  
         * 把集合转化为数组  
     */  
    public static String[] toStringArray(Collection<String> collection) {  
            //边界处理  
        if (collection == null) {  
            return null;  
        }  
                //toArray(T[] a)把list里面的元素放入a中，并返回a  
        return collection.toArray(new String[collection.size()]);  
    }  
  
    /**  
          *把Enumeration类型转化为数串 
     */  
    public static String[] toStringArray(Enumeration<String> enumeration) {  
        if (enumeration == null) {  
            return null;  
        }  
                //先转换为list  
        List<String> list = Collections.list(enumeration);  
                //toArray(T[] a)把list里面的元素放入a中，并返回a  
        return list.toArray(new String[list.size()]);  
    }  
  
    /**  
         *选择 字符数组array中首部或者尾部都是空白的元素（字符串串去掉其空串 
     */  
    public static String[] trimArrayElements(String[] array) {  
                //如果array为空，则返回长度串的数串 
        if (ObjectUtils.isEmpty(array)) {  
            return new String[0];  
        }  
                //建立串��length为array.length的数组，其实具体实现上没这个必要  
        String[] result = new String[array.length];  
        for (int i = 0; i < array.length; i++) {  
                       //获取当前元素  
            String element = array[i];  
                        //如果当前元素不为空，则返回经过trim处理的字符串  
                        //trim()此字符串移除了前导和尾部空白的副本，如果没有前导和尾部空白，则返回此字符串串  
                        //直接array[i] = (element != null ? element.trim() : null);也可串 
            result[i] = (element != null ? element.trim() : null);  
        }  
                //返回串��新数串 
        return result;  
    }  
  
    /**  
         *去掉数组中的重复的元串 
         * 方法：遍历数组，把元素加入set里自动过滤掉重复的元素，由于使用set，导致处理过的数串 
         * 是排好序的数串 
     */  
    public static String[] removeDuplicateStrings(String[] array) {  
                //如果数组为空，直接返回array  
        if (ObjectUtils.isEmpty(array)) {  
            return array;  
        }  
        Set<String> set = new TreeSet<String>();  
                //循环遍历数组，把数组元素加入到set串 
        for (String element : array) {  
            set.add(element);  
        }  
                //把set转化为数串 
        return toStringArray(set);  
    }  
  
    /**  
          *把一个字符串分按照delimiter分割成两个子字符串，组成数组返回  
     */  
    public static String[] split(String toSplit, String delimiter) {  
               //边界处理。个人认为该边界处理的有问题，如果toSplit不为空串delimiter为空的话，返回的串��是原来的字符串组成的  
              //长度为一的数串new String[]{toSplit}，可该做法直接返回了空串  
        if (!hasLength(toSplit) || !hasLength(delimiter)) {  
            return null;  
        }  
                //获得delimiter的位串 
        int offset = toSplit.indexOf(delimiter);  
        if (offset < 0) {//此时不符合要串 
            return null;  
        }  
                //获得在delimiter之前的子字符串 
        String beforeDelimiter = toSplit.substring(0, offset);  
                //获得在delimiter之后的子字符串 
        String afterDelimiter = toSplit.substring(offset + delimiter.length());  
                //组成数组返回  
        return new String[] {beforeDelimiter, afterDelimiter};  
    }  
  
    /**  
     * Take an array Strings and split each element based on the given delimiter.  
     * A <code>Properties</code> instance is then generated, with the left of the  
     * delimiter providing the key, and the right of the delimiter providing the value.  
     * <p>Will trim both the key and value before adding them to the  
     * <code>Properties</code> instance.  
     * @param array the array to process  
     * @param delimiter to split each element using (typically the equals symbol)  
     * @param charsToDelete one or more characters to remove from each element  
     * prior to attempting the split operation (typically the quotation mark  
     * symbol), or <code>null</code> if no removal should occur  
     * @return a <code>Properties</code> instance representing the array contents,  
     * or <code>null</code> if the array to process was <code>null</code> or empty  
     */  
    public static Properties splitArrayElementsIntoProperties(  
            String[] array, String delimiter, String charsToDelete) {  
  
        if (ObjectUtils.isEmpty(array)) {  
            return null;  
        }  
        Properties result = new Properties();  
        for (String element : array) {  
            if (charsToDelete != null) {  
                element = deleteAny(element, charsToDelete);  
            }  
            String[] splittedElement = split(element, delimiter);  
            if (splittedElement == null) {  
                continue;  
            }  
            result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());  
        }  
        return result;  
    }  
  
    /**  
     * Tokenize the given String into a String array via a StringTokenizer.  
     * Trims tokens and omits empty tokens.  
     * <p>The given delimiters string is supposed to consist of any number of  
     * delimiter characters. Each of those characters can be used to separate  
     * tokens. A delimiter is always a single character; for multi-character  
     * delimiters, consider using <code>delimitedListToStringArray</code>  
     * @param str the String to tokenize  
     * @param delimiters the delimiter characters, assembled as String  
     * (each of those characters is individually considered as delimiter).  
     * @return an array of the tokens  
     * @see StringTokenizer
     * @see String#trim()
     * @see #delimitedListToStringArray  
     */  
    public static String[] tokenizeToStringArray(String str, String delimiters) {  
        return tokenizeToStringArray(str, delimiters, true, true);  
    }  
  
    /**  
     * Tokenize the given String into a String array via a StringTokenizer.  
     * <p>The given delimiters string is supposed to consist of any number of  
     * delimiter characters. Each of those characters can be used to separate  
     * tokens. A delimiter is always a single character; for multi-character  
     * delimiters, consider using <code>delimitedListToStringArray</code>  
     * @param str the String to tokenize  
     * @param delimiters the delimiter characters, assembled as String  
     * (each of those characters is individually considered as delimiter)  
     * @param trimTokens trim the tokens via String's <code>trim</code>  
     * @param ignoreEmptyTokens omit empty tokens from the result array  
     * (only applies to tokens that are empty after trimming; StringTokenizer  
     * will not consider subsequent delimiters as token in the first place).  
     * @return an array of the tokens (<code>null</code> if the input String  
     * was <code>null</code>)  
     * @see StringTokenizer
     * @see String#trim()
     * @see #delimitedListToStringArray  
     */  
    public static String[] tokenizeToStringArray(  
            String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {  
  
        if (str == null) {  
            return null;  
        }  
        StringTokenizer st = new StringTokenizer(str, delimiters);  
        List<String> tokens = new ArrayList<String>();  
        while (st.hasMoreTokens()) {  
            String token = st.nextToken();  
            if (trimTokens) {  
                token = token.trim();  
            }  
            if (!ignoreEmptyTokens || token.length() > 0) {  
                tokens.add(token);  
            }  
        }  
        return toStringArray(tokens);  
    }  
  
  
  
    public static String arrayToDelimitedString(Object[] arr, String delim) {  
               //边界处理  
        if (ObjectUtils.isEmpty(arr)) {  
            return "";  
        }  
        if (arr.length == 1) {  
                       //把一个对象arr[0]通过调用nullSafeToString转化为String  
            return ObjectUtils.nullSafeToString(arr[0]);
        }  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < arr.length; i++) {  
            if (i > 0) {  
                sb.append(delim);  
            }  
            sb.append(arr[i]);  
        }  
        return sb.toString();  
    }
    
    /**
     * 将原来编码为fromCharset的字符串转化为编码为toCharset的字符串
     * @param str
     * @param fromCharset
     * @param toCharset
     * @return"ISO-8859-1"  "UTF-8"
     */
    public static String changeCode(String str,String fromCharset,String toCharset){
       try {
    	   str = new String(str.getBytes(fromCharset), toCharset);
       	} catch (Exception e) {
       		e.printStackTrace();
       	}
       return str;
    }
    
    /**
     * 将输入流转化为字符串
     * @param in
     * @return
     */
    public static String copyToString(InputStream in){
    	String str = null;
    	try {
    		str = StreamUtils.copyToString(in, Charset.forName(CharsetCode.CHARSET_UTF));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	return str;
    }
    
    /**
     * 该方法的作用是,将含下划线的字符串转为驼峰命名.如"user_type"转为驼峰以后userType
     * @param param 
     * @return
     */
    public static String underlineToCamel(String param){  
        if (param==null||"".equals(param.trim())){  
            return "";  
        }
        //如果以t_,c_开头
        if (param.startsWith("t_") || param.startsWith("c_")){
        	param = param.substring(2, param.length());
        	System.out.println(param);
        }
        
        StringBuilder sb = new StringBuilder(param);  
        Matcher mc = Pattern.compile("_").matcher(param);  
        int i = 0;  
        while (mc.find()){  
            int position = mc.end()-(i++);
            sb.replace(position-1,position+1,sb.substring(position,position+1).toUpperCase());  
        }

        return sb.toString();  
    }
    
    public static void main(String[] args) {
		String param = "t_sda_c_da";
		param = underlineToCamel(param);
		System.out.println(param);
	}
}  