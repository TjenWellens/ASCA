package eu.tjenwellens.lessenroosterchecker.elements;

/**
 *
 * @author Tjen
 */
public class TimeStampOverlapException extends Exception {
    public TimeStampOverlapException(Les les) {
        super("Overlap in les:" + les);
    }
}
