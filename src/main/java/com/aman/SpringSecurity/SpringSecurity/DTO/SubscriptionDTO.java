package com.aman.SpringSecurity.SpringSecurity.DTO;

import com.aman.SpringSecurity.SpringSecurity.Entity.Users;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
public class SubscriptionDTO {
    private Long Id;
    private Users users;
    private String plan;
    private Integer activeSession;
}
