package com.aman.SpringSecurity.SpringSecurity.Entity;

import com.aman.SpringSecurity.SpringSecurity.Entity.Enums.Subscriptions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users users;

    @Enumerated(EnumType.STRING)
    private Subscriptions plan;

    @Column(nullable = false)
    @ColumnDefault("1")
    private Integer activeSession;

}
