package eu.tjenwellens.lessenroosterchecker.comparev2;

/**
 *
 * @author Tjen
 */
public class CourseNotFixedException extends RuntimeException {
    public CourseNotFixedException(String courseName) {
        super("Course was not fixed: " + courseName);
    }
}
