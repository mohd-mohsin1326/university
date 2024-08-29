CREATE TABLE IF NOT EXISTS professor (
    professorId INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(250),
    department VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS course (
    courseId INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(250),
    credits INTEGER,
    professorId INTEGER,
    FOREIGN KEY (professorId) REFERENCES professor(professorId)
);

CREATE TABLE IF NOT EXISTS student (
    studentId INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(250),
    email VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS course_student (
    courseId INTEGER,
    studentId INTEGER,
    PRIMARY KEY (courseId, studentId),
    FOREIGN KEY (courseId) REFERENCES course(courseId),
    FOREIGN KEY (studentId) REFERENCES student(studentId)
);
