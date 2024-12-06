package com.example.javaexpririence.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ForCycleWebSocketHandler extends TextWebSocketHandler {


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Conexión WebSocket establecida para el ciclo FOR");
        session.sendMessage(new TextMessage("Conexión establecida. Envíe la cantidad de iteraciones del ciclo FOR."));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        int iterations;

        try {
            iterations = Integer.parseInt(payload);
            session.sendMessage(new TextMessage("Número de iteraciones recibido: " + iterations));
        } catch (NumberFormatException e) {
            iterations = 10;
            session.sendMessage(new TextMessage("Número de iteraciones inválido. Usando valor por defecto: " + iterations));
        }

        int finalIterations = iterations;
        new Thread(() -> simulateForCycle(session, finalIterations)).start();
    }

    private void simulateForCycle(WebSocketSession session, int iterations) {
        try {
            session.sendMessage(new TextMessage("Iniciando simulación del ciclo FOR con " + iterations + " iteraciones."));

            for (int i = 1; i <= iterations; i++) {
                session.sendMessage(new TextMessage("Iteración " + i + " del ciclo FOR: ejecutando lógica del cuerpo del ciclo."));
                Thread.sleep(1000); // Simula la duración de cada iteración
            }

            session.sendMessage(new TextMessage("¡Ciclo FOR completado con éxito! Todas las iteraciones fueron ejecutadas."));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.sendMessage(new TextMessage("Error durante la ejecución del ciclo FOR: " + e.getMessage()));
            } catch (Exception ignored) {
            }
        }
    }
}
