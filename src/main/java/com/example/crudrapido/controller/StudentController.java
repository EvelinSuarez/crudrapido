package com.example.crudrapido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.example.crudrapido.entity.Student;
import com.example.crudrapido.service.StudentService;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    // listar estudiantes
    @RequestMapping({ "/students", "/" })
    public String students(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("students", studentService.getStudents());
        return "students";
    }

    // agregar estudiante
    @GetMapping("/students/nuevo")
    public String crearStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "crear_student";
    }

    // actualizar estudiante
    @GetMapping("/students/update/{id}")
    public String UpdateStudent(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudent(id));
        return "editar_student";
    }
    
    // Registrar
    @PostMapping("/students/nuevo")
    public String guardarStudent(@ModelAttribute("student") Student student) {
        studentService.saveOrUpdate(student);
        return "redirect:/students";
    }

    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student,
            Model model) { 
        Student studentExistente = studentService.getStudent(id);
        studentExistente.setId(id);
        studentExistente.setFirstName(student.getFirstName());
        studentExistente.setLastName(student.getLastName());
        studentExistente.setEmail(student.getEmail());

        studentService.saveOrUpdate(studentExistente);
        return "redirect:/students";
    }

    //Consultar
    @PostMapping("/students/search")
    public String searchStudent(@ModelAttribute("student") Student student, Model model) {
        Student studentAlone = new Student();
        try {
            Student findStudent = studentService.getStudent(student.getId());
            if (findStudent != studentAlone) {
                model.addAttribute("student", findStudent);
                return "students";
            }else{
                return "students";
            }
        } catch (Exception e) {
            // return ("Error: " + e);
            return "students";
        }
    }



    //Delete
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return "redirect:/students";
    } 
}
