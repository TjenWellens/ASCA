package eu.tjenwellens.lessenroosterchecker.comparev2;

/**
 *
 * @author Tjen
 */
class CourseNotFixedException extends RuntimeException {
    public CourseNotFixedException(Course course) {
        super("Course was not fixed: " + course.getName());
    }
}
