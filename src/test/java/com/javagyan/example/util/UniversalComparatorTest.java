package com.javagyan.example.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.javagyan.example.util.UniversalComparator.SortOrder;

public class UniversalComparatorTest {

    private class Department implements Comparable {
        private final String name;

        private final String head;

        private Department(final String name, final String head) {
            super();
            this.name = name;
            this.head = head;
        }

        public int compareTo(final Object o) {
            return this.name.compareTo(((Department) o).getName());
        }

        public String getHead() {
            return this.head;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Department [");
            if (this.head != null) {
                builder.append("head=").append(this.head).append(", ");
            }
            if (this.name != null) {
                builder.append("name=").append(this.name);
            }
            builder.append("]");
            return builder.toString();
        }

    }

    private class Employee {
        private final String name;

        private final Long Age;

        private final Double Salary;

        private Department department;

        private Employee(final String name, final Long age, final Double salary, final Department department) {
            super();
            this.name = name;
            this.Age = age;
            this.Salary = salary;
            this.department = department;
        }

        public Long getAge() {
            return this.Age;
        }

        public Department getDepartment() {
            return this.department;
        }

        public String getName() {
            return this.name;
        }

        public Double getSalary() {
            return this.Salary;
        }

        public void setDepartment(final Department department) {
            this.department = department;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Employee [");
            if (this.Age != null) {
                builder.append("Age=").append(this.Age).append(", ");
            }
            if (this.Salary != null) {
                builder.append("Salary=").append(this.Salary).append(", ");
            }
            if (this.department != null) {
                builder.append("department=").append(this.department).append(", ");
            }
            if (this.name != null) {
                builder.append("name=").append(this.name);
            }
            builder.append("]");
            return builder.toString();
        }

    }

    @Test
    public void testUniversalCompartor() {
        final List<Employee> employees = new ArrayList<Employee>();
        for (int i = 0; i < 10; i++) {
            employees.add(new Employee("A" + i, (i * 2l), (i * 10d), new Department("d" + i, "dh" + i)));
        }
        Collections.sort(employees, new UniversalComparator("getDepartment", SortOrder.DESC));

        for (final Employee e : employees) {
            System.out.println(e);

        }
        Assert.assertTrue(employees.get(0).getName().equals("A9"));
        Assert.assertTrue(employees.get(9).getName().equals("A0"));
    }
}
