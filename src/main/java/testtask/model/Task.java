package testtask.model;

import lombok.Data;

@Data
public class Task {
    private long id;
    private int reward;
    private boolean complete;
}
