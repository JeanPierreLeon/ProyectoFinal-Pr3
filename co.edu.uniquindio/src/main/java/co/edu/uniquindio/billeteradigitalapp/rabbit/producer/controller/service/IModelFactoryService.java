package co.edu.uniquindio.billeteradigitalapp.rabbit.producer.controller.service;

public interface IModelFactoryService {
    void producirMensaje(String queue, String message);
}
