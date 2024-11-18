module co.edu.uniquindio.billeteradigitalapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mapstruct;
    requires java.desktop;
    requires java.logging;
    requires com.rabbitmq.client;


    opens co.edu.uniquindio.billeteradigitalapp to javafx.fxml;
    exports co.edu.uniquindio.billeteradigitalapp;

    opens co.edu.uniquindio.billeteradigitalapp.Model;
    exports co.edu.uniquindio.billeteradigitalapp.Model;
    opens co.edu.uniquindio.billeteradigitalapp.ViewController;
    exports co.edu.uniquindio.billeteradigitalapp.ViewController;
    opens co.edu.uniquindio.billeteradigitalapp.Controller;
    exports co.edu.uniquindio.billeteradigitalapp.Controller;
    exports co.edu.uniquindio.billeteradigitalapp.Mappings.Dto;
    exports co.edu.uniquindio.billeteradigitalapp.Mappings.Mapper;
    opens co.edu.uniquindio.billeteradigitalapp.Exceptions;
    exports co.edu.uniquindio.billeteradigitalapp.Exceptions;
}