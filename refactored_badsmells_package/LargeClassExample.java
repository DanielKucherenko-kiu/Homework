package badsmells;

import java.util.ArrayList;
import java.util.List;

/*
 * Refactored smell: Large Class
 *
 * What changed:
 * - Split the original broad class into smaller domain-focused components
 * - Grouped related state and behavior into enrollment, staffing, finance,
 *   support, facilities, website, and payroll services
 *
 * Why better:
 * Responsibilities are separated by area, which reduces class size, improves
 * cohesion, and makes future changes more localized.
 */
public class LargeClassExample {

    static class SchoolProfile {
        private final String schoolName;
        private final String address;

        SchoolProfile(String schoolName, String address) {
            this.schoolName = schoolName;
            this.address = address;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public String getAddress() {
            return address;
        }
    }

    static class EnrollmentOffice {
        private final List<String> students = new ArrayList<>();
        private final List<String> courses = new ArrayList<>();

        public void enrollStudent(String student) {
            students.add(student);
        }

        public void addCourse(String course) {
            courses.add(course);
        }
    }

    static class StaffingOffice {
        private final List<String> teachers = new ArrayList<>();

        public void hireTeacher(String teacher) {
            teachers.add(teacher);
        }
    }

    static class FinanceOffice {
        private double budget;

        public void chargeTuition(double amount) {
            budget += amount;
        }

        public void paySalary(double amount) {
            budget -= amount;
        }
    }

    static class HelpDesk {
        private int openTickets;

        public void openTicket() {
            openTickets++;
        }
    }

    static class WebsiteManager {
        private String websiteTheme;

        public void updateTheme(String theme) {
            websiteTheme = theme;
        }
    }

    static class TransportService {
        private String busSchedule;

        public void publishSchedule(String schedule) {
            busSchedule = schedule;
        }
    }

    static class CafeteriaService {
        private String cafeteriaMenu;

        public void publishMenu(String menu) {
            cafeteriaMenu = menu;
        }
    }

    static class PayrollService {
        private String payrollDay;

        public void setPayrollDay(String payrollDay) {
            this.payrollDay = payrollDay;
        }
    }

    public void clientCode() {
        SchoolProfile profile = new SchoolProfile("Future Academy", "Tbilisi");
        EnrollmentOffice enrollmentOffice = new EnrollmentOffice();
        StaffingOffice staffingOffice = new StaffingOffice();
        FinanceOffice financeOffice = new FinanceOffice();
        HelpDesk helpDesk = new HelpDesk();
        WebsiteManager websiteManager = new WebsiteManager();
        TransportService transportService = new TransportService();
        CafeteriaService cafeteriaService = new CafeteriaService();
        PayrollService payrollService = new PayrollService();

        enrollmentOffice.enrollStudent("Nino");
        staffingOffice.hireTeacher("Ms. Kapanadze");
        enrollmentOffice.addCourse("Refactoring");
        financeOffice.chargeTuition(2400);
        financeOffice.paySalary(1200);
        helpDesk.openTicket();
        websiteManager.updateTheme("blue");
        transportService.publishSchedule("Route A at 08:00");
        cafeteriaService.publishMenu("Soup and salad");
        payrollService.setPayrollDay("Friday");

    }
}
