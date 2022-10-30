package testtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testtask.model.Clan;
import testtask.service.ClanService;
import testtask.service.ClanServiceInMemory;
import testtask.service.TaskService;
import testtask.service.UserAddGoldService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class HttpController {
    private static final int PORT = 8080;
    private HttpServer httpServer;
    final Logger log = LoggerFactory.getLogger(HttpController.class);

    public HttpController() throws IOException {
        this.httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        ClanService clanService = new ClanServiceInMemory();
        TaskService taskService = new TaskService(clanService);
        UserAddGoldService userAddGoldService = new UserAddGoldService(clanService);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        httpServer.createContext("/clans", clanHttpHandler(clanService, mapper));
        httpServer.createContext("/clans/task", taskHttpHandler(taskService));
        httpServer.createContext("/clans/user", userHttpHandler(userAddGoldService));
    }

    public void start() {
        log.info("Server starts on http://localhost:" + PORT);
        httpServer.start();
    }

    private HttpHandler clanHttpHandler(ClanService clanService, ObjectMapper objectMapper) {
        return exchange -> {
            try {
                switch (exchange.getRequestMethod()) {
                    case "GET":
                        String key = exchange.getRequestURI().getRawQuery();
                        int id = Integer.parseInt(key);
                        String response = objectMapper.writeValueAsString(clanService.getClan(id));
                        Headers headers = exchange.getResponseHeaders();
                        headers.set("Content-Type",
                                "application/json");
                        exchange.sendResponseHeaders(200, response.length());

                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                        break;
                    case "POST":
                        if (exchange.getRequestBody() != null) {
                            clanService.createClan(objectMapper.readValue(exchange.getRequestBody(),
                                    Clan.class));
                            exchange.sendResponseHeaders(201, 0);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    case "PUT":
                        if (exchange.getRequestBody() != null) {
                            clanService.updateClan(objectMapper.readValue(exchange.getRequestBody(),
                                    Clan.class));
                            exchange.sendResponseHeaders(204, 0);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    case "DELETE":
                        String k = exchange.getRequestURI().getRawQuery();
                        if (!k.isEmpty()) {
                            int clanId = Integer.parseInt(k);
                            clanService.deleteClan(clanId);
                            exchange.sendResponseHeaders(204, -1);
                        } else {
                            exchange.sendResponseHeaders(400, 0);
                        }
                        break;
                    default:
                        System.out.println("/clans ждёт HTTP-запрос, а получил " + exchange.getRequestMethod());
                        exchange.sendResponseHeaders(405, 0);
                }
            } finally {
                exchange.close();
            }
        };
    }

    private HttpHandler userHttpHandler(UserAddGoldService userService) {
        return exchange -> {
            try {
                switch (exchange.getRequestMethod()) {
                    case "PUT":
                        String query = exchange.getRequestURI().getRawQuery();
                        Map<String, String> result = queryToMap(query);
                        int userId = Integer.parseInt(result.get("userId"));
                        int clanId = Integer.parseInt(result.get("clanId"));
                        int gold = Integer.parseInt(result.get("gold"));
                        userService.addGoldToClan(userId, clanId, gold);
                        exchange.sendResponseHeaders(204, 0);
                        break;
                    default:
                        System.out.println("/clans/user ждёт HTTP-запрос, а получил " + exchange.getRequestMethod());
                        exchange.sendResponseHeaders(405, 0);
                }
            } finally {
                exchange.close();
            }

        };
    }

    private HttpHandler taskHttpHandler(TaskService taskService) {
        return exchange -> {
            try {
                switch (exchange.getRequestMethod()) {
                    case "PUT":
                        String query = exchange.getRequestURI().getRawQuery();
                        Map<String, String> result = queryToMap(query);
                        int clanId = Integer.parseInt(result.get("clanId"));
                        int taskId = Integer.parseInt(result.get("taskId"));
                        taskService.completeTask(clanId, taskId);
                        exchange.sendResponseHeaders(204, 0);
                        break;
                    default:
                        System.out.println("/clans/user ждёт HTTP-запрос, а получил " + exchange.getRequestMethod());
                        exchange.sendResponseHeaders(405, 0);
                }
            } finally {
                exchange.close();
            }

        };
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        if (query != null || !query.isEmpty()) {
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], entry[1]);
                } else {
                    result.put(entry[0], "");
                }
            }
        }
        return result;
    }
}
