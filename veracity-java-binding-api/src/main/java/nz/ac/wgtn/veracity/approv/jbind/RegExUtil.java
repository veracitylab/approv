package nz.ac.wgtn.veracity.approv.jbind;

import java.util.regex.Pattern;

/**
 * Utility to convert strings with wildcards to regex.
 * Note that the pattern syntax is simplified for valid java method / class names and descriptors.
 * @author jens dietrich
 */
public class RegExUtil {

    public static Pattern glob2regex(String glob) {
        String out = "^";
        for(int i = 0; i < glob.length(); ++i)
        {
            char c = glob.charAt(i);
            switch(c)
            {
                case '*': out += ".*"; break; // wildcard
                case '?': out += '.'; break;  // single char wildcard
                case '.': out += "\\."; break;  // package name delimiter
                case '$': out += "\\$"; break;  // used in inner class names
                case '(': out += "\\("; break;  // used in descriptors
                case ')': out += "\\)"; break;  // used in descriptors
                case '[': out += "\\["; break;  // used in descriptors
                default: out += c;
            }
        }
        out += '$';
        return Pattern.compile(out);
    }
}
