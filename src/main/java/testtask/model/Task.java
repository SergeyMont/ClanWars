package testtask.model;

import lombok.Data;

@Data
public class Task {
    private int id;
    private String name;
    private int reward;
    private boolean complete;
}
