//Develop a Hibernate-based application to perform CRUD (Create, Read, Update, Delete) operations on a Student entity using Hibernate ORM with MySQL.
//Requirements:
//1.	Configure Hibernate using hibernate.cfg.xml.
//2.	Create an Entity class (Student.java) with attributes: id, name, and age.
//3.	Implement Hibernate SessionFactory to perform CRUD operations.
//4.	Test the CRUD functionality with sample data

//code-
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD//EN"
        "http://hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- JDBC connection settings -->
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/your_db</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">your_password</property>

        <!-- Hibernate settings -->
        <property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
        <property name="hibernate.hbm2ddl.auto">update</property>
        <property name="show_sql">true</property>

        <!-- Mapping -->
        <mapping class="com.example.Student"/>
    </session-factory>
</hibernate-configuration>

Student.java (Entity Class)
package com.example;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int age;

    // Constructors
    public Student() {}

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student { id=" + id + ", name='" + name + "', age=" + age + " }";
    }
}
(CRUD Operations)
package com.example;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class StudentDAO {
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void saveStudent(Student student) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(student);
        tx.commit();
        session.close();
    }

    public Student getStudent(int id) {
        Session session = sessionFactory.openSession();
        Student student = session.get(Student.class, id);
        session.close();
        return student;
    }

    public void updateStudent(Student student) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(student);
        tx.commit();
        session.close();
    }

    public void deleteStudent(Student student) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(student);
        tx.commit();
        session.close();
    }
}
(Test CRUD Operations)
package com.example;

public class Main {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();

        // Create
        Student student1 = new Student("Alice", 22);
        dao.saveStudent(student1);

        // Read
        Student fetchedStudent = dao.getStudent(student1.getId());
        System.out.println("Fetched: " + fetchedStudent);

        // Update
        fetchedStudent.setAge(23);
        dao.updateStudent(fetchedStudent);
        System.out.println("Updated: " + dao.getStudent(fetchedStudent.getId()));

        // Delete
        dao.deleteStudent(fetchedStudent);
        System.out.println("Deleted Student with ID: " + fetchedStudent.getId());
    }
}

Dependencies (Maven pom.xml)
<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.6.15.Final</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>
