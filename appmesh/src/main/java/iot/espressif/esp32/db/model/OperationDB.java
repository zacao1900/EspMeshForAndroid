package iot.espressif.esp32.db.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;
import io.objectbox.annotation.Unique;

@Entity
public class OperationDB {
    @Id
    public long id;

    public String type;

    @Unique
    @Index
    public String identity;

    public long time;
}
