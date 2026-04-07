package badsmells;

/*
 * Refactored smell: Shotgun Surgery
 *
 * What changed:
 * - Introduced CoursePresentation to centralize course title wording
 * - Let Course, Invoice, and Certificate use that single source of truth
 *
 * Why better:
 * A wording change now happens in one place instead of several classes, which
 * reduces scattered edits and the risk of inconsistent output.
 */
public class ShotgunSurgeryExample {

    static class CoursePresentation {
        private final String title;

        CoursePresentation(String title) {
            this.title = title;
        }

        public String courseLabel() {
            return "Course: " + title;
        }

        public String invoiceDescription() {
            return "Invoice for " + title;
        }

        public String certificateText() {
            return "Completed " + title;
        }
    }

    static class Course {
        private final CoursePresentation presentation;

        Course(CoursePresentation presentation) {
            this.presentation = presentation;
        }

        public String label() {
            return presentation.courseLabel();
        }
    }

    static class Invoice {
        private final CoursePresentation presentation;

        Invoice(CoursePresentation presentation) {
            this.presentation = presentation;
        }

        public String description() {
            return presentation.invoiceDescription();
        }
    }

    static class Certificate {
        private final CoursePresentation presentation;

        Certificate(CoursePresentation presentation) {
            this.presentation = presentation;
        }

        public String text() {
            return presentation.certificateText();
        }
    }

    public void clientCode() {
        CoursePresentation presentation = new CoursePresentation("Refactoring");
        Course course = new Course(presentation);
        Invoice invoice = new Invoice(presentation);
        Certificate certificate = new Certificate(presentation);

        System.out.println(course.label());
        System.out.println(invoice.description());
        System.out.println(certificate.text());
    }
}
