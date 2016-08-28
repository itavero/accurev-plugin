package hudson.plugins.accurev.parsers.output;

import hudson.plugins.accurev.AccurevLauncher.ICmdOutputParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * Filters the output of any command and just returns the last few lines.
 */
public final class ParseLastFewLines implements ICmdOutputParser<List<String>, Integer> {
    public List<String> parse(InputStream cmdOutput, Integer numberOfLines) throws 
            IOException {
        final LinkedList<String> result = new LinkedList<>();
        final Reader stringReader = new InputStreamReader(cmdOutput, Charset.defaultCharset());
        int linesRemainingBeforeWeAreFull = numberOfLines;
        String line;
        try (BufferedReader lineReader = new BufferedReader(stringReader)) {
            line = lineReader.readLine();
            while (line != null) {
                result.add(line);
                if (linesRemainingBeforeWeAreFull > 0) {
                    linesRemainingBeforeWeAreFull--;
                } else {
                    result.removeFirst();
                }
                line = lineReader.readLine();
            }
        }
        return result;
    }
}
